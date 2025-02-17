package com.dotnomi.stranded.sound;

import com.dotnomi.stranded.Stranded;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SameParameterValue", "unused"})
public class ModSounds {
	public static final List<SoundEvent> VOICEOVER_LANDING_INTRO = registerVoiceover("voiceover_landing_intro", 4);
	public static final SoundEvent FABRICATOR_USE = register("fabricator_use");

	public static void initialize() {}

	private static SoundEvent register(String name) {
		Identifier id = Identifier.of(Stranded.MOD_ID, name);
		Stranded.LOGGER.debug("Registered sound event: {}", id);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	private static List<SoundEvent> registerVoiceover(String name, int count) {
		List<SoundEvent> soundEvents = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			soundEvents.add(register(name + "_" + i));
		}
		return soundEvents;
	}
}
