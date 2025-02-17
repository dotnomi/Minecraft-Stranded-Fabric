package com.dotnomi.stranded.client.gui.screen;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.client.gui.widget.FabricatorButtonWidget;
import com.dotnomi.stranded.client.gui.widget.FabricatorProgressBarWidget;
import com.dotnomi.stranded.client.gui.widget.FabricatorRecipeWidget;
import com.dotnomi.stranded.client.gui.widget.FabricatorResultWidget;
import com.dotnomi.stranded.client.gui.widget.FabricatorScrollBarWidget;
import com.dotnomi.stranded.client.gui.widget.FabricatorIngredientWidget;
import com.dotnomi.stranded.util.InventoryUtils;
import com.dotnomi.stranded.dto.FabricatorRecipe;
import com.dotnomi.stranded.network.packet.FabricatorCraftingFinishedC2SPayload;
import com.dotnomi.stranded.screenhandler.FabricatorScreenHandler;
import com.dotnomi.stranded.sound.ModSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FabricatorScreen extends HandledScreen<FabricatorScreenHandler> {
  private static final Identifier BACKGROUND_TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/stranded_crafter.png");
  private static final FabricatorRecipe DEFAULT_RECIPE = new FabricatorRecipe.Builder()
    .withIngredient(Items.ANDESITE, 0)
    .withIngredient(Items.DIORITE, 0)
    .withIngredient(Items.GRANITE, 0)
    .withResult(Items.AIR, 0)
    .withSteps(1)
    .build();

  private final BlockEntity blockEntity;
  private final PlayerInventory playerInventory;
  private final List<FabricatorRecipe> recipes;
  private final FabricatorButtonWidget craftButton;
  private final FabricatorResultWidget resultWidget;
  private final List<FabricatorIngredientWidget> ingredientWidgets = new ArrayList<>();
  private final FabricatorProgressBarWidget progressBarWidget;
  private final FabricatorScrollBarWidget scrollBarWidget;
  private final List<FabricatorRecipeWidget> recipeWidgets = new ArrayList<>();
  private final TextFieldWidget searchTextField;

  private FabricatorRecipe currentRecipe;
  private FabricatorRecipe lastRecipe;
  private int currentStep = 0;
  private long lastStepTime = 0;
  private boolean hasFinished = false;
  private int recipeListScrollOffset = 0;

  private long lastOpacityTime = 0;

  public FabricatorScreen(FabricatorScreenHandler handler, PlayerInventory inventory, Text title) {
    super(handler, inventory, title);
    this.backgroundWidth = 279;
    this.backgroundHeight = 169;
    this.blockEntity = handler.getBlockEntity();
    this.recipes = handler.getRecipes();
    this.playerInventory = inventory;

    this.currentRecipe = DEFAULT_RECIPE;
    this.lastRecipe = DEFAULT_RECIPE;

    this.craftButton = new FabricatorButtonWidget(0, 0);
    this.resultWidget = new FabricatorResultWidget(0,0, ItemStack.EMPTY);
    this.progressBarWidget = new FabricatorProgressBarWidget(0,0);
    this.scrollBarWidget = new FabricatorScrollBarWidget(0,0);
    this.searchTextField = new TextFieldWidget(Stranded.CLIENT.get().textRenderer, 0,0, 87, 8, Text.empty());

    for (int i = 0; i < 3; i++) {
      this.ingredientWidgets.add(new FabricatorIngredientWidget(0, 0, ItemStack.EMPTY));
    }

    recipes.forEach(recipe -> {
      recipeWidgets.add(new FabricatorRecipeWidget(0, 0, recipe, this));
    });
  }

  public void changeRecipe(FabricatorRecipe recipe) {
    this.currentRecipe = recipe;
  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (mouseX < this.searchTextField.getX()
      || mouseX > this.searchTextField.getX() + this.searchTextField.getWidth()
      || mouseY < this.searchTextField.getY()
      || mouseY > this.searchTextField.getY() + this.searchTextField.getHeight()) {
      this.setFocused(craftButton);
    }
    return super.mouseClicked(mouseX, mouseY, button);
  }

  @Override
  public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    double scrollSpeedFactor = Math.max (1.0, ((double) this.scrollBarWidget.getHeight() / 134) * 8);
    this.scrollBarWidget.setOffset(this.scrollBarWidget.getY() - (int) (verticalAmount * scrollSpeedFactor) - this.scrollBarWidget.getTopY());
    return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
  }

  @Override
  public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.getFocused() == this.searchTextField) {
      return this.searchTextField.keyPressed(keyCode, scanCode, modifiers) && this.searchTextField.isActive();
    } else {
      if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
        if (!this.craftButton.getKeyPressed()) {
          this.craftButton.setKeyPressed(true);
        }
      }
      return super.keyPressed(keyCode, scanCode, modifiers);
    }
  }

  @Override
  public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
    if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_SPACE) {
      this.craftButton.setKeyPressed(false);
    }
    return super.keyReleased(keyCode, scanCode, modifiers);
  }

  @Override
  protected void setInitialFocus() {
    this.setInitialFocus(this.craftButton);
  }

  @Override
  protected void init() {
    super.init();
    this.titleX = (107 + backgroundWidth - textRenderer.getWidth(title)) / 2;
    this.playerInventoryTitleX = 107;

    this.progressBarWidget.setX(x + 108);
    this.progressBarWidget.setY(y + 52);
    this.progressBarWidget.setMaxSteps(currentRecipe.getSteps());
    this.addDrawableChild(progressBarWidget);
    this.lastStepTime = Util.getMeasuringTimeMs();
    this.lastOpacityTime = Util.getMeasuringTimeMs();

    this.craftButton.setX(x + 233);
    this.craftButton.setY(y + 19);
    this.addDrawableChild(this.craftButton);

    this.searchTextField.setX(x + 12);
    this.searchTextField.setY(y + 13);
    this.searchTextField.setPlaceholder(Text.literal("Search"));
    this.searchTextField.setFocusUnlocked(false);
    this.searchTextField.setEditableColor(Colors.LIGHT_GRAY);
    this.searchTextField.setUneditableColor(Colors.LIGHT_GRAY);
    this.searchTextField.setDrawsBackground(false);
    this.searchTextField.setMaxLength(50);
    this.addDrawableChild(this.searchTextField);
    this.searchTextField.setEditable(true);

    this.scrollBarWidget.setHeight(134);
    this.scrollBarWidget.setX(x + 94);
    this.scrollBarWidget.setY(y + 24);
    this.scrollBarWidget.setMaxY(y + 158);
    this.scrollBarWidget.setMinY(y + 24);
    this.scrollBarWidget.setOffset(0);
    this.addDrawableChild(scrollBarWidget);

    this.resultWidget.setX(x + 188);
    this.resultWidget.setY(y + 19);
    this.resultWidget.setItemStack(new ItemStack(
      currentRecipe.getResult().getLeft(),
      currentRecipe.getResult().getRight()
    ));
    this.addDrawableChild(resultWidget);

    int startX = x + 107;
    int spacingX = 21;
    int ingredientY = y + 23;

    int ingredientCount = currentRecipe.getIngredients().size();

    int offsetX = 0;
    if (ingredientCount == 1) {
      offsetX = spacingX * 2;
    } else if (ingredientCount == 2) {
      offsetX = spacingX;
    }

    int index = 0;
    for (Map.Entry<Item, Integer> ingredientEntry : currentRecipe.getIngredients().entrySet()) {
      if (index < ingredientWidgets.size()) {
        FabricatorIngredientWidget ingredientWidget = ingredientWidgets.get(index);
        ingredientWidget.setX(startX + (spacingX * index) + offsetX);
        ingredientWidget.setY(ingredientY);
        ingredientWidget.setItemStack(new ItemStack(
          ingredientEntry.getKey(),
          ingredientEntry.getValue()
        ));
        this.addDrawableChild(ingredientWidget);
        index++;
      }
    }

    for (FabricatorRecipeWidget recipeWidget : recipeWidgets) {
      this.addDrawableChild(recipeWidget);
    }

    this.currentRecipe = recipeWidgets.getFirst().getRecipe();

    this.updateButtonStatus();
    this.updateCraftingProgress();
  }

  @Override
  public void resize(MinecraftClient client, int width, int height) {

    int oldY = y;

    /*int oldMinY = this.scrollBarWidget.getMinY();
    int offsetY = this.scrollBarWidget.getY() - oldMinY;*/
    super.resize(client, width, height);
    int newY = y;
    this.scrollBarWidget.setTopY(this.y + 24);

    /*int newMinY = this.scrollBarWidget.getMinY();

    int minY = newMinY - oldMinY;
    this.scrollBarWidget.setOffset(offsetY);*/
    //this.scrollBarWidget.setY(y + newMinY + offsetY);
    //Stranded.LOGGER.debug("Resize oldY={}, newY={}, scrollBarY={}, minY={}, maxY={}", oldY, newY, newY + 24, this.scrollBarWidget.getMinY(), this.scrollBarWidget.getMaxY());
  }

  @Override
  protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight, 512, 256);
    context.getMatrices().push();
    context.getMatrices().translate(0.0F, 0.0F, 200.0F);
    context.drawText(Stranded.CLIENT.get().textRenderer, this.title, x + this.titleX, y + this.titleY, Colors.LIGHT_GRAY, true);
    context.drawText(Stranded.CLIENT.get().textRenderer, this.playerInventoryTitle, x + this.playerInventoryTitleX, y + this.playerInventoryTitleY, Colors.LIGHT_GRAY, true);
    context.getMatrices().pop();
  }

  @Override
  public void render(DrawContext context, int mouseX, int mouseY, float delta) {
    this.updateButtonStatus();
    this.updateScrollbarActiveState();
    super.render(context, mouseX, mouseY, delta);

    if (this.currentRecipe != this.lastRecipe) {
      this.lastRecipe = this.currentRecipe;
      this.ingredientWidgets.forEach(ingredientWidget -> {
        ingredientWidget.setItemStack(new ItemStack(Items.AIR, 0));
      });
      this.currentStep = 0;
    }

    recipeWidgets.forEach(recipeWidget -> {
      if (!this.searchTextField.getText().isBlank()) {
        if (recipeWidget.getRecipe().getTitle().toLowerCase().contains(this.searchTextField.getText().toLowerCase())) {
          recipeWidget.visible = true;
          recipeWidget.setHighlightedText(this.searchTextField.getText());
        } else {
          recipeWidget.visible = false;
        }
      } else {
        recipeWidget.visible = true;
        recipeWidget.setHighlightedText("");
      }
    });
    this.renderRecipeList(context);

    this.scrollBarWidget.setHeight(this.calculateScrollbarHeight());
    this.recipeListScrollOffset = (int) (this.scrollBarWidget.getScrollFactor() * -this.getScrollableContentHeight());

    int ingredientCount = currentRecipe.getIngredients().size();

    int startX = x + 107;
    int spacingX = 21;

    int offsetX = 0;
    if (ingredientCount == 1) {
      offsetX = spacingX * 2;
    } else if (ingredientCount == 2) {
      offsetX = spacingX;
    }

    int index = 0;
    for (Map.Entry<Item, Integer> ingredientEntry : this.currentRecipe.getIngredients().entrySet()) {
      if (index < this.ingredientWidgets.size()) {
        FabricatorIngredientWidget ingredientWidget = this.ingredientWidgets.get(index);
        ingredientWidget.setX(startX + (spacingX * index) + offsetX);
        ingredientWidget.setItemStack(new ItemStack(
          ingredientEntry.getKey(),
          ingredientEntry.getValue()
        ));
        index++;
      }
    }

    this.resultWidget.setItemStack(new ItemStack(
      this.currentRecipe.getResult().getLeft(),
      this.currentRecipe.getResult().getRight()
    ));

    this.updateCraftingProgress();
    this.drawMouseoverTooltip(context, mouseX, mouseY);
  }

  private void updateCraftingProgress() {
    if (!this.craftButton.active) {
      this.currentStep = 0;
    }

    if (this.craftButton.isInteracted() && this.craftButton.active) {
      long currentStepTime = Util.getMeasuringTimeMs();
      if (currentStepTime - this.lastStepTime >= 250) {
        this.lastStepTime = currentStepTime;
        this.currentStep++;
        this.hasFinished = false;
        this.progressBarWidget.setOpacity(1.0);
        this.craftButton.setOpacity(1.0);
        Stranded.CLIENT.get().getSoundManager().play(PositionedSoundInstance.master(ModSounds.FABRICATOR_USE, 1.0F));
      }
    }

    if (this.currentStep == this.currentRecipe.getSteps()) {
      if (!this.craftButton.isInteracted()) {
        this.currentStep = 0;
      }

      if (!this.hasFinished) {
        this.hasFinished = true;
        ClientPlayNetworking.send(new FabricatorCraftingFinishedC2SPayload(blockEntity.getPos(), currentRecipe));
      }
    }

    if (this.currentStep > this.currentRecipe.getSteps()) {
      this.currentStep = 1;
    }

    long currentOpacityTime = Util.getMeasuringTimeMs();
    if (currentOpacityTime - this.lastOpacityTime >= 25) {
      this.lastOpacityTime = currentOpacityTime;
      this.progressBarWidget.setOpacity(Math.max(0.6, this.progressBarWidget.getOpacity() - 0.04));
      this.craftButton.setOpacity(Math.max(0.6, this.craftButton.getOpacity() - 0.04));
    }

    this.progressBarWidget.setProgress((double) this.currentStep / this.currentRecipe.getSteps());
    this.progressBarWidget.setMaxSteps(currentRecipe.getSteps());
  }

  private void updateButtonStatus() {
    this.craftButton.active = InventoryUtils.hasIngredients(this.playerInventory, this.currentRecipe) && InventoryUtils.hasInventorySpace(this.playerInventory, this.currentRecipe);
  }

  private void renderRecipeList(DrawContext context) {
    int minusIndex = 0;
    for (int index = 0; index < recipeWidgets.size(); index++) {
      FabricatorRecipeWidget recipeWidget = this.recipeWidgets.get(index);
      if (!recipeWidget.visible) {
        minusIndex++;
        continue;
      }
      recipeWidget.setClipping(this.x + 11, this.y + 24, this.x + 92, this.y + 158);
      recipeWidget.setX(this.x + 11);
      recipeWidget.setY(this.y + this.recipeListScrollOffset + 24 + (index - minusIndex) * 18);
    }
    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, this.x+ 10, this.y + 23, 10, 169, 83, 2, 512, 256);
    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, this.x+ 10, this.y + 157, 10, 171, 83, 2, 512, 256);
  }

  private int getScrollableContentHeight() {
    return recipeWidgets.size() * 18 - 134;
  }

  private int calculateScrollbarHeight() {
    int scrollableHeight = getScrollableContentHeight();
    if (scrollableHeight <= 0) {
      return 134;
    } else {
      float ratio = (float) 134 / (recipeWidgets.size() * 18);
      int scrollbarHeight = (int) (134 * ratio);
      return Math.max(scrollbarHeight, 16);
    }
  }

  private void updateScrollbarActiveState() {
    this.scrollBarWidget.active = getScrollableContentHeight() > 0;
  }
}
