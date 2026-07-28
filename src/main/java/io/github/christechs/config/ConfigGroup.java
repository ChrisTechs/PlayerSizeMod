package io.github.christechs.config;

import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

public class ConfigGroup extends ConfigElement {
    public final List<ConfigElement> elements = new ArrayList<>();
    public boolean expanded = false;
    public ConfigOption<Boolean> masterToggle;
    public Identifier icon;

    public ConfigGroup(
            String id,
            String name,
            String description,
            Identifier icon,
            ConfigOption<Boolean> masterToggle) {
        super(id, name, description);
        this.icon = icon;
        this.masterToggle = masterToggle;
    }

    public <T extends ConfigElement> T add(T element) {
        this.elements.add(element);
        return element;
    }
}