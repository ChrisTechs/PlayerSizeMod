package io.github.christechs.psm;

import io.github.christechs.config.ConfigManager;
import io.github.christechs.psm.command.PSMCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class PlayerSizeMod implements ClientModInitializer {
    public static Logger PSM_LOGGER = LogManager.getLogger("PlayerSizeMod");

    @Override
    public void onInitializeClient() {
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("PSM/pithelper.json").toFile();
        try {
            configFile.getParentFile().mkdirs();
            configFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ConfigManager.init(configFile);

        PSMCommand.register();

        LocationUtils.init();
    }
}
