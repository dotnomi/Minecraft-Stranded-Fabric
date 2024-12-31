package com.dotnomi.stranded.event;

import com.dotnomi.stranded.config.ConfigEntryType;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ConfigChangedEvent {
  Event<ConfigChangedEvent> EVENT = EventFactory.createArrayBacked(
    ConfigChangedEvent.class,
    listeners -> (modid, fieldName, configEntryType, newValue, oldValue) -> {
      for (ConfigChangedEvent listener : listeners) {
        listener.onConfigChanged(modid, fieldName, configEntryType, newValue, oldValue);
      }
    }
  );

  void onConfigChanged(String modid, String fieldName, ConfigEntryType configEntryType, Object newValue, Object oldValue);
}
