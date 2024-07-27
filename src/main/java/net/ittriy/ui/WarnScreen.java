package net.ittriy.ui;

import net.ittriy.Titlebarchanger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class WarnScreen extends Screen {
    private static final Component WARNING_TITLE = Component.translatable("gui.warning_title");
    private static Component WARNING_MESSAGE = Component.translatable("gui.warning_desc");

    private List<FormattedCharSequence> wrappedMessage;

    public WarnScreen() {
        super(WARNING_TITLE);
    }

    @Override
    protected void init() {
        if (Titlebarchanger.systemStatus == Titlebarchanger.SystemStatus.LIMITED_SUITABILITY) {
            WARNING_MESSAGE = Component.translatable("gui.warning_desc");
        } else if (Titlebarchanger.systemStatus == Titlebarchanger.SystemStatus.NOT_SUITABLE) {
            WARNING_MESSAGE = Component.translatable("gui.warning_desc_not_suitable");
        }

        super.init();
        wrappedMessage = font.split(WARNING_MESSAGE, width - 50);

        int buttonWidth = 300;
        int buttonHeight = 20;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = height / 2 + (wrappedMessage.size() * font.lineHeight) / 2 + 30;
        this.addRenderableWidget(Button.builder(Component.translatable("gui.close"), p_280801_ -> this.onClose())
                .bounds(buttonX, buttonY, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        super.render(guiGraphics, mouseX, mouseY, delta);

        guiGraphics.drawCenteredString(font, WARNING_TITLE, width / 2, height / 2 - (font.lineHeight * 2) - 30, 0xFF0000);

        int messageY = height / 2 - (wrappedMessage.size() * font.lineHeight) / 2;
        for (FormattedCharSequence line : wrappedMessage) {
            guiGraphics.drawCenteredString(font, line, width / 2, messageY, 0xFFFFFF);
            messageY += font.lineHeight + 5;
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        Titlebarchanger.configFile.getConfig().setShowWarnScreen(false);
        Titlebarchanger.configFile.saveConfig();
        Minecraft.getInstance().setScreen(null);
    }
}
