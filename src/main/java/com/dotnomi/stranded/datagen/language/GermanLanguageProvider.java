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

    // Config
    translationBuilder.add("stranded.midnightconfig.title", "Stranded on Mars Einstellungen");
    translationBuilder.add("stranded.midnightconfig.category.miscellaneous", "Sonstiges");
    translationBuilder.add("stranded.midnightconfig.isDebugModeEnabled", "Debug-Modus umschalten");

    // Voiceover
    translationBuilder.add("hud.stranded.skip-voiceover", "Überspringen ({key})");
    translationBuilder.add("voiceover.stranded.landing_intro",
      "Willkommen zurück, Pionier. Ich muss sagen, dein Überleben ist... unerwartet." + ModConfig.TEXT_SEPARATOR +
      "Aber hey, wir wollen uns nicht mit Wundern aufhalten. Wie du vielleicht bemerkt hast, hat unser Schiff eine Bruchlandung auf dem Mars hingelegt." + ModConfig.TEXT_SEPARATOR +
      "Bevor du auf dumme Gedanken kommst: Das war definitiv nicht meine Schuld." + ModConfig.TEXT_SEPARATOR +
      "Ich schiebe sogar die ganze Verantwortung auf dich - schließlich bist du derjenige, der hier überleben soll."
    );

    // Miscellaneous
    translationBuilder.add("soundCategory.voiceover", "Stranded on Mars Sprachausgabe");
  }
}
