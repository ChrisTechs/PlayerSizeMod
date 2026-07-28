package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.BorderWidth;
import io.github.christechs.clayj.math.Color;
import io.github.christechs.clayj.math.CornerRadius;
import io.github.christechs.clayj.math.Dimensions;

public class RenderData {
    public final Dimensions sourceDimensions = new Dimensions();
    public Color backgroundColor;
    public CornerRadius cornerRadius;
    public CharSequence text;
    public int textStart;
    public int textLength;
    public Color textColor;
    public int fontId;
    public int fontSize;
    public int letterSpacing;
    public int lineHeight;
    public Object imageData;

    public boolean scrollHorizontal;
    public boolean scrollVertical;

    public Color borderColor;
    public BorderWidth borderWidth;

    public Object customData;

    public void set(RenderData other) {
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

        this.text = other.text;
        this.textStart = other.textStart;
        this.textLength = other.textLength;

        if (other.textColor != null) {
            if (this.textColor == null) this.textColor = new Color();
            this.textColor.set(other.textColor);
        } else {
            this.textColor = null;
        }

        this.fontId = other.fontId;
        this.fontSize = other.fontSize;
        this.letterSpacing = other.letterSpacing;
        this.lineHeight = other.lineHeight;

        this.sourceDimensions.set(other.sourceDimensions);
        this.imageData = other.imageData;

        this.scrollHorizontal = other.scrollHorizontal;
        this.scrollVertical = other.scrollVertical;

        if (other.borderColor != null) {
            if (this.borderColor == null) this.borderColor = new Color();
            this.borderColor.set(other.borderColor);
        } else {
            this.borderColor = null;
        }

        if (other.borderWidth != null) {
            if (this.borderWidth == null) this.borderWidth = new BorderWidth();
            this.borderWidth.set(other.borderWidth);
        } else {
            this.borderWidth = null;
        }

        this.customData = other.customData;
    }

    public void reset() {
        backgroundColor = null;
        cornerRadius = null;
        text = null;
        textStart = 0;
        textLength = 0;
        textColor = null;
        fontId = 0;
        fontSize = 0;
        letterSpacing = 0;
        lineHeight = 0;
        sourceDimensions.width = 0;
        sourceDimensions.height = 0;
        imageData = null;
        scrollHorizontal = false;
        scrollVertical = false;
        borderColor = null;
        borderWidth = null;
        customData = null;
    }
}