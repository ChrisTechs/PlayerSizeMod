package io.github.christechs.clayj.math;

public class Color {
    public float r, g, b, a;

    public Color() {
    }

    public Color(float r, float g, float b, float a) {
        set(r, g, b, a);
    }

    public Color set(Color other) {
        return set(other.r, other.g, other.b, other.a);
    }

    public Color set(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        return this;
    }

    public Color r(float r) {
        this.r = r;
        return this;
    }

    public Color g(float g) {
        this.g = g;
        return this;
    }

    public Color b(float b) {
        this.b = b;
        return this;
    }

    public Color a(float a) {
        this.a = a;
        return this;
    }

    public Color parseHex(String hex) {
        if (hex == null || hex.isEmpty()) return this;

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        try {
            if (hex.length() == 6) {
                this.r = Integer.parseInt(hex.substring(0, 2), 16);
                this.g = Integer.parseInt(hex.substring(2, 4), 16);
                this.b = Integer.parseInt(hex.substring(4, 6), 16);
                this.a = 255f;
            } else if (hex.length() == 8) {
                this.r = Integer.parseInt(hex.substring(0, 2), 16);
                this.g = Integer.parseInt(hex.substring(2, 4), 16);
                this.b = Integer.parseInt(hex.substring(4, 6), 16);
                this.a = Integer.parseInt(hex.substring(6, 8), 16);
            }
        } catch (NumberFormatException ignored) {
        }

        return this;
    }
}