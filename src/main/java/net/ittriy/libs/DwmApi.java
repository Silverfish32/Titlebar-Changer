package net.ittriy.libs;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;

public interface DwmApi extends StdCallLibrary {
    DwmApi INSTANCE = Native.load("dwmapi", DwmApi.class, W32APIOptions.DEFAULT_OPTIONS);

    WinDef.DWORD DWMWA_USE_IMMERSIVE_DARK_MODE = new WinDef.DWORD(20);
    WinDef.DWORD DWMWA_WINDOW_CORNER_PREFERENCE = new WinDef.DWORD(33);

    WinDef.DWORD DWMWA_CAPTION_COLOR = new WinDef.DWORD(35);
    WinDef.DWORD DWMWA_TEXT_COLOR = new WinDef.DWORD(36);
    WinDef.DWORD DWMWA_BORDER_COLOR  = new WinDef.DWORD(34);

    @SuppressWarnings("UnusedReturnValue")
    WinNT.HRESULT DwmSetWindowAttribute(
            WinDef.HWND hwnd,
            WinDef.DWORD dwAttribute,
            WinDef.LPVOID pvAttribute,
            WinDef.DWORD cbAttribute
    );
}
