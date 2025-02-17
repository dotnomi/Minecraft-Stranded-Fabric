package com.dotnomi.stranded.network;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.network.packet.ExampleC2SPacket;
import com.dotnomi.stranded.network.packet.ExampleS2CPacket;
import com.dotnomi.stranded.network.packet.FabricatorCraftingFinishedC2SPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public class ModPayloads {
  public static void initialize() {
    // Client To Server
    registerC2S(ExampleC2SPacket.IDENTIFIER, ExampleC2SPacket.CODEC);
    registerC2S(FabricatorCraftingFinishedC2SPayload.IDENTIFIER, FabricatorCraftingFinishedC2SPayload.CODEC);

    // Server To Client
    registerS2C(ExampleS2CPacket.IDENTIFIER, ExampleS2CPacket.CODEC);
  }

  private static <T extends CustomPayload> void registerC2S(CustomPayload.Id<T> id, PacketCodec<RegistryByteBuf, T> codec) {
    PayloadTypeRegistry.playC2S().register(id, codec);
    Stranded.LOGGER.debug("Registered client to server payload type: {}", id);
  }

  private static <T extends CustomPayload> void registerS2C(CustomPayload.Id<T> id, PacketCodec<RegistryByteBuf, T> codec) {
    PayloadTypeRegistry.playS2C().register(id, codec);
    Stranded.LOGGER.debug("Registered server to client payload type: {}", id);
  }
}
