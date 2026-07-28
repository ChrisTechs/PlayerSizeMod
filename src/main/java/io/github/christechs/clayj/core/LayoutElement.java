package io.github.christechs.clayj.core;

import io.github.christechs.clayj.ClayJ;
import io.github.christechs.clayj.ClayJContext;
import io.github.christechs.clayj.config.ConfigBuilder;
import io.github.christechs.clayj.config.ElementConfig;
import io.github.christechs.clayj.config.LayoutConfigBuilder;
import io.github.christechs.clayj.enums.ClayJError;
import io.github.christechs.clayj.enums.ElementConfigType;
import io.github.christechs.clayj.math.Dimensions;

public final class LayoutElement {

    public static final int MAX_CONFIGS = 9;

    public final Dimensions minDimensions = new Dimensions();

    public final ElementConfig[] elementConfigs = new ElementConfig[MAX_CONFIGS];

    public int childrenStart = -1;
    public int childrenLength = 0;

    public boolean isTextElement = false;
    public int textElementDataIndex = -1;
    public Dimensions dimensions = new Dimensions();

    public LayoutConfigBuilder layoutConfig;
    public int elementConfigsLength = 0;

    public int id = 0;

    public LayoutElement() {
        for (int i = 0; i < MAX_CONFIGS; i++) {
            elementConfigs[i] = new ElementConfig();
        }
    }

    public void set(LayoutElement other) {
        this.minDimensions.set(other.minDimensions);
        this.childrenStart = other.childrenStart;
        this.childrenLength = other.childrenLength;
        this.isTextElement = other.isTextElement;
        this.textElementDataIndex = other.textElementDataIndex;
        this.dimensions.set(other.dimensions);
        this.layoutConfig = other.layoutConfig;

        this.elementConfigsLength = other.elementConfigsLength;
        for (int i = 0; i < other.elementConfigsLength; i++) {
            this.elementConfigs[i].set(other.elementConfigs[i]);
        }
        this.id = other.id;
    }

    public ConfigBuilder getConfig(ElementConfigType type) {
        for (int i = 0; i < elementConfigsLength; i++) {
            if (elementConfigs[i].type == type) {
                return elementConfigs[i].config;
            }
        }
        return null;
    }

    public void attachConfig(ElementConfigType type, ConfigBuilder config) {
        ClayJContext context = ClayJ.getContext();

        if (getConfig(type) != null) {
            if (context != null && context.errorHandler != null) {
                context.errorHandler.handleError(ClayJError.DUPLICATE_CONFIG);
            }
            return;
        }
        if (elementConfigsLength >= MAX_CONFIGS) {
            if (context != null && context.errorHandler != null) {
                context.errorHandler.handleError(ClayJError.CONFIG_CAPACITY_EXCEEDED);
            }
            return;
        }

        ElementConfig slot = elementConfigs[elementConfigsLength++];
        slot.type = type;
        slot.config = config;
    }

    public void reset() {
        childrenStart = -1;
        childrenLength = 0;
        isTextElement = false;
        textElementDataIndex = -1;

        dimensions.width = 0;
        dimensions.height = 0;
        minDimensions.width = 0;
        minDimensions.height = 0;

        layoutConfig = null;

        for (int i = 0; i < elementConfigsLength; i++) {
            elementConfigs[i].reset();
        }
        elementConfigsLength = 0;

        id = 0;
    }
}