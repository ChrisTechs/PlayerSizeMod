package io.github.christechs.config;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ConfigOption<T> extends ConfigElement {
    private final Supplier<T> getter;
    private final Consumer<T> setter;
    public double min = Double.MIN_VALUE;
    public double max = Double.MAX_VALUE;

    public ConfigOption(
            String id, String name, String description, Supplier<T> getter, Consumer<T> setter) {
        super(id, name, description);
        this.getter = getter;
        this.setter = setter;
    }

    public T get() {
        return getter.get();
    }

    public void set(T value) {
        setter.accept(value);
    }

    public ConfigOption<T> setBounds(double min, double max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public ConfigOption<T> setHidden(boolean hidden) {
        this.hidden = hidden;
        return this;
    }
}