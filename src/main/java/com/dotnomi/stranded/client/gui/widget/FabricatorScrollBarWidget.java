package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FabricatorScrollBarWidget extends ClickableWidget {
  private static final Identifier TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/fabricator.png");
  private static final int WIDTH = 6;
  private static final int HEIGHT = 134;
  private static final int U = 314;
  private static final int ACTIVE_V = 5;
  private static final int INACTIVE_V = 0;

  private int scrollBarHeight;
  private int scrollBarOffset;
  private int mouseClickOffset;

  public FabricatorScrollBarWidget(int x, int y) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    this.scrollBarHeight = HEIGHT;
    this.scrollBarOffset = 0;
  }

  public double getScrollFactor() {
    return (double) scrollBarOffset / (this.getHeight() - this.scrollBarHeight);
  }

  public void setScrollBarHeight(int scrollBarHeight) {
    this.scrollBarHeight = scrollBarHeight;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    this.scrollBarOffset = Math.clamp(this.scrollBarOffset, 0, this.getHeight() - this.scrollBarHeight);
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
  public boolean mouseClicked(double mouseX, double mouseY, int button) {
    if (this.active && this.visible) {
      if (mouseY >= this.getY() + this.scrollBarOffset && mouseY <= this.getY() + this.scrollBarOffset + this.scrollBarHeight) {
        return super.mouseClicked(mouseX, mouseY, button);
      }
    }
    return false;
  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    this.mouseClickOffset = Math.clamp((int) mouseY - this.getY() - this.scrollBarOffset, 0, this.scrollBarHeight - 1);
  }

  @Override
  protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
    this.scrollBarOffset = (int) (mouseY - this.getY() - this.mouseClickOffset);
  }

  @Override
  public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
    double scrollSpeedFactor = Math.max(1.0, ((double) this.scrollBarHeight / this.getHeight()) * 8);
    this.scrollBarOffset -= (int) (verticalAmount * scrollSpeedFactor);
    return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }

  private void drawScrollbar(DrawContext context, int vOffset) {
    int y = this.getY() + this.scrollBarOffset;
    context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), y, U, vOffset, getWidth(), 2, 512, 256);
    for (int yOffset = 2; yOffset < this.scrollBarHeight - 2; yOffset++) {
      context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), y + yOffset, U, vOffset + 2, getWidth(), 1, 512, 256);
    }
    context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), y + this.scrollBarHeight - 2, U, vOffset + 3, getWidth(), 2, 512, 256);
  }
}
