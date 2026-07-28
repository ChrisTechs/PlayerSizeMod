package io.github.christechs.config;

import java.util.function.Supplier;

public class ConfigDisplay extends ConfigElement {
    public final Supplier<String> displayValue;

    public ConfigDisplay(String id, String name, String description, Supplier<String> displayValue) {
        super(id, name, description);
        this.displayValue = displayValue;
    }
}