package me.ritomg.raptor.module.modules.client;

import com.lukflug.panelstudio.base.AnimatedToggleable;
import me.ritomg.raptor.RaptorClient;
import me.ritomg.raptor.gui.RaptorClientGui;
import me.ritomg.raptor.module.Category;
import me.ritomg.raptor.setting.SettingsManager;
import me.ritomg.raptor.setting.values.BooleanSetting;
import me.ritomg.raptor.setting.values.ColorSetting;
import me.ritomg.raptor.setting.values.IntegerSetting;
import me.ritomg.raptor.setting.values.ModeSetting;
import me.ritomg.raptor.util.RCColor;
import me.ritomg.raptor.module.Module;

import java.util.Arrays;
import java.util.function.Supplier;

@Module.info(name = "ClickGUI", category = Category.Client, drawn = false, Description = "Allows you to change settings in a GUI thanks to PanelStudio", toggleMsg = false)
public class ClickGuiModule extends Module {

    public ModeSetting theme = registerMode("Theme", Arrays.asList("RainbowTheme", "ClearGradientTheme", "GamesenseTheme", "ClearTheme", "Windows"), "GamesenseTheme");
    public ModeSetting scrolling = registerMode("Scrolling", Arrays.asList("Screen", "Container"), "Screen");
    public ModeSetting layout = registerMode("Layout", Arrays.asList("Normal", "CSGO", "Search", "Single"), "Normal");

    public IntegerSetting scrollSpeed = registerInteger("Scroll Speed", 10, 1, 20);
    public IntegerSetting animationSpeed = registerInteger("Animation Speed", 200, 0, 1000);

    public BooleanSetting showHUD = registerBoolean("Show HUD types", false);

    public BooleanSetting ignoreDisabled = registerBoolean("IgnoreDisabled", true);
    public BooleanSetting buttonRainbow = registerBoolean("ButtonRainbow", false);
    public IntegerSetting gradient = registerInteger("Gradient", 1,1,50);

    public void onEnable() {
        RaptorClient.gui.enterGUI();
         disable();
    }

    public ColorSetting registerColor (String name, String configName, Supplier<Boolean> isVisible, boolean rainbow, boolean rainbowEnabled, boolean alphaEnabled, RCColor value) {
        ColorSetting setting=new ColorSetting(name,configName,this,isVisible,rainbow,rainbowEnabled,alphaEnabled,value);
        SettingsManager.addSetting(setting);
        return setting;
    }

}