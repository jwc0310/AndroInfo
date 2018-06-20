package com.andy.androinfo.utils;

import android.util.Log;

/**
 * Created by Administrator on 2018/6/20.
 */

public class LogUtil {

    private static final boolean debug = true;

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
