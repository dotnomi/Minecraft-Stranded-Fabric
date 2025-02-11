package com.dotnomi.stranded.block.custom;

import com.dotnomi.stranded.block.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class StrandedCrafterBlockEntity extends BlockEntity {
  public StrandedCrafterBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.STRANDED_CRAFTER_BLOCK_ENTITY, pos, state);
  }
}
