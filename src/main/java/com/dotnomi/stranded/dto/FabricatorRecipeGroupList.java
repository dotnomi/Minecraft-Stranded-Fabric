package com.dotnomi.stranded.dto;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FabricatorRecipeGroupList {
  public static final PacketCodec<ByteBuf, FabricatorRecipeGroupList> PACKET_CODEC = new PacketCodec<>() {
    private final Gson gson = new Gson();

    public FabricatorRecipeGroupList decode(ByteBuf byteBuf) {
      String jsonString = byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
      return gson.fromJson(jsonString, FabricatorRecipeGroupList.class);
    }

    public void encode(ByteBuf byteBuf, FabricatorRecipeGroupList fabricatorRecipeGroupList) {
      byteBuf.writeCharSequence(gson.toJson(fabricatorRecipeGroupList), StandardCharsets.UTF_8);
    }
  };

  private List<FabricatorRecipeGroup> recipeGroups;

  public FabricatorRecipeGroupList(List<FabricatorRecipeGroup> recipeGroups) {
    this.recipeGroups = recipeGroups;
  }

  public List<FabricatorRecipeGroup> getRecipeGroups() {
    return recipeGroups;
  }

  public void setRecipeGroups(List<FabricatorRecipeGroup> recipeGroups) {
    this.recipeGroups = recipeGroups;
  }
}
