package io.github.christechs.clayj.math;

public class Sizing {
    public final SizingAxis width = new SizingAxis();
    public final SizingAxis height = new SizingAxis();

    public Sizing() {
    }

    public Sizing(SizingAxis width, SizingAxis height) {
        set(width, height);
    }

    public Sizing set(Sizing other) {
        this.width.set(other.width);
        this.height.set(other.height);
        return this;
    }

    public Sizing set(SizingAxis width, SizingAxis height) {
        this.width.set(width);
        this.height.set(height);
        return this;
    }

    public SizingAxis sizingAxis(boolean xAxis) {
        return xAxis ? width : height;
    }

    public Dimensions clamp(Dimensions d) {
        d.height = clampHeight(d.height);
        d.width = clampWidth(d.width);
        return d;
    }

    public float clampWidth(float w) {
        return Math.max(width.minMax.min, Math.min(width.minMax.max, w));
    }

    public float clampHeight(float h) {
        return Math.max(height.minMax.min, Math.min(height.minMax.max, h));
    }
}