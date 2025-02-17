package com.dotnomi.stranded.client.gui.widget;

import com.dotnomi.stranded.Stranded;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

public class FabricatorIngredientWidget extends ClickableWidget {
  private static final Identifier BACKGROUND_TEXTURE = Identifier.of(Stranded.MOD_ID, "textures/gui/container/fabricator.png");
  private static final int WIDTH = 18;
  private static final int HEIGHT = 18;
  private static final int U = 320;
  private static final int V = 49;

  private final TextRenderer textRenderer;
  private ItemStack itemStack;

  public FabricatorIngredientWidget(int x, int y, ItemStack itemStack) {
    super(x, y, WIDTH, HEIGHT, Text.empty());
    textRenderer = Stranded.CLIENT.get().textRenderer;
    this.itemStack = itemStack;
    active = false;
  }

  @Override
  protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
    if (itemStack.getItem() == Items.AIR || itemStack.getCount() <= 0) {
      return;
    }

    context.drawTexture(RenderLayer::getGuiTextured, BACKGROUND_TEXTURE, getX(), getY(), U, V, getWidth(), getHeight(), 512, 256);
    context.drawItem(itemStack, getX() + 1, getY() + 1);
    drawIngredientInfo(context, getX(), getY());
  }

  @Override
  protected void appendClickableNarrations(NarrationMessageBuilder builder) {

  }

  public void setItemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
  }

  private void drawIngredientInfo(DrawContext context, int x, int y) {
    context.getMatrices().push();
    context.getMatrices().translate(0.0F, 0.0F, 200.0F);
    context.drawText(textRenderer, String.valueOf(itemStack.getCount()), x + 19 - 2 - textRenderer.getWidth(String.valueOf(itemStack.getCount())), y + 6 + 3, Colors.WHITE, true);
    context.getMatrices().pop();
  }
}
