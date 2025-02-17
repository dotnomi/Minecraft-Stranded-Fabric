package com.dotnomi.stranded.datagen;

import com.dotnomi.stranded.block.ModBlocks;
import com.dotnomi.stranded.item.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModModelProvider extends FabricModelProvider {
	public ModModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.TITANIUM_FOUNDATION);
		blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.FABRICATOR);
	}

	@Override
	public void generateItemModels(ItemModelGenerator itemModelGenerator) {
		itemModelGenerator.register(ModItems.ACRYLITE, Models.GENERATED);
		itemModelGenerator.register(ModItems.RAW_TITANIUM, Models.GENERATED);
		itemModelGenerator.register(ModItems.TITANIUM_INGOT, Models.GENERATED);
		itemModelGenerator.register(ModItems.POLYMER_RESIN, Models.GENERATED);
		itemModelGenerator.register(ModItems.RAW_LITHIUM, Models.GENERATED);
		itemModelGenerator.register(ModItems.SULPHUR, Models.GENERATED);
	}
}
