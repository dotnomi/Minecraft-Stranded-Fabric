package com.dotnomi.stranded.client.render;

import com.dotnomi.stranded.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static com.dotnomi.stranded.Stranded.CLIENT;
import static com.dotnomi.stranded.Stranded.LOGGER;

public class CableRenderer implements WorldRenderEvents.Last {
  private static boolean test = true;
  private static int count = 0;
  private static Vec3d currentCableEndPoint = Vec3d.ZERO; // Aktuelle Endposition des Kabels (wird smooth bewegt)
  private static final double SMOOTHING_FACTOR = 0.01;


  @Override
  public void onLast(WorldRenderContext worldRenderContext) {
    BlockPos start1 = new BlockPos(70, 58, 60);
    BlockPos end1 = new BlockPos(64, 58, 60);
    BlockPos start2 = new BlockPos(70, 59, 60);
    BlockPos end2 = new BlockPos(64, 59, 54);
    BlockPos start3 = new BlockPos(70, 60, 60);
    BlockPos end3 = new BlockPos(70, 60, 54);
    BlockPos start4 = new BlockPos(70, 64, 60);
    BlockPos end4 = new BlockPos(64, 59, 60);

    int viewDistance = CLIENT.get().options.getViewDistance().getValue() * 16;
    int startFadeDistance = (int) (viewDistance * 0.8);
    int playerDistance = (int) getDistanceToPlayer(start1.toCenterPos(), end1.toCenterPos());

    int alpha = 255;
    if (playerDistance >= startFadeDistance) {
      if (playerDistance > viewDistance) {
        alpha = 0;
      } else {
        double percentage = (double) (playerDistance - startFadeDistance) / (viewDistance - startFadeDistance);
        alpha = (int) (255 * (1 - percentage));
      }
    }

    double pixel = 1.0 / 16.0;
    Color black = new Color(21, 21, 21, alpha);
    Color hologram = new Color(241, 107, 39, 125);
    Color hologramError = new Color(241, 39, 56, 125);

    int cableMaxLength = 2 * 16;

    if (playerDistance < viewDistance) {
      if (Objects.requireNonNull(CLIENT.get().player).getMainHandStack().getItem() == ModItems.ACRYLITE) {
        Vec3d targetPlayerPos = Objects.requireNonNull(CLIENT.get().player).getPos().add(0,1,0);

        Color hologramColor = hologram;
        if (currentCableEndPoint == Vec3d.ZERO) {
          if (start1.toCenterPos().distanceTo(targetPlayerPos) <= cableMaxLength) {
            currentCableEndPoint = targetPlayerPos;
          }
        }

        if (start1.toCenterPos().distanceTo(targetPlayerPos) <= cableMaxLength) {
          Vec3d movement = targetPlayerPos.subtract(currentCableEndPoint).multiply(SMOOTHING_FACTOR);
          currentCableEndPoint = currentCableEndPoint.add(movement);
        } else {
          Vec3d direction = targetPlayerPos.subtract(start1.toCenterPos()).normalize();
          Vec3d errorTargetPos = start1.toCenterPos().add(direction.multiply(cableMaxLength));
          Vec3d movement = errorTargetPos.subtract(currentCableEndPoint).multiply(SMOOTHING_FACTOR);
          currentCableEndPoint = currentCableEndPoint.add(movement);
          hologramColor = hologramError;
        }

        renderParabolicCableSegment(Objects.requireNonNull(worldRenderContext.matrixStack()), hologramColor, start1.toCenterPos(), currentCableEndPoint, 12 * (1.0/16), 8);
      }
      renderParabolicCableSegment(Objects.requireNonNull(worldRenderContext.matrixStack()), black, start1.toCenterPos().add(0,-4 * pixel,0), end1.toCenterPos().add(0,-4 * pixel,0), 12 * pixel, 8);
      renderParabolicCableSegment(Objects.requireNonNull(worldRenderContext.matrixStack()), black, start2.toCenterPos().add(0,-4 * pixel,0), end2.toCenterPos().add(0,-4 * pixel,0), 12 * pixel, 8);
      renderParabolicCableSegment(Objects.requireNonNull(worldRenderContext.matrixStack()), black, start3.toCenterPos().add(0,-4 * pixel,0), end3.toCenterPos().add(0,-4 * pixel,0), 12 * pixel, 8);
      renderParabolicCableSegment(Objects.requireNonNull(worldRenderContext.matrixStack()), black, start4.toCenterPos().add(0,-4 * pixel,0), end4.toCenterPos().add(0,-4 * pixel,0), 12 * pixel, 8);
      //renderLine(Objects.requireNonNull(worldRenderContext.matrixStack()), orange, startPos.toCenterPos(), endPos.toCenterPos());

      //renderLine(Objects.requireNonNull(worldRenderContext.matrixStack()), black, startPos.toCenterPos().add(part,part,part), endPos.toCenterPos().add(part,part,part));
    }
  }

  private static Vec3d transformVec3d(Vec3d in) {
    Camera camera = CLIENT.get().gameRenderer.getCamera();
    Vec3d camPos = camera.getPos();
    return in.subtract(camPos);
  }

  private double getDistanceToPlayer(Vec3d startPos, Vec3d endPos) {
    Camera camera = CLIENT.get().gameRenderer.getCamera();
    Vec3d camPos = camera.getPos();

    double distanceToStart = camPos.distanceTo(startPos);
    double distanceToEnd = camPos.distanceTo(endPos);

    return distanceToStart + distanceToEnd / 2;
  }


  private static void genericAABBRender(VertexFormat.DrawMode mode, VertexFormat format, ShaderProgramKey shader, Matrix4f stack, Vec3d start, Vec3d dimensions, Color color, RenderAction action) {
    float red = color.getRed() / 255f;
    float green = color.getGreen() / 255f;
    float blue = color.getBlue() / 255f;
    float alpha = color.getAlpha() / 255f;
    Vec3d vec3d = transformVec3d(start);
    Vec3d end = vec3d.add(dimensions);
    float x1 = (float) vec3d.x;
    float y1 = (float) vec3d.y;
    float z1 = (float) vec3d.z;
    float x2 = (float) end.x;
    float y2 = (float) end.y;
    float z2 = (float) end.z;
    useBuffer(mode, format, shader, bufferBuilder -> action.run(bufferBuilder, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, stack));
  }

  private static void useBuffer(VertexFormat.DrawMode mode, VertexFormat format, ShaderProgramKey shader, Consumer<BufferBuilder> runner) {
    Tessellator tessellator = Tessellator.getInstance();
    BufferBuilder bufferBuilder = tessellator.begin(mode, format);

    runner.accept(bufferBuilder);

    setupRender();
    RenderSystem.setShader(shader);
    draw(bufferBuilder);
    endRender();
  }

  public static void draw(BufferBuilder builder) {
    BufferRenderer.drawWithGlobalProgram(builder.end());
  }

  private static void endRender() {
    RenderSystem.enableCull();
    RenderSystem.disableBlend();
  }

  private static void setupRender() {
    RenderSystem.enableBlend();
    RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    RenderSystem.enableDepthTest();
    RenderSystem.depthFunc(GL11.GL_LEQUAL);
    RenderSystem.disableCull();
  }

  public static void renderLine(MatrixStack matrices, Color color, Vec3d start, Vec3d end) {
    Matrix4f s = matrices.peek().getPositionMatrix();
    genericAABBRender(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR, ShaderProgramKeys.POSITION_COLOR, s, start, end.subtract(start), color, (buffer, x, y, z, x1, y1, z1, red, green, blue, alpha, matrix) -> {
      buffer.vertex(matrix, x, y, z).color(red, green, blue, alpha);
      buffer.vertex(matrix, x1, y1, z1).color(red, green, blue, alpha);
    });
  }

  public static List<Vec3d> generateParabolicCablePoints(Vec3d startPos, Vec3d endPos, double yOffset, int segmentCount) {
    List<Vec3d> points = new ArrayList<>();
    if (segmentCount <= 1) {
      points.add(startPos);
      points.add(endPos);
      return points;
    }

    Vec3d direction = endPos.subtract(startPos);
    for (int i = 0; i < segmentCount; i++) {
      double t = (double) i / (segmentCount - 1);
      Vec3d straightPoint = startPos.add(direction.multiply(t));
      // Parabelberechnung für die Y-Richtung (hängt nach unten)
      double sagOffsetY = yOffset * (1 - Math.pow(2 * t - 1, 2)); // Parabelform: y = offset * (1 - (2t-1)^2)
      Vec3d parabolicPoint = straightPoint.add(new Vec3d(0, -sagOffsetY, 0)); // Negatives Y für "nach unten hängen"
      points.add(parabolicPoint);
    }
    return points;
  }

  public void renderParabolicCableSegment(MatrixStack matrices, Color color, Vec3d startPos, Vec3d endPos, double yOffset, int segmentCount) {
    List<Vec3d> cablePoints = generateParabolicCablePoints(startPos, endPos, yOffset, segmentCount);
    for (int i = 0; i < cablePoints.size() - 1; i++) {
      renderCableSegment(matrices, color, cablePoints.get(i), cablePoints.get(i + 1));
    }
  }

  public static void renderCableSegment(MatrixStack matrices, Color color, Vec3d start, Vec3d end) {
    Matrix4f s = matrices.peek().getPositionMatrix();
    Vec3d dimensions = end.subtract(start);
    genericAABBRender(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR, ShaderProgramKeys.POSITION_COLOR, s, start, dimensions, color, (buffer, x, y, z, x1, y1, z1, red, green, blue, alpha, matrix) -> {
      float pixel = (float) (1.0 / 16.0);
      float cableThickness = (float) (0.5 * pixel);

      Vec3d a = new Vec3d(x, y, z);
      Vec3d b = new Vec3d(x1, y1, z1);
      Vec3d cableDirection = b.subtract(a).normalize();
      double cableLength = b.subtract(a).length();

      Vec3d planeWidthDirection;
      if (Math.abs(cableDirection.y) < 0.99) { // Y-Axis as up direction
        planeWidthDirection = cableDirection.crossProduct(new Vec3d(0, 1, 0)).normalize();
      } else { // X-Axis as up direction
        planeWidthDirection = cableDirection.crossProduct(new Vec3d(1, 0, 0)).normalize();
      }
      Vec3d planeHeightDirection = cableDirection.crossProduct(planeWidthDirection).normalize();

      if (test) {
        if (count >= 4) {
          test = false;
        } else {
          count++;
          LOGGER.debug("direction {}", cableDirection);
        }
      }

      List<Vec3d> vertices = List.of(
        /* ---+ Plane 1 +--- */
        a.add(planeWidthDirection.multiply(cableThickness)).add(planeHeightDirection.multiply(-cableThickness)).add(cableDirection.multiply(-.005)), // Bottom left
        a.add(planeWidthDirection.multiply(-cableThickness)).add(planeHeightDirection.multiply(cableThickness)).add(cableDirection.multiply(-.005)), // Top left
        a.add(planeWidthDirection.multiply(-cableThickness)).add(planeHeightDirection.multiply(cableThickness)).add(cableDirection.multiply(cableLength)).add(cableDirection.multiply(.005)), // Top right
        a.add(planeWidthDirection.multiply(cableThickness)).add(planeHeightDirection.multiply(-cableThickness)).add(cableDirection.multiply(cableLength)).add(cableDirection.multiply(.005)), // Bottom right
        /* ---+ Plane 2 +--- */
        a.add(planeWidthDirection.multiply(-cableThickness)).add(planeHeightDirection.multiply(-cableThickness)).add(cableDirection.multiply(-.005)), // Bottom left
        a.add(planeWidthDirection.multiply(cableThickness)).add(planeHeightDirection.multiply(cableThickness)).add(cableDirection.multiply(-.005)), // Top left
        a.add(planeWidthDirection.multiply(cableThickness)).add(planeHeightDirection.multiply(cableThickness)).add(cableDirection.multiply(cableLength)).add(cableDirection.multiply(.005)), // Top right
        a.add(planeWidthDirection.multiply(-cableThickness)).add(planeHeightDirection.multiply(-cableThickness)).add(cableDirection.multiply(cableLength)).add(cableDirection.multiply(.005)) // Bottom right
      );

      for (Vec3d vertex : vertices) {
        buffer.vertex(matrix, (float) vertex.x, (float) vertex.y, (float) vertex.z).color(red, green, blue, alpha).texture(0f, 0f).light(0).normal(0, 0, 1);
      }
    });
  }

  interface RenderAction {
    void run(BufferBuilder buffer, float x, float y, float z, float x1, float y1, float z1, float red, float green, float blue, float alpha, Matrix4f matrix);
  }
}
