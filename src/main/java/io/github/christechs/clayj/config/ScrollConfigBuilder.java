package io.github.christechs.clayj.config;

public final class ScrollConfigBuilder implements ConfigBuilder {
    public boolean horizontal = false;
    public boolean vertical = false;

    public ScrollConfigBuilder set(ScrollConfigBuilder other) {
        this.horizontal = other.horizontal;
        this.vertical = other.vertical;
        return this;
    }

    public ScrollConfigBuilder horizontal(boolean scroll) {
        this.horizontal = scroll;
        return this;
    }

    public ScrollConfigBuilder vertical(boolean scroll) {
        this.vertical = scroll;
        return this;
    }

    public ScrollConfigBuilder both() {
        this.horizontal = true;
        this.vertical = true;
        return this;
    }

    public ScrollConfigBuilder none() {
        this.horizontal = false;
        this.vertical = false;
        return this;
    }

    public void reset() {
        this.horizontal = false;
        this.vertical = false;
    }
}