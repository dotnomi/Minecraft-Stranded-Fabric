package com.dotnomi.stranded.datagen.language;

import com.dotnomi.stranded.config.ModConfig;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class GermanLanguageProvider extends FabricLanguageProvider {
  public GermanLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
    super(dataOutput, "de_de", registryLookup);
  }

  @Override
  public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
    // Items
    translationBuilder.add("item.stranded.acrylite", "Akrylit");
    translationBuilder.add("item.stranded.raw_titanium", "Rohes Titan");
    translationBuilder.add("item.stranded.titanium_ingot", "Titanbarren");
    translationBuilder.add("item.stranded.polymer_resin", "Polymer Harz");
    translationBuilder.add("item.stranded.raw_lithium", "Rohes Lithium");
    translationBuilder.add("item.stranded.sulphur", "Schwefel");

    // Blocks
    translationBuilder.add("block.stranded.titanium_foundation", "Titanium Fundament");
    translationBuilder.add("block.stranded.fabricator", "Verarbeiter");

    // Config
    translationBuilder.add("stranded.midnightconfig.title", "Stranded on Mars Einstellungen");
    translationBuilder.add("stranded.midnightconfig.category.miscellaneous", "Sonstiges");
    translationBuilder.add("stranded.midnightconfig.isDebugModeEnabled", "Debug-Modus umschalten");

    // Keybindings
    translationBuilder.add("key.category.stranded", "Stranded on Mars");
    translationBuilder.add("key.stranded.debug", "Debug Funktionen ausführen");
    translationBuilder.add("key.stranded.skip-voiceover", "Überspringe Sprachausgabe");

    // Miscellaneous
    translationBuilder.add("soundCategory.voiceover", "Stranded on Mars Sprachausgabe");

    // Fabricator Recipes
    translationBuilder.add("fabricator.recipe.iron_ingot", "Eisenbarren");
    translationBuilder.add("fabricator.recipe.iron_nuggets", "Eisenklumpen");
    translationBuilder.add("fabricator.recipe.netherite_ingot", "Netheritbarren");
    translationBuilder.add("fabricator.recipe.diorite", "Diorit");
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
    translationBuilder.add("hud.stranded.skip-voiceover", "Überspringen ({key})");
    translationBuilder.add("voiceover.stranded.landing_intro",
      "Willkommen zurück, Pionier. Ich muss sagen, dein Überleben ist... unerwartet." + ModConfig.TEXT_SEPARATOR +
      "Aber hey, wir wollen uns nicht mit Wundern aufhalten. Wie du vielleicht bemerkt hast, hat unser Schiff eine Bruchlandung auf dem Mars hingelegt." + ModConfig.TEXT_SEPARATOR +
      "Bevor du auf dumme Gedanken kommst: Das war definitiv nicht meine Schuld." + ModConfig.TEXT_SEPARATOR +
      "Ich schiebe sogar die ganze Verantwortung auf dich - schließlich bist du derjenige, der hier überleben soll."
    );
  }
}
