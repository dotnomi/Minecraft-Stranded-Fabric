package com.dotnomi.stranded.block;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.item.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {
  public static final Block TITANIUM_FOUNDATION = register("titanium_foundation", AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
  public static final FabricatorBlock FABRICATOR = register("fabricator", FabricatorBlock::new, AbstractBlock.Settings.copy(Blocks.STONE));

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
      entries.add(TITANIUM_FOUNDATION);
      entries.add(FABRICATOR);
    });
  }

  public static RegistryKey<Block> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Stranded.MOD_ID, id));
  }

  public static Block register(String id, AbstractBlock.Settings settings) {
    return register(id, Block::new, settings);
  }

  public static <T extends Block> T register(String id, Function<AbstractBlock.Settings, T> factory, AbstractBlock.Settings settings) {
    RegistryKey<Block> key = keyOf(id);
    T block = factory.apply(settings.registryKey(key));
    Stranded.LOGGER.debug("Registered block: {}", key.getValue());
    Registry.register(Registries.BLOCK, key, block);
    ModItems.register(block, BlockItem::new, new Item.Settings());
    return block;
  }
}
