package me.ritomg.raptor.filemanager;

import com.google.gson.*;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.gui.RaptorClientGui;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.module.ModuleManager;
import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.setting.SettingsManager;
import me.ritomg.raptor.setting.values.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SaveConfig {

    public static final String fileName = "RaptorClient/";
    private static final String moduleName = "Modules/";
    private static final String categoryMisc = "/";
    private static final String mainName = "Main/";
    private static final String miscName = "Misc/";

    public static void init() {
        try {
            saveConfig();
            saveModules();
            saveEnabledModules();
            saveModuleKeybinds();
            saveDrawnModules();
            saveToggleMessagesModules();
            saveClickGUIPositions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RaptorClient.logInfo("Saved Config!");
    }

    private static void saveConfig() throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            Files.createDirectories(Paths.get(fileName));
        }
        if (!Files.exists(Paths.get(fileName + moduleName))) {
            Files.createDirectories(Paths.get(fileName + moduleName));
        }
        if (!Files.exists(Paths.get(fileName + mainName))) {
            Files.createDirectories(Paths.get(fileName + mainName));
        }
        if (!Files.exists(Paths.get(fileName + miscName))) {
            Files.createDirectories(Paths.get(fileName + miscName));
        }
    }

    private static void registerFiles(String location, String name) throws IOException {
        if (Files.exists(Paths.get(fileName + location + name + ".json"))) {
            File file = new File(fileName + location + name + ".json");

            file.delete();

        }
        Files.createFile(Paths.get(fileName + location + name + ".json"));
    }

    private static void saveModules() throws IOException {
        for (Module module : ModuleManager.getModules()) {
            try {
                saveModuleDirect(module);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void saveModuleDirect(Module module) throws IOException {
        registerFiles(moduleName, module.getName());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName + moduleName + module.getName() + ".json"), StandardCharsets.UTF_8);
        JsonObject moduleObject = new JsonObject();
        JsonObject settingObject = new JsonObject();
        moduleObject.add("Module", new JsonPrimitive(module.getName()));

        for (Setting setting : SettingsManager.getSettingsForModule(module)) {
            if (setting instanceof BooleanSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((BooleanSetting) setting).getValue()));
            } else if (setting instanceof IntegerSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((IntegerSetting) setting).getValue()));
            } else if (setting instanceof DoubleSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((DoubleSetting) setting).getValue()));
            } else if (setting instanceof ColorSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((ColorSetting) setting).toLong()));
            } else if (setting instanceof ModeSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((ModeSetting) setting).getValue()));
            } else if (setting instanceof StringSetting) {
                settingObject.add(setting.getConfigName(), new JsonPrimitive(((StringSetting) setting).getValue()));
            }
        }
        moduleObject.add("Settings", settingObject);
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    private static void saveEnabledModules() throws IOException {

        registerFiles(mainName, "Toggle");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName + mainName + "Toggle" + ".json"), StandardCharsets.UTF_8);
        JsonObject moduleObject = new JsonObject();
        JsonObject enabledObject = new JsonObject();

        for (Module module : ModuleManager.getModules()) {

            enabledObject.add(module.getName(), new JsonPrimitive(module.isEnabled()));
        }
        moduleObject.add("Modules", enabledObject);
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    private static void saveModuleKeybinds() throws IOException {

        registerFiles(mainName, "Bind");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName + mainName + "Bind" + ".json"), StandardCharsets.UTF_8);
        JsonObject moduleObject = new JsonObject();
        JsonObject bindObject = new JsonObject();

        for (Module module : ModuleManager.getModules()) {

            bindObject.add(module.getName(), new JsonPrimitive(module.getBind()));
        }
        moduleObject.add("Modules", bindObject);
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    private static void saveDrawnModules() throws IOException {

        registerFiles(mainName, "Drawn");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName + mainName + "Drawn" + ".json"), StandardCharsets.UTF_8);
        JsonObject moduleObject = new JsonObject();
        JsonObject drawnObject = new JsonObject();

        for (Module module : ModuleManager.getModules()) {

            drawnObject.add(module.getName(), new JsonPrimitive(module.isDrawn()));
        }
        moduleObject.add("Modules", drawnObject);
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }

    private static void saveToggleMessagesModules() throws IOException {

        registerFiles(mainName, "ToggleMessages");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName + mainName + "ToggleMessages" + ".json"), StandardCharsets.UTF_8);
        JsonObject moduleObject = new JsonObject();
        JsonObject toggleMessagesObject = new JsonObject();

        for (Module module : ModuleManager.getModules()) {

            toggleMessagesObject.add(module.getName(), new JsonPrimitive(module.isToggleMsg()));
        }
        moduleObject.add("Modules", toggleMessagesObject);
        String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));
        fileOutputStreamWriter.write(jsonString);
        fileOutputStreamWriter.close();
    }


    private static void saveClickGUIPositions() throws IOException {
        registerFiles(mainName, "ClickGUI");
        RaptorClientGui.gui.saveConfig(new GuiConfig(fileName + mainName));
    }
}