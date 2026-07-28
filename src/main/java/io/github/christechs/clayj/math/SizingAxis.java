package io.github.christechs.clayj.math;

import io.github.christechs.clayj.enums.SizingType;

public class SizingAxis {
    public final SizingMinMax minMax = new SizingMinMax();
    public float percent;
    public SizingType type;

    public SizingAxis() {
    }

    public SizingAxis(SizingType type) {
        this.type = type;
    }

    public SizingAxis(SizingType type, float percent) {
        this.type = type;
        this.percent = percent;
    }

    public SizingAxis(SizingType type, float min, float max) {
        this.type = type;
        this.minMax.min = min;
        this.minMax.max = max;
    }

    public SizingAxis set(SizingAxis sizingAxis) {
        this.minMax.set(sizingAxis.minMax.min, sizingAxis.minMax.max);
        this.percent = sizingAxis.percent;
        this.type = sizingAxis.type;
        return this;
    }

    public SizingAxis type(SizingType type) {
        this.type = type;
        return this;
    }

    public SizingAxis percent(float percent) {
        this.percent = percent;
        return this;
    }

    public SizingAxis minMax(float min, float max) {
        this.minMax.set(min, max);
        return this;
    }
}