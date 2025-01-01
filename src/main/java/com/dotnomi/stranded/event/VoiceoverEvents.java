package com.dotnomi.stranded.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Arrays;

public final class VoiceoverEvents {
	public static final Event<StartVoiceover> START_VOICEOVER = EventFactory.createArrayBacked(StartVoiceover.class,
		listeners -> (voiceoverId) -> Arrays.stream(listeners).toList().forEach((listener) -> listener.onStartVoiceover(voiceoverId))
	);

	@FunctionalInterface
	@Environment(EnvType.CLIENT)
	public interface StartVoiceover {
		void onStartVoiceover(String voiceoverId);
	}
}