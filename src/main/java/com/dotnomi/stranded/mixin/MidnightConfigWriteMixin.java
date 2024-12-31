package com.dotnomi.stranded.mixin;

import com.dotnomi.stranded.event.ConfigChangedEvent;
import com.dotnomi.stranded.config.ConfigEntryType;
import eu.midnightdust.lib.config.MidnightConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Mixin(value = MidnightConfig.class, remap = false)
public class MidnightConfigWriteMixin {
  @Unique
  private static final Map<String, Object> previousValues = new HashMap<>();

  @Inject(method = "init", at = @At("TAIL"))
  private static void init(String modid, Class<? extends MidnightConfig> config, CallbackInfo ci) {
    MidnightConfig configInstance = MidnightConfig.getClass(modid);

    for (Field field : configInstance.getClass().getFields()) {
      if (isEntry(field)) {
        try {
          previousValues.put(field.getName(), field.get(configInstance));
        } catch (IllegalAccessException ignored) {}
      }
    }
  }

  @Inject(method = "writeChanges", at = @At("HEAD"))
  private void writeChanges(String modid, CallbackInfo ci) {
    MidnightConfig configInstance = MidnightConfig.getClass(modid);

    for (Field field : configInstance.getClass().getFields()) {
      if (isEntry(field)) {
        ConfigEntryType configEntryType;
        if (isClientEntry(field) && !isServerEntry(field)) {
          configEntryType = ConfigEntryType.CLIENT;
        } else if (!isClientEntry(field) && isServerEntry(field)) {
          configEntryType = ConfigEntryType.SERVER;
        } else {
          configEntryType = ConfigEntryType.COMMON;
        }

        try {
          if (!Objects.equals(previousValues.get(field.getName()), field.get(configInstance))) {
            ConfigChangedEvent.EVENT.invoker().onConfigChanged(
              modid, field.getName(), configEntryType, field.get(configInstance), previousValues.get(field.getName())
            );
            previousValues.put(field.getName(), field.get(configInstance));
          }
        } catch (IllegalAccessException ignored) {}
      }
    }
  }

  @Unique
  private static boolean isEntry(Field field) {
    return field.isAnnotationPresent(MidnightConfig.Entry.class);
  }

  @Unique
  private static boolean isClientEntry(Field field) {
    return field.isAnnotationPresent(MidnightConfig.Client.class);
  }

  @Unique
  private static boolean isServerEntry(Field field) {
    return field.isAnnotationPresent(MidnightConfig.Server.class);
  }
}
