package com.dotnomi.stranded;

import com.dotnomi.stranded.event.ConfigChangedEvent;
import com.dotnomi.stranded.event.TestHandler;
import com.dotnomi.stranded.logging.LoggerConfig;
import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;

public class Stranded implements ModInitializer, ClientModInitializer {
	public static final String MOD_ID = "stranded";
	public static final Logger LOGGER = LogUtils.getLogger();

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, ModConfig.class);
		LoggerConfig.setDebugMode(LOGGER.getName(), ModConfig.isDebugModeEnabled);
		Stranded.LOGGER.debug("Stranded Mod initialization...");

		LOGGER.debug("DEBUG");
		LOGGER.info("INFO");
		LOGGER.warn("WARN");
		LOGGER.error("ERROR");
		Stranded.LOGGER.debug("Stranded Mod initialized successfully");
	}

	@Override
	public void onInitializeClient() {
		Stranded.LOGGER.debug("Stranded Client initialization...");

		ConfigChangedEvent.CLIENT_CONFIG_CHANGED.register(new TestHandler());

		//TODO: Do Stuff

		Stranded.LOGGER.debug("Stranded Client initialized successfully");
	}
}