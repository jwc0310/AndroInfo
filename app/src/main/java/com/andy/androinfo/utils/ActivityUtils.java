package com.andy.androinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.util.Log;

import java.util.List;

public class ActivityUtils {
    private static String TAG = "ActivityUtils";
    public static String gles(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int version  = configurationInfo.reqGlEsVersion;
        int version2  = configurationInfo.reqGlEsVersion;
        String strVersion = ((version & 0xffff0000) >> 16) +"." +(version2 & 0xffff);
        Log.e(TAG, "version = " +strVersion);
        return strVersion;
    }

    public static void printAllServices(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(30);
        for (ActivityManager.RunningServiceInfo serviceInfo : serviceInfos) {
            Log.e(TAG, serviceInfo.service.getClassName());
            Log.e(TAG, serviceInfo.service.getPackageName());
        }

        Log.e(TAG, "-----------------------------------");

        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            Log.e(TAG, appProcessInfo.processName);
        }

        Log.e(TAG, "------------------------------------");
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(30);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : taskInfos) {
            Log.e(TAG, runningTaskInfo.baseActivity.getPackageName());
        }
    }

}
