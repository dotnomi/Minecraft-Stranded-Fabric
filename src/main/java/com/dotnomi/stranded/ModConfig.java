package com.dotnomi.stranded;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModConfig extends MidnightConfig {
  private static final String MISCELLANEOUS = "miscellaneous";

  @Entry(category = MISCELLANEOUS) public static boolean isDebugModeEnabled = false;

  @Client
  @Entry(category = MISCELLANEOUS) public static boolean clientOnly = false;

  @Server
  @Entry(category = MISCELLANEOUS) public static boolean serverOnly = false;
}
