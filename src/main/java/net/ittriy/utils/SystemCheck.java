package net.ittriy.utils;

import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinDef;
import net.ittriy.TitlebarChanger;

import java.util.Arrays;
import java.util.List;

public class SystemCheck {
    public static class OSVERSIONINFOEX extends Structure {
        public static class ByReference extends OSVERSIONINFOEX implements Structure.ByReference {}

        public WinDef.DWORD dwOSVersionInfoSize = new WinDef.DWORD();
        public WinDef.DWORD dwMajorVersion = new WinDef.DWORD();
        public WinDef.DWORD dwMinorVersion = new WinDef.DWORD();
        public WinDef.DWORD dwBuildNumber = new WinDef.DWORD();
        public WinDef.DWORD dwPlatformId = new WinDef.DWORD();
        public byte[] szCSDVersion = new byte[128];
        public WinDef.WORD wServicePackMajor = new WinDef.WORD();
        public WinDef.WORD wServicePackMinor = new WinDef.WORD();
        public WinDef.WORD wSuiteMask = new WinDef.WORD();
        public byte wProductType;
        public byte wReserved;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList("dwOSVersionInfoSize", "dwMajorVersion", "dwMinorVersion", "dwBuildNumber", "dwPlatformId",
                    "szCSDVersion", "wServicePackMajor", "wServicePackMinor", "wSuiteMask", "wProductType", "wReserved");
        }
    }

    public interface Ntdll extends com.sun.jna.Library {
        Ntdll INSTANCE = Native.load("ntdll", Ntdll.class);

        int RtlGetVersion(OSVERSIONINFOEX.ByReference osVersionInfo);
    }

    public static TitlebarChanger.SystemStatus checkSystem() {
        OSVERSIONINFOEX.ByReference osVersionInfo = new OSVERSIONINFOEX.ByReference();
        osVersionInfo.dwOSVersionInfoSize.setValue(osVersionInfo.size());

        int result = Ntdll.INSTANCE.RtlGetVersion(osVersionInfo);

        if (result == 0) {
            int major = osVersionInfo.dwMajorVersion.intValue();
            int minor = osVersionInfo.dwMinorVersion.intValue();
            int build = osVersionInfo.dwBuildNumber.intValue();

            if (major == 10 && build >= 22000) {
                TitlebarChanger.LOGGER.info("Detected Windows 11, build number {}.{}", major, build);
                return TitlebarChanger.SystemStatus.SUITABLE;
            } else if (major == 10) {
                TitlebarChanger.LOGGER.info("Your operating system has an earlier version than is required to use this mod. Some functions may be limited. {}.{}", major, build);
                return TitlebarChanger.SystemStatus.LIMITED_SUITABILITY;
            } else {
                TitlebarChanger.LOGGER.info("Unfortunately, your operating system does not support using this mod, so it cannot work. {}.{}", major, build);
                return TitlebarChanger.SystemStatus.NOT_SUITABLE;
            }
        } else {
            TitlebarChanger.LOGGER.error("Failed to get version info, error code: {}", result);
            return TitlebarChanger.SystemStatus.NOT_SUITABLE;
        }
    }
}
