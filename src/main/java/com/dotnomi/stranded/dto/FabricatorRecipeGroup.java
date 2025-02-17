package com.dotnomi.stranded.dto;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class FabricatorRecipeGroup {
  public static final PacketCodec<ByteBuf, FabricatorRecipeGroup> PACKET_CODEC = new PacketCodec<>() {
    private final Gson gson = new Gson();

    public FabricatorRecipeGroup decode(ByteBuf byteBuf) {
      String jsonString = byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
      return gson.fromJson(jsonString, FabricatorRecipeGroup.class);
    }

    public void encode(ByteBuf byteBuf, FabricatorRecipeGroup fabricatorRecipeGroup) {
      byteBuf.writeCharSequence(gson.toJson(fabricatorRecipeGroup), StandardCharsets.UTF_8);
    }
  };

  private String id;
  private String title;

  public FabricatorRecipeGroup(String id) {
    this.id = id;
    this.title = "fabricator.recipeGroup." + id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
