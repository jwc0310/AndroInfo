package com.andy.androinfo.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;

public class TestHideTools {

    private static final String TAG = TestHideTools.class.getName();

    public static void test(Context context) {
        String packageName = "com.tencent.ssss";
        // /data/data
        String installpath = context.getFilesDir().getPath();
        File file = new File("/data/data/" +packageName);
        if (file.exists()) {
            Log.e(TAG, packageName +" is exist");
        } else {
            Log.e(TAG, packageName +" is not exist");
        }

        Log.e(TAG, "\n");
        String path = Environment.getExternalStorageDirectory().getPath();

        File file1 = new File(path +"/Android/data");
        if (file1.exists()) {
            File[] files = file1.listFiles();
            for (File file2 : files) {
                Log.e(TAG, file2.getAbsolutePath());
            }
        }

        Log.e(TAG, "\n");

        PackageManager pm = context.getPackageManager();
        List<PackageInfo> list = pm.getInstalledPackages(0);
        for (PackageInfo info : list) {
            Log.e(TAG, info.packageName);
//            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
//                Log.e(TAG, info.packageName);
        }
        packageName = "com.android.installer";
        file = new File("/data/data/" +packageName);
        if (file.exists()) {
            Log.e(TAG, packageName +" is exist");
        } else {
            Log.e(TAG, packageName +" is not exist");
        }
    }


}
