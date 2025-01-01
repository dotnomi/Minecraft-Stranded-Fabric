package com.dotnomi.stranded.client.voiceover;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.sound.ModSoundCategories;
import com.dotnomi.stranded.util.SoundUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.registry.Registries;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Queue;

public class VoiceoverManager {
	private static final String VOICEOVER_SUBTITLE_SEPARATOR = "\u0001";

	private static volatile VoiceoverManager instance;

	private final Queue<Voiceover> voiceoverQueue = new ArrayDeque<>();
	private float currentVoiceoverDuration = 0.0f;
	private boolean hasVoiceoverEnded = false;
	private int subtitleCount = 0;

	private String currentSubtitle = null;
	private boolean isVisible = false;

	public static VoiceoverManager getInstance() {
		if (instance == null) {
			synchronized (VoiceoverManager.class) {
				if (instance == null) {
					instance = new VoiceoverManager();
					ClientTickEvents.END_CLIENT_TICK.register(instance::onClientTick);
				}
			}
		}
		return instance;
	}

	public void playVoiceover(String voiceoverId) {
		stopVoiceover();
		loadVoiceover(voiceoverId);

		if (!voiceoverQueue.isEmpty()) {
			isVisible = true;
			playNextVoiceover();
		}
	}

	public void skipCurrentVoiceover() {
		if (voiceoverQueue.isEmpty()) {
			return;
		}

		Voiceover currentVoiceover = voiceoverQueue.peek();
		Stranded.CLIENT.get().getSoundManager().stop(currentVoiceover.soundInstance());

		voiceoverQueue.poll();
		playNextVoiceover();
	}

	public void stopVoiceover() {
		Stranded.CLIENT.get().getSoundManager().stopSounds(null, ModSoundCategories.STRANDED_VOICEOVER.get());
		voiceoverQueue.clear();
		currentSubtitle = null;
		isVisible = false;
		subtitleCount = 0;
		currentVoiceoverDuration = 0.0f;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public String getCurrentSubtitle() {
		return currentSubtitle;
	}

	private void onClientTick(MinecraftClient minecraftClient) {
		if (voiceoverQueue.isEmpty()) {
			currentSubtitle = null;
			isVisible = false;
		}

		if (!voiceoverQueue.isEmpty() && hasVoiceoverEnded) {
			voiceoverQueue.poll();
			playNextVoiceover();
		}

		if (!voiceoverQueue.isEmpty() && currentVoiceoverDuration >= voiceoverQueue.peek().duration()) {
			currentVoiceoverDuration = 0.0f;
			hasVoiceoverEnded = true;
		} else if (!minecraftClient.isPaused()) {
			currentVoiceoverDuration += 0.05f;
			hasVoiceoverEnded = false;
		}
	}

	private void playNextVoiceover() {
		if (voiceoverQueue.isEmpty()) {
			return;
		}

		Voiceover voiceover = voiceoverQueue.peek();
		Stranded.CLIENT.get().getSoundManager().play(voiceover.soundInstance());

		Subtitle subtitle = voiceover.subtitle();
		if (subtitle != null && subtitle.index() < subtitleCount) {
			String translatedSubtitles = I18n.translate(subtitle.translationKey());
			String[] seperatedSubtitles = translatedSubtitles.split(VOICEOVER_SUBTITLE_SEPARATOR);

			currentSubtitle = seperatedSubtitles[subtitle.index()];
			hasVoiceoverEnded = false;
			currentVoiceoverDuration = 0.0f;
		}
	}

	private void loadVoiceover(String voiceoverId) {
		ResourceManager resourceManager = Stranded.CLIENT.get().getResourceManager();
		Stranded.LOGGER.info("Loading voiceover: {}", voiceoverId);
		Identifier voiceoverIdentifier = Identifier.of(Stranded.MOD_ID, "voiceover/" + voiceoverId + ".json");
		Gson gson = new Gson();

		JsonObject voiceoverJson;
		try {
			Resource resource = resourceManager.getResource(voiceoverIdentifier).orElseThrow(() -> new RuntimeException(voiceoverId + ".json not found"));
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
				voiceoverJson = gson.fromJson(reader, JsonObject.class);
			}
		} catch (Exception exception) {
			Stranded.LOGGER.error("Failed to load voiceover file: {}.json", voiceoverId, exception);
			return;
		}

		String translatedSubtitles = I18n.translate(voiceoverJson.get("subtitle").getAsString());
		subtitleCount = translatedSubtitles.split(VOICEOVER_SUBTITLE_SEPARATOR).length;

		JsonArray jsonSounds = voiceoverJson.getAsJsonArray("sounds");
		for (int subtitleIndex = 0; subtitleIndex < jsonSounds.size(); subtitleIndex++) {
			String soundEventId = jsonSounds.get(subtitleIndex).getAsString();
			SoundEvent soundEvent = Registries.SOUND_EVENT.get(Identifier.of(Stranded.MOD_ID, soundEventId));

			if (soundEvent == null) {
				Stranded.LOGGER.error("Failed to load sound event: {}", soundEventId);
				return;
			}

			Voiceover voiceover = new Voiceover(
				getVoiceoverSoundInstance(soundEvent),
				SoundUtils.getSoundFileDuration(Identifier.of(Stranded.MOD_ID, "sounds/" + soundEventId + ".ogg")),
				new Subtitle(subtitleIndex, voiceoverJson.get("subtitle").getAsString())
			);

			voiceoverQueue.add(voiceover);
		}
	}

	private PositionedSoundInstance getVoiceoverSoundInstance(SoundEvent soundEvent) {
		return new PositionedSoundInstance(
			soundEvent.id(),
			ModSoundCategories.STRANDED_VOICEOVER.get(),
			1.0f,
			1.0f,
			SoundInstance.createRandom(),
			false,
			0,
			SoundInstance.AttenuationType.NONE,
			0.0,
			0.0,
			0.0,
			true
		);
	}
}
