package io.github.christechs.clayj.core;

import io.github.christechs.clayj.config.ScrollConfigBuilder;
import io.github.christechs.clayj.math.Dimensions;
import io.github.christechs.clayj.math.Vector2;

public class ScrollContainerData {
    public final Dimensions scrollContainerDimensions = new Dimensions();
    public final Dimensions contentDimensions = new Dimensions();
    public Vector2 scrollPosition;
    public ScrollConfigBuilder config;
    public boolean found;

    public void set(ScrollContainerData other) {
        this.scrollContainerDimensions.set(other.scrollContainerDimensions);
        this.contentDimensions.set(other.contentDimensions);

        if (other.scrollPosition != null) {
            if (this.scrollPosition == null) this.scrollPosition = new Vector2();
            this.scrollPosition.set(other.scrollPosition);
        } else {
            this.scrollPosition = null;
        }

        this.config = other.config;
        this.found = other.found;
    }

    public void reset() {
        scrollPosition = null;
        scrollContainerDimensions.width = 0;
        scrollContainerDimensions.height = 0;
        contentDimensions.width = 0;
        contentDimensions.height = 0;
        config = null;
        found = false;
    }
}