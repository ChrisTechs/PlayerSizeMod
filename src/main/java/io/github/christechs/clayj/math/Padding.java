package io.github.christechs.clayj.math;

public class Padding {
    public int left;
    public int right;
    public int top;
    public int bottom;

    public Padding() {
    }

    public Padding(int left, int right, int top, int bottom) {
        set(left, right, top, bottom);
    }

    public Padding(int padding) {
        set(padding, padding, padding, padding);
    }

    public Padding set(Padding other) {
        return set(other.left, other.right, other.top, other.bottom);
    }

    public Padding set(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        return this;
    }

    public Padding all(int padding) {
        return set(padding, padding, padding, padding);
    }

    public Padding x(int horizontalPadding) {
        this.left = horizontalPadding;
        this.right = horizontalPadding;
        return this;
    }

    public Padding y(int verticalPadding) {
        this.top = verticalPadding;
        this.bottom = verticalPadding;
        return this;
    }

    public int vertical() {
        return top + bottom;
    }

    public int horizontal() {
        return left + right;
    }

    public float sizeAxis(boolean xAxis) {
        return xAxis ? (float) horizontal() : (float) vertical();
    }
}