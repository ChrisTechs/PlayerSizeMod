package io.github.christechs.config;

public abstract class ConfigElement {
    public final String id;
    public final String name;
    public final String description;
    public boolean hidden = false;

    public ConfigElement(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}