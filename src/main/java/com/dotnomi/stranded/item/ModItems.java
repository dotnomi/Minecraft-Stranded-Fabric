package com.dotnomi.stranded.item;

import com.dotnomi.stranded.Stranded;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ModItems {
  public static final Map<Block, Item> BLOCK_ITEMS = new HashMap<>();

  public static final Item ACRYLITE = register("acrylite", new Item.Settings());
  public static final Item RAW_TITANIUM = register("raw_titanium", new Item.Settings());
  public static final Item TITANIUM_INGOT = register("titanium_ingot", new Item.Settings());
  public static final Item POLYMER_RESIN = register("polymer_resin", new Item.Settings());
  public static final Item RAW_LITHIUM = register("raw_lithium", new Item.Settings());
  public static final Item SULPHUR = register("sulphur", new Item.Settings());

  public static void initialize() {
    ItemGroupEvents.modifyEntriesEvent(ModItemGroups.STRANDED_ITEMS).register(entries -> {
      entries.add(ACRYLITE);
      entries.add(RAW_TITANIUM);
      entries.add(TITANIUM_INGOT);
      entries.add(POLYMER_RESIN);
      entries.add(RAW_LITHIUM);
      entries.add(SULPHUR);
    });
  }

  public static RegistryKey<Item> keyOf(String id) {
    return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Stranded.MOD_ID, id));
  }

  public static RegistryKey<Item> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.ITEM, id);
  }

  @SuppressWarnings("UnusedReturnValue")
  public static Item register(Block block, BiFunction<Block, Item.Settings, Item> factory, Item.Settings settings) {
    Optional<RegistryKey<Block>> optionalKey = Registries.BLOCK.getKey(block);
    if (optionalKey.isEmpty()) throw new IllegalArgumentException("Block " + block + " does not exist");
    return register(
      keyOf(optionalKey.get().getValue()), itemSettings -> factory.apply(block, itemSettings), settings.useBlockPrefixedTranslationKey()
    );
  }

  public static Item register(String id, Item.Settings settings) {
    return register(keyOf(id), Item::new, settings);
  }

  public static Item register(RegistryKey<Item> key, Function<Item.Settings, Item> factory, Item.Settings settings) {
    Item item = factory.apply(settings.registryKey(key));
    if (item instanceof BlockItem blockItem) {
      blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
    }
    Stranded.LOGGER.debug("Registered item: {}", key.getValue());
    return Registry.register(Registries.ITEM, key, item);
  }
}
