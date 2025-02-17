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
    translationBuilder.add("block.stranded.fabricator", "Fabricator");

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

    // Fabricator Recipes
    translationBuilder.add("fabricator.recipe.iron_ingot", "Iron Ingot");
    translationBuilder.add("fabricator.recipe.iron_nuggets", "Iron Nuggets");
    translationBuilder.add("fabricator.recipe.netherite_ingot", "Netherite Ingot");
    translationBuilder.add("fabricator.recipe.diorite", "Diorite");
    translationBuilder.add("fabricator.recipe.dummy1", "Dummy 1");
    translationBuilder.add("fabricator.recipe.dummy2", "Dummy 2");
    translationBuilder.add("fabricator.recipe.dummy3", "Dummy 3");
    translationBuilder.add("fabricator.recipe.dummy4", "Dummy 4");
    translationBuilder.add("fabricator.recipe.dummy5", "Dummy 5");
    translationBuilder.add("fabricator.recipe.dummy6", "Dummy 6");
    translationBuilder.add("fabricator.recipe.dummy7", "Dummy 7");
    translationBuilder.add("fabricator.recipe.dummy8", "Dummy 8");
    translationBuilder.add("fabricator.recipe.dummy9", "Dummy 9");
    translationBuilder.add("fabricator.recipe.dummy10", "Dummy 10");

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
