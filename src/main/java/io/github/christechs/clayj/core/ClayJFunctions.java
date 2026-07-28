package io.github.christechs.clayj.core;

import io.github.christechs.clayj.config.TextConfigBuilder;
import io.github.christechs.clayj.enums.ClayJError;
import io.github.christechs.clayj.math.Dimensions;
import io.github.christechs.clayj.math.Vector2;

public interface ClayJFunctions {
    @FunctionalInterface
    interface MeasureTextFunction {
        void measure(CharSequence text, int startOffset, int length, TextConfigBuilder config, Dimensions outDimensions);
    }

    @FunctionalInterface
    interface ErrorHandler {
        void handleError(ClayJError errorType);
    }

    @FunctionalInterface
    interface QueryScrollOffsetFunction {
        Vector2 query(int elementId);
    }
}