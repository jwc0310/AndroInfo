package com.andy.androinfo.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by Administrator on 2018/6/20.
 */

public class LogUtil {

    private static boolean debug = true;
    public static boolean MemUtils_debug = false;
    public static boolean Emulator_debug = false;
    public static boolean ActivityUtils_debug = false;
    public static boolean BatteryUtils_debug = false;
    public static boolean DirUtils_debug = false;
    public static boolean Test_debug = false;
    public static boolean PackageUtils_debug = true;
    public static boolean LocationUtils_debug = false;
    public static boolean StorageUtil_debug = true;
    public static boolean WifiUtils_debug = false;
    public static boolean XmlUtils_debug = false;
    public static boolean FileUtil_debug = true;
    public static boolean WifUtils_debug = false;
    public static boolean ZipUtils_debug = false;
    public static boolean HookUtil_debug = false;
    public static boolean NetworkUtil_debug = false;

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }

    public static void e(boolean module_debug, String tag, String log) {
        if (!debug)
            return;

        if (!module_debug)
            return;

        if (TextUtils.isEmpty(log)) {
            return;
        }

        Log.e(tag, log);
    }

    public static void e(String tag, String string) {
        if (!debug)
            return;
        Log.e(tag, string);
    }

    public static void i(String tag, String string) {
        if (!debug)
            return;
        Log.i(tag, string);
    }



    public static boolean Gapps_debug = false;
}
