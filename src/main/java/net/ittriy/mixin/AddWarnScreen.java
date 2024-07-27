package net.ittriy.mixin;

import net.ittriy.TitlebarChanger;
import net.ittriy.ui.WarnScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class AddWarnScreen
{
    @Inject(at = @At("TAIL"), method = "init")
    protected void init(CallbackInfo ci) {
        if (TitlebarChanger.systemStatus != TitlebarChanger.SystemStatus.SUITABLE) {
            if (TitlebarChanger.configFile.getConfig().getShowWarnScreen()) {
                MinecraftClient.getInstance().setScreen(new WarnScreen());
            }
        }
    }
}
