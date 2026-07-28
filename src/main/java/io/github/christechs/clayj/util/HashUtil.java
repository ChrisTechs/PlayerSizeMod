package io.github.christechs.clayj.util;

import io.github.christechs.clayj.core.ElementId;

public class HashUtil {

    public static void hashNumber(int offset, int seed, ElementId outId) {
        outId.set(hashNumber(offset, seed), offset, seed, "");
    }

    public static int hashNumber(int offset, int seed) {
        int hash = seed;
        hash += (offset + 48);
        hash += (hash << 10);
        hash ^= (hash >>> 6);

        hash += (hash << 3);
        hash ^= (hash >>> 11);
        hash += (hash << 15);

        return hash + 1;
    }

    public static void hashString(CharSequence key, int offset, int seed, ElementId outId) {
        int hash = 0;
        int base = seed;

        for (int i = 0; i < key.length(); i++) {
            base += key.charAt(i);
            base += (base << 10);
            base ^= (base >>> 6);
        }

        hash = base;
        hash += offset;
        hash += (hash << 10);
        hash ^= (hash >>> 6);

        hash += (hash << 3);
        base += (base << 3);
        hash ^= (hash >>> 11);
        base ^= (base >>> 11);
        hash += (hash << 15);
        base += (base << 15);

        outId.set(hash + 1, offset, base + 1, key);
    }

    public static int hashString(CharSequence key, int offset, int seed) {
        int hash = 0;
        int base = seed;

        for (int i = 0; i < key.length(); i++) {
            base += key.charAt(i);
            base += (base << 10);
            base ^= (base >>> 6);
        }

        hash = base;
        hash += offset;
        hash += (hash << 10);
        hash ^= (hash >>> 6);

        hash += (hash << 3);
        base += (base << 3);
        hash ^= (hash >>> 11);
        base ^= (base >>> 11);
        hash += (hash << 15);
        base += (base << 15);

        return hash + 1;
    }
}