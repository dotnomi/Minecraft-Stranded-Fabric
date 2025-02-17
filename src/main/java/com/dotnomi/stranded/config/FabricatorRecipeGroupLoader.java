package com.dotnomi.stranded.config;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.dto.FabricatorRecipeGroup;
import com.dotnomi.stranded.dto.FabricatorRecipeGroupList;
import com.dotnomi.stranded.util.JsonHandler;
import net.fabricmc.loader.api.FabricLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FabricatorRecipeGroupLoader {
  private static FabricatorRecipeGroupLoader instance;

  private final Path path;
  private final JsonHandler<FabricatorRecipeGroupList> jsonHandler;

  public static FabricatorRecipeGroupLoader getInstance() {
    if (instance == null) {
      instance = new FabricatorRecipeGroupLoader();
    }
    return instance;
  }

  public FabricatorRecipeGroupLoader() {
    this.path = FabricLoader.getInstance().getConfigDir().resolve(Stranded.MOD_ID).resolve("fabricator_recipe_groups.json");
    this.jsonHandler = new JsonHandler<>(this.path, FabricatorRecipeGroupList.class);
  }

  public FabricatorRecipeGroupList load() {
    if (!Files.exists(this.path)) {
      jsonHandler.write(this.getDefaults());
    }
    FabricatorRecipeGroupList recipeGroups = jsonHandler.read();
    return recipeGroups == null ? new FabricatorRecipeGroupList(new ArrayList<>()) : recipeGroups;
  }

  public FabricatorRecipeGroupList getDefaults() {
    return new FabricatorRecipeGroupList(List.of(
      new FabricatorRecipeGroup("test1"),
      new FabricatorRecipeGroup("test2")
    ));
  }
}
