package net.ittriy;

import com.mojang.blaze3d.platform.Window;
import com.mojang.logging.LogUtils;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef;
import net.ittriy.config.ConfigFile;
import net.ittriy.libs.DwmApi;
import net.ittriy.ui.TitleBarSettings;
import net.ittriy.utils.ColorConverter;
import net.ittriy.utils.SystemCheck;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.slf4j.Logger;


@Mod(Titlebarchanger.MODID)
public class Titlebarchanger {

    public static final String MODID = "titlebarchanger";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ConfigFile configFile = new ConfigFile();
    public static SystemStatus systemStatus;

    public Titlebarchanger() {

        systemStatus = SystemCheck.checkSystem();
        configFile.loadConfig();
    }

    public static void applyChanges(Window window) {
        long glfwWindow = window.getWindow();
        long hwndLong = GLFWNativeWin32.glfwGetWin32Window(glfwWindow);
        WinDef.HWND hwnd = new WinDef.HWND(Pointer.createConstant(hwndLong));

        Memory memTheme = new Memory(Native.POINTER_SIZE);
        Memory memCorner = new Memory(Native.POINTER_SIZE);
        Memory memTitleBarColor = new Memory(Native.POINTER_SIZE);
        Memory memTitleBarTextColor = new Memory(Native.POINTER_SIZE);
        Memory memTitleBarStrokeColor = new Memory(Native.POINTER_SIZE);

        if (TitleBarSettings.theme == 1) {
            memTheme.setInt(0, 1);
            memTitleBarColor.setInt(0, -1);
            memTitleBarTextColor.setInt(0, -1);
            memTitleBarStrokeColor.setInt(0, -1);
        } else if (TitleBarSettings.theme == 0) {
            memTheme.setInt(0, 0);
            memTitleBarColor.setInt(0, -1);
            memTitleBarTextColor.setInt(0, -1);
            memTitleBarStrokeColor.setInt(0, -1);
        }
        else
        {
            memTheme.setInt(0, 0);
            memTitleBarColor.setInt(0, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarColor().getR(), configFile.getConfig().getTitleBarColor().getG()
                    ,configFile.getConfig().getTitleBarColor().getB()));
            memTitleBarTextColor.setInt(0, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarTextColor().getR(), configFile.getConfig().getTitleBarTextColor().getG()
                    ,configFile.getConfig().getTitleBarTextColor().getB()));
            memTitleBarStrokeColor.setInt(0, ColorConverter.rgbToHex(configFile.getConfig().getTitleBarStrokeColor().getR(), configFile.getConfig().getTitleBarStrokeColor().getG()
                    ,configFile.getConfig().getTitleBarStrokeColor().getB()));
        }

        if (TitleBarSettings.corner == 0) {
            memCorner.setInt(0, 1);
        } else if (TitleBarSettings.corner == 1) {
            memCorner.setInt(0, 2);
        }
        else if (TitleBarSettings.corner == 2) {
            memCorner.setInt(0, 3);
        }

        DwmApi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DwmApi.DWMWA_USE_IMMERSIVE_DARK_MODE,
                new WinDef.LPVOID(memTheme),
                new WinDef.DWORD(WinDef.DWORD.SIZE));
        DwmApi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DwmApi.DWMWA_WINDOW_CORNER_PREFERENCE,
                new WinDef.LPVOID(memCorner),
                new WinDef.DWORD(WinDef.DWORD.SIZE));
        DwmApi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DwmApi.DWMWA_CAPTION_COLOR,
                new WinDef.LPVOID(memTitleBarColor),
                new WinDef.DWORD(WinDef.DWORD.SIZE));
        DwmApi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DwmApi.DWMWA_TEXT_COLOR,
                new WinDef.LPVOID(memTitleBarTextColor),
                new WinDef.DWORD(WinDef.DWORD.SIZE));
        DwmApi.INSTANCE.DwmSetWindowAttribute(
                hwnd,
                DwmApi.DWMWA_BORDER_COLOR,
                new WinDef.LPVOID(memTitleBarStrokeColor),
                new WinDef.DWORD(WinDef.DWORD.SIZE));
    }

    public enum SystemStatus {
        SUITABLE,
        LIMITED_SUITABILITY,
        NOT_SUITABLE
    }
}
