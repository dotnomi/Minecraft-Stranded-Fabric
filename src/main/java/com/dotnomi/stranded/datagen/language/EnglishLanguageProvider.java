package com.dotnomi.stranded.datagen.language;

import com.dotnomi.stranded.config.ModConfig;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class EnglishLanguageProvider extends FabricLanguageProvider {
  public EnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, "en_us", registryLookup);
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
    // Items
    translationBuilder.add("item.stranded.acrylite", "Acrylite");
    translationBuilder.add("item.stranded.raw_titanium", "Raw Titanium");
    translationBuilder.add("item.stranded.titanium_ingot", "Titanium Ingot");
    translationBuilder.add("item.stranded.polymer_resin", "Polymer Resin");
    translationBuilder.add("item.stranded.raw_lithium", "Raw Lithium");
    translationBuilder.add("item.stranded.sulphur", "Sulphur");

    // Blocks
    translationBuilder.add("block.stranded.titanium_foundation", "Titanium Foundation");
    translationBuilder.add("block.stranded.stranded_crafter", "Fabricator (Dummy)");

    // Config
    translationBuilder.add("stranded.midnightconfig.title", "Stranded on Mars Settings");
    translationBuilder.add("stranded.midnightconfig.category.miscellaneous", "Miscellaneous");
    translationBuilder.add("stranded.midnightconfig.isDebugModeEnabled", "Toggle Debug Mode");

    // Keybindings
    translationBuilder.add("key.category.stranded", "Stranded on Mars");
    translationBuilder.add("key.stranded.debug", "Execute Debug Functions");
    translationBuilder.add("key.stranded.skip-voiceover", "Skip Voiceover");

    // Miscellaneous
    translationBuilder.add("soundCategory.voiceover", "Stranded on Mars Voiceover");

    // Voiceover
    translationBuilder.add("hud.stranded.skip-voiceover", "Skip ({key})");
    translationBuilder.add("voiceover.stranded.landing_intro",
      "Welcome back, Pioneer. I must say, your survival is... unexpected." + ModConfig.TEXT_SEPARATOR +
      "But hey, let's not dwell on miracles. As you may have noticed, our ship has crash-landed on Mars." + ModConfig.TEXT_SEPARATOR +
      "Before you get any ideas, this is definitely not my fault." + ModConfig.TEXT_SEPARATOR +
      "In fact, I’m assigning all responsibility to you – after all, you're the one who’s supposed to be surviving here."
    );
  }
}
