package com.dotnomi.stranded.event.handler;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.event.ConfigChangedEvent;
import com.dotnomi.stranded.event.ConfigEntryType;

public class ConfigChangedEventHandler implements ConfigChangedEvent {
  @Override
  public void onConfigChanged(String modid, String fieldName, ConfigEntryType configEntryType, Object newValue, Object oldValue) {
    Stranded.LOGGER.info("{} changed from {} to {}", fieldName, oldValue, newValue);
  }
}
