package net.ittriy.ui;

import net.ittriy.TitlebarChanger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;

public class WarnScreen extends Screen {
    private static final Text WARNING_TITLE = Text.translatable("gui.warning_title");
    private static Text WARNING_MESSAGE = Text.translatable("gui.warning_desc");

    private List<OrderedText> wrappedMessage;

    public WarnScreen() {
        super(WARNING_TITLE);
    }

    @Override
    protected void init() {
        if (TitlebarChanger.systemStatus == TitlebarChanger.SystemStatus.LIMITED_SUITABILITY) {
            WARNING_MESSAGE = Text.translatable("gui.warning_desc");
        } else if (TitlebarChanger.systemStatus == TitlebarChanger.SystemStatus.NOT_SUITABLE) {
            WARNING_MESSAGE = Text.translatable("gui.warning_desc_not_suitable");
        }

        super.init();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        wrappedMessage = textRenderer.wrapLines(WARNING_MESSAGE, width - 50);

        int buttonWidth = 300;
        int buttonHeight = 20;
        int buttonX = (width - buttonWidth) / 2;
        int buttonY = height / 2 + (wrappedMessage.size() * textRenderer.fontHeight) / 2 + 30;
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.close"), p_280801_ -> this.close())
                .dimensions(buttonX, buttonY, buttonWidth, buttonHeight)
                .build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        context.drawCenteredTextWithShadow(textRenderer, WARNING_TITLE, width / 2, height / 2 - (textRenderer.fontHeight * 2) - 30, 0xFF0000);

        int messageY = height / 2 - (wrappedMessage.size() * textRenderer.fontHeight) / 2;
        for (OrderedText line : wrappedMessage) {
            context.drawCenteredTextWithShadow(textRenderer, line, width / 2, messageY, 0xFFFFFF);
            messageY += textRenderer.fontHeight + 5;
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void close() {
        super.close();
        TitlebarChanger.configFile.getConfig().setShowWarnScreen(false);
        TitlebarChanger.configFile.saveConfig();
        MinecraftClient.getInstance().setScreen(null);
    }

}
