package com.dotnomi.stranded.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StrandedCrafterBlock extends BlockWithEntity {
  public StrandedCrafterBlock(Settings settings) {
    super(settings);
  }

  @Override
  protected MapCodec<? extends BlockWithEntity> getCodec() {
    return createCodec(StrandedCrafterBlock::new);
  }

  @Override
  public BlockRenderType getRenderType(BlockState state) {
    return BlockRenderType.MODEL;
  }

  @Override
  protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (!world.isClient()) {

      player.sendMessage(Text.literal("Stranded Crafter Block"), false);

      // Server-Seite: GUI öffnen
      NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
      if (screenHandlerFactory != null) {
        player.openHandledScreen(screenHandlerFactory);




        // **Daten vom Server laden und an den Client senden:**
        String serverData = "Hallo vom Server!"; // Hier echte Daten laden (z.B. aus einer Konfigurationsdatei, Datenbank, etc.)
        //CustomScreenHandler.sendDataToClient((net.minecraft.server.network.ServerPlayerEntity) player, serverData);
      }
    }
    return ActionResult.SUCCESS;
  }

  /*@Override
  public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
    return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) ->
      new CustomScreenHandler(syncId), Text.translatable("screen.deinmodid.custom_screen"));
  }*/

  @Override
  public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return null;
  }
}
