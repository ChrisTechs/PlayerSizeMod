package io.github.christechs.clayj.core;

import io.github.christechs.clayj.enums.InteractionState;
import io.github.christechs.clayj.math.Vector2;

public class MousePointerData {
    public final Vector2 position = new Vector2();
    public InteractionState state = InteractionState.RELEASED;

    public void set(MousePointerData other) {
        this.position.set(other.position);
        this.state = other.state;
    }
}