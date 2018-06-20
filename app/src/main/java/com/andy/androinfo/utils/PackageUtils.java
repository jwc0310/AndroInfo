package com.andy.androinfo.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class PackageUtils {

    private static final String TAG = PackageUtils.class.getSimpleName();

    public static String getInstalledPackages(Context context) {
        JSONArray jas = new JSONArray();
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> pinfo = packageManager.getInstalledApplications(0x2000);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String packageName = pinfo.get(i).packageName;
                jas.put(packageName);
            }
        }

        return jas.toString();
    }
}
