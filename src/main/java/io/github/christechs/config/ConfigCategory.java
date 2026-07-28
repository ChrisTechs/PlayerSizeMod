package io.github.christechs.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategory {
    public final String id;
    public final String name;
    public final List<ConfigElement> elements = new ArrayList<>();

    public ConfigCategory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public <T extends ConfigElement> T add(T element) {
        this.elements.add(element);
        return element;
    }
}