package com.dotnomi.stranded.config;

import eu.midnightdust.lib.config.MidnightConfig;

@SuppressWarnings("unused")
public class ModConfig extends MidnightConfig {
  public static final String TEXT_SEPARATOR = "\u0001";
  private static final String MISCELLANEOUS = "miscellaneous";

  @Entry(category = MISCELLANEOUS) public static boolean isDebugModeEnabled = false;

  @Client
  @Entry(category = MISCELLANEOUS) public static boolean clientOnly = false;

  @Server
  @Entry(category = MISCELLANEOUS) public static boolean serverOnly = false;
}
