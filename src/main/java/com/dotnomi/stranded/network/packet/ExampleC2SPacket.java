package com.dotnomi.stranded.network.packet;

import com.dotnomi.stranded.Stranded;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record ExampleC2SPacket(int number) implements CustomPayload {
  public static final CustomPayload.Id<ExampleC2SPacket> IDENTIFIER =
    new CustomPayload.Id<>(Identifier.of(Stranded.MOD_ID, "example_c2s_packet"));

  public static final PacketCodec<RegistryByteBuf, ExampleC2SPacket> CODEC = PacketCodec.tuple(
    PacketCodecs.INTEGER,
    ExampleC2SPacket::number,
    ExampleC2SPacket::new
  );

  @Override
  public Id<? extends CustomPayload> getId() {
    return IDENTIFIER;
  }

  public void handlePacket(ServerPlayNetworking.Context context) {
    // Code on server
    context.player().sendMessage(Text.literal("Hello from the Client!"), false);
    ServerPlayNetworking.send(context.player(), new ExampleS2CPacket(0));
  }
}
