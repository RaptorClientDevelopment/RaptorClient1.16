package me.ritomg.raptor.setting.values;

import me.ritomg.raptor.setting.Setting;
import me.ritomg.raptor.module.Module;

public class DoubleSetting extends Setting<Double> {

    private final double min;
    private final double max;

    public DoubleSetting(String name, Module module, double value, double min, double max) {
        super(value, name, module);

        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

}