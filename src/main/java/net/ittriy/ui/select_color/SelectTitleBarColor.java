package net.ittriy.ui.select_color;

import net.ittriy.Titlebarchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SelectTitleBarColor extends SelectColor{

    public SelectTitleBarColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarColor().setR(redValue);
        configFile.getConfig().getTitleBarColor().setB(blueValue);
        configFile.getConfig().getTitleBarColor().setG(greenValue);
        configFile.saveConfig();
        Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
        super.saveConfig();
    }

    @Override
    protected void loadConfig() {
        super.loadConfig();
        redValue = configFile.getConfig().getTitleBarColor().getR();
        blueValue = configFile.getConfig().getTitleBarColor().getB();
        greenValue = configFile.getConfig().getTitleBarColor().getG();
    }

}
