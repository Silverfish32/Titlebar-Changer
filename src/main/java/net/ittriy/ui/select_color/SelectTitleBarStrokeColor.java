package net.ittriy.ui.select_color;

import net.ittriy.Titlebarchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SelectTitleBarStrokeColor extends SelectColor {
    public SelectTitleBarStrokeColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarStrokeColor().setR(redValue);
        configFile.getConfig().getTitleBarStrokeColor().setB(blueValue);
        configFile.getConfig().getTitleBarStrokeColor().setG(greenValue);
        configFile.saveConfig();
        Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
        super.saveConfig();
    }

    @Override
    protected void loadConfig() {
        super.loadConfig();
        redValue = configFile.getConfig().getTitleBarStrokeColor().getR();
        blueValue = configFile.getConfig().getTitleBarStrokeColor().getB();
        greenValue = configFile.getConfig().getTitleBarStrokeColor().getG();
    }
}
