package com.dotnomi.stranded.event.handler;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.config.ConfigEntryType;
import com.dotnomi.stranded.config.ModConfig;
import com.dotnomi.stranded.util.FieldUtils;
import com.dotnomi.stranded.event.ConfigChangedEvent;
import com.dotnomi.stranded.logging.LoggerConfig;

public class ConfigChangedEventHandler implements ConfigChangedEvent {
  @Override
  public void onConfigChanged(String modid, String fieldName, ConfigEntryType configEntryType, Object newValue, Object oldValue) {
    Stranded.LOGGER.debug("{} changed from {} to {}", fieldName, oldValue, newValue);
    changeLogLevel(configEntryType, fieldName, newValue);
  }

  private void changeLogLevel(ConfigEntryType configEntryType, String fieldName, Object newValue) {
    if (configEntryType != ConfigEntryType.COMMON) return;
    String debugModeFieldName = FieldUtils.getFieldName(ModConfig.class, ModConfig.isDebugModeEnabled);
    if (fieldName == null || debugModeFieldName == null) return;
    if (!debugModeFieldName.equals(fieldName)) return;
    if (newValue instanceof Boolean isDebugModeEnabled) {
      LoggerConfig.setDebugMode(Stranded.LOGGER.getName(), isDebugModeEnabled);
    }
  }
}
