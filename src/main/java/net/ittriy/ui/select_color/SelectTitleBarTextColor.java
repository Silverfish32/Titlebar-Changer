package net.ittriy.ui.select_color;

import net.ittriy.TitlebarChanger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SelectTitleBarTextColor extends SelectColor {
    public SelectTitleBarTextColor(Screen lastScreen, Text title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarTextColor().setR(redValue);
        configFile.getConfig().getTitleBarTextColor().setB(blueValue);
        configFile.getConfig().getTitleBarTextColor().setG(greenValue);
        configFile.saveConfig();
        TitlebarChanger.applyChanges(MinecraftClient.getInstance().getWindow());
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
