package com.dotnomi.stranded.network.packet;

import com.dotnomi.stranded.Stranded;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record ExampleS2CPacket(int number) implements CustomPayload {
  public static final Id<ExampleS2CPacket> IDENTIFIER =
    new Id<>(Identifier.of(Stranded.MOD_ID, "example_s2c_packet"));

  public static final PacketCodec<RegistryByteBuf, ExampleS2CPacket> CODEC = PacketCodec.tuple(
    PacketCodecs.INTEGER,
    ExampleS2CPacket::number,
    ExampleS2CPacket::new
  );
  
  @Override
  public Id<? extends CustomPayload> getId() {
    return IDENTIFIER;
  }

  public void handlePacket(ClientPlayNetworking.Context context) {
    // Code on client
    context.player().sendMessage(Text.literal("Hello from the Server!"), false);
  }
}
