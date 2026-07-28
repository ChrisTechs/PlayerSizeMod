package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.Dimensions;

public class TextElementData {
    public final Dimensions preferredDimensions = new Dimensions();
    public CharSequence text;
    public int elementIndex = -1;
    public int wrappedLinesStart = 0;
    public int wrappedLinesLength = 0;

    public void reset() {
        text = null;
        preferredDimensions.width = 0f;
        preferredDimensions.height = 0f;
        elementIndex = -1;
        wrappedLinesStart = 0;
        wrappedLinesLength = 0;
    }
}