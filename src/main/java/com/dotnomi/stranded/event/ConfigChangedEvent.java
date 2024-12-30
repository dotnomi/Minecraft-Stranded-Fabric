package com.dotnomi.stranded.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ConfigChangedEvent {
  public static final Event<CommonConfigChanged> COMMON_CONFIG_CHANGED = EventFactory.createArrayBacked(
    CommonConfigChanged.class,
    listeners -> (modid, fieldName, oldValue, newValue) -> {
      for (CommonConfigChanged listener : listeners) {
        listener.onCommonConfigChanged(modid, fieldName, oldValue, newValue);
      }
    }
  );

  public static final Event<ClientConfigChanged> CLIENT_CONFIG_CHANGED = EventFactory.createArrayBacked(
    ClientConfigChanged.class,
    listeners -> (modid, fieldName, oldValue, newValue) -> {
      for (ClientConfigChanged listener : listeners) {
        listener.onClientConfigChanged(modid, fieldName, oldValue, newValue);
      }
    }
  );

  public static final Event<ServerConfigChanged> SERVER_CONFIG_CHANGED = EventFactory.createArrayBacked(
    ServerConfigChanged.class,
    listeners -> (modid, fieldName, oldValue, newValue) -> {
      for (ServerConfigChanged listener : listeners) {
        listener.onServerConfigChanged(modid, fieldName, oldValue, newValue);
      }
    }
  );

  @FunctionalInterface
  public interface CommonConfigChanged {
    void onCommonConfigChanged(String modid, String fieldName, Object oldValue, Object newValue);
  }

  @FunctionalInterface
  public interface ClientConfigChanged {
    void onClientConfigChanged(String modid, String fieldName, Object oldValue, Object newValue);
  }

  @FunctionalInterface
  public interface ServerConfigChanged {
    void onServerConfigChanged(String modid, String fieldName, Object oldValue, Object newValue);
  }
}
