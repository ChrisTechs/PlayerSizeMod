package io.github.christechs.psm.ui.screen;

import io.github.christechs.config.ConfigManager;
import io.github.christechs.psm.ui.base.AutoConfigScreen;

public class PSMConfigMenu extends AutoConfigScreen {

    public PSMConfigMenu() {
        super("Player Size Mod");
        loadConfigCategories();
    }

    @Override
    public void removed() {
        super.removed();

        ConfigManager.save();
    }
}