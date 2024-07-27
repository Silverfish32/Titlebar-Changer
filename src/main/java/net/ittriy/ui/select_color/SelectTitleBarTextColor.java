package net.ittriy.ui.select_color;


import net.ittriy.Titlebarchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SelectTitleBarTextColor extends SelectColor {
    public SelectTitleBarTextColor(Screen lastScreen, Component title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarTextColor().setR(redValue);
        configFile.getConfig().getTitleBarTextColor().setB(blueValue);
        configFile.getConfig().getTitleBarTextColor().setG(greenValue);
        configFile.saveConfig();
        Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
        super.saveConfig();
    }

    @Override
    protected void loadConfig() {
        super.loadConfig();
        redValue = configFile.getConfig().getTitleBarTextColor().getR();
        greenValue = configFile.getConfig().getTitleBarTextColor().getG();
        blueValue = configFile.getConfig().getTitleBarTextColor().getB();
    }
}
