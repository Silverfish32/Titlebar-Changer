package net.ittriy.utils;

public class ColorConverter {
    public static int rgbToHex(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException("RGB values should be between 0 and 255.");
        }
        return (b << 16) | (g << 8) | r;
    }
}
