package com.andy.androinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

public class MemUtils {
    private static final String TAG = "MemUtils";


    public static ActivityManager.MemoryInfo getMemInfo(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        LogUtil.e(LogUtil.MemUtils_debug, TAG, "total = " + memoryInfo.totalMem);
        LogUtil.e(LogUtil.MemUtils_debug, TAG, "avail = " + memoryInfo.availMem);
        LogUtil.e(LogUtil.MemUtils_debug, TAG, "threshold = " + memoryInfo.threshold);
        LogUtil.e(LogUtil.MemUtils_debug, TAG, "lowMemory = " + memoryInfo.lowMemory);
        return memoryInfo;
    }



}
