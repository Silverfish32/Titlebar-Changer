package net.ittriy.mixins;

import net.ittriy.Titlebarchanger;
import net.ittriy.ui.WarnScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(TitleScreen.class)
public class AddWarnScreen
{
    @Inject(at = @At("TAIL"), method = "init")
    protected void init(CallbackInfo ci) {
        if (Titlebarchanger.systemStatus != Titlebarchanger.SystemStatus.SUITABLE) {
            if (Titlebarchanger.configFile.getConfig().getShowWarnScreen()) {
                Minecraft.getInstance().setScreen(new WarnScreen());
            }
        }
    }
}
