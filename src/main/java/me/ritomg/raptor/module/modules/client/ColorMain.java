package me.ritomg.raptor.module.modules.client;


import me.ritomg.raptor.module.Category;
import me.ritomg.raptor.setting.values.BooleanSetting;
import me.ritomg.raptor.setting.values.ColorSetting;
import me.ritomg.raptor.setting.values.ModeSetting;
import me.ritomg.raptor.module.Module;
import me.ritomg.raptor.util.ColorUtil;
import me.ritomg.raptor.util.RCColor;
import net.minecraft.util.Formatting;

import java.util.Arrays;

@Module.info(name = "GuiToggles", category = Category.Client, drawn = false, Description = "Change GUI colours and fonts", toggleMsg = false)
public class ColorMain extends Module {

    public ColorSetting enabledColor = registerColor("Main Color", new RCColor(255, 0, 0, 255));
    public BooleanSetting customFont = registerBoolean("Custom Font", false);
    public BooleanSetting textFont = registerBoolean("Custom Text", false);
    public ModeSetting friendColor = registerMode("Friend Color", ColorUtil.colors, "Blue");
    public ModeSetting enemyColor = registerMode("Enemy Color", ColorUtil.colors, "Red");
    public ModeSetting chatEnableColor = registerMode("Msg Enbl", ColorUtil.colors, "Green");
    public ModeSetting chatDisableColor = registerMode("Msg Dsbl", ColorUtil.colors, "Red");
    public ModeSetting colorModel = registerMode("Color Type", Arrays.asList("RGB", "HSB"), "RGB");

    public Formatting getFriendColor() {
        return ColorUtil.settingToFormatting(friendColor);
    }

    public Formatting getEnemyColor() {
        return ColorUtil.settingToFormatting(enemyColor);
    }

    public Formatting getEnabledColor() {
        return ColorUtil.settingToFormatting(chatEnableColor);
    }

    public Formatting getDisabledColor() {
        return ColorUtil.settingToFormatting(chatDisableColor);
    }

    public RCColor getFriendRCColor() {
        return new RCColor(ColorUtil.settingToColor(friendColor));
    }

    public RCColor getEnemyRCColor() {
        return new RCColor(ColorUtil.settingToColor(enemyColor));
    }
}