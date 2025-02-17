package com.dotnomi.stranded.network.packet;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.block.ModBlocks;
import com.dotnomi.stranded.dto.FabricatorRecipe;
import com.dotnomi.stranded.util.InventoryUtils;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public record FabricatorCraftingFinishedC2SPayload(BlockPos blockPos, FabricatorRecipe recipe) implements CustomPayload {
  public static final CustomPayload.Id<FabricatorCraftingFinishedC2SPayload> IDENTIFIER =
    new CustomPayload.Id<>(Identifier.of(Stranded.MOD_ID, "fabricator_crafting_finished_c2s_packet"));

  public static final PacketCodec<RegistryByteBuf, FabricatorCraftingFinishedC2SPayload> CODEC = PacketCodec.tuple(
    BlockPos.PACKET_CODEC,
    FabricatorCraftingFinishedC2SPayload::blockPos,
    FabricatorRecipe.PACKET_CODEC,
    FabricatorCraftingFinishedC2SPayload::recipe,
    FabricatorCraftingFinishedC2SPayload::new
  );

  @Override
  public CustomPayload.Id<? extends CustomPayload> getId() {
    return IDENTIFIER;
  }

  public void handlePacket(ServerPlayNetworking.Context context) {
    if (isPlayerAllowedToCraft(context.player())) {
      addResultToInventory(context.player());
      removeIngredientsFromInventory(context.player());
    }
  }

  private void addResultToInventory(ServerPlayerEntity player) {
    Item resultItem = recipe.getResult().getLeft();
    int resultAmount = recipe.getResult().getRight();
    ItemStack resultItemStack = new ItemStack(resultItem, resultAmount);
    player.getInventory().insertStack(resultItemStack);
  }

  private void removeIngredientsFromInventory(ServerPlayerEntity player) {
    recipe.getIngredients().forEach((ingredient, amountToRemove) -> {
      if (ingredient != Items.AIR) {
        int removedCount = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
          ItemStack stackInSlot = player.getInventory().getStack(i);
          if (stackInSlot.isOf(ingredient)) {
            int removableAmount = Math.min(amountToRemove - removedCount, stackInSlot.getCount());
            stackInSlot.decrement(removableAmount);
            removedCount += removableAmount;
            if (removedCount == amountToRemove) {
              break;
            }
          }
        }
      }
    });
  }

  private boolean isPlayerAllowedToCraft(ServerPlayerEntity player) {
    return InventoryUtils.hasIngredients(player.getInventory(), this.recipe)
      && InventoryUtils.hasInventorySpace(player.getInventory(), this.recipe)
      && player.getWorld().getBlockState(this.blockPos).isOf(ModBlocks.FABRICATOR)
      && player.canInteractWithBlockAt(blockPos, 4.0);
  }
}
