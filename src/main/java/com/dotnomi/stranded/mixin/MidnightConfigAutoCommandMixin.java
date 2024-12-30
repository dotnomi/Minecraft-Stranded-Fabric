package com.dotnomi.stranded.mixin;

import com.dotnomi.stranded.Stranded;
import eu.midnightdust.lib.config.AutoCommand;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;

@Mixin(value = AutoCommand.class, remap = false)
public abstract class MidnightConfigAutoCommandMixin {
  @Final
  @Shadow
  String modid;

  @Final
  @Shadow
  Field field;

  @Inject(method = "setValue", at = @At("HEAD"))
  private void setValue(ServerCommandSource source, Object value, String action, CallbackInfoReturnable<Integer> cir) throws IllegalAccessException {
    Stranded.LOGGER.info("({}) [{}]: Setting value from {} to {}", StringUtils.capitalize(modid), field.getName(), field.get(null), value);
    //TODO: invoke Event
  }
}
