package net.ittriy.ui.select_color;

import net.ittriy.TitlebarChanger;
import net.ittriy.config.ConfigFile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class SelectColor extends Screen {

    private ColorSlider slider1;
    private ColorSlider slider2;
    private ColorSlider slider3;

    protected int redValue;
    protected int greenValue;
    protected int blueValue;

    protected final ConfigFile configFile = TitlebarChanger.configFile;

    private Screen lastScreen;

    public SelectColor(Screen lastScreen, Text title) {
        super(title);
        loadConfig();
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        slider1 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 48 - 6, 310, 20, Text.translatable("gui.red"), 255, redValue, newValue -> redValue = newValue);
        slider2 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 72 - 6, 310, 20, Text.translatable("gui.green"), 255, greenValue, newValue -> greenValue = newValue);
        slider3 = new ColorSlider(this.width / 2 - 155, this.height / 6 + 96 - 6, 310, 20, Text.translatable("gui.blue"), 255, blueValue, newValue -> blueValue = newValue);
        this.addDrawableChild(slider1);
        this.addDrawableChild(slider2);
        this.addDrawableChild(slider3);

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.done"), button -> { saveConfig();
            MinecraftClient.getInstance().setScreen(lastScreen); }).dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20).build());
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    protected void saveConfig() {

    }

    protected void loadConfig()
    {

    }

    private static class ColorSlider extends SliderWidget {
        private final int maxValue;
        private final Consumer<Integer> onValueChange;

        public ColorSlider(int x, int y, int width, int height, Text text, int maxValue, int initialValue, Consumer<Integer> onValueChange) {
            super(x, y, width, height, text, initialValue / (double) maxValue);
            this.maxValue = maxValue;
            this.onValueChange = onValueChange;
            this.updateMessage();
        }

        @Override
        protected void updateMessage() {
            this.setMessage(Text.literal(this.getMessage().getString().split(":")[0] + ": " + (int) (this.value * maxValue)));
        }

        @Override
        protected void applyValue() {
            int intValue = (int) (this.value * maxValue);
            this.onValueChange.accept(intValue);
        }
    }
}
