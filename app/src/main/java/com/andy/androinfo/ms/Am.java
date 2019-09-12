package com.andy.androinfo.ms;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.andy.androinfo.AndroInfoApplication;

import java.util.List;

public class Am {

    private static final String TAG = "Tag_am";
    private static Am instance = null;
    private Context context;

    public Am(Context context) {
        this.context = context;
    }

    public static Am getInstance() {
        if (instance == null) {
            synchronized (Am.class) {
                if (instance == null)
                    instance = new Am(AndroInfoApplication.getGlobal());
            }
        }

        return instance;
    }


    public void getRunningAppProcesses() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        if (processInfos == null)
            return;
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
            Log.e(TAG, "info { " + processInfo +" }");
            Log.e(TAG, "info { " + processInfo.processName +" }");
        }
    }


    public void getRunningServices() {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(20);
        if (runningServiceInfos == null)
            return;
        for (ActivityManager.RunningServiceInfo serviceInfo : runningServiceInfos) {
            Log.e(TAG, "info { " + serviceInfo +" }");
            Log.e(TAG, "info { " + serviceInfo.process +" }");
            Log.e(TAG, "info { " + serviceInfo.service.flattenToString() +" }");
        }
    }






}
