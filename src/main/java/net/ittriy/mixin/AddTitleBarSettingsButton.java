package net.ittriy.mixin;

import net.ittriy.TitlebarChanger;
import net.ittriy.ui.TitleBarSettings;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Supplier;

@Mixin(OptionsScreen.class)
public abstract class AddTitleBarSettingsButton {

    @Shadow protected abstract ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier);

    @Shadow @Final private GameOptions settings;

    @Shadow @Final private Screen parent;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/option/OptionsScreen;createButton(Lnet/minecraft/text/Text;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/widget/ButtonWidget;", ordinal = 4), locals = LocalCapture.CAPTURE_FAILHARD)
    protected void init(CallbackInfo ci, DirectionalLayoutWidget directionalLayoutWidget, DirectionalLayoutWidget directionalLayoutWidget2, GridWidget gridWidget, GridWidget.Adder adder) {
        if (TitlebarChanger.systemStatus == TitlebarChanger.SystemStatus.SUITABLE) {
            if (TitlebarChanger.configFile.getConfig().getShowTheMenu()) {
                ButtonWidget buttonWidget = (ButtonWidget) adder.add(this.createButton(Text.translatable("gui.titlebar_settings"), () -> {
                    return new TitleBarSettings(this.parent, this.settings);
                }));
            }
        }
    }
}
