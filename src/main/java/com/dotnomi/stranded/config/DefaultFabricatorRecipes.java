package com.dotnomi.stranded.config;

import com.dotnomi.stranded.dto.FabricatorRecipe;
import com.dotnomi.stranded.dto.FabricatorRecipeList;
import net.minecraft.item.Items;

import java.util.List;

public class DefaultFabricatorRecipes {
  public static FabricatorRecipeList getDefaultFabricatorRecipes() {
    return new FabricatorRecipeList(List.of(
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.iron_ingot")
        .withIngredient(Items.RAW_IRON, 1)
        .withResult(Items.IRON_INGOT, 1)
        .withSteps(3)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.iron_nuggets")
        .withIngredient(Items.IRON_INGOT, 1)
        .withResult(Items.IRON_NUGGET, 5)
        .withSteps(1)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.netherite_ingot")
        .withIngredient(Items.IRON_INGOT, 1)
        .withIngredient(Items.COAL, 2)
        .withResult(Items.NETHERITE_INGOT, 1)
        .withSteps(5)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.diorite")
        .withIngredient(Items.STONE, 1)
        .withIngredient(Items.WHITE_DYE, 1)
        .withIngredient(Items.BLACK_DYE, 1)
        .withResult(Items.DIORITE, 1)
        .withSteps(3)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy1")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(1)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy2")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(2)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy3")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(3)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy4")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(4)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy5")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(5)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy6")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(6)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy7")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(7)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy8")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(8)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy9")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(9)
        .build(),
      new FabricatorRecipe.Builder()
        .withTitle("fabricator.recipe.dummy10")
        .withIngredient(Items.DIRT, 1)
        .withResult(Items.GRASS_BLOCK, 1)
        .withSteps(10)
        .build()
    ));
  }
}
