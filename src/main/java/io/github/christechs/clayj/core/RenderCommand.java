package io.github.christechs.clayj.core;

import io.github.christechs.clayj.enums.RenderCommandType;
import io.github.christechs.clayj.math.BoundingBox;

public class RenderCommand {
    public final BoundingBox boundingBox = new BoundingBox();
    public final RenderData renderData = new RenderData();
    public Object userData;
    public int id;
    public short zIndex;
    public RenderCommandType commandType = RenderCommandType.NONE;

    public void set(RenderCommand other) {
        this.boundingBox.set(other.boundingBox);
        this.renderData.set(other.renderData);
        this.userData = other.userData;
        this.id = other.id;
        this.zIndex = other.zIndex;
        this.commandType = other.commandType;
    }

    public void reset() {
        boundingBox.x = 0;
        boundingBox.y = 0;
        boundingBox.width = 0;
        boundingBox.height = 0;
        renderData.reset();
        userData = null;
        id = 0;
        zIndex = 0;
        commandType = RenderCommandType.NONE;
    }
}