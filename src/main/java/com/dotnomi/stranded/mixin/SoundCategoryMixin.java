package com.dotnomi.stranded.mixin;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.sound.ModSoundCategories;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("SameParameterValue")
@Mixin(SoundCategory.class)
public class SoundCategoryMixin {
  @Invoker("<init>")
  private static SoundCategory createSoundCategory(String internalName, int internalId, String name) {
    throw new AssertionError();
  }

  @Shadow
  @Final
  @Mutable
  private static SoundCategory[] field_15255;

  @Inject(method = "<clinit>", at = @At("RETURN"))
  private static void cinit(CallbackInfo callbackInfo) {
    ArrayList<SoundCategory> soundCategories = new ArrayList<>(Arrays.asList(field_15255));
    try {
      ModSoundCategories.STRANDED_VOICEOVER.set(addSoundCategory(soundCategories, "voiceover"));
    } catch (Exception exception) {
      Stranded.LOGGER.error("Failed to add custom sound category", exception);
    }
    field_15255 = soundCategories.toArray(new SoundCategory[0]);
  }

  @Unique
  private static SoundCategory addSoundCategory(ArrayList<SoundCategory> soundCategories, String name) {
    SoundCategory soundCategory = createSoundCategory(
      name.toUpperCase(),
      soundCategories.getLast().ordinal() + 1,
      name.toLowerCase()
    );
    soundCategories.add(soundCategory);
    return soundCategory;
  }
}
