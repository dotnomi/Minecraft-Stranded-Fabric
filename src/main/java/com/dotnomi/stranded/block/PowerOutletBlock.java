package com.dotnomi.stranded.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class PowerOutletBlock extends FacingBlock {
  private static final VoxelShape SHAPE_UP = Stream.of(
    Block.createCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 1.0D, 11.0D),
    Block.createCuboidShape(6.0D, 1.0D, 6.0D, 10.0D, 6.0D, 10.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  private static final  VoxelShape SHAPE_DOWN = Stream.of(
    Block.createCuboidShape(5.0D, 15.0D, 5.0D, 11.0D, 16.0D, 11.0D),
    Block.createCuboidShape(6.0D, 10.0D, 6.0D, 10.0D, 15.0D, 10.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  private static final  VoxelShape SHAPE_NORTH = Stream.of(
    Block.createCuboidShape(5.0D, 5.0D, 15.0D, 11.0D, 11.0D, 16.0D),
    Block.createCuboidShape(6.0D, 6.0D, 10.0D, 10.0D, 10.0D, 15.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  private static final  VoxelShape SHAPE_SOUTH = Stream.of(
    Block.createCuboidShape(5.0D, 5.0D, 0.0D, 11.0D, 11.0D, 1.0D),
    Block.createCuboidShape(6.0D, 6.0D, 1.0D, 10.0D, 10.0D, 6.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  private static final  VoxelShape SHAPE_WEST = Stream.of(
    Block.createCuboidShape(15.0D, 5.0D, 5.0D, 16.0D, 11.0D, 11.0D),
    Block.createCuboidShape(10.0D, 6.0D, 6.0D, 15.0D, 10.0D, 10.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  private static final  VoxelShape SHAPE_EAST = Stream.of(
    Block.createCuboidShape(0.0D, 5.0D, 5.0D, 1.0D, 11.0D, 11.0D),
    Block.createCuboidShape(1.0D, 6.0D, 6.0D, 6.0D, 10.0D, 10.0D)
  ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

  public PowerOutletBlock(Settings settings) {
    super(settings);
  }

  @Override
  public MapCodec<? extends FacingBlock> getCodec() {
    return createCodec(PowerOutletBlock::new);
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    return switch (state.get(FACING)) {
      case UP -> SHAPE_UP;
      case DOWN -> SHAPE_DOWN;
      case NORTH -> SHAPE_NORTH;
      case SOUTH -> SHAPE_SOUTH;
      case WEST -> SHAPE_WEST;
      case EAST -> SHAPE_EAST;
    };
  }

  @Override
  public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
    super.randomDisplayTick(state, world, pos, random);
  }

  @Override
  public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
    return this.getDefaultState().with(FACING, context.getSide());
  }

  @Override
  public BlockState rotate(BlockState state, BlockRotation rotation) {
    return state.with(FACING, rotation.rotate(state.get(FACING)));
  }

  @Override
  public BlockState mirror(BlockState state, BlockMirror mirror) {
    return state.with(FACING, mirror.apply(state.get(FACING)));
  }

  @Override
  public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING);
  }
}
