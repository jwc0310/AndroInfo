package com.andy.androinfo.emulator;

import android.content.Context;
import android.os.Debug;
import android.provider.Settings;

import java.io.File;

/**
 * Created by Administrator on 2018/6/28.
 */

public class Detecter {

    public static String getDetecterInfo(Context context) {
        StringBuilder builder = new StringBuilder("");

        builder.append("Usb debug is ");
        builder.append(checkUsbDebugModeIsOn(context) ? "on" : "off");
        builder.append("\n");

        builder.append("Developer mode is ");
        builder.append(isDebuggerConnected() ? "on" : "off");
        builder.append("\n");

        builder.append("Cgroup file is ");
        builder.append(checkCgroupFileExist() ? "exist" : "not exist");
        builder.append("\n");

        return builder.toString();
    }

    public static boolean isDebuggerConnected() {
        return Debug.isDebuggerConnected();
    }

    private static boolean checkUsbDebugModeIsOn (Context context) {
        boolean enableAdb = (Settings.Secure.getInt(context.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0) > 0);
        return enableAdb;
    }

    private static boolean checkCgroupFileExist() {
        File cgroupFile = new File("/proc/self/cgroup");
        return cgroupFile.exists();
    }

    private static String getUsbRelatedProperties () {
        StringBuilder builder = new StringBuilder("");
        return builder.toString();

    }

}
