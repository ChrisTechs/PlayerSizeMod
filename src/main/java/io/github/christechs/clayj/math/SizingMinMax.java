package io.github.christechs.clayj.math;

public class SizingMinMax {
    public float min;
    public float max;

    public SizingMinMax() {
    }

    public SizingMinMax(float min, float max) {
        set(min, max);
    }

    public SizingMinMax set(SizingMinMax other) {
        return set(other.min, other.max);
    }

    public SizingMinMax set(float min, float max) {
        this.min = min;
        this.max = max;
        return this;
    }

    public SizingMinMax min(float min) {
        this.min = min;
        return this;
    }

    public SizingMinMax max(float max) {
        this.max = max;
        return this;
    }
}