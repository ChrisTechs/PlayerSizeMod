package io.github.christechs.clayj.util;

import io.github.christechs.clayj.ClayJ;
import io.github.christechs.clayj.ClayJContext;
import io.github.christechs.clayj.enums.ClayJError;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArenaPool<T> {
    private final T[] pool;
    private final Consumer<T> resetAction;
    private final T fallbackInstance;
    private int cursor = 0;

    @SuppressWarnings("unchecked")
    public ArenaPool(int capacity, Supplier<T> factory, Consumer<T> resetAction) {
        this.pool = (T[]) new Object[capacity];
        this.resetAction = resetAction;
        for (int i = 0; i < capacity; i++) {
            this.pool[i] = factory.get();
        }
        this.fallbackInstance = factory.get();
    }

    public ArenaPool(int capacity, Supplier<T> factory) {
        this(capacity, factory, null);
    }

    public T take() {
        if (cursor >= pool.length) {
            ClayJContext context = ClayJ.getContext();
            if (context != null && context.errorHandler != null) {
                context.errorHandler.handleError(ClayJError.ARENA_CAPACITY_EXCEEDED);
            }
            if (resetAction != null) {
                resetAction.accept(fallbackInstance);
            }
            return fallbackInstance;
        }

        T item = pool[cursor++];

        if (resetAction != null) {
            resetAction.accept(item);
        }

        return item;
    }

    public void reset() {
        cursor = 0;
    }

    public int capacity() {
        return pool.length;
    }
}