package io.github.christechs.clayj.core;

public class LayoutElementTreeRoot {
    public int layoutElementIndex;
    public int parentId;
    public int clipElementId;
    public short zIndex;

    public void reset() {
        layoutElementIndex = 0;
        parentId = 0;
        clipElementId = 0;
        zIndex = 0;
    }
}