package com.andy.androinfo.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/6/20.
 */

public class LogUtil {

    private static boolean debug = false;

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
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
}
