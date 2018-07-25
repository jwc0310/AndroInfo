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

    public static boolean checkRoot(Context context) {
        boolean root = false;

        return root;
    }

    public static String deviceInfo(Context context) {

        StringBuilder builder = new StringBuilder();
        builder.append(formatProperty("ro.debuggable", PropertyUtil.getprop("ro.debuggable", "no value")));
        builder.append(formatProperty("ro.secure", PropertyUtil.getprop("ro.secure", "no value")));
        builder.append(formatProperty("persist.sys.usb.config", PropertyUtil.getprop("persist.sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.config", PropertyUtil.getprop("sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.state", PropertyUtil.getprop("sys.usb.state", "no value")));
        builder.append(formatProperty("persist.service.adb.enable", PropertyUtil.getprop("persist.service.adb.enable", "no value")));

        builder.append(formatProperty("ro.build.flavor", PropertyUtil.getprop("ro.build.flavor", "no value")));
        builder.append(formatProperty("ro.build.tags", PropertyUtil.getprop("ro.build.tags", "no value")));
        builder.append(formatProperty("ro.board.platform", PropertyUtil.getprop("ro.board.platform", "no value")));

        builder.append("\n");
        builder.append(formatProperty("gsm.version.baseband", PropertyUtil.getprop("gsm.version.baseband", "no value")));
        builder.append(formatProperty("gsm.operator.alpha", PropertyUtil.getprop("gsm.operator.alpha", "no value")));

        builder.append("\n");
        builder.append(formatProperty("ro.product.board", Build.BOARD));
        builder.append(formatProperty("ro.product.name", Build.PRODUCT));
        builder.append(formatProperty("ro.product.device", Build.DEVICE));
        builder.append(formatProperty("ro.build.id", Build.ID));
        builder.append(formatProperty("ro.build.display.id", Build.DISPLAY));

        builder.append(formatProperty("ro.product.manufacturer", Build.MANUFACTURER));

        builder.append(formatProperty("ro.product.brand", Build.BRAND));
        builder.append(formatProperty("ro.product.model", Build.MODEL));
        builder.append(formatProperty("ro.hardware", Build.HARDWARE));
        builder.append(formatProperty("ro.build.fingerprint", Build.FINGERPRINT));

        builder.append(formatProperty("ro.build.type", Build.TYPE));
        builder.append(formatProperty("cpuabi", Build.CPU_ABI));
        builder.append(formatProperty("ro.bootloader", Build.BOOTLOADER));

        builder.append(formatProperty("ro.build.host", Build.HOST));
        builder.append(formatProperty("ro.build.user", Build.USER));
        builder.append(formatProperty("ro.build.tags", Build.TAGS));

        String androidid = Settings.System.getString(context.getContentResolver(), "android_id");
        builder.append(formatProperty("android_id", androidid));

        builder.append(formatProperty("Serial", Build.SERIAL));

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

    private static boolean checkDeviceDebuggable() {
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }


}
