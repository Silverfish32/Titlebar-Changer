package net.ittriy.ui;

import net.ittriy.Titlebarchanger;
import net.ittriy.ui.select_color.SelectTitleBarColor;
import net.ittriy.ui.select_color.SelectTitleBarStrokeColor;
import net.ittriy.ui.select_color.SelectTitleBarTextColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class TitleBarSettings extends OptionsSubScreen {
    private final Screen lastScreen;
    public static int theme = Titlebarchanger.configFile.getConfig().getTheme();
    public static int corner = Titlebarchanger.configFile.getConfig().getCorner();

    private Component windowThemeText;
    private Component windowCornerText;

    public TitleBarSettings(Screen lastScreen, Options options) {
        super(lastScreen,  options ,Component.translatable("gui.titlebar_settings"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        updateWindowThemeText();
        updateWindowStyleText();
    }

    @Override
    public void resize(Minecraft p_96575_, int p_96576_, int p_96577_) {
        rebuildWidgets();
        super.resize(p_96575_, p_96576_, p_96577_);
    }

    @Override
    protected void addOptions() {

    }

    private void updateWindowThemeText() {
        windowThemeText = Component.translatable(theme == 1 ? "gui.window_theme_dark" : theme == 0 ? "gui.window_theme_light" : "gui.window_theme_custom");
    }

    private void updateWindowStyleText() {
        windowCornerText = Component.translatable(corner == 0 ? "gui.window_style_rectangular" :
                (corner == 1 ? "gui.window_style_default" : "gui.window_style_semi_rounded"));
    }

    private void addButtons() {
        this.addRenderableWidget(Button.builder(
                        windowThemeText,
                        button -> {
                            toggleTheme();
                            Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
                            rebuildWidgets();
                        })
                .bounds(this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20)
                .build()
        );

        this.addRenderableWidget(Button.builder(
                        CommonComponents.GUI_DONE,
                        p -> this.minecraft.setScreen(this.lastScreen))
                .bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build()
        );

        this.addRenderableWidget(Button.builder(
                        windowCornerText,
                        button -> {
                            toggleCorner();
                            Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
                            rebuildWidgets();
                        })
                .bounds(this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20)
                .build()
        );

        this.addRenderableWidget(Button.builder(
                                Component.translatable("gui.set_titlebar_color"),
                                button -> Minecraft.getInstance().setScreen(new SelectTitleBarColor(this, Component.translatable("gui.set_titlebar_color")))
                        )
                        .bounds(this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20)
                        .build()
        ).active = theme == 2;

        this.addRenderableWidget(Button.builder(
                                Component.translatable("gui.set_titlebar_text_color"),
                                button -> Minecraft.getInstance().setScreen(new SelectTitleBarTextColor(this, Component.translatable("gui.set_titlebar_text_color")))
                        )
                        .bounds(this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20)
                        .build()
        ).active = theme == 2;

        this.addRenderableWidget(Button.builder(
                                Component.translatable("gui.set_titlebar_stroke_color"),
                                button -> Minecraft.getInstance().setScreen(new SelectTitleBarStrokeColor(this, Component.translatable("gui.set_titlebar_stroke_color")))
                        )
                        .bounds(this.width / 2 - 155, this.height / 6 + 96 - 6, 310, 20)
                        .build()
        ).active = theme == 2;
    }


    private void toggleTheme() {
        switch (theme){
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
        Titlebarchanger.configFile.getConfig().setTheme(theme);
        Titlebarchanger.configFile.saveConfig();
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
        Titlebarchanger.configFile.getConfig().setCorner(corner);
        updateWindowStyleText();
        Titlebarchanger.configFile.saveConfig();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        addButtons();
    }

}
