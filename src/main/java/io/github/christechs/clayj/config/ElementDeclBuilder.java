package io.github.christechs.clayj.config;

import io.github.christechs.clayj.ClayJ;
import io.github.christechs.clayj.core.ElementId;
import io.github.christechs.clayj.enums.AttachToElement;
import io.github.christechs.clayj.enums.FloatingAttachPoint;
import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;
import io.github.christechs.clayj.math.Vector2;
import io.github.christechs.clayj.util.HashUtil;

public final class ElementDeclBuilder implements ConfigBuilder {
    public ElementId id = new ElementId();
    public LayoutConfigBuilder layout;
    public Color backgroundColor;
    public CornerRadius cornerRadius;
    public ImageConfigBuilder image;
    public FloatingConfigBuilder floating;
    public ScrollConfigBuilder scroll;
    public BorderConfigBuilder border;
    public Object userData;
    public CustomConfigBuilder custom;

    public ElementDeclBuilder() {
    }

    public ElementDeclBuilder set(ElementDeclBuilder other) {
        this.id.set(other.id);
        this.layout = other.layout;

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

        this.image = other.image;
        this.floating = other.floating;
        this.scroll = other.scroll;
        this.border = other.border;
        this.userData = other.userData;
        this.custom = other.custom;
        return this;
    }

    private LayoutConfigBuilder safeLayout() {
        if (this.layout == null) this.layout = ClayJ.getContext().transientLayouts.take();
        return this.layout;
    }

    public ElementDeclBuilder id(CharSequence idString) {
        HashUtil.hashString(idString, 0, 0, this.id);
        return this;
    }

    public ElementDeclBuilder id(ElementId id) {
        this.id = id;
        return this;
    }

    public ElementDeclBuilder layout(LayoutConfigBuilder layout) {
        this.layout = layout;
        return this;
    }

    public ElementDeclBuilder widthGrow() {
        safeLayout().widthGrow();
        return this;
    }

    public ElementDeclBuilder widthFixed(float w) {
        safeLayout().widthFixed(w);
        return this;
    }

    public ElementDeclBuilder widthPercent(float p) {
        safeLayout().widthPercent(p);
        return this;
    }

    public ElementDeclBuilder widthFit() {
        safeLayout().widthFit();
        return this;
    }

    public ElementDeclBuilder heightGrow() {
        safeLayout().heightGrow();
        return this;
    }

    public ElementDeclBuilder heightFixed(float h) {
        safeLayout().heightFixed(h);
        return this;
    }

    public ElementDeclBuilder heightPercent(float p) {
        safeLayout().heightPercent(p);
        return this;
    }

    public ElementDeclBuilder heightFit() {
        safeLayout().heightFit();
        return this;
    }

    public ElementDeclBuilder sizeFixed(float w, float h) {
        safeLayout().widthFixed(w).heightFixed(h);
        return this;
    }

    public ElementDeclBuilder sizeGrow() {
        safeLayout().widthGrow().heightGrow();
        return this;
    }

    public ElementDeclBuilder padding(int all) {
        safeLayout().padding(all);
        return this;
    }

    public ElementDeclBuilder padding(int x, int y) {
        safeLayout().padding(x, y);
        return this;
    }

    public ElementDeclBuilder gap(int gap) {
        safeLayout().gap(gap);
        return this;
    }

    public ElementDeclBuilder dirLeftToRight() {
        safeLayout().dirLeftToRight();
        return this;
    }

    public ElementDeclBuilder dirTopToBottom() {
        safeLayout().dirTopToBottom();
        return this;
    }

    public ElementDeclBuilder alignCenter() {
        safeLayout().alignCenter();
        return this;
    }

    public ElementDeclBuilder alignCenterX() {
        safeLayout().alignCenterX();
        return this;
    }

    public ElementDeclBuilder alignCenterY() {
        safeLayout().alignCenterY();
        return this;
    }

    public ElementDeclBuilder alignRight() {
        safeLayout().alignRight();
        return this;
    }

    public ElementDeclBuilder alignBottom() {
        safeLayout().alignBottom();
        return this;
    }

    public ElementDeclBuilder alignLeft() {
        safeLayout().alignLeft();
        return this;
    }

    public ElementDeclBuilder alignTop() {
        safeLayout().alignTop();
        return this;
    }

    public ElementDeclBuilder bg(Color color) {
        if (this.backgroundColor == null) this.backgroundColor = new Color();
        this.backgroundColor.set(color.r, color.g, color.b, color.a);
        return this;
    }

    public ElementDeclBuilder bg(int rgb) {
        if (backgroundColor == null) backgroundColor = new Color();
        this.backgroundColor.set(rgb, rgb, rgb, 255);
        return this;
    }

    public ElementDeclBuilder bg(int r, int g, int b) {
        if (backgroundColor == null) backgroundColor = new Color();
        this.backgroundColor.set(r, g, b, 255);
        return this;
    }

    public ElementDeclBuilder bg(int r, int g, int b, int a) {
        if (backgroundColor == null) backgroundColor = new Color();
        this.backgroundColor.set(r, g, b, a);
        return this;
    }

    public ElementDeclBuilder radius(CornerRadius cornerRadius) {
        this.cornerRadius = cornerRadius;
        return this;
    }

    public ElementDeclBuilder radius(float all) {
        if (this.cornerRadius == null) this.cornerRadius = new CornerRadius();
        this.cornerRadius.set(all, all, all, all);
        return this;
    }

    public ElementDeclBuilder scroll(ScrollConfigBuilder scroll) {
        this.scroll = scroll;
        return this;
    }

    public ElementDeclBuilder scrollV() {
        if (this.scroll == null) this.scroll = new ScrollConfigBuilder();
        this.scroll.vertical(true);
        return this;
    }

    public ElementDeclBuilder scrollH() {
        if (this.scroll == null) this.scroll = new ScrollConfigBuilder();
        this.scroll.horizontal(true);
        return this;
    }

    public ElementDeclBuilder scrollBoth() {
        if (this.scroll == null) this.scroll = new ScrollConfigBuilder();
        this.scroll.both();
        return this;
    }

    public ElementDeclBuilder border(BorderConfigBuilder border) {
        this.border = border;
        return this;
    }

    public ElementDeclBuilder border(Color color, int width) {
        if (this.border == null) this.border = new BorderConfigBuilder();
        this.border.color(color).width(width);
        return this;
    }

    public ElementDeclBuilder border(Color color, int left, int right, int top, int bottom, int between) {
        if (this.border == null) this.border = new BorderConfigBuilder();
        this.border.color(color).width(left, right, top, bottom, between);
        return this;
    }

    public ElementDeclBuilder image(ImageConfigBuilder image) {
        this.image = image;
        return this;
    }

    public ElementDeclBuilder floating(FloatingConfigBuilder floating) {
        this.floating = floating;
        return this;
    }

    public ElementDeclBuilder floating(AttachToElement attachTo, FloatingAttachPoint attachElement, FloatingAttachPoint attachParent, Vector2 offset, int zIndex) {
        return floating(attachTo, 0, attachElement, attachParent, offset, zIndex);
    }

    public ElementDeclBuilder floating(AttachToElement attachTo, int parentId, FloatingAttachPoint attachElement, FloatingAttachPoint attachParent, Vector2 offset, int zIndex) {
        if (this.floating == null) this.floating = new FloatingConfigBuilder();
        this.floating.attachTo(attachTo, parentId)
                .attach(attachElement, attachParent)
                .offset(offset.x, offset.y)
                .zIndex((short) zIndex);
        return this;
    }

    public ElementDeclBuilder userData(Object userData) {
        this.userData = userData;
        return this;
    }

    public ElementDeclBuilder custom(Object customData) {
        if (this.custom == null) this.custom = ClayJ.getContext().transientCustoms.take();
        this.custom.customData = customData;
        return this;
    }

    public void reset() {
        this.id.reset();
        this.layout = null;
        this.backgroundColor = null;
        this.cornerRadius = null;
        this.image = null;
        this.floating = null;
        this.scroll = null;
        this.border = null;
        this.userData = null;
        this.custom = null;
    }
}