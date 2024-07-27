package net.ittriy.mixins;

import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import net.ittriy.Titlebarchanger;
import net.ittriy.libs.DwmApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(Minecraft.class)
public class WindowCreateMixin {

    @Inject(at = @At("TAIL"), method = "<init>")
    public void Minecraft(GameConfig p_91084_, CallbackInfo ci) {
        if (Titlebarchanger.systemStatus == Titlebarchanger.SystemStatus.SUITABLE)
        Titlebarchanger.applyChanges(Minecraft.getInstance().getWindow());
        else if (Titlebarchanger.systemStatus == Titlebarchanger.SystemStatus.LIMITED_SUITABILITY) {
            long glfwWindow = Minecraft.getInstance().getWindow().getWindow();
            long hwndLong = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
            WinDef.HWND hwnd = new WinDef.HWND(Pointer.createConstant(hwndLong));
            Memory memTheme = new Memory(Native.POINTER_SIZE);
            memTheme.setInt(0, 1);
            DwmApi.INSTANCE.DwmSetWindowAttribute(
                    hwnd,
                    DwmApi.DWMWA_USE_IMMERSIVE_DARK_MODE,
                    new WinDef.LPVOID(memTheme),
                    new WinDef.DWORD(WinDef.DWORD.SIZE));
        }
        else
        {
            LOGGER.warn("Your OS is not suitable");
        }
    }
}