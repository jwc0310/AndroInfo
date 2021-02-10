package com.andy.androinfo.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

import java.util.List;

public class ActivityUtils {
    private static String TAG = "ActivityUtils";
    public static String gles(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        int version  = configurationInfo.reqGlEsVersion;
        int version2  = configurationInfo.reqGlEsVersion;
        String strVersion = ((version & 0xffff0000) >> 16) +"." +(version2 & 0xffff);
        LogUtil.e(LogUtil.ActivityUtils_debug, TAG, "version = " +strVersion);
        return strVersion;
    }


    public static String showRunningAppProcess(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = activityManager.getRunningAppProcesses();
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("Running App Process:");
        builder.append("\n");
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            builder.append(info.processName);
            builder.append("\n");
        }

        return builder.toString();

    }

    public static void printAllServices(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceInfos = activityManager.getRunningServices(0x7fffffff);
        for (ActivityManager.RunningServiceInfo serviceInfo : serviceInfos) {
            LogUtil.e(LogUtil.ActivityUtils_debug, TAG, serviceInfo.service.getClassName());
            LogUtil.e(LogUtil.ActivityUtils_debug, TAG, serviceInfo.service.getPackageName());
        }

        LogUtil.e(LogUtil.ActivityUtils_debug, TAG, "-----------------------------------");

        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            LogUtil.e(LogUtil.ActivityUtils_debug, TAG, appProcessInfo.processName);
        }

        LogUtil.e(LogUtil.ActivityUtils_debug, TAG, "------------------------------------");
        List<ActivityManager.RunningTaskInfo> taskInfos = activityManager.getRunningTasks(0x7fffffff);
        for (ActivityManager.RunningTaskInfo runningTaskInfo : taskInfos) {
            LogUtil.e(LogUtil.ActivityUtils_debug, TAG, runningTaskInfo.baseActivity.getPackageName());
        }
    }

}
