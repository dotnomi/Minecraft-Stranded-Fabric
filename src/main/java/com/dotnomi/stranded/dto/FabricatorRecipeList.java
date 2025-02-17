package com.dotnomi.stranded.dto;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FabricatorRecipeList {
  public static final PacketCodec<ByteBuf, FabricatorRecipeList> PACKET_CODEC = new PacketCodec<>() {
    private final Gson gson = new Gson();

    public FabricatorRecipeList decode(ByteBuf byteBuf) {
      String jsonString = byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
      return gson.fromJson(jsonString, FabricatorRecipeList.class);
    }

    public void encode(ByteBuf byteBuf, FabricatorRecipeList fabricatorRecipeList) {
      byteBuf.writeCharSequence(gson.toJson(fabricatorRecipeList), StandardCharsets.UTF_8);
    }
  };

  private List<FabricatorRecipe> recipes;

  public FabricatorRecipeList(List<FabricatorRecipe> recipes) {
    this.recipes = recipes;
  }

  public List<FabricatorRecipe> getRecipes() {
    return recipes;
  }

  public void setRecipes(List<FabricatorRecipe> recipes) {
    this.recipes = recipes;
  }
}
