package com.andy.androinfo.utils;

import android.os.Build;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Androinfo {

    public static String deviceInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("设备型号：");
        builder.append(Build.BOARD);
        return builder.toString();
    }

}
