package com.dotnomi.stranded;

import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stranded implements ModInitializer {
	public static final String MOD_ID = "stranded";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.debug("Hello Fabric world!");
	}
}