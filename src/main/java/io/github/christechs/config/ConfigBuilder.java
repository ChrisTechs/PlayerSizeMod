package io.github.christechs.config;

import net.minecraft.resources.Identifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigBuilder {

    public static ConfigGroup group(
            String id,
            String name,
            String description,
            Identifier icon,
            ConfigOption<Boolean> masterToggle) {
        return new ConfigGroup(id, name, description, icon, masterToggle);
    }

    public static ConfigButton button(
            String id, String name, String description, String buttonLabel, Runnable action) {
        return new ConfigButton(id, name, description, buttonLabel, action);
    }

    public static ConfigDisplay display(
            String id, String name, String description, Supplier<String> value) {
        return new ConfigDisplay(id, name, description, value);
    }

    public static ConfigOption<Boolean> virtual(
            String id,
            String name,
            String desc,
            Supplier<Boolean> getter,
            Consumer<Boolean> setter) {
        return new ConfigOption<>(id, name, desc, getter, setter);
    }

    public static ConfigOption<Integer> virtualInt(
            String id,
            String name,
            String desc,
            Supplier<Integer> getter,
            Consumer<Integer> setter) {
        return new ConfigOption<>(id, name, desc, getter, setter);
    }
}