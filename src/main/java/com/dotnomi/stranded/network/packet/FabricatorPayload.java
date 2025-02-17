package com.dotnomi.stranded.network.packet;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.dto.FabricatorRecipeList;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record FabricatorPayload(BlockPos position, FabricatorRecipeList recipes) implements CustomPayload {
  public static final CustomPayload.Id<FabricatorPayload> IDENTIFIER =
    new CustomPayload.Id<>(Identifier.of(Stranded.MOD_ID, "fabricator_initialize_packet"));

  public static final PacketCodec<RegistryByteBuf, FabricatorPayload> CODEC = PacketCodec.tuple(
    BlockPos.PACKET_CODEC,
    FabricatorPayload::position,
    FabricatorRecipeList.PACKET_CODEC,
    FabricatorPayload::recipes,
    FabricatorPayload::new
  );

  @Override
  public Id<? extends CustomPayload> getId() {
    return IDENTIFIER;
  }
}
