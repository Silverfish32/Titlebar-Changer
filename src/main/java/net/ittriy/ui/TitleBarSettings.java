package net.ittriy.ui;

import net.ittriy.TitlebarChanger;
import net.ittriy.ui.select_color.SelectTitleBarColor;
import net.ittriy.ui.select_color.SelectTitleBarStrokeColor;
import net.ittriy.ui.select_color.SelectTitleBarTextColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class TitleBarSettings extends GameOptionsScreen {
    private final Screen lastScreen;
    public static int theme = TitlebarChanger.configFile.getConfig().getTheme();
    public static int corner = TitlebarChanger.configFile.getConfig().getCorner();

    private Text windowThemeText;
    private Text windowCornerText;

    public TitleBarSettings(Screen lastScreen, GameOptions gameOptions) {
        super(lastScreen, gameOptions, Text.translatable("gui.titlebar_settings"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        updateWindowThemeText();
        updateWindowStyleText();
        addButtons();
    }

    @Override
    protected void addOptions() {

    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        clearAndInit();
    }

    private void updateWindowThemeText() {
        windowThemeText = Text.translatable(theme == 1 ? "gui.window_theme_dark" : theme == 0 ? "gui.window_theme_light" : "gui.window_theme_custom");
    }

    private void updateWindowStyleText() {
        windowCornerText = Text.translatable(corner == 0 ? "gui.window_style_rectangular" :
                (corner == 1 ? "gui.window_style_default" : "gui.window_style_semi_rounded"));
    }

    private void addButtons() {
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        this.addDrawableChild(ButtonWidget.builder(
                        windowThemeText,
                        button -> {
                            toggleTheme();
                            TitlebarChanger.applyChanges(MinecraftClient.getInstance().getWindow());
                            clearAndInit();

                        })
                .position(this.width / 2 - 155, this.height / 6 + 48 - 6)
                .size(150, 20)
                .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                        Text.translatable("gui.done"),
                        button -> MinecraftClient.getInstance().setScreen(this.lastScreen))
                .position(this.width / 2 - 100, this.height / 6 + 168)
                .size(200, 20)
                .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                        windowCornerText,
                        button -> {
                            toggleCorner();
                            TitlebarChanger.applyChanges(MinecraftClient.getInstance().getWindow());
                            clearAndInit();
                        })
                .position(this.width / 2 + 5, this.height / 6 + 48 - 6)
                .size(150, 20)
                .build()
        );

        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("gui.set_titlebar_color"),
                                button -> MinecraftClient.getInstance().setScreen(new SelectTitleBarColor(this, Text.translatable("gui.set_titlebar_color")))
                        )
                        .position(this.width / 2 - 155, this.height / 6 + 72 - 6)
                        .size(150, 20)
                        .build()
        ).active = theme == 2;

        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("gui.set_titlebar_text_color"),
                                button -> MinecraftClient.getInstance().setScreen(new SelectTitleBarTextColor(this, Text.translatable("gui.set_titlebar_text_color")))
                        )
                        .position(this.width / 2 + 5, this.height / 6 + 72 - 6)
                        .size(150, 20)
                        .build()
        ).active = theme == 2;

        this.addDrawableChild(ButtonWidget.builder(
                                Text.translatable("gui.set_titlebar_stroke_color"),
                                button -> MinecraftClient.getInstance().setScreen(new SelectTitleBarStrokeColor(this, Text.translatable("gui.set_titlebar_stroke_color")))
                        )
                        .position(this.width / 2 - 155, this.height / 6 + 96 - 6)
                        .size(310, 20)
                        .build()
        ).active = theme == 2;
    }

    private void toggleTheme() {
        switch (theme) {
            case 1: // Dark
                theme = 0;
                break;
            case 0: // Light
                theme = 2;
                break;
            case 2: // Custom
                theme = 1;
                break;
        }
        TitlebarChanger.configFile.getConfig().setTheme(theme);
        TitlebarChanger.configFile.saveConfig();
        updateWindowThemeText();
    }

    private void toggleCorner() {
        switch (corner) {
            case 0: // Rectangular
                corner = 1;
                break;
            case 1: // Default
                corner = 2;
                break;
            case 2: // Semi-Rounded
                corner = 0;
                break;
        }
        TitlebarChanger.configFile.getConfig().setCorner(corner);
        updateWindowStyleText();
        TitlebarChanger.configFile.saveConfig();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawCenteredTextWithShadow(textRenderer, this.title, this.width / 2, 40, 0xFFFFFF);
    }
}
