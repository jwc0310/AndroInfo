package com.andy.androinfo.ms;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.andy.androinfo.AndroInfoApplication;

import java.util.List;

public class Pm {

    private static final String TAG = "Tag_pm";
    private static Pm instance = null;
    private Context context;

    public Pm(Context context) {
        this.context = context;
    }

    public static Pm getInstance() {
        if (instance == null) {
            synchronized (Am.class) {
                if (instance == null)
                    instance = new Pm(AndroInfoApplication.getGlobal());
            }
        }

        return instance;
    }

    public void getAppWidgetInfos() {
        List<AppWidgetProviderInfo> appWidgetProviderInfos =  AppWidgetManager.getInstance(context).getInstalledProviders();
        for (AppWidgetProviderInfo info : appWidgetProviderInfos) {
            Log.e(TAG, "info {" + info.toString() + "}");
        }
    }


    public void queryIntentActivities() {
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
        for (ResolveInfo resolveInfo : resolveInfos) {
            Log.e(TAG, "info {" + resolveInfo.toString() + "}");
        }
//        pm.queryContentProviders();
//        pm.queryIntentServices()
    }

    public void queryBroadcastReceivers() {
        Intent intent = new Intent(Intent.ACTION_BOOT_COMPLETED);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfoList = pm.queryBroadcastReceivers(intent, 0);
        for (ResolveInfo info : resolveInfoList) {
            Log.e(TAG, "info {" + info.toString() + "}");
            Log.e(TAG, "info {" + info.activityInfo.packageName + "}");
        }
    }

    public void queryContentProviders() {

    }

    public void queryIntentServices() {
    }

}
