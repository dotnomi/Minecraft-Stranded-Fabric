package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class FabricatorButtonWidget extends ClickableWidget {
  private static final Identifier TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/stranded_crafter.png");

  private static final int WIDTH = 36;
  private static final int HEIGHT = 49;

  private static final int U = 320;
  private static final int V = 0;

  private boolean buttonPressed = false;
  private boolean keyPressed = false;
  private double opacity;

  public FabricatorButtonWidget(int x, int y) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    opacity = 1.0;
  }

  public double getOpacity() {
    return this.opacity;
  }

  public void setOpacity(double opacity) {
    this.opacity = opacity;
  }

  public boolean getKeyPressed() {
    return this.keyPressed;
  }

  public void setKeyPressed(boolean keyPressed) {
    this.keyPressed = keyPressed;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    int colorWithOpacity = ColorHelper.withAlpha((int) (0xFF * opacity), 0xFFFFFF);
    if (this.active) {
      if (isHovered() || this.buttonPressed || this.keyPressed) {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U + WIDTH, V, getWidth(), getHeight(), 512, 256, colorWithOpacity);
      } else {
        context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U + WIDTH * 2, V, getWidth(), getHeight(), 512, 256);
      }
    } else {
      context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, getX(), getY(), U, V, getWidth(), getHeight(), 512, 256);
    }
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }

  @Override
  public void onClick(double mouseX, double mouseY) {
    if (!this.buttonPressed) {
      this.buttonPressed = true;
    }
  }

  @Override
  public void onRelease(double mouseX, double mouseY) {
    this.buttonPressed = false;
  }

  public boolean isInteracted() {
    return this.buttonPressed || this.keyPressed;
  }
}
