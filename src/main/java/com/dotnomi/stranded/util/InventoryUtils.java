package com.dotnomi.stranded.util;

import com.dotnomi.stranded.dto.FabricatorRecipe;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class InventoryUtils {
  public static boolean hasIngredients(PlayerInventory inventory, FabricatorRecipe recipe) {
    boolean hasEnoughIngredients = true;

    for (Map.Entry<Item, Integer> ingredientEntry : recipe.getIngredients().entrySet()) {
      Item ingredientItem = ingredientEntry.getKey();
      int neededAmount = ingredientEntry.getValue();
      int playerAmount = 0;

      for (int i = 0; i < inventory.size(); i++) {
        ItemStack stackInSlot = inventory.getStack(i);
        if (stackInSlot.isOf(ingredientItem)) {
          playerAmount += stackInSlot.getCount();
        }
      }

      if (playerAmount < neededAmount) {
        hasEnoughIngredients = false;
        break;
      }
    }
    return hasEnoughIngredients;
  }

  public static boolean hasInventorySpace(PlayerInventory inventory, FabricatorRecipe recipe) {
    Item item = recipe.getResult().getLeft();
    int amount = recipe.getResult().getRight();

    if (amount <= 0) {
      return true;
    }

    int remainingAmountToAdd = amount;
    for (int i = 0; i < inventory.main.size(); i++) {
      ItemStack stackInSlot = inventory.getStack(i);
      if (stackInSlot.isOf(item)) {
        int spaceInStack = stackInSlot.getMaxCount() - stackInSlot.getCount();
        int addableToStack = Math.min(remainingAmountToAdd, spaceInStack);
        remainingAmountToAdd -= addableToStack;
        if (remainingAmountToAdd == 0) {
          return true;
        }
      }
    }

    if (remainingAmountToAdd > 0) {
      int emptySlots = 0;
      for (int i = 0; i < inventory.main.size(); i++) {
        if (inventory.getStack(i).isEmpty()) {
          emptySlots++;
        }
      }
      int fullStacksPossibleInEmptySlots = emptySlots;
      return remainingAmountToAdd <= fullStacksPossibleInEmptySlots * item.getMaxCount();
    }
    return false;
  }

  public static int calculateMaxCraftable(PlayerInventory inventory, FabricatorRecipe recipe) {
    int maxCraftableAmount = Integer.MAX_VALUE;

    for (Map.Entry<Item, Integer> ingredient : recipe.getIngredients().entrySet()) {
      int requiredAmount = ingredient.getValue();

      if (requiredAmount <= 0) {
        continue;
      }

      int availableAmount = 0;
      for (int i = 0; i < inventory.size(); i++) {
        ItemStack stackInSlot = inventory.getStack(i);
        if (stackInSlot.isOf(ingredient.getKey())) {
          availableAmount += stackInSlot.getCount();
        }
      }

      if (availableAmount == 0) {
        return 0;
      }

      int maxCraftableForIngredient = availableAmount / requiredAmount;
      if (maxCraftableForIngredient < maxCraftableAmount) {
        maxCraftableAmount = maxCraftableForIngredient;
      }
    }

    if (maxCraftableAmount == Integer.MAX_VALUE) {
      return 0;
    }

    return maxCraftableAmount;
  }
}
