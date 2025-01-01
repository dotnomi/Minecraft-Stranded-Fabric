package com.dotnomi.stranded.sound;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.util.Lazy;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ModSounds {
	public static final Lazy<List<SoundEvent>> VOICEOVER_LANDING_INTRO = new Lazy<>();

	public static void register() {
		VOICEOVER_LANDING_INTRO.set(registerVoiceoverSoundEvents("voiceover_landing_intro", 4));
	}

	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = Identifier.of(Stranded.MOD_ID, name);
		Stranded.LOGGER.debug("Registered sound event: {}", id);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	private static List<SoundEvent> registerVoiceoverSoundEvents(String name, int count) {
		List<SoundEvent> soundEvents = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			soundEvents.add(registerSoundEvent(name + "_" + i));
		}
		return soundEvents;
	}
}
