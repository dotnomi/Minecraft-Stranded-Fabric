package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.dto.FabricatorRecipeGroup;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class FabricatorRecipeGroupWidget extends ClickableWidget {
  private static final Identifier TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/fabricator.png");
  private static final int WIDTH = 81;
  private static final int HEIGHT = 12;
  private static final int U = 364;
  private static final int NORMAL_V = 85;
  private static final int HOVERED_V = 97;

  private final FabricatorRecipeGroup recipeGroup;

  private boolean isRecipeGroupVisible = true;
  private int clippingStartX = 0;
  private int clippingEndX = 0;
  private int clippingStartY = 0;
  private int clippingEndY = 0;

  public FabricatorRecipeGroupWidget(int x, int y, FabricatorRecipeGroup recipeGroup) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    this.recipeGroup = recipeGroup;
  }

  public FabricatorRecipeGroup getRecipeGroup() {
    return recipeGroup;
  }

  public boolean getIsRecipeGroupVisible() {
    return isRecipeGroupVisible;
  }

  public void setClipping(int startX, int startY, int endX, int endY) {
    this.clippingStartX = startX;
    this.clippingEndX = endX;
    this.clippingStartY = startY;
    this.clippingEndY = endY;
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

    TextRenderer textRenderer = Stranded.CLIENT.get().textRenderer;
    Text iconText;
    if (this.getIsRecipeGroupVisible()) {
      iconText = Text.literal("-");
    } else {
      iconText = Text.literal("+");
    }
    context.drawText(textRenderer, iconText, getRight() - textRenderer.getWidth(iconText) - 2, getY() + 2, Colors.WHITE, true);
    drawScrollableText(context, Stranded.CLIENT.get().textRenderer, Text.translatable(this.recipeGroup.getTitle()), getX() + 2, getY(), getRight() - textRenderer.getWidth(iconText) - 4, getBottom(), Colors.WHITE);
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
      this.isRecipeGroupVisible = !this.isRecipeGroupVisible;
    }
  }

  private boolean isMouseInClippingZone(double mouseX, double mouseY) {
    return (mouseX >= this.clippingStartX && mouseX <= this.clippingEndX) && (mouseY >= this.clippingStartY && mouseY <= this.clippingEndY);
  }
}
