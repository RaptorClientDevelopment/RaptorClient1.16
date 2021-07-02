package me.ritomg.raptor.setting;

import me.ritomg.raptor.module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsManager {

    private static ArrayList<Setting> settings;

    public static void init() {
        settings = new ArrayList<>();
    }

    public static void addSetting(Setting setting) {
        settings.add(setting);
    }

    public static ArrayList<Setting> getSettings() {
        return settings;
    }

    public static List<Setting> getSettingsForModule(Module module) {
        return settings.stream().filter(setting -> setting.getModule().equals(module)).collect(Collectors.toList());
    }
}