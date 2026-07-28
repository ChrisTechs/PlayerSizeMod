package io.github.christechs.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigCategoryData {
    public final String id;
    public final String name;
    public final List<ConfigElement> elements = new ArrayList<>();

    public ConfigCategoryData(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add(ConfigElement element) {
        this.elements.add(element);
    }
}