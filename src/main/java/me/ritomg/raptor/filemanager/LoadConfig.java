package me.ritomg.raptor.filemanager;

import com.google.gson.*;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.gui.RaptorClientGui;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.module.ModuleManager;
import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.setting.SettingsManager;
import me.ritomg.raptor.setting.values.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class LoadConfig {

    private static final String fileName = "RaptorClient/";
    private static final String moduleName = "Modules/";
    private static final String mainName = "Main/";
    private static final String miscName = "Misc/";

    public static void init() {
        try {
            loadModules();
            loadEnabledModules();
            loadModuleKeybinds();
            loadDrawnModules();
            loadToggleMessageModules();
            loadClickGUIPositions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //big shoutout to lukflug for helping/fixing this
    private static void loadModules() throws IOException {
        String moduleLocation = fileName + moduleName;

        for (Module module : ModuleManager.getModules()) {
            try {
                loadModuleDirect(moduleLocation, module);
            } catch (IOException e) {
                System.out.println(module.getName());
                e.printStackTrace();
            }
        }
    }

    private static void loadModuleDirect(String moduleLocation, Module module) throws IOException {
        if (!Files.exists(Paths.get(moduleLocation + module.getName() + ".json"))) {
            return;
        }

        InputStream inputStream = Files.newInputStream(Paths.get(moduleLocation + module.getName() + ".json"));
        JsonObject moduleObject;
        try {
            moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
        }catch (java.lang.IllegalStateException e) {
            return;
        }

        if (moduleObject.get("Module") == null) {
            return;
        }

        JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
        for (Setting setting : SettingsManager.getSettingsForModule(module)) {
            JsonElement dataObject = settingObject.get(setting.getConfigName());
            try {
                if (dataObject != null && dataObject.isJsonPrimitive()) {
                    if (setting instanceof BooleanSetting) {
                        setting.setValue(dataObject.getAsBoolean());
                    } else if (setting instanceof IntegerSetting) {
                        setting.setValue(dataObject.getAsInt());
                    } else if (setting instanceof DoubleSetting) {
                        setting.setValue(dataObject.getAsDouble());
                    } else if (setting instanceof ColorSetting) {
                        ((ColorSetting) setting).fromLong(dataObject.getAsLong());
                    } else if (setting instanceof ModeSetting) {
                        setting.setValue(dataObject.getAsString());
                    } else if (setting instanceof StringSetting) {
                        setting.setValue(dataObject.getAsString());
                    }
                }
            } catch (java.lang.NumberFormatException e) {
                System.out.println(setting.getConfigName() + " " + module.getName());
                System.out.println(dataObject);
            }
        }
        inputStream.close();
    }

    private static void loadEnabledModules() throws IOException {
        String enabledLocation = fileName + mainName;

        if (!Files.exists(Paths.get(enabledLocation + "Toggle" + ".json"))) {
            return;
        }

        InputStream inputStream = Files.newInputStream(Paths.get(enabledLocation + "Toggle" + ".json"));
        JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (moduleObject.get("Modules") == null) {
            return;
        }

        JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (Module module : ModuleManager.getModules()) {
            JsonElement dataObject = settingObject.get(module.getName());

            if (dataObject != null && dataObject.isJsonPrimitive()) {
                if (dataObject.getAsBoolean()) {
                    try {
                        module.enable();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        inputStream.close();
    }

    private static void loadModuleKeybinds() throws IOException {
        String bindLocation = fileName + mainName;

        if (!Files.exists(Paths.get(bindLocation + "Bind" + ".json"))) {
            return;
        }

        InputStream inputStream = Files.newInputStream(Paths.get(bindLocation + "Bind" + ".json"));
        JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (moduleObject.get("Modules") == null) {
            return;
        }

        JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (Module module : ModuleManager.getModules()) {
            JsonElement dataObject = settingObject.get(module.getName());

            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setBind(dataObject.getAsInt());
            }
        }
        inputStream.close();
    }

    private static void loadDrawnModules() throws IOException {
        String drawnLocation = fileName + mainName;

        if (!Files.exists(Paths.get(drawnLocation + "Drawn" + ".json"))) {
            return;
        }

        InputStream inputStream = Files.newInputStream(Paths.get(drawnLocation + "Drawn" + ".json"));
        JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (moduleObject.get("Modules") == null) {
            return;
        }

        JsonObject settingObject = moduleObject.get("Modules").getAsJsonObject();
        for (Module module : ModuleManager.getModules()) {
            JsonElement dataObject = settingObject.get(module.getName());

            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setDrawn(dataObject.getAsBoolean());
            }
        }
        inputStream.close();
    }

    private static void loadToggleMessageModules() throws IOException {
        String toggleMessageLocation = fileName + mainName;

        if (!Files.exists(Paths.get(toggleMessageLocation + "ToggleMessages" + ".json"))) {
            return;
        }

        InputStream inputStream = Files.newInputStream(Paths.get(toggleMessageLocation + "ToggleMessages" + ".json"));
        JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (moduleObject.get("Modules") == null) {
            return;
        }

        JsonObject toggleObject = moduleObject.get("Modules").getAsJsonObject();
        for (Module module : ModuleManager.getModules()) {
            JsonElement dataObject = toggleObject.get(module.getName());

            if (dataObject != null && dataObject.isJsonPrimitive()) {
                module.setToggleMsg(dataObject.getAsBoolean());
            }
        }
        inputStream.close();
    }
    private static void loadClickGUIPositions() throws IOException {
        RaptorClientGui.gui.loadConfig(new GuiConfig(fileName + mainName));
    }

}