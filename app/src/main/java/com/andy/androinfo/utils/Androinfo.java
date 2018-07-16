package com.andy.androinfo.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Androinfo {

    public static String deviceInfo(Context context) {

        StringBuilder builder = new StringBuilder();
        builder.append(formatProperty("ro.debuggable", PropertyUtil.getprop("ro.debuggable", "no value")));
        builder.append(formatProperty("ro.secure", PropertyUtil.getprop("ro.secure", "no value")));
        builder.append(formatProperty("persist.sys.usb.config", PropertyUtil.getprop("persist.sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.config", PropertyUtil.getprop("sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.state", PropertyUtil.getprop("sys.usb.state", "no value")));
        builder.append(formatProperty("persist.service.adb.enable", PropertyUtil.getprop("persist.service.adb.enable", "no value")));
        builder.append(formatProperty("gsm.version.baseband", PropertyUtil.getprop("gsm.version.baseband", "no value")));
        builder.append(formatProperty("ro.build.flavor", PropertyUtil.getprop("ro.build.flavor", "no value")));
        builder.append(formatProperty("ro.board.platform", PropertyUtil.getprop("ro.board.platform", "no value")));

        builder.append("\n");
        builder.append(formatProperty("Board", Build.BOARD));
        builder.append(formatProperty("Product", Build.PRODUCT));
        builder.append(formatProperty("Manufacturer", Build.MANUFACTURER));
        builder.append(formatProperty("Brand", Build.BRAND));
        builder.append(formatProperty("Device", Build.DEVICE));
        builder.append(formatProperty("Module", Build.MODEL));
        builder.append(formatProperty("Hardware", Build.HARDWARE));
        builder.append(formatProperty("Fingerprint", Build.FINGERPRINT));
        builder.append(formatProperty("Serial", Build.SERIAL));
        builder.append(formatProperty("Type", Build.TYPE));
        builder.append(formatProperty("CpuAbi", Build.CPU_ABI));
        builder.append(formatProperty("BootLoader", Build.BOOTLOADER));
        builder.append(formatProperty("Display", Build.DISPLAY));
        builder.append(formatProperty("Host", Build.HOST));
        builder.append(formatProperty("Tags", Build.TAGS));

        String androidid = Settings.System.getString(context.getContentResolver(), "android_id");
        builder.append(formatProperty("android_id", androidid));

        return builder.toString();
    }

    private static String formatProperty(String prefix, String value) {
        StringBuilder builder = new StringBuilder("");
        builder.append(prefix);
        builder.append(" : ");
        builder.append(value);
        builder.append("\n");
        return builder.toString();
    }

}
