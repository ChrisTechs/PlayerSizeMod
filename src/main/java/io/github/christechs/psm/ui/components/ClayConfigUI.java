package io.github.christechs.psm.ui.components;

import io.github.christechs.clayj.config.ImageConfigBuilder;
import io.github.christechs.clayj.config.LayoutConfigBuilder;
import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;
import io.github.christechs.config.*;

import static io.github.christechs.clayj.ClayJ.*;

public class ClayConfigUI {

    public static String activeSliderInputId = "";

    public static boolean elementMatches(ConfigElement element, String query) {
        if (query == null || query.trim().isEmpty()) return true;
        if (element.hidden) return false;

        String q = query.toLowerCase();
        if (element.name.toLowerCase().contains(q)) return true;
        if (element.description != null && element.description.toLowerCase().contains(q)) return true;

        if (element instanceof ConfigGroup) {
            for (ConfigElement child : ((ConfigGroup) element).elements) {
                if (elementMatches(child, q)) return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public static void drawConfigElement(ConfigElement element, String query) {
        if (element.hidden || !elementMatches(element, query)) return;

        if (element instanceof ConfigOption) {

            ConfigOption<?> opt = (ConfigOption<?>) element;

            if (opt.get() instanceof Boolean)
                configToggle((ConfigOption<Boolean>) opt);
            else if (opt.get() instanceof Integer)
                configSpinner((ConfigOption<Integer>) opt);
            else if (opt.get() instanceof Float || opt.get() instanceof Double)
                configSlider((ConfigOption<Float>) opt);
            else if (opt.get() instanceof String)
                configTextInput((ConfigOption<String>) opt);

        } else if (element instanceof ConfigGroup) {

            drawConfigGroup((ConfigGroup) element, query);

        } else if (element instanceof ConfigButton) {

            ConfigButton btn = (ConfigButton) element;

            el(decl().layout(layout().dirTopToBottom().gap(4)), () -> {

                text(btn.name, txt().size(1).color(new Color(200, 200, 200, 255)));

                if (btn.description != null && !btn.description.isEmpty()) {
                    text(btn.description, txt().size(1).color(new Color(150, 150, 150, 255)));
                }

                ClayComponents.button("CfgBtn_" + btn.id, btn.buttonLabel, btn.action);
            });
        } else if (element instanceof ConfigDisplay) {
            ConfigDisplay disp = (ConfigDisplay) element;
            el(decl()
                            .bg(new Color(35, 38, 45, 255))
                            .radius(6)
                            .layout(layout()
                                    .padding(10, 10)),
                    () -> text(
                            disp.name + ": §e" + disp.displayValue.get(),
                            txt().size(1).color(new Color(255, 255, 255, 255))
                    )
            );
        }
    }

    public static void drawConfigGroup(ConfigGroup group, String query) {
        boolean isSearching = query != null && !query.trim().isEmpty();
        if (isSearching) group.expanded = true;

        el(decl()
                        .id("Grp_" + group.id)
                        .bg(new Color(40, 45, 55, 255))
                        .radius(6)
                        .layout(layout()
                                .widthGrow().heightFit()
                                .dirTopToBottom()
                                .padding(10, 10)
                                .gap(10)),
                () -> {
                    renderGroupHeader(group);
                    if (group.expanded) renderGroupBody(group, query);
                }
        );
    }

    private static void renderGroupHeader(ConfigGroup group) {
        el(decl().layout(layout()
                        .widthGrow().heightFixed(24)
                        .dirLeftToRight()
                        .alignLeft().alignCenterY()
                        .gap(10)),
                () -> {
                    ClayComponents.button(
                            "Btn_Toggle_" + group.id,
                            group.expanded ? "▼" : "▶",
                            () -> group.expanded = !group.expanded
                    );

                    if (group.icon != null) {
                        el(decl()
                                        .image(new ImageConfigBuilder()
                                                .data(group.icon)
                                                .sourceDim(16, 16)
                                        ).layout(layout()
                                                .widthFixed(16).heightFixed(16)),
                                () -> {
                                });
                    }

                    if (group.masterToggle != null) {
                        configToggle(group.masterToggle);
                    } else {
                        text(group.name, txt().size(1).color(new Color(255, 255, 255, 255)));
                    }
                });
    }

    private static void renderGroupBody(ConfigGroup group, String query) {
        if (group.description != null && !group.description.isEmpty()) {
            text(group.description, txt().size(1).color(new Color(180, 180, 180, 255)));
        }

        el(decl().layout(layout().dirTopToBottom().gap(8).padding(15, 0)), () -> {
            boolean groupExplicitMatch = group.name.toLowerCase().contains(query == null ? "" : query.toLowerCase());
            for (ConfigElement child : group.elements) {
                drawConfigElement(child, groupExplicitMatch ? "" : query);
            }
        });
    }

    public static void configSlider(ConfigOption<Float> option) {
        String sliderId = "Sld_" + option.id;
        String textId = "ValTxt_" + sliderId;

        el(decl().layout(layout().dirTopToBottom().gap(4)), () -> {

            el(decl().layout(layout().dirLeftToRight().widthGrow().alignCenterY()), () -> {
                text(option.name, txt().size(1).color(new Color(200, 200, 200, 255)));
                el(decl().layout(layout().widthGrow()), () -> {
                });

                if (activeSliderInputId.equals(sliderId)) {
                    ClayComponents.textInput(
                            "TxtInp_" + sliderId,
                            "Value",
                            String.valueOf(option.get()),
                            60,
                            (val) -> {
                                try {
                                    option.set(Float.parseFloat(val));
                                } catch (NumberFormatException ignored) {
                                }
                            }
                    );

                    if (!ClayComponents.activeTextInputId.equals("TxtInp_" + sliderId) &&
                            !ClayComponents.isClicked("TxtInp_" + sliderId)) {
                        activeSliderInputId = "";
                    }
                } else {
                    if (ClayComponents.isDoubleClicked(textId) || ClayComponents.isDoubleClicked("SldTrack_" + sliderId)) {
                        activeSliderInputId = sliderId;
                        ClayComponents.activeTextInputId = "TxtInp_" + sliderId;
                        ClayComponents.activeText = new StringBuilder(String.valueOf(option.get()));
                        ClayComponents.cursorPos = ClayComponents.activeText.length();
                        ClayComponents.activeTextCallback = (val) -> {
                            try {
                                option.set(Float.parseFloat(val));
                            } catch (Exception ignored) {
                            }
                        };
                    }

                    el(decl().id(textId).layout(layout().padding(4, 0)), () -> {
                        text(String.format("%.2f", option.get()), txt().size(1).color(new Color(255, 255, 255, 255)));
                    });
                }
            });

            float min = ((Number) option.min).floatValue();
            float max = ((Number) option.max).floatValue();
            float newValue = ClayComponents.slider(sliderId, option.get(), min, max, 200);

            if (newValue != option.get()) {
                option.set(newValue);
                activeSliderInputId = "";
            }
        });
    }

    public static void configTextInput(ConfigOption<String> option) {
        el(decl().layout(layout().dirTopToBottom().gap(4)), () -> {
            text(option.name, txt().size(1).color(new Color(200, 200, 200, 255)));
            ClayComponents.textInput("Txt_" + option.id, option.name, option.get(), 200, option::set);
        });
    }

    public static void configToggle(ConfigOption<Boolean> option) {
        String id = "Tgl_" + option.id;
        boolean value = option.get();

        el(decl().layout(layout()
                        .dirLeftToRight()
                        .gap(10)
                        .alignLeft().alignCenterY()),
                () -> {
                    if (ClayComponents.isClicked(id))
                        option.set(!value);

                    LayoutConfigBuilder toggleLayout = layout().widthFixed(32).heightFixed(16).padding(2, 0).alignCenterY();
                    if (value) toggleLayout.alignRight();
                    else toggleLayout.alignLeft();

                    Color trackColor = value ? new Color(60, 180, 80, 255) : new Color(30, 32, 38, 255);
                    Color thumbColor = value ? new Color(255, 255, 255, 255) : new Color(150, 150, 160, 255);

                    el(decl()
                                    .id(id)
                                    .bg(trackColor)
                                    .radius(new CornerRadius(8))
                                    .layout(toggleLayout),
                            () -> el(decl()
                                            .bg(thumbColor)
                                            .radius(new CornerRadius(6))
                                            .layout(layout()
                                                    .widthFixed(12).heightFixed(12)),
                                    () -> {
                                    })
                    );

                    el(decl().layout(layout().dirTopToBottom()), () -> {
                        text(option.name, txt().size(1).color(new Color(255, 255, 255, 255)));
                        if (option.description != null && !option.description.isEmpty()) {
                            text(option.description, txt().size(1).color(new Color(150, 150, 150, 255)));
                        }
                    });
                }
        );
    }

    public static void configSpinner(ConfigOption<Integer> option) {
        String id = "Spn_" + option.id;
        int value = option.get();

        el(decl().layout(layout()
                        .dirLeftToRight()
                        .gap(8)
                        .alignLeft().alignCenterY()),
                () -> {
                    text(
                            option.name + ":",
                            txt().size(1)
                                    .color(new Color(200, 200, 200, 255))
                    );

                    ClayComponents.button(
                            id + "_down",
                            "-",
                            () -> {
                                if (value > option.min)
                                    option.set(value - 1);
                            }
                    );
                    el(decl().layout(layout()
                                    .widthFixed(20).heightFixed(20)
                                    .alignCenter()),
                            () -> text(String.valueOf(value), txt().size(1)));

                    ClayComponents.button(id + "_up", "+", () -> {
                                if (value < option.max) option.set(value + 1);
                            }
                    );
                }
        );
    }
}