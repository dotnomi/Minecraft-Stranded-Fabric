package com.dotnomi.stranded.logging;

import com.dotnomi.stranded.Stranded;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

public class LoggerConfig {
  public static void setDebugMode(boolean enableDebugMode) {
    if (enableDebugMode) {
      Configurator.setLevel("com.dotnomi.stranded", Level.DEBUG);
      Stranded.LOGGER.info("Debug mode enabled");
    } else {
      Configurator.setLevel("com.dotnomi.stranded", Level.INFO);
      Stranded.LOGGER.info("Debug mode disabled");
    }
  }
}
