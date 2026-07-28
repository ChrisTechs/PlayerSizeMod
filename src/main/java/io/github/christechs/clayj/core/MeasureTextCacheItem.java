package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.Dimensions;

public class MeasureTextCacheItem {
    public final Dimensions unwrappedDimensions = new Dimensions();
    public int measureWordsStartIndex = 0;
    public boolean containsNewlines = false;

    public int id = 0;
    public int nextIndex = 0;
    public int generation = 0;

    public void set(MeasureTextCacheItem other) {
        this.unwrappedDimensions.set(other.unwrappedDimensions);
        this.measureWordsStartIndex = other.measureWordsStartIndex;
        this.containsNewlines = other.containsNewlines;
        this.id = other.id;
        this.nextIndex = other.nextIndex;
        this.generation = other.generation;
    }

    public void reset() {
        unwrappedDimensions.width = 0;
        unwrappedDimensions.height = 0;
        measureWordsStartIndex = 0;
        containsNewlines = false;
        id = 0;
        nextIndex = 0;
        generation = 0;
    }
}