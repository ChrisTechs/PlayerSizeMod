package io.github.christechs.clayj.math;

public class BoundingBox {
    public float x;
    public float y;
    public float width;
    public float height;

    public BoundingBox() {
    }

    public BoundingBox(float x, float y, float width, float height) {
        set(x, y, width, height);
    }

    public BoundingBox set(BoundingBox other) {
        return set(other.x, other.y, other.width, other.height);
    }

    public BoundingBox set(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public boolean contains(float px, float py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    public boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }

    public boolean intersects(BoundingBox other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}