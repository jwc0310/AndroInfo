package com.andy.androinfo.utils;

import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Array;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Androinfo {

    public static boolean checkRoot(Context context) {
        boolean root = false;

        String[] list = new String[] {"a", "b"};
        Object object = Array.newInstance(String.class, 2);
        Array.set(object, 0, list[0]);
        Array.set(object, 1, list[1]);

        return root;
    }

    public static String deviceInfo(Context context) {

        StringBuilder builder = new StringBuilder();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        builder.append(formatProperty("Mac: ", WifiUtils.getMacDefault(context)));
        builder.append(formatProperty("device_id", tm.getDeviceId()));
        builder.append(formatProperty("System android_id", Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID)));
        builder.append(formatProperty("Secure android_id", Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)));
        builder.append(formatProperty("ro.debuggable", PropertyUtil.getprop("ro.debuggable", "no value")));
        builder.append(formatProperty("ro.secure", PropertyUtil.getprop("ro.secure", "no value")));
        builder.append(formatProperty("persist.sys.usb.config", PropertyUtil.getprop("persist.sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.config", PropertyUtil.getprop("sys.usb.config", "no value")));
        builder.append(formatProperty("sys.usb.state", PropertyUtil.getprop("sys.usb.state", "no value")));
        builder.append(formatProperty("persist.service.adb.enable", PropertyUtil.getprop("persist.service.adb.enable", "no value")));

        builder.append(formatProperty("ro.build.flavor", PropertyUtil.getprop("ro.build.flavor", "no value")));
        builder.append(formatProperty("ro.build.tags", PropertyUtil.getprop("ro.build.tags", "no value")));
        builder.append(formatProperty("ro.board.platform", PropertyUtil.getprop("ro.board.platform", "no value")));
        builder.append(formatProperty("ro.product.cpu.abilist32", PropertyUtil.getprop("ro.product.cpu.abilist32", "no value")));
        String tmp = "";
        String[] abis32 = Build.SUPPORTED_32_BIT_ABIS;
        for (int i = 0; i< abis32.length; i++) {
            tmp += abis32[i];
            tmp += ",";
        }
        builder.append(formatProperty("ro.product.cpu.abilist32", tmp));
        builder.append(formatProperty("ro.product.cpu.abilist", PropertyUtil.getprop("ro.product.cpu.abilist", "no value")));
        tmp = "";
        abis32 = Build.SUPPORTED_ABIS;
        for (int i = 0; i< abis32.length; i++) {
            tmp += abis32[i];
            tmp += ",";
        }
        builder.append(formatProperty("ro.product.cpu.abilist",tmp));

        builder.append("\n");
        builder.append(formatProperty("gsm.version.baseband", PropertyUtil.getprop("gsm.version.baseband", "no value")));
        builder.append(formatProperty("gsm.operator.alpha", PropertyUtil.getprop("gsm.operator.alpha", "no value")));

        builder.append("\n");
        builder.append("screenRotation = " + display.getRotation());
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
        builder.append(formatProperty("cpuabi2", Build.CPU_ABI2));
        builder.append(formatProperty("ro.bootloader", Build.BOOTLOADER));

        builder.append(formatProperty("ro.build.host", Build.HOST));
        builder.append(formatProperty("ro.build.user", Build.USER));
        builder.append(formatProperty("ro.build.tags", Build.TAGS));

        String androidid = Settings.System.getString(context.getContentResolver(), "android_id");
        builder.append(formatProperty("android_id", androidid));

        builder.append(formatProperty("Serial", Build.SERIAL));
        builder.append(formatProperty("isDebuggable", isDebuggerAttached() + ""));

        String netInterface = "";

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en != null && en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                netInterface += intf.getName();
                netInterface += ",";
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        builder.append(formatProperty("network", netInterface));

        PackageUtils.getInstallPackages(context);

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

    private static boolean isDebuggerAttached() {
        boolean result = Debug.isDebuggerConnected();
        if (!result) {
            boolean result2 = Debug.waitingForDebugger();
            return result2;
        }
        return result;
    }

    private static boolean checkDeviceDebuggable() {
        String buildTags = Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }

        return false;
    }


}
