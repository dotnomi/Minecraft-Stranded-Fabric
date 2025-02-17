package com.dotnomi.stranded.config;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.dto.FabricatorRecipeList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FabricatorRecipeLoader {
  private static final String RECIPE_FILE_NAME = "fabricator_recipes.json";

  private static FabricatorRecipeLoader instance;

  private Path path;

  public static FabricatorRecipeLoader getInstance() {
    if (instance == null) {
      instance = new FabricatorRecipeLoader();
    }

    instance.path = FabricLoader.getInstance().getConfigDir().resolve(Stranded.MOD_ID).resolve(RECIPE_FILE_NAME);
    return instance;
  }

  public FabricatorRecipeList load() {
    if (!Files.exists(this.path)) {
      Stranded.LOGGER.debug("No fabricator recipes config found. Creating from default...");
      createDefaultRecipeFile();
    }

    Stranded.LOGGER.debug("Loading fabricator recipes...");
    return loadRecipesFromJson(this.path);
  }

  private void createDefaultRecipeFile() {
    generateRecipeJson(DefaultFabricatorRecipes.getDefaultFabricatorRecipes());
  }

  private FabricatorRecipeList loadRecipesFromJson(Path recipesPath) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    try (BufferedReader reader = Files.newBufferedReader(recipesPath, StandardCharsets.UTF_8)) {
      FabricatorRecipeList recipes = gson.fromJson(reader, FabricatorRecipeList.class);
      if (recipes == null) {
        return new FabricatorRecipeList(new ArrayList<>());
      }
      return recipes;
    } catch (IOException exception) {
      Stranded.LOGGER.error("Failed to load fabricator recipes.", exception);
      return new FabricatorRecipeList(new ArrayList<>());
    }
  }

  private void generateRecipeJson(FabricatorRecipeList recipes) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json = gson.toJson(recipes);

    try {
      Files.createDirectories(this.path.getParent());
    } catch (IOException exception) {
      Stranded.LOGGER.error("Failed to create directories for the config file.", exception);
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.path.toFile()))) {
      writer.write(json);
    } catch (IOException exception) {
      Stranded.LOGGER.error("Failed to write to the config file.", exception);
    }
  }
}
