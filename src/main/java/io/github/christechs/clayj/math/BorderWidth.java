package io.github.christechs.clayj.math;

public class BorderWidth {
    public int left;
    public int right;
    public int top;
    public int bottom;
    public int betweenChildren;

    public BorderWidth() {
    }

    public BorderWidth(int left, int right, int top, int bottom, int betweenChildren) {
        set(left, right, top, bottom, betweenChildren);
    }

    public BorderWidth set(BorderWidth other) {
        return set(other.left, other.right, other.top, other.bottom, other.betweenChildren);
    }

    public BorderWidth set(int left, int right, int top, int bottom, int betweenChildren) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.betweenChildren = betweenChildren;
        return this;
    }

    public BorderWidth all(int width) {
        this.left = width;
        this.right = width;
        this.top = width;
        this.bottom = width;
        return this;
    }

    public BorderWidth between(int between) {
        this.betweenChildren = between;
        return this;
    }
}