package me.ritomg.raptor.util;

import com.mojang.blaze3d.platform.GlStateManager;

import java.awt.*;

public class RCColor extends Color {

    private static final long serialVersionUID = 1L;

    public RCColor(int rgb) {
        super(rgb);
    }

    public RCColor(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public RCColor(int r, int g, int b) {
        super(r, g, b);
    }

    public RCColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public RCColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public RCColor(RCColor color, int a) {
        super(color.getRed(), color.getGreen(), color.getBlue(), a);
    }

    public static RCColor fromHSB(float hue, float saturation, float brightness) {
        return new RCColor(Color.getHSBColor(hue, saturation, brightness));
    }

    public float getHue() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[0];
    }

    public float getSaturation() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[1];
    }

    public float getBrightness() {
        return RGBtoHSB(getRed(), getGreen(), getBlue(), null)[2];
    }

    public void glColor() {
        GlStateManager.clearColor(getRed() / 255.0f, getGreen() / 255.0f, getBlue() / 255.0f, getAlpha() / 255.0f);
    }
    
}
