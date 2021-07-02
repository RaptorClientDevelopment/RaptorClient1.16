package me.ritomg.raptor.setting.values;

import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.module.Module;

import java.util.List;

public class ModeSetting extends Setting<String> {

    private final List<String> modes;

    public ModeSetting(String name, Module module, String value, List<String> modes) {
        super(value, name, module);

        this.modes = modes;
    }

    public List<String> getModes() {
        return this.modes;
    }

    public void increment() {
        int modeIndex = modes.indexOf(getValue());
        modeIndex = (modeIndex + 1) % modes.size();
        setValue(modes.get(modeIndex));
    }

    public void decrement() {
        int modeIndex = modes.indexOf(getValue());
        modeIndex-=1;
        if (modeIndex<0) modeIndex=modes.size()-1;
        setValue(modes.get(modeIndex));
    }

}