package me.ritomg.raptor.setting;

import com.lukflug.panelstudio.base.IBoolean;
import me.ritomg.raptor.util.RCColor;
import me.ritomg.raptor.module.Module;

import java.util.function.Supplier;

public abstract class Setting<T> {

    private T value;
    private final String name;
    private final String configName;
    private final Module module;
    private final Supplier<Boolean>isvisible;

    public Setting(T value, String name, Module module) {
        this.value = value;
        this.name = name;
        this.configName = name.replace(" ", "");
        this.module = module;
        this.isvisible = ()->true;
    }

    public Setting(T value, String name, String configName, Module module, Supplier<Boolean> isVisible) {
        this.value = value;
        this.name = name;
        this.configName = configName;
        this.module = module;
        this.isvisible = isVisible;
    }


    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public String getConfigName() {
        return this.configName;
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isVisible(){
        return isvisible.get();
    }

}
