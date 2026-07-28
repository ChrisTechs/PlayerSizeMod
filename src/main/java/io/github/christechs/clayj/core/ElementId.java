package io.github.christechs.clayj.core;

public class ElementId {
    public int id;
    public int offset;
    public int baseId;
    public CharSequence stringId;

    public ElementId() {
        this.stringId = "";
    }

    public ElementId(int id, int offset, int baseId, CharSequence stringId) {
        set(id, offset, baseId, stringId);
    }

    public void set(ElementId other) {
        set(other.id, other.offset, other.baseId, other.stringId);
    }

    public void set(int id, int offset, int baseId, CharSequence stringId) {
        this.id = id;
        this.offset = offset;
        this.baseId = baseId;
        this.stringId = stringId != null ? stringId : "";
    }

    public void reset() {
        this.id = 0;
        this.offset = 0;
        this.baseId = 0;
        this.stringId = "";
    }
}