package net.ittriy.ui.select_color;

import net.ittriy.TitlebarChanger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class SelectTitleBarStrokeColor extends SelectColor {
    public SelectTitleBarStrokeColor(Screen lastScreen, Text title) {
        super(lastScreen, title);
    }

    @Override
    protected void saveConfig() {
        configFile.getConfig().getTitleBarStrokeColor().setR(redValue);
        configFile.getConfig().getTitleBarStrokeColor().setB(blueValue);
        configFile.getConfig().getTitleBarStrokeColor().setG(greenValue);
        configFile.saveConfig();
        TitlebarChanger.applyChanges(MinecraftClient.getInstance().getWindow());
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
