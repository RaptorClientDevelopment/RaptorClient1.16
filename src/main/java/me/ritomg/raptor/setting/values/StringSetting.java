package me.ritomg.raptor.setting.values;

import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.setting.Setting;

public class StringSetting extends Setting<String> {

    public StringSetting(String value, String name, Module module) {
        super(value, name, module);
    }

}
