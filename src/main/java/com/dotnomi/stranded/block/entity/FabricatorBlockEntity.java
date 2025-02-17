package com.dotnomi.stranded.block.entity;

import com.dotnomi.stranded.config.FabricatorRecipeGroupLoader;
import com.dotnomi.stranded.config.FabricatorRecipeLoader;
import com.dotnomi.stranded.network.packet.FabricatorPayload;
import com.dotnomi.stranded.screenhandler.FabricatorScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class FabricatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<FabricatorPayload> {
  public FabricatorBlockEntity(BlockPos pos, BlockState state) {
    super(ModBlockEntities.FABRICATOR_BLOCK_ENTITY, pos, state);
  }

  @Override
  public FabricatorPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
    return new FabricatorPayload(this.getPos(), FabricatorRecipeGroupLoader.getInstance().load(), FabricatorRecipeLoader.getInstance().load());
  }

  @Override
  public Text getDisplayName() {
    return Text.literal("Fabricator");
  }

  @Override
  public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
    return new FabricatorScreenHandler(syncId, playerInventory, this);
  }


  @Override
  public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
    return BlockEntityUpdateS2CPacket.create(this);
  }

  @Override
  public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
    return super.toInitialChunkDataNbt(registries);
  }
}
