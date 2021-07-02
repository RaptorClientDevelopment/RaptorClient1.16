package me.ritomg.raptor;

import me.ritomg.raptor.gui.RaptorClientGui;
import me.ritomg.raptor.module.ModuleManager;
import me.ritomg.raptor.setting.SettingsManager;
import me.zero.alpine.EventBus;
import me.zero.alpine.EventManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RaptorClient implements ModInitializer {

    public static String ModName = "RaptorClient";
    public static String ModVersion = "1.0";

    public static Logger logger = LogManager.getLogger(ModName);
    public static EventBus eventBus = new EventManager();
    public static RaptorClientGui gui;

    public static void logInfo(String infoLog) {
        logger.info(infoLog);
    }

    public static void logError(String errorLog) {
        logger.error(errorLog);
    }


    @Override
    public void onInitialize() {
        logInfo("Starting RaptorClient");
        StartClient();
        logInfo("Started RaptorClient");

    }

    private void StartClient() {
        SettingsManager.init();
        logger.info("Setting Manager Started");
        ModuleManager.init();
        logInfo("ModuleManager Started");
        gui = new RaptorClientGui();
    }
}
