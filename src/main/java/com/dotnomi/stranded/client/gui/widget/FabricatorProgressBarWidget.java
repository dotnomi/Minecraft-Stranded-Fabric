package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class FabricatorProgressBarWidget extends ClickableWidget {
  private static final Identifier BACKGROUND_TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/fabricator.png");
  private static final int WIDTH = 105;
  private static final int HEIGHT = 15;
  private static final int OVERLAY_U = 106;
  private static final int OVERLAY_V = 169;

  private static final int ICON_WIDTH = 20;
  private static final int ICON_HEIGHT = 14;
  private static final int ICON_U = 288;
  private static final int ICON_V = 16;

  private final TextRenderer textRenderer;

  private double progress;
  private int maxSteps;
  private double opacity;

  public FabricatorProgressBarWidget(int x, int y) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    this.progress = 0.0;
    this.maxSteps = 1;
    this.opacity = 1.0;
    this.active = false;
    this.textRenderer = Stranded.CLIENT.get().textRenderer;
  }

  public void setProgress(double progress) {
    this.progress = Math.max(0.0, Math.min(1.0, progress));
  }

  public void setMaxSteps(int maxSteps) {
    this.maxSteps = maxSteps;
  }

  public double getOpacity() {
    return this.opacity;
  }

  public void setOpacity(double opacity) {
    this.opacity = opacity;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    int progressWidth = (int) (getWidth() * progress);
    context.fill(getX(), getY(), getX() + progressWidth, getY() + getHeight(), ColorHelper.withAlpha((int) (0xFF * opacity), 0xF16B27));
    int steps = (int) (maxSteps - Math.round(maxSteps * progress));
    String progressText = String.valueOf(steps == 0 ? maxSteps : steps);
    int textWidth = textRenderer.getWidth(progressText);
    int centeredWidth = (WIDTH - ICON_WIDTH - textWidth) / 2;
    context.drawText(this.textRenderer, progressText, getX() + centeredWidth + ICON_WIDTH + 2 + 1, getY() + 4 + 1, 0xFF000000, false);
    context.drawText(this.textRenderer, progressText, getX() + centeredWidth + ICON_WIDTH + 2, getY() + 4, 0xFFC6C6C6, false);
    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, getX() + centeredWidth, getY() + 1, ICON_U, ICON_V, ICON_WIDTH, ICON_HEIGHT, 512, 256);
    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, getX() - 2, getY() - 2, OVERLAY_U, OVERLAY_V, getWidth() + 4, getHeight() + 4, 512, 256);
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }
}
