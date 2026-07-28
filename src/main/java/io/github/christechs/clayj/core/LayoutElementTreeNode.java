package io.github.christechs.clayj.core;

import io.github.christechs.clayj.math.Vector2;

public class LayoutElementTreeNode {
    public final Vector2 position = new Vector2();
    public final Vector2 nextChildOffset = new Vector2();
    public LayoutElement layoutElement;
}