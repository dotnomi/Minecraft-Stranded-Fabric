package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class FabricatorScrollBarWidget extends ClickableWidget {
  private static final Identifier TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/stranded_crafter.png");
  private static final int WIDTH = 6;
  private static final int HEIGHT = 134;
  private static final int U = 314;
  private static final int ACTIVE_V = 5;
  private static final int INACTIVE_V = 0;

  private int scrollBarHeight;
  private int scrollBarOffset;

  private int topY;
  private int maxY;
  private int minY;
  private int mouseClickOffset;
  private int offset;

  public FabricatorScrollBarWidget(int x, int y) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    this.maxY = 256;
    this.minY = 0;
    this.topY = y;
    this.offset = 0;
    this.scrollBarHeight = HEIGHT;
    this.scrollBarOffset = 0;
  }

  public double getScrollFactor() {
    return (double) (this.getY() - this.minY) / (this.maxY - getHeight() - this.minY);
  }

  public int getTopY() {
    return this.topY;
  }

  public int setTopY(int topY) {
    return this.topY = topY;
  }

  public void setMaxY(int maxY) {
    this.maxY = maxY;
  }

  public int getMaxY() {
    return this.maxY;
  }

  public int getMinY() {
    return this.minY;
  }

  public void setMinY(int minY) {
    this.minY = minY;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    context.fill(this.getX(), this.getTopY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), Colors.RED);
    this.setY(Math.clamp(this.topY + this.offset, this.minY, this.maxY - getHeight()));
    if (this.active) {
      this.drawScrollbar(context, ACTIVE_V);
    } else {
      this.drawScrollbar(context, INACTIVE_V);
    }
  }

  @Override
  protected void drawScrollableText(DrawContext context, TextRenderer textRenderer, int xMargin, int color) {
    super.drawScrollableText(context, textRenderer, xMargin, color);
  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    this.mouseClickOffset = Math.clamp((int) mouseY - getY(), 0, this.getHeight() - 1);
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    this.setOffset((int) (mouseY - this.mouseClickOffset - this.topY));
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }

  private void drawScrollbar(DrawContext context, int vOffset) {
    context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U, vOffset, getWidth(), 2, 512, 256);
    for (int y = 2; y < getHeight() - 2; y++) {
      context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY() + y, U, vOffset + 2, getWidth(), 1, 512, 256);
    }
    context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY() + getHeight() - 2, U, vOffset + 3, getWidth(), 2, 512, 256);
  }
}
