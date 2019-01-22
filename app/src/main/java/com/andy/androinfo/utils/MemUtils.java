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
        Log.e(TAG, "total = " + memoryInfo.totalMem);
        Log.e(TAG, "avail = " + memoryInfo.availMem);
        Log.e(TAG, "threshold = " + memoryInfo.threshold);
        Log.e(TAG, "lowMemory = " + memoryInfo.lowMemory);
        return memoryInfo;
    }



}
