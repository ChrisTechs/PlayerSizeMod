package io.github.christechs.clayj.config;

import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;

public final class SharedConfigBuilder implements ConfigBuilder {
    public Color backgroundColor;
    public CornerRadius cornerRadius;
    public Object userData;

    public SharedConfigBuilder set(SharedConfigBuilder other) {
        if (other.backgroundColor != null) {
            if (this.backgroundColor == null) this.backgroundColor = new Color();
            this.backgroundColor.set(other.backgroundColor);
        } else {
            this.backgroundColor = null;
        }

        if (other.cornerRadius != null) {
            if (this.cornerRadius == null) this.cornerRadius = new CornerRadius();
            this.cornerRadius.set(other.cornerRadius);
        } else {
            this.cornerRadius = null;
        }

        this.userData = other.userData;
        return this;
    }

    public SharedConfigBuilder bg(Color color) {
        this.backgroundColor = color;
        return this;
    }

    public SharedConfigBuilder radius(CornerRadius radius) {
        this.cornerRadius = radius;
        return this;
    }

    public SharedConfigBuilder radius(float all) {
        if (this.cornerRadius == null) this.cornerRadius = new CornerRadius();
        this.cornerRadius.set(all, all, all, all);
        return this;
    }

    public SharedConfigBuilder userData(Object data) {
        this.userData = data;
        return this;
    }

    public void reset() {
        this.backgroundColor = null;
        this.cornerRadius = null;
        this.userData = null;
    }
}