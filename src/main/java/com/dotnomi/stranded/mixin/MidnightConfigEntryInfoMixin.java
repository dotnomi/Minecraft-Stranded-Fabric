package com.dotnomi.stranded.mixin;

import com.dotnomi.stranded.Stranded;
import eu.midnightdust.lib.config.MidnightConfig;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(value = MidnightConfig.EntryInfo.class, remap = false)
public abstract class MidnightConfigEntryInfoMixin {
  @Shadow()
  String modid;

  @Shadow()
  Object value;

  @Shadow()
  Field field;

  @Inject(method = "setValue", at = @At("HEAD"))
  private void setValue(Object value, CallbackInfo ci) {
    Stranded.LOGGER.info("({}) [{}]: Setting value from {} to {}", StringUtils.capitalize(modid), field.getName(), this.value, value);
    //TODO: invoke Event
  }
}
