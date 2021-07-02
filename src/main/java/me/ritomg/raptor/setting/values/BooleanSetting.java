package me.ritomg.raptor.setting.values;

import com.lukflug.panelstudio.setting.IBooleanSetting;
import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.module.Module;

public class BooleanSetting extends Setting<Boolean> {

    public BooleanSetting(String name, Module module, boolean value) {
        super(value, name, module);
    }

}