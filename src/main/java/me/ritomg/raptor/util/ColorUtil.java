package me.ritomg.raptor.util;

import me.ritomg.raptor.setting.values.ModeSetting;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class ColorUtil {

    public static List<String> colors = Arrays.asList("Black", "Dark Green", "Dark Red", "Gold", "Dark Gray", "Green", "Red", "Yellow", "Dark Blue", "Dark Aqua", "Dark Purple", "Gray", "Blue", "Aqua", "Light Purple", "White");

    public static Formatting settingToFormatting(ModeSetting setting) {
        if (setting.getValue().equalsIgnoreCase("Black")) {
            return Formatting.BLACK;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Green")) {
            return Formatting.DARK_GREEN;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Red")) {
            return Formatting.DARK_RED;
        }
        if (setting.getValue().equalsIgnoreCase("Gold")) {
            return Formatting.GOLD;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Gray")) {
            return Formatting.DARK_GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Green")) {
            return Formatting.GREEN;
        }
        if (setting.getValue().equalsIgnoreCase("Red")) {
            return Formatting.RED;
        }
        if (setting.getValue().equalsIgnoreCase("Yellow")) {
            return Formatting.YELLOW;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Blue")) {
            return Formatting.DARK_BLUE;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Aqua")) {
            return Formatting.DARK_AQUA;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Purple")) {
            return Formatting.DARK_PURPLE;
        }
        if (setting.getValue().equalsIgnoreCase("Gray")) {
            return Formatting.GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Blue")) {
            return Formatting.BLUE;
        }
        if (setting.getValue().equalsIgnoreCase("Light Purple")) {
            return Formatting.LIGHT_PURPLE;
        }
        if (setting.getValue().equalsIgnoreCase("White")) {
            return Formatting.WHITE;
        }
        if (setting.getValue().equalsIgnoreCase("Aqua")) {
            return Formatting.AQUA;
        }
        return null;
    }

    public static Formatting textToFormatting(ModeSetting setting) {
        if (setting.getValue().equalsIgnoreCase("Black")) {
            return Formatting.BLACK;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Green")) {
            return Formatting.DARK_GREEN;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Red")) {
            return Formatting.DARK_RED;
        }
        if (setting.getValue().equalsIgnoreCase("Gold")) {
            return Formatting.GOLD;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Gray")) {
            return Formatting.DARK_GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Green")) {
            return Formatting.GREEN;
        }
        if (setting.getValue().equalsIgnoreCase("Red")) {
            return Formatting.RED;
        }
        if (setting.getValue().equalsIgnoreCase("Yellow")) {
            return Formatting.YELLOW;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Blue")) {
            return Formatting.DARK_BLUE;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Aqua")) {
            return Formatting.DARK_AQUA;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Purple")) {
            return Formatting.DARK_PURPLE;
        }
        if (setting.getValue().equalsIgnoreCase("Gray")) {
            return Formatting.GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Blue")) {
            return Formatting.BLUE;
        }
        if (setting.getValue().equalsIgnoreCase("Light Purple")) {
            return Formatting.LIGHT_PURPLE;
        }
        if (setting.getValue().equalsIgnoreCase("White")) {
            return Formatting.WHITE;
        }
        if (setting.getValue().equalsIgnoreCase("Aqua")) {
            return Formatting.AQUA;
        }
        return null;
    }

    public static Color settingToColor(ModeSetting setting) {
        if (setting.getValue().equalsIgnoreCase("Black")) {
            return Color.BLACK;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Green")) {
            return Color.GREEN.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Dark Red")) {
            return Color.RED.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Gold")) {
            return Color.yellow.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Dark Gray")) {
            return Color.DARK_GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Green")) {
            return Color.green;
        }
        if (setting.getValue().equalsIgnoreCase("Red")) {
            return Color.red;
        }
        if (setting.getValue().equalsIgnoreCase("Yellow")) {
            return Color.yellow;
        }
        if (setting.getValue().equalsIgnoreCase("Dark Blue")) {
            return Color.blue.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Dark Aqua")) {
            return Color.CYAN.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Dark Purple")) {
            return Color.MAGENTA.darker();
        }
        if (setting.getValue().equalsIgnoreCase("Gray")) {
            return Color.GRAY;
        }
        if (setting.getValue().equalsIgnoreCase("Blue")) {
            return Color.blue;
        }
        if (setting.getValue().equalsIgnoreCase("Light Purple")) {
            return Color.magenta;
        }
        if (setting.getValue().equalsIgnoreCase("White")) {
            return Color.WHITE;
        }
        if (setting.getValue().equalsIgnoreCase("Aqua")) {
            return Color.cyan;
        }
        return Color.WHITE;
    }
}