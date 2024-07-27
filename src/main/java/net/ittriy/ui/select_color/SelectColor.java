package net.ittriy.ui.select_color;

import net.ittriy.Titlebarchanger;
import net.ittriy.config.ConfigFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SelectColor extends Screen {

    private ColorSlider slider1;
    private ColorSlider slider2;
    private ColorSlider slider3;

    protected int redValue;
    protected int greenValue;
    protected int blueValue;

    protected final ConfigFile configFile = Titlebarchanger.configFile;

    private Screen lastScreen;

    public SelectColor(Screen lastScreen, Component title) {
        super(title);
        loadConfig();
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {

        this.clearWidgets();

        slider1 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 48 - 6, 310, 20, Component.translatable("gui.red"), 255, redValue, newValue -> redValue = newValue);
        slider2 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 72 - 6, 310, 20, Component.translatable("gui.green"), 255, greenValue, newValue -> greenValue = newValue);
        slider3 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 96 - 6, 310, 20, Component.translatable("gui.blue"), 255, blueValue, newValue -> blueValue = newValue);
        this.addRenderableWidget(slider1);
        this.addRenderableWidget(slider2);
        this.addRenderableWidget(slider3);

        this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), button -> { saveConfig();
            Minecraft.getInstance().setScreen(lastScreen); }).bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    protected void saveConfig() {

    }

    protected void loadConfig()
    {

    }

    private static class ColorSlider extends AbstractSliderButton {
        private final int maxValue;
        private final Consumer<Integer> onValueChange;

        public ColorSlider(int x, int y, int width, int height, Component text, int maxValue, int initialValue, Consumer<Integer> onValueChange) {
            super(x, y, width, height, text, initialValue / (double) maxValue);
            this.maxValue = maxValue;
            this.onValueChange = onValueChange;
            this.updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Component.literal(this.getMessage().getString().split(":")[0] + ": " + (int) (this.value * maxValue)));
        }

        @Override
        protected void applyValue() {
            int intValue = (int) (this.value * maxValue);
            this.onValueChange.accept(intValue);
        }

        public double getValue() {
            return this.value;
        }
    }
}
