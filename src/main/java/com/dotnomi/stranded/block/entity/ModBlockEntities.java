package com.dotnomi.stranded.block.entity;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.block.ModBlocks;
import com.dotnomi.stranded.block.custom.StrandedCrafterBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Set;

public class ModBlockEntities {
  public static final BlockEntityType<StrandedCrafterBlockEntity> STRANDED_CRAFTER_BLOCK_ENTITY = create("stranded_crafter", StrandedCrafterBlockEntity::new, ModBlocks.STRANDED_CRAFTER);

  public static void initialize() {}

  private static <T extends BlockEntity> BlockEntityType<T> create(String id, BlockEntityType.BlockEntityFactory<? extends T> factory, Block... blocks) {
    if (blocks.length == 0) {
      Stranded.LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", id);
    }
    return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(Stranded.MOD_ID, id), new BlockEntityType<>(factory, Set.of(blocks)));
  }
}
