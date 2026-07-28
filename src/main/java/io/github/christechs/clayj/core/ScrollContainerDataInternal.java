package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.BoundingBox;
import io.github.christechs.clayj.math.Dimensions;
import io.github.christechs.clayj.math.Vector2;

public class ScrollContainerDataInternal {
    public final BoundingBox boundingBox = new BoundingBox();
    public final Dimensions contentSize = new Dimensions();
    public final Vector2 scrollOrigin = new Vector2();
    public final Vector2 pointerOrigin = new Vector2();
    public final Vector2 scrollMomentum = new Vector2();
    public final Vector2 scrollPosition = new Vector2();
    public final Vector2 previousDelta = new Vector2();
    public LayoutElement layoutElement;
    public float momentumTime = 0f;
    public int elementId = 0;
    public boolean openThisFrame = false;
    public boolean pointerScrollActive = false;

    public void reset() {
        layoutElement = null;
        boundingBox.x = 0;
        boundingBox.y = 0;
        boundingBox.width = 0;
        boundingBox.height = 0;
        contentSize.width = 0;
        contentSize.height = 0;
        scrollOrigin.x = 0;
        scrollOrigin.y = 0;
        pointerOrigin.x = 0;
        pointerOrigin.y = 0;
        scrollMomentum.x = 0;
        scrollMomentum.y = 0;
        scrollPosition.x = 0;
        scrollPosition.y = 0;
        previousDelta.x = 0;
        previousDelta.y = 0;
        momentumTime = 0f;
        elementId = 0;
        openThisFrame = false;
        pointerScrollActive = false;
    }
}