package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.Dimensions;

public class WrappedTextLine {
    public final Dimensions dimensions = new Dimensions();
    public int lineStart;
    public int lineLength;

    public void reset() {
        dimensions.width = 0f;
        dimensions.height = 0f;
        lineStart = 0;
        lineLength = 0;
    }
}