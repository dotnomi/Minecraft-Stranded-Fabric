package com.dotnomi.stranded;

import com.dotnomi.stranded.datagen.ModBlockTagProvider;
import com.dotnomi.stranded.datagen.ModItemTagProvider;
import com.dotnomi.stranded.datagen.ModLootTableProvider;
import com.dotnomi.stranded.datagen.ModModelProvider;
import com.dotnomi.stranded.datagen.ModRecipeProvider;
import com.dotnomi.stranded.datagen.language.EnglishLanguageProvider;
import com.dotnomi.stranded.datagen.language.GermanLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class StrandedDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		Stranded.LOGGER.debug("Stranded Mod data generation...");
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(EnglishLanguageProvider::new);
		pack.addProvider(GermanLanguageProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModItemTagProvider::new);
		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModRecipeProvider::new);
		Stranded.LOGGER.debug("Stranded Mod data generated successfully");
	}
}
