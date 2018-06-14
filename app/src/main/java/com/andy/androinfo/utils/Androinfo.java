package com.andy.androinfo.utils;

import android.os.Build;
import android.util.Log;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2018/5/19.
 */

public class Androinfo {

    public static String deviceInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("设备型号：");
        builder.append(Build.BOARD);

        Logger logger = Logger.getLogger("mytag");
        logger.info("cc");

        return builder.toString();
    }

}
