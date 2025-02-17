package com.dotnomi.stranded.dto;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.StandardCharsets;
import java.util.List;

public record FabricatorInitializationData(List<FabricatorRecipeGroup> recipeGroups, List<FabricatorRecipe> recipes) {
  public static final PacketCodec<ByteBuf, FabricatorInitializationData> PACKET_CODEC = new PacketCodec<>() {
    private final Gson gson = new Gson();

    public FabricatorInitializationData decode(ByteBuf byteBuf) {
      String jsonString = byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
      return gson.fromJson(jsonString, FabricatorInitializationData.class);
    }

    public void encode(ByteBuf byteBuf, FabricatorInitializationData fabricatorInitializationData) {
      byteBuf.writeCharSequence(gson.toJson(fabricatorInitializationData), StandardCharsets.UTF_8);
    }
  };
}
