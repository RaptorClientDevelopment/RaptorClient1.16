package me.ritomg.raptor.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

public class FontUtil {

    public static MinecraftClient mc = MinecraftClient.getInstance();

    public static void drawStringWithShadow(MatrixStack matrix, TextRenderer renderer, String text, int x, int y, RCColor color) {
        DrawableHelper.drawStringWithShadow(matrix, renderer,text, x, y, color.getRGB());
    }

    public static int getFontHeight() {
        return mc.textRenderer.fontHeight;
    }

    public static  int getFontWidth(String text) {
        return mc.textRenderer.getWidth(text);
    }

}
