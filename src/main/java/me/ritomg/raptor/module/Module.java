package me.ritomg.raptor.module;

import com.lukflug.panelstudio.base.IToggleable;
import com.lukflug.panelstudio.setting.IKeybindSetting;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.setting.SettingsManager;
import me.ritomg.raptor.setting.values.*;
import me.ritomg.raptor.util.RCColor;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

public abstract class Module implements IToggleable, IKeybindSetting {

    protected static final MinecraftClient mc = MinecraftClient.getInstance();
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface info {
        String name();

        String Description() default "";

        Category category();

        int priority() default 0;

        int bind() default GLFW.GLFW_KEY_UNKNOWN;

        boolean enabled() default false;

        boolean drawn() default true;

        boolean toggleMsg() default true;

        boolean alwaysEnabled() default false;
    }

    private final String name = getDeclaration().name();
    private final String description = getDeclaration().Description();
    private final Category category = getDeclaration().category();
    private final int priority = getDeclaration().priority();
    private int bind = getDeclaration().bind();
    private boolean enabled = getDeclaration().enabled();
    private boolean drawn = getDeclaration().drawn();
    private boolean toggleMsg = getDeclaration().toggleMsg();
    private boolean alwaysEnabled = getDeclaration().alwaysEnabled();

    private info getDeclaration() {
        return getClass().getAnnotation(info.class);
    }

    protected void onEnable() {

    }

    protected void onDisable() {

    }

    public void onUpdate() {

    }

    public void onRender() {

    }


    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private String disabledMessage = name + " turned OFF!";

    public void setDisabledMessage(String message) {
        this.disabledMessage = message;
    }

    public void enable() {
        setEnabled(true);
        RaptorClient.eventBus.subscribe(this);
        onEnable();
 //       if (toggleMsg && mc.player != null) MessageBus.sendClientPrefixMessage(ModuleManager.getModule(ColorMain.class).getEnabledColor() + name + " turned ON!");
    }

    public void disable() {
        if (alwaysEnabled) {
            enable();
//            MessageBus.sendClientPrefixMessage(name + "is a always enabled module you can't turn this off");
            return;
        }
        setEnabled(false);
        RaptorClient.eventBus.unsubscribe(this);
        onDisable();
//        if (toggleMsg && mc.player != null) MessageBus.sendClientPrefixMessage(ModuleManager.getModule(ColorMain.class).getDisabledColor() + disabledMessage);
        setDisabledMessage(name + " turned OFF!");
    }

    public void toggle() {
        if (isEnabled()) {
            disable();
        } else if (!isEnabled()) {
            enable();
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {

        if (this.description.length() <= 5) {
            return null;
        }

        else return this.description;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getPriority() {
        return priority;
    }

    public int getBind() {
        return this.bind;
    }

    public void setBind(int bind) {
        if (bind >= 0 && bind <= 255) {
            this.bind = bind;
        }
    }

    public String getHudInfo() {
        return "";
    }

    public boolean isDrawn() {
        return this.drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public boolean isToggleMsg() { return this.toggleMsg;}

    public void setToggleMsg(boolean toggleMsg) {
        this.toggleMsg = toggleMsg;
    }

    public boolean isAlwaysEnabled() {
        return alwaysEnabled;
    }

    public void setAlwaysEnabled(boolean alwaysEnabled) {
        this.alwaysEnabled = alwaysEnabled;
    }

    protected IntegerSetting registerInteger(String name, int value, int min, int max) {
        IntegerSetting integerSetting = new IntegerSetting(name, this, value, min, max);
        SettingsManager.addSetting(integerSetting);
        return integerSetting;
    }

    protected DoubleSetting registerDouble(String name, double value, double min, double max) {
        DoubleSetting doubleSetting = new DoubleSetting(name, this, value, min, max);
        SettingsManager.addSetting(doubleSetting);
        return doubleSetting;
    }

    protected BooleanSetting registerBoolean(String name, boolean value) {
        BooleanSetting booleanSetting = new BooleanSetting(name, this, value);
        SettingsManager.addSetting(booleanSetting);
        return booleanSetting;
    }

    protected ModeSetting registerMode(String name, List<String> modes, String value) {
        ModeSetting modeSetting = new ModeSetting(name, this, value, modes);
        SettingsManager.addSetting(modeSetting);
        return modeSetting;
    }

    protected ColorSetting registerColor(String name, RCColor color) {
        ColorSetting colorSetting = new ColorSetting(name, this, false, color);
        SettingsManager.addSetting(colorSetting);
        return colorSetting;
    }

    protected StringSetting registerString(String name, String value) {
        StringSetting stringSetting = new StringSetting(value,name,this);
        SettingsManager.addSetting(stringSetting);
        return stringSetting;
    }

    protected ColorSetting registerColor(String name) {
        return registerColor(name, new RCColor(90, 145, 240));
    }

    @Override
    public boolean isOn() {
        return this.enabled;
    }

    @Override
    public int getKey() {
        return this.getBind();
    }

    @Override
    public void setKey(int key) {
        setBind(key);
    }

    @Override
    public String getKeyName() {
        if (this.bind <= 0 || this.bind > 255) {
            return "NONE";
        } else {
            return GLFW.glfwGetKeyName(bind,bind);
        }
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}