package io.github.christechs.clayj.util;

public class ArrayUtil {

    public static <T> T removeSwapback(T[] array, int length, int index) {
        if (index >= 0 && index < length) {
            T removed = array[index];
            array[index] = array[length - 1];
            array[length - 1] = removed;
            return removed;
        }
        return null;
    }

    public static int removeSwapback(int[] array, int length, int index) {
        if (index >= 0 && index < length) {
            int removed = array[index];
            array[index] = array[length - 1];
            array[length - 1] = removed;
            return removed;
        }
        return 0;
    }
}