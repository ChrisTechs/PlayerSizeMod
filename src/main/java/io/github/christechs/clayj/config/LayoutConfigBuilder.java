package io.github.christechs.clayj.config;

import io.github.christechs.clayj.enums.LayoutAlignmentX;
import io.github.christechs.clayj.enums.LayoutAlignmentY;
import io.github.christechs.clayj.enums.LayoutDirection;
import io.github.christechs.clayj.enums.SizingType;
import io.github.christechs.clayj.math.Padding;
import io.github.christechs.clayj.math.Sizing;
import io.github.christechs.clayj.math.SizingAxis;

public final class LayoutConfigBuilder implements ConfigBuilder {

    public final Sizing sizing = new Sizing();
    public final Padding padding = new Padding();

    public int childGap = 0;
    public LayoutAlignmentX alignX = LayoutAlignmentX.LEFT;
    public LayoutAlignmentY alignY = LayoutAlignmentY.TOP;
    public LayoutDirection direction = LayoutDirection.LEFT_TO_RIGHT;

    public LayoutConfigBuilder() {
    }

    public LayoutConfigBuilder set(LayoutConfigBuilder other) {
        this.sizing.set(other.sizing);
        this.padding.set(other.padding);
        this.childGap = other.childGap;
        this.alignX = other.alignX;
        this.alignY = other.alignY;
        this.direction = other.direction;
        return this;
    }

    public LayoutConfigBuilder sizing(SizingAxis width, SizingAxis height) {
        sizing.set(width, height);
        return this;
    }

    public LayoutConfigBuilder widthGrow() {
        sizing.width.type = SizingType.GROW;
        return this;
    }

    public LayoutConfigBuilder widthFixed(float width) {
        sizing.width.type = SizingType.FIXED;
        sizing.width.minMax.min = width;
        sizing.width.minMax.max = width;
        return this;
    }

    public LayoutConfigBuilder widthPercent(float percent) {
        sizing.width.type = SizingType.PERCENT;
        sizing.width.percent = percent;
        return this;
    }

    public LayoutConfigBuilder widthFit() {
        sizing.width.type = SizingType.FIT;
        return this;
    }

    public LayoutConfigBuilder heightGrow() {
        sizing.height.type = SizingType.GROW;
        return this;
    }

    public LayoutConfigBuilder heightFixed(float height) {
        sizing.height.type = SizingType.FIXED;
        sizing.height.minMax.min = height;
        sizing.height.minMax.max = height;
        return this;
    }

    public LayoutConfigBuilder heightPercent(float percent) {
        sizing.height.type = SizingType.PERCENT;
        sizing.height.percent = percent;
        return this;
    }

    public LayoutConfigBuilder heightFit() {
        sizing.height.type = SizingType.FIT;
        return this;
    }

    public LayoutConfigBuilder alignCenterX() {
        this.alignX = LayoutAlignmentX.CENTER;
        return this;
    }

    public LayoutConfigBuilder alignCenterY() {
        this.alignY = LayoutAlignmentY.CENTER;
        return this;
    }

    public LayoutConfigBuilder alignCenter() {
        this.alignX = LayoutAlignmentX.CENTER;
        this.alignY = LayoutAlignmentY.CENTER;
        return this;
    }

    public LayoutConfigBuilder alignRight() {
        this.alignX = LayoutAlignmentX.RIGHT;
        return this;
    }

    public LayoutConfigBuilder alignBottom() {
        this.alignY = LayoutAlignmentY.BOTTOM;
        return this;
    }

    public LayoutConfigBuilder alignLeft() {
        this.alignX = LayoutAlignmentX.LEFT;
        return this;
    }

    public LayoutConfigBuilder alignTop() {
        this.alignY = LayoutAlignmentY.TOP;
        return this;
    }

    public LayoutConfigBuilder dirTopToBottom() {
        this.direction = LayoutDirection.TOP_TO_BOTTOM;
        return this;
    }

    public LayoutConfigBuilder dirLeftToRight() {
        this.direction = LayoutDirection.LEFT_TO_RIGHT;
        return this;
    }

    public LayoutConfigBuilder padding(int all) {
        this.padding.left = all;
        this.padding.right = all;
        this.padding.top = all;
        this.padding.bottom = all;
        return this;
    }

    public LayoutConfigBuilder padding(int x, int y) {
        this.padding.left = x;
        this.padding.right = x;
        this.padding.top = y;
        this.padding.bottom = y;
        return this;
    }

    public LayoutConfigBuilder gap(int gap) {
        this.childGap = gap;
        return this;
    }

    public void reset() {
        sizing.width.type = SizingType.FIT;
        sizing.width.percent = 0f;
        sizing.width.minMax.min = 0f;
        sizing.width.minMax.max = 0f;

        sizing.height.type = SizingType.FIT;
        sizing.height.percent = 0f;
        sizing.height.minMax.min = 0f;
        sizing.height.minMax.max = 0f;

        padding.left = 0;
        padding.right = 0;
        padding.top = 0;
        padding.bottom = 0;

        childGap = 0;
        alignX = LayoutAlignmentX.LEFT;
        alignY = LayoutAlignmentY.TOP;
        direction = LayoutDirection.LEFT_TO_RIGHT;
    }
}