package io.github.christechs.psm.ui;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.christechs.clayj.LayoutResults;
import io.github.christechs.clayj.core.RenderCommand;
import io.github.christechs.clayj.math.BoundingBox;
import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;
import org.joml.Matrix3x2fc;
import org.jspecify.annotations.Nullable;

public class ClayRenderer {

    public static void draw(GuiGraphicsExtractor extractor, LayoutResults results) {
        Minecraft mc = Minecraft.getInstance();

        for (int i = 0; i < results.length(); i++) {
            RenderCommand cmd = results.get(i);
            BoundingBox box = cmd.boundingBox;

            switch (cmd.commandType) {
                case RECTANGLE:
                    renderRectangle(extractor, cmd, box, mc);
                    break;
                case BORDER:
                    renderBorder(extractor, cmd, box);
                    break;
                case IMAGE:
                    renderImage(extractor, cmd, box);
                    break;
                case TEXT:
                    renderText(extractor, cmd, box, mc);
                    break;
                case CUSTOM:
                    renderCustomItem(extractor, cmd, box, mc);
                    break;
                case SCISSOR_START:
                    extractor.enableScissor(
                            (int) Math.round(box.x),
                            (int) Math.round(box.y),
                            (int) Math.round(box.x + box.width),
                            (int) Math.round(box.y + box.height)
                    );
                    break;
                case SCISSOR_END:
                    extractor.disableScissor();
                    break;
            }
        }
    }

    private static void renderRectangle(GuiGraphicsExtractor extractor, RenderCommand cmd, BoundingBox box, Minecraft mc) {
        Color bgColor = cmd.renderData.backgroundColor;
        CornerRadius cr = cmd.renderData.cornerRadius;

        int colorInt = colorToInt(bgColor);
        if (bgColor.a <= 0) return;

        float x = box.x;
        float y = box.y;
        float w = box.width;
        float h = box.height;

        if (cr != null && (cr.topLeft > 0 || cr.topRight > 0 || cr.bottomLeft > 0 || cr.bottomRight > 0)) {
            float maxR = Math.min(w / 2f, h / 2f);
            float rTL = Math.min(cr.topLeft, maxR);
            float rTR = Math.min(cr.topRight, maxR);
            float rBL = Math.min(cr.bottomLeft, maxR);
            float rBR = Math.min(cr.bottomRight, maxR);

            float guiScale = (float) mc.getWindow().getGuiScale();

            extractor.guiRenderState.addGuiElement(new AARoundedRectRenderState(
                    RenderPipelines.GUI, TextureSetup.noTexture(), new Matrix3x2f(extractor.pose()),
                    x, y, w, h, rTL, rTR, rBL, rBR, colorInt, guiScale, extractor.scissorStack.peek()
            ));
        } else {
            extractor.fill((int) x, (int) y, (int) (x + w), (int) (y + h), colorInt);
        }
    }

    private static void renderBorder(GuiGraphicsExtractor extractor, RenderCommand cmd, BoundingBox box) {
        int colorInt = colorToInt(cmd.renderData.borderColor);
        if (cmd.renderData.borderColor.a <= 0) return;

        int x = (int) box.x;
        int y = (int) box.y;
        int w = (int) box.width;
        int h = (int) box.height;

        int top = (int) cmd.renderData.borderWidth.top;
        int bottom = (int) cmd.renderData.borderWidth.bottom;
        int left = (int) cmd.renderData.borderWidth.left;
        int right = (int) cmd.renderData.borderWidth.right;

        if (top > 0) extractor.fill(x, y, x + w, y + top, colorInt);
        if (bottom > 0) extractor.fill(x, y + h - bottom, x + w, y + h, colorInt);
        if (left > 0) extractor.fill(x, y, x + left, y + h, colorInt);
        if (right > 0) extractor.fill(x + w - right, y, x + w, y + h, colorInt);
    }

    private static void renderImage(GuiGraphicsExtractor extractor, RenderCommand cmd, BoundingBox box) {
        if (!(cmd.renderData.imageData instanceof Identifier texture)) return;

        extractor.blit(
                RenderPipelines.GUI_TEXTURED,
                texture,
                (int) box.x,
                (int) box.y,
                0, 0,
                (int) box.width,
                (int) box.height,
                (int) box.width,
                (int) box.height
        );
    }

    private static void renderText(GuiGraphicsExtractor extractor, RenderCommand cmd, BoundingBox box, Minecraft mc) {
        CharSequence fullText = cmd.renderData.text;
        int start = cmd.renderData.textStart;
        int length = cmd.renderData.textLength;
        String lineText = fullText.subSequence(start, start + length).toString();

        float textScale = cmd.renderData.fontSize > 0 ? cmd.renderData.fontSize : 1.0f;
        int colorInt = colorToInt(cmd.renderData.textColor);

        Matrix3x2fStack poseStack = extractor.pose();
        poseStack.pushMatrix();
        poseStack.translate(box.x, box.y);
        poseStack.scale(textScale, textScale);

        extractor.text(mc.font, lineText, 0, 0, colorInt, false);

        poseStack.popMatrix();
    }

    private static void renderCustomItem(GuiGraphicsExtractor extractor, RenderCommand cmd, BoundingBox box, Minecraft mc) {
        if (!(cmd.renderData.customData instanceof ItemStack item)) return;

        extractor.item(item, (int) box.x, (int) box.y);
        extractor.itemDecorations(mc.font, item, (int) box.x, (int) box.y);
    }

    private static int colorToInt(Color color) {
        return ((int) color.a << 24) | ((int) color.r << 16) | ((int) color.g << 8) | (int) color.b;
    }

    @Environment(EnvType.CLIENT)
    public record AARoundedRectRenderState(
            RenderPipeline pipeline,
            TextureSetup textureSetup,
            Matrix3x2fc pose,
            float x, float y, float w, float h,
            float rTL, float rTR, float rBL, float rBR,
            int color,
            float guiScale,
            @Nullable ScreenRectangle scissorArea,
            @Nullable ScreenRectangle bounds
    ) implements GuiElementRenderState {

        public AARoundedRectRenderState(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fc pose, float x, float y, float w, float h, float rTL, float rTR, float rBL, float rBR, int color, float guiScale, @Nullable ScreenRectangle scissorArea) {
            this(pipeline, textureSetup, pose, x, y, w, h, rTL, rTR, rBL, rBR, color, guiScale, scissorArea, getBounds(x, y, x + w, y + h, pose, scissorArea));
        }

        private static @Nullable ScreenRectangle getBounds(float x0, float y0, float x1, float y1, Matrix3x2fc pose, @Nullable ScreenRectangle scissorArea) {
            ScreenRectangle bounds = (new ScreenRectangle((int) x0, (int) y0, (int) (x1 - x0), (int) (y1 - y0))).transformMaxBounds(pose);
            return scissorArea != null ? scissorArea.intersection(bounds) : bounds;
        }

        @Override
        public void buildVertices(VertexConsumer vc) {
            float maxRTop = Math.max(rTL, rTR);
            float maxRBot = Math.max(rBL, rBR);

            if (h - maxRTop - maxRBot > 0) {
                fillQuad(vc, x, y + maxRTop, x + w, y + h - maxRBot, color);
            }

            if (maxRTop > 0) {
                fillQuad(vc, x + rTL, y, x + w - rTR, y + maxRTop, color);
            }

            if (maxRBot > 0) {
                fillQuad(vc, x + rBL, y + h - maxRBot, x + w - rBR, y + h, color);
            }

            if (rTL > 0) drawCorner(vc, x + rTL, y + rTL, rTL, -1, -1);
            if (rTR > 0) drawCorner(vc, x + w - rTR, y + rTR, rTR, 1, -1);
            if (rBL > 0) drawCorner(vc, x + rBL, y + h - rBL, rBL, -1, 1);
            if (rBR > 0) drawCorner(vc, x + w - rBR, y + h - rBR, rBR, 1, 1);
        }

        private void drawCorner(VertexConsumer vc, float cx, float cy, float r, int dx, int dy) {
            int physicalR = (int) Math.ceil(r * guiScale);
            float pcx = cx * guiScale;
            float pcy = cy * guiScale;

            for (int j = 0; j < physicalR; j++) {
                float py = j + 0.5f;
                float pySq = py * py;

                float maxDistForSolid = physicalR - 0.5f;
                float maxDistSq = maxDistForSolid * maxDistForSolid;

                int solidI = 0;
                if (maxDistSq > pySq) {
                    float maxPx = (float) Math.sqrt(maxDistSq - pySq);
                    solidI = (int) (maxPx - 0.5f);
                }
                solidI = Math.clamp(solidI, 0, physicalR);

                if (solidI > 0) {
                    float drawX_start = (pcx + (dx == 1 ? 0 : -solidI)) / guiScale;
                    float drawX_end = (pcx + (dx == 1 ? solidI : 0)) / guiScale;
                    float drawY_start = (pcy + (dy == 1 ? j : -j - 1)) / guiScale;
                    float drawY_end = drawY_start + (1.0f / guiScale);

                    float minX = Math.min(drawX_start, drawX_end);
                    float maxX = Math.max(drawX_start, drawX_end);
                    float minY = Math.min(drawY_start, drawY_end);
                    float maxY = Math.max(drawY_start, drawY_end);

                    fillQuad(vc, minX, minY, maxX, maxY, color);
                }

                for (int i = solidI; i < physicalR; i++) {
                    float px = i + 0.5f;
                    float dist = (float) Math.sqrt(px * px + pySq);

                    float alphaMult = physicalR - dist + 0.5f;
                    if (alphaMult <= 0) break;

                    float drawX_start = (pcx + (dx == 1 ? i : -i - 1)) / guiScale;
                    float drawX_end = drawX_start + (1.0f / guiScale);
                    float drawY_start = (pcy + (dy == 1 ? j : -j - 1)) / guiScale;
                    float drawY_end = drawY_start + (1.0f / guiScale);

                    float minX = Math.min(drawX_start, drawX_end);
                    float maxX = Math.max(drawX_start, drawX_end);
                    float minY = Math.min(drawY_start, drawY_end);
                    float maxY = Math.max(drawY_start, drawY_end);

                    if (alphaMult >= 1.0f) {
                        fillQuad(vc, minX, minY, maxX, maxY, color);
                    } else {
                        int originalAlpha = (color >> 24) & 0xFF;
                        int blendedAlpha = (int) (originalAlpha * alphaMult);
                        int blendedColor = (blendedAlpha << 24) | (color & 0x00FFFFFF);
                        fillQuad(vc, minX, minY, maxX, maxY, blendedColor);
                    }
                }
            }
        }

        private void fillQuad(VertexConsumer vc, float minX, float minY, float maxX, float maxY, int c) {
            vc.addVertexWith2DPose(pose, minX, minY).setColor(c);
            vc.addVertexWith2DPose(pose, minX, maxY).setColor(c);
            vc.addVertexWith2DPose(pose, maxX, maxY).setColor(c);
            vc.addVertexWith2DPose(pose, maxX, minY).setColor(c);
        }
    }
}