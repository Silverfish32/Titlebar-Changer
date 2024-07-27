package net.ittriy.mixins;

import net.ittriy.Titlebarchanger;
import net.ittriy.ui.TitleBarSettings;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.network.chat.Component;
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
    @Shadow protected abstract Button openScreenButton(Component p_344129_, Supplier<Screen> p_342943_);

    @Shadow @Final private Options options;

    @Shadow @Final private Screen lastScreen;

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/options/OptionsScreen;openScreenButton(Lnet/minecraft/network/chat/Component;Ljava/util/function/Supplier;)Lnet/minecraft/client/gui/components/Button;", ordinal = 4), locals = LocalCapture.CAPTURE_FAILHARD)
    protected void init(CallbackInfo ci, LinearLayout $$0, LinearLayout $$1, GridLayout $$2, GridLayout.RowHelper $$3) {
        if (Titlebarchanger.systemStatus == Titlebarchanger.SystemStatus.SUITABLE) {
            $$3.addChild(this.openScreenButton(Component.translatable("gui.titlebar_settings"), () -> new TitleBarSettings(this.lastScreen, this.options)));
        }
    }
}
