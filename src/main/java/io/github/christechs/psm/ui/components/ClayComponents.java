package io.github.christechs.psm.ui.components;

import io.github.christechs.clayj.config.LayoutConfigBuilder;
import io.github.christechs.clayj.core.ElementId;
import io.github.christechs.clayj.core.LayoutElementHashMapItem;
import io.github.christechs.clayj.core.ScrollContainerData;
import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;
import io.github.christechs.clayj.math.Vector2;
import io.github.christechs.clayj.util.HashUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static io.github.christechs.clayj.ClayJ.*;
import static io.github.christechs.clayj.enums.AttachToElement.PARENT;
import static io.github.christechs.clayj.enums.FloatingAttachPoint.LEFT_CENTER;
import static io.github.christechs.clayj.enums.FloatingAttachPoint.LEFT_TOP;

public class ClayComponents {

    private static final Map<String, ScrollBounds> scrollCache = new HashMap<>();
    public static int mouseDeltaY = 0;
    public static String activeDragElementId = "";
    public static int mouseX = 0, mouseY = 0;
    public static String activeTextInputId = "";
    public static StringBuilder activeText = new StringBuilder();
    public static Consumer<String> activeTextCallback = null;
    public static int cursorPos = 0;
    private static int previousMouseY = 0;

    private static boolean mouseClicked = false;
    private static boolean mouseDoubleClicked = false;
    private static boolean clickConsumed = false;

    public static void updateInputState(int x, int y, boolean clicked, boolean doubleClicked, boolean isMouseDown) {
        mouseDeltaY = y - previousMouseY;
        previousMouseY = y;
        mouseX = x;
        mouseY = y;
        mouseClicked = clicked;
        mouseDoubleClicked = doubleClicked;
        clickConsumed = false;

        if (!isMouseDown) {
            activeDragElementId = "";
        }
    }

    public static boolean isClicked(String id) {
        if (mouseClicked && !clickConsumed && pointerOver(id)) {
            clickConsumed = true;
            return true;
        }
        return false;
    }

    public static boolean isDoubleClicked(String id) {
        if (mouseDoubleClicked && !clickConsumed && pointerOver(id)) {
            clickConsumed = true;
            return true;
        }
        return false;
    }

    public static void button(String id, String text, Runnable onClick) {
        boolean hover = pointerOver(id);
        if (isClicked(id)) onClick.run();

        el(decl()
                        .id(id)
                        .bg(new Color(hover ? 100 : 70, 100, 200, 255))
                        .radius(new CornerRadius(6))
                        .layout(layout()
                                .padding(8, 4)
                                .alignCenter()),
                () -> text(text, txt().size(1).color(new Color(255, 255, 255, 255)))
        );
    }

    public static void tab(String id, String label, boolean active, Runnable onClick) {
        boolean hover = pointerOver(id);
        if (isClicked(id)) onClick.run();

        el(decl()
                        .id(id)
                        .bg(new Color(
                                active ? 60 : (hover ? 40 : 30),
                                active ? 120 : (hover ? 45 : 32),
                                active ? 220 : (hover ? 50 : 38),
                                255
                        )).radius(new CornerRadius(6))
                        .layout(layout()
                                .widthGrow().heightFixed(30)
                                .padding(10, 0)
                                .alignLeft().alignCenterY()),
                () -> text(label, txt().size(1).color(new Color(255, 255, 255, 255))));
    }

    public static float slider(String id, float value, float min, float max, float width) {
        String trackId = "SldTrack_" + id;
        String thumbId = "SldThumb_" + id;

        boolean isHovered = pointerOver(trackId) || pointerOver(thumbId);
        boolean isActive = activeDragElementId.equals(thumbId);

        if (isHovered && mouseClicked && !clickConsumed) {
            activeDragElementId = thumbId;
            isActive = true;
            clickConsumed = true;
        }

        LayoutElementHashMapItem item = getContext().getHashMapItem(HashUtil.hashString(trackId, 0, 0));
        if (isActive && item != null) {
            float localX = mouseX - item.boundingBox.x;
            float rawRatio = localX / item.boundingBox.width;
            float ratio = Math.max(0f, Math.min(1f, rawRatio));
            value = min + ratio * (max - min);
        }

        float finalRatio = Math.max(0f, Math.min(1f, (value - min) / (max - min)));

        el(decl().id(trackId).layout(layout().widthFixed(width).heightFixed(16).alignLeft().alignCenterY()), () -> {

            el(decl().bg(new Color(25, 28, 35, 255)).radius(6)
                    .floating(PARENT, LEFT_CENTER, LEFT_CENTER, new Vector2(0, 0), 0)
                    .layout(layout().widthFixed(width).heightFixed(12)), () -> {
            });

            if (finalRatio > 0.01f) {
                el(decl().bg(new Color(60, 120, 220, 255)).radius(6)
                        .floating(PARENT, LEFT_CENTER, LEFT_CENTER, new Vector2(0, 0), 0)
                        .layout(layout().widthFixed(finalRatio * width).heightFixed(12)), () -> {
                });
            }

            el(decl().id(thumbId).bg(new Color(255, 255, 255, 255)).radius(8)
                    .floating(PARENT, LEFT_CENTER, LEFT_CENTER, new Vector2((finalRatio * width) - 8, 0), 0)
                    .layout(layout().widthFixed(16).heightFixed(16)), () -> {
            });
        });

        return value;
    }

    public static void scrollbar(String containerId) {
        ScrollContainerData data = new ScrollContainerData();
        ElementId eId = new ElementId();
        HashUtil.hashString(containerId, 0, 0, eId);
        getScrollContainerData(eId, data);

        ScrollBounds bounds = scrollCache.computeIfAbsent(containerId, k -> new ScrollBounds());

        if (data.found && data.scrollContainerDimensions.height > 0) {
            bounds.containerHeight = data.scrollContainerDimensions.height;
            bounds.contentHeight = data.contentDimensions.height;
            if (data.scrollPosition != null) {
                bounds.scrollY = data.scrollPosition.y;
            }
        }

        float trackHeight = bounds.containerHeight;
        float contentHeight = Math.max(bounds.contentHeight, trackHeight);
        float scrollableRange = contentHeight - trackHeight;

        if (scrollableRange <= 2.0f) return;

        String trackId = "ScrollTrack_" + containerId;
        el(decl()
                        .id(trackId)
                        .bg(new Color(20, 20, 25, 255))
                        .radius(2)
                        .layout(layout()
                                .widthFixed(4).heightGrow()),
                () -> renderScrollThumb(containerId, data, bounds, trackHeight, contentHeight, scrollableRange)
        );
    }

    private static void renderScrollThumb(
            String containerId,
            ScrollContainerData data,
            ScrollBounds bounds,
            float trackHeight,
            float contentHeight,
            float scrollableRange) {
        String thumbId = "ScrollThumb_" + containerId;
        String trackId = "ScrollTrack_" + containerId;

        float thumbHeight = Math.max(20.0f, trackHeight * (trackHeight / contentHeight));
        float maxThumbY = trackHeight - thumbHeight;

        boolean isHovered = pointerOver(thumbId) || pointerOver(trackId);
        boolean isActive = activeDragElementId.equals(thumbId);

        if (isHovered && mouseClicked) {
            activeDragElementId = thumbId;
            isActive = true;
        }

        float scrollRatio = -bounds.scrollY / scrollableRange;
        float thumbY = scrollRatio * maxThumbY;

        if (isActive && data.scrollPosition != null) {
            float deltaRatio = (float) mouseDeltaY / maxThumbY;
            data.scrollPosition.y -= deltaRatio * scrollableRange;

            if (data.scrollPosition.y > 0) data.scrollPosition.y = 0;
            if (data.scrollPosition.y < -scrollableRange) data.scrollPosition.y = -scrollableRange;

            thumbY = (-data.scrollPosition.y / scrollableRange) * maxThumbY;
        }

        float finalThumbY = Math.max(0, Math.min(thumbY, maxThumbY));

        el(decl()
                        .id(thumbId)
                        .bg(new Color(
                                isActive || isHovered ? 100 : 80,
                                isActive || isHovered ? 100 : 80,
                                isActive || isHovered ? 110 : 90,
                                255
                        ))
                        .radius(2)
                        .floating(
                                PARENT,
                                LEFT_TOP,
                                LEFT_TOP,
                                new Vector2(0, finalThumbY),
                                0
                        ).layout(layout()
                                .widthGrow().heightFixed(thumbHeight)),
                () -> {
                }
        );
    }

    public static void textInput(String id, String placeholder, String value, float width, Consumer<String> onChange) {
        boolean isFocused = activeTextInputId.equals(id);
        handleTextInputInteraction(id, value, onChange, isFocused);

        String display = value.isEmpty() && !isFocused ? "§8" + placeholder : value;

        LayoutConfigBuilder config = layout()
                .heightFixed(24)
                .padding(8, 0)
                .alignLeft().alignCenterY();
        if (width > 0) config.widthFixed(width);
        else config.widthGrow();

        el(decl()
                        .id(id)
                        .bg(new Color(
                                isFocused ? 25 : 20,
                                isFocused ? 27 : 22,
                                isFocused ? 31 : 26,
                                255
                        ))
                        .radius(4)
                        .layout(config),
                () -> {
                    text(display, txt().size(1).color(new Color(255, 255, 255, 255)));
                    if (isFocused && (System.currentTimeMillis() / 500) % 2 == 0) {
                        renderCursor(value);
                    }
                }
        );
    }

    private static void handleTextInputInteraction(String id, String value, Consumer<String> onChange, boolean isFocused) {
        if (isClicked(id)) {
            activeTextInputId = id;
            activeText = new StringBuilder(value);
            activeTextCallback = onChange;

            LayoutElementHashMapItem item = getContext().getHashMapItem(HashUtil.hashString(id, 0, 0));
            if (item != null) {
                float localX = mouseX - item.boundingBox.x - 8;
                int newCursorPos = 0;
                for (int i = 0; i <= value.length(); i++) {
                    int w = Minecraft.getInstance().font.width(value.substring(0, i));
                    if (localX < w + 4) {
                        newCursorPos = i;
                        break;
                    }
                    newCursorPos = i;
                }
                cursorPos = newCursorPos;
            } else {
                cursorPos = value.length();
            }
        } else if (mouseClicked && !pointerOver(id) && isFocused) {
            activeTextInputId = "";
        }

        if (isFocused && !activeText.toString().equals(value)) {
            activeText = new StringBuilder(value);
            cursorPos = Math.min(cursorPos, value.length());
        }
    }

    private static void renderCursor(String value) {
        int safeCursor = Math.min(cursorPos, value.length());
        int cursorOffset = Minecraft.getInstance().font.width(value.substring(0, safeCursor));

        el(decl()
                        .bg(new Color(255, 255, 255, 255))
                        .floating(
                                PARENT,
                                LEFT_CENTER,
                                LEFT_CENTER,
                                new Vector2(cursorOffset + 8, 0),
                                0)
                        .layout(layout()
                                .widthFixed(2).heightFixed(12)),
                () -> {
                }
        );
    }

    public static boolean isAllowedChatCharacter(char character) {
        return character != 167 && character >= ' ' && character != 127;
    }

    public static void onKeyTyped(char typedChar, int keyCode, boolean isControlDown) {
        if (activeTextInputId.isEmpty() || activeTextCallback == null) return;

        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && cursorPos > 0) {
            activeText.deleteCharAt(cursorPos - 1);
            cursorPos--;
            activeTextCallback.accept(activeText.toString());
        } else if (keyCode == GLFW.GLFW_KEY_DELETE && cursorPos < activeText.length()) {
            activeText.deleteCharAt(cursorPos);
            activeTextCallback.accept(activeText.toString());
        } else if (keyCode == GLFW.GLFW_KEY_LEFT && cursorPos > 0) {
            cursorPos--;
        } else if (keyCode == GLFW.GLFW_KEY_RIGHT && cursorPos < activeText.length()) {
            cursorPos++;
        } else if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            activeTextInputId = "";
            activeTextCallback = null;
        } else if (keyCode == GLFW.GLFW_KEY_V && isControlDown) {
            String clipboard = Minecraft.getInstance().keyboardHandler.getClipboard();
            if (clipboard != null && !clipboard.isEmpty()) {
                activeText.insert(cursorPos, clipboard);
                cursorPos += clipboard.length();
                activeTextCallback.accept(activeText.toString());
            }
        } else if (keyCode == GLFW.GLFW_KEY_C && isControlDown) {
            Minecraft.getInstance().keyboardHandler.setClipboard(activeText.toString());
        } else if (typedChar != '\0' && isAllowedChatCharacter(typedChar)) {
            activeText.insert(cursorPos, typedChar);
            cursorPos++;
            activeTextCallback.accept(activeText.toString());
        }
    }

    private static class ScrollBounds {
        float containerHeight;
        float contentHeight;
        float scrollY;
    }
}