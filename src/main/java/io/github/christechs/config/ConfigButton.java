package io.github.christechs.config;

public class ConfigButton extends ConfigElement {
    public final String buttonLabel;
    public final Runnable action;

    public ConfigButton(
            String id, String name, String description, String buttonLabel, Runnable action) {
        super(id, name, description);
        this.buttonLabel = buttonLabel;
        this.action = action;
    }
}