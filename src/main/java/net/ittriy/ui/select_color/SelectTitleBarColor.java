package net.ittriy.ui.select_color;

import net.ittriy.TitlebarChanger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SelectTitleBarColor extends SelectColor{

    public SelectTitleBarColor(Screen lastScreen, Text title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarColor().setR(redValue);
        configFile.getConfig().getTitleBarColor().setB(blueValue);
        configFile.getConfig().getTitleBarColor().setG(greenValue);
        configFile.saveConfig();
        TitlebarChanger.applyChanges(MinecraftClient.getInstance().getWindow());
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
