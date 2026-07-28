package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.BoundingBox;

public class LayoutElementHashMapItem {
    public final BoundingBox boundingBox = new BoundingBox();
    public final ElementId elementId = new ElementId();
    public LayoutElement layoutElement;
    public int generation;
    public int idAlias;
    public int nextIndex = -1;
    public int elementIndex;

    public Runnable onHoverFunction;

    public void reset() {
        boundingBox.set(0, 0, 0, 0);
        elementId.set(0, 0, 0, "");
        layoutElement = null;
        generation = 0;
        idAlias = 0;
        nextIndex = -1;
        onHoverFunction = null;
        elementIndex = -1;
    }
}