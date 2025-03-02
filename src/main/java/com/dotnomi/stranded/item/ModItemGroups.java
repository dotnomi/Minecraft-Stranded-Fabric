package com.dotnomi.stranded.item;

import com.dotnomi.stranded.Stranded;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
  public static final RegistryKey<ItemGroup> STRANDED_ITEMS = keyOf(Identifier.of(Stranded.MOD_ID, "stranded_item_group"));

  public static void initialize() {
    Registry.register(Registries.ITEM_GROUP, STRANDED_ITEMS, FabricItemGroup.builder()
      .icon(() -> new ItemStack(ModItems.ACRYLITE))
      .displayName(Text.translatable("itemGroup.stranded.stranded_items"))
      .build());
  }

  public static RegistryKey<ItemGroup> keyOf(Identifier id) {
    return RegistryKey.of(RegistryKeys.ITEM_GROUP, id);
  }
}
