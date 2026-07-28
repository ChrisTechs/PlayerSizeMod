package io.github.christechs.psm.ui.base;

import io.github.christechs.clayj.ClayJ;
import io.github.christechs.clayj.LayoutResults;
import io.github.christechs.clayj.math.Dimensions;
import io.github.christechs.clayj.math.Vector2;
import io.github.christechs.psm.ui.ClayRenderer;
import io.github.christechs.psm.ui.components.ClayComponents;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;
import org.lwjgl.glfw.GLFW;

public abstract class ClayScreen extends Screen {

    protected boolean mouseClickedThisFrame = false;
    protected boolean doubleClickedThisFrame = false;
    protected boolean isMouseDown = false;
    protected boolean isControlDown = false;
    private boolean initialized = false;
    private long lastFrameTime = System.currentTimeMillis();
    private float scrollMomentum = 0f;
    private long lastClickTime = 0; // Added for robust double-click detection

    protected ClayScreen(Component title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();
        if (!initialized) {
            if (ClayJ.getContext() == null) {
                ClayJ.initialize(2048, 2048, new Dimensions(this.width, this.height));
                ClayJ.setMeasureTextFunction((text, start, len, config, outDimensions) -> {
                    String sub = text.subSequence(start, start + len).toString();
                    int scale = config.fontSize > 0 ? config.fontSize : 1;
                    int stringWidth = this.minecraft.font.width(sub) * scale;
                    int stringHeight = this.minecraft.font.lineHeight * scale;
                    outDimensions.set(stringWidth, stringHeight);
                });
            }
            initialized = true;
        }
    }

    @Override
    public void removed() {
        super.removed();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if (scrollY != 0) {
            scrollMomentum = (float) (scrollMomentum + Math.signum(scrollY) * 15f);
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTicks);

        long currentTime = System.currentTimeMillis();
        float deltaTime = (currentTime - lastFrameTime) / 1000f;
        lastFrameTime = currentTime;

        float scrollDeltaY = scrollMomentum;
        scrollMomentum *= 0.75f;
        if (Math.abs(scrollMomentum) < 0.1f) scrollMomentum = 0f;

        ClayComponents.updateInputState(mouseX, mouseY, this.mouseClickedThisFrame, this.doubleClickedThisFrame, this.isMouseDown);

        ClayJ.setLayoutDimensions(this.width, this.height);
        ClayJ.setPointerState(new Vector2(mouseX, mouseY), this.isMouseDown);
        ClayJ.updateScrollContainers(true, new Vector2(0, scrollDeltaY / 10.0f), deltaTime);

        ClayJ.beginLayout();
        buildLayout(mouseX, mouseY, deltaTime);
        LayoutResults results = ClayJ.endLayout();

        ClayRenderer.draw(graphics, results);

        this.mouseClickedThisFrame = false;
        this.doubleClickedThisFrame = false;
    }

    protected abstract void buildLayout(int mouseX, int mouseY, float deltaTime);

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == 0) {
            this.mouseClickedThisFrame = true;
            this.isMouseDown = true;

            long now = System.currentTimeMillis();
            if (doubleClick || (now - lastClickTime < 300)) {
                this.doubleClickedThisFrame = true;
            }
            lastClickTime = now;
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (event.button() == 0) {
            this.isMouseDown = false;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean charTyped(CharacterEvent event) {
        ClayComponents.onKeyTyped((char) event.codepoint(), -1, this.isControlDown);
        return super.charTyped(event);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_LEFT_CONTROL || event.key() == GLFW.GLFW_KEY_RIGHT_CONTROL) {
            this.isControlDown = true;
        }
        ClayComponents.onKeyTyped('\0', event.key(), this.isControlDown);
        return super.keyPressed(event);
    }

    @Override
    public boolean keyReleased(KeyEvent event) {
        if (event.key() == GLFW.GLFW_KEY_LEFT_CONTROL || event.key() == GLFW.GLFW_KEY_RIGHT_CONTROL) {
            this.isControlDown = false;
        }
        return super.keyReleased(event);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}