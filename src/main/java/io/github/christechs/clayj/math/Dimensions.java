package io.github.christechs.clayj.math;

public class Dimensions {
    public float width;
    public float height;

    public Dimensions() {
    }

    public Dimensions(float width, float height) {
        set(width, height);
    }

    public Dimensions set(Dimensions other) {
        return set(other.width, other.height);
    }

    public Dimensions set(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Dimensions add(float w, float h) {
        this.width += w;
        this.height += h;
        return this;
    }

    public Dimensions scale(float scalar) {
        this.width *= scalar;
        this.height *= scalar;
        return this;
    }

    public float sizeAxis(boolean xAxis) {
        return xAxis ? width : height;
    }

    public float aspect() {
        if (height == 0) return 0;
        return width / height;
    }
}