package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.client.gui.screen.FabricatorScreen;
import com.dotnomi.stranded.dto.FabricatorRecipe;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class FabricatorRecipeWidget extends ClickableWidget {
  private static final Identifier TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/fabricator.png");
  private static final int WIDTH = 81;
  private static final int HEIGHT = 18;
  private static final int U = 364;
  private static final int NORMAL_V = 49;
  private static final int HOVERED_V = 67;

  private final FabricatorRecipe recipe;
  private final FabricatorScreen fabricatorScreen;

  private String highlightedText = "";
  private int maxCraftableAmount = 0;
  private int clippingStartX = 0;
  private int clippingEndX = 0;
  private int clippingStartY = 0;
  private int clippingEndY = 0;

  public FabricatorRecipeWidget(int x, int y, FabricatorRecipe recipe, FabricatorScreen fabricatorScreen) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    this.recipe = recipe;
    this.fabricatorScreen = fabricatorScreen;
  }

  public FabricatorRecipe getRecipe() {
    return recipe;
  }

  public void setHighlightedText(String highlightedText) {
    this.highlightedText = highlightedText;
  }

  public void setClipping(int startX, int startY, int endX, int endY) {
    this.clippingStartX = startX;
    this.clippingEndX = endX;
    this.clippingStartY = startY;
    this.clippingEndY = endY;
  }

  public void setMaxCraftableAmount(int maxCraftableAmount) {
    this.maxCraftableAmount = maxCraftableAmount;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    if (!this.visible) return;
    context.getMatrices().push();
    context.enableScissor( this.clippingStartX, this.clippingStartY, this.clippingEndX, this.clippingEndY);
    if (this.isHovered() && this.isMouseInClippingZone(mouseX, mouseY)) {
      context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U, HOVERED_V, getWidth(), getHeight(), 512, 256);
    } else {
      context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U, NORMAL_V, getWidth(), getHeight(), 512, 256);
    }

    context.drawItem(new ItemStack(this.recipe.getResult().getLeft()), getX() + 1, getY() + 1);
    context.getMatrices().pop();

    drawCraftableAmount(context, getX(), getY());

    context.getMatrices().push();
    String translatedRecipeTitle = I18n.translate(this.recipe.getTitle());
    MutableText recipeTitle = Text.literal("");
    int startIndex = translatedRecipeTitle.toLowerCase().indexOf(this.highlightedText.toLowerCase());
    if (startIndex != -1) {
      recipeTitle.append(Text.literal(translatedRecipeTitle.substring(0, startIndex)).formatted(Formatting.WHITE));
      recipeTitle.append(Text.literal(translatedRecipeTitle.substring(startIndex, startIndex + this.highlightedText.length())).formatted(Formatting.YELLOW));
      recipeTitle.append(Text.literal(translatedRecipeTitle.substring(startIndex + this.highlightedText.length())).formatted(Formatting.WHITE));
    } else {
      recipeTitle.append(Text.literal(translatedRecipeTitle).formatted(Formatting.WHITE));
    }

    drawScrollableText(context, Stranded.CLIENT.get().textRenderer, recipeTitle, getX() + 20, getY(), getRight() - 2, getBottom(), Colors.WHITE);
    context.disableScissor();
    context.getMatrices().pop();
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }

  @Override
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (isMouseInClippingZone(mouseX, mouseY)) {
      return super.mouseClicked(mouseX, mouseY, button);
    }
    return false;
  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    super.onClick(mouseX, mouseY);
    if (this.visible && this.active) {
      this.fabricatorScreen.changeRecipe(this.getRecipe());
    }
  }

  private boolean isMouseInClippingZone(double mouseX, double mouseY) {
    return (mouseX >= this.clippingStartX && mouseX <= this.clippingEndX) && (mouseY >= this.clippingStartY && mouseY <= this.clippingEndY);
  }

  private void drawCraftableAmount(DrawContext context, int x, int y) {
    TextRenderer textRenderer = Stranded.CLIENT.get().textRenderer;
    Text itemCount;
    int color = Colors.WHITE;
    if (maxCraftableAmount <= 0) {
      itemCount = Text.literal("0");
      color = Colors.LIGHT_RED;
    } else {
      itemCount = Text.literal(String.valueOf(maxCraftableAmount * this.getRecipe().getResult().getRight()));
    }
    context.getMatrices().push();
    context.getMatrices().translate(0.0F, 0.0F, 200.0F);
    if (textRenderer.getWidth(itemCount) >= 14) {
      drawScrollableText(context, textRenderer, itemCount, x + 1, y + 9, x + 16, y + 17, color);
    } else {
      context.drawText(textRenderer, itemCount, x + 17 - textRenderer.getWidth(itemCount), y + 9, color, true);
    }
    context.getMatrices().pop();
  }
}
