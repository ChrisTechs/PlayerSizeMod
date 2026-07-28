package io.github.christechs.psm.config;

import io.github.christechs.config.annotations.ConfigCategory;
import io.github.christechs.config.annotations.ConfigProperty;
import io.github.christechs.config.annotations.ConfigRange;

public class PSMConfig {

    public static PSMConfig INSTANCE = new PSMConfig();

    @ConfigCategory(name = "General")
    public General general = new General();

    public static General general() {
        if (INSTANCE == null) {
            INSTANCE = new PSMConfig();
        }
        if (INSTANCE.general == null) {
            INSTANCE.general = new General();
        }
        return INSTANCE.general;
    }

    public static class General {

        @ConfigProperty(name = "Master Toggle", description = "Category Enabled.")
        public boolean enabled = false;

        @ConfigRange(min = 0.1, max = 2.0)
        @ConfigProperty(name = "Player Size", description = "Size of your own player model.")
        public float playerSize = 1.0f;

        @ConfigRange(min = 0.1, max = 2.0)
        @ConfigProperty(name = "Other Players Size", description = "Size of other player models.")
        public float otherPlayerSize = 1.0f;

        @ConfigRange(min = 0.1, max = 2.0)
        @ConfigProperty(name = "NPC Size", description = "Size of npc models.")
        public float npcSize = 1.0f;

    }
}