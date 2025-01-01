package com.dotnomi.stranded;

import com.dotnomi.stranded.client.input.ModKeyBindings;
import com.dotnomi.stranded.client.voiceover.VoiceoverManager;
import com.dotnomi.stranded.config.ModConfig;
import com.dotnomi.stranded.event.ConfigChangedEvent;
import com.dotnomi.stranded.event.handler.ConfigChangedEventHandler;
import com.dotnomi.stranded.logging.LoggerConfig;
import com.dotnomi.stranded.sound.ModSounds;
import com.dotnomi.stranded.util.Lazy;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;

public class Stranded implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "stranded";
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final Lazy<MinecraftClient> CLIENT = new Lazy<>();
	public static final Lazy<VoiceoverManager> VOICEOVER_MANAGER = new Lazy<>();

	@Override
	public void onInitialize() {
		initializeConfig();
		Stranded.LOGGER.debug("Stranded Mod initialization...");

		ModSounds.register();

		Stranded.LOGGER.debug("Stranded Mod initialized successfully");
	}

	@Override
	public void onInitializeClient() {
		Stranded.LOGGER.debug("Stranded Client initialization...");

		CLIENT.set(MinecraftClient.getInstance());
		VOICEOVER_MANAGER.set(VoiceoverManager.getInstance());

		ModKeyBindings.register();

		Stranded.LOGGER.debug("Stranded Client initialized successfully");
	}

	private void initializeConfig() {
		MidnightConfig.init(MOD_ID, ModConfig.class);
		LoggerConfig.setDebugMode(LOGGER.getName(), ModConfig.isDebugModeEnabled);
		ConfigChangedEvent.EVENT.register(new ConfigChangedEventHandler());
	}
}