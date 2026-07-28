package io.github.christechs.clayj.config;

public sealed interface ConfigBuilder permits
        BorderConfigBuilder,
        CustomConfigBuilder,
        ElementDeclBuilder,
        FloatingConfigBuilder,
        ImageConfigBuilder,
        LayoutConfigBuilder,
        ScrollConfigBuilder,
        SharedConfigBuilder,
        TextConfigBuilder {

    void reset();

}
