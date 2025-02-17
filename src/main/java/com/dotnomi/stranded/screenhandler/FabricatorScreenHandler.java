package com.dotnomi.stranded.screenhandler;

import com.dotnomi.stranded.block.ModBlocks;
import com.dotnomi.stranded.block.entity.FabricatorBlockEntity;
import com.dotnomi.stranded.dto.FabricatorRecipe;
import com.dotnomi.stranded.dto.FabricatorRecipeGroup;
import com.dotnomi.stranded.network.packet.FabricatorPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class FabricatorScreenHandler extends ScreenHandler {
  private final FabricatorBlockEntity blockEntity;
  private final ScreenHandlerContext context;
  private final List<FabricatorRecipeGroup> recipeGroups = new ArrayList<>();
  private final List<FabricatorRecipe> recipes = new ArrayList<>();

  // Client Constructor
  public FabricatorScreenHandler(int syncId, PlayerInventory playerInventory, FabricatorPayload fabricatorPayload) {
    this(syncId, playerInventory, (FabricatorBlockEntity) playerInventory.player.getWorld().getBlockEntity(fabricatorPayload.position()));
    this.recipeGroups.clear();
    this.recipeGroups.addAll(fabricatorPayload.data().recipeGroups());
    this.recipes.clear();
    this.recipes.addAll(fabricatorPayload.data().recipes());
  }

  // Main Constructor
  public FabricatorScreenHandler(int syncId, PlayerInventory playerInventory, FabricatorBlockEntity blockEntity) {
    super(ModScreenHandlerTypes.FABRICATOR, syncId);

    this.blockEntity = blockEntity;
    this.context = ScreenHandlerContext.create(this.blockEntity.getWorld(), this.blockEntity.getPos());

    addPlayerHotbarSlots(playerInventory, 108, 142);
    addPlayerInventorySlots(playerInventory, 108, 84);
  }

  @Override
  public ItemStack quickMove(PlayerEntity player, int slot) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean canUse(PlayerEntity player) {
    return canUse(context, player, ModBlocks.FABRICATOR);
  }

  public FabricatorBlockEntity getBlockEntity() {
    return blockEntity;
  }

  public List<FabricatorRecipe> getRecipes() {
    return recipes;
  }

  public List<FabricatorRecipeGroup> getRecipeGroups() {
    return recipeGroups;
  }
}
