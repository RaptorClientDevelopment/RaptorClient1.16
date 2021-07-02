package me.ritomg.raptor.setting.values;

import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.setting.Setting;

public class IntegerSetting extends Setting<Integer> {

    private final int min;
    private final int max;

    public IntegerSetting(String name, Module module, int value, int min, int max) {
        super(value, name, module);

        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

}
