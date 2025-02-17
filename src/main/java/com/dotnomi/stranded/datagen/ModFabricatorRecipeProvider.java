package com.dotnomi.stranded.datagen;

import com.dotnomi.stranded.dto.FabricatorRecipe;
import com.dotnomi.stranded.dto.FabricatorRecipeList;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModFabricatorRecipeProvider implements DataProvider {
  protected final FabricDataOutput dataOutput;

  public ModFabricatorRecipeProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> ignored) {
    this.dataOutput = dataOutput;
  }

  @Override
  public CompletableFuture<?> run(DataWriter writer) {
    FabricatorRecipeList fabricatorRecipes = new FabricatorRecipeList(List.of(
      new FabricatorRecipe.Builder()
        .withTitle("Eisenbarren")
        .withIngredient(Items.RAW_IRON, 1)
        .withResult(Items.IRON_INGOT, 1)
        .withSteps(3)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("Eisennuggets (Erweiterung)")
        .withIngredient(Items.IRON_INGOT, 1)
        .withResult(Items.IRON_NUGGET, 5)
        .withSteps(1)
        .build()
    ));

    Gson gson = new Gson();
    return DataProvider.writeToPath(writer, gson.toJsonTree(fabricatorRecipes), getRecipeConfigPath());
  }

  @Override
  public String getName() {
    return "Fabricator Recipes";
  }

  private Path getRecipeConfigPath() {
    return dataOutput
      .getResolver(DataOutput.OutputType.DATA_PACK, "fabricator")
      .resolveJson(Identifier.of(dataOutput.getModId(), "default_recipes"));
  }
}
