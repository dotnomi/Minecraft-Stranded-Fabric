package com.dotnomi.stranded.dto;

import com.google.gson.Gson;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class FabricatorRecipe {
  public static final PacketCodec<ByteBuf, FabricatorRecipe> PACKET_CODEC = new PacketCodec<>() {
    private final Gson gson = new Gson();

    public FabricatorRecipe decode(ByteBuf byteBuf) {
      String jsonString = byteBuf.readCharSequence(byteBuf.readableBytes(), StandardCharsets.UTF_8).toString();
      return gson.fromJson(jsonString, FabricatorRecipe.class);
    }

    public void encode(ByteBuf byteBuf, FabricatorRecipe fabricatorRecipe) {
      byteBuf.writeCharSequence(gson.toJson(fabricatorRecipe), StandardCharsets.UTF_8);
    }
  };

  private String recipeGroupId;
  private String title;
  private HashMap<String, Integer> ingredients;
  private Pair<String, Integer> result;
  private int steps;

  public FabricatorRecipe(
    final String recipeGroupId,
    final String title,
    final HashMap<String, Integer> ingredients,
    final Pair<String, Integer> result,
    final int steps
  ) {
    this.recipeGroupId = recipeGroupId;
    this.title = title;
    this.ingredients = ingredients;
    this.result = result;
    this.steps = steps;
  }

  public String getRecipeGroupId() {
    return recipeGroupId;
  }

  public void setRecipeGroupId(String recipeGroupId) {
    this.recipeGroupId = recipeGroupId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public HashMap<Item, Integer> getIngredients() {
    return (HashMap<Item, Integer>) ingredients.entrySet().stream().collect(Collectors.toMap(
      entry -> Registries.ITEM.get(Identifier.of(entry.getKey())), Map.Entry::getValue
    ));
  }

  public void setIngredients(HashMap<String, Integer> ingredients) {
    this.ingredients = ingredients;
  }

  public Pair<Item, Integer> getResult() {
    return new Pair<>(Registries.ITEM.get(Identifier.of(result.getLeft())), result.getRight());
  }

  public void setResult(Pair<String, Integer> result) {
    this.result = result;
  }

  public int getSteps() {
    return steps;
  }

  public void setSteps(int steps) {
    this.steps = steps;
  }

  public static class Builder {
    private String recipeGroupId;
    private String title;
    private final HashMap<String, Integer> ingredients = new HashMap<>();
    private Pair<String, Integer> result = new Pair<>(Items.DIRT.toString(), 1);
    private int steps = 1;

    public Builder() {}

    public Builder withRecipeGroupId(String recipeGroupId) {
      this.recipeGroupId = recipeGroupId;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withIngredient(@NotNull Item item, int amount) {
      this.ingredients.put(item.toString(), amount);
      return this;
    }

    public Builder withResult(@NotNull Item item, int amount) {
      this.result = new Pair<>(item.toString(), amount);
      return this;
    }

    public Builder withSteps(int steps) {
      this.steps = steps;
      return this;
    }

    public FabricatorRecipe build() {
      return new FabricatorRecipe(recipeGroupId, title, ingredients, result, steps);
    }
  }
}
