package com.andy.androinfo.emulator;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.provider.Settings;
import android.util.Log;

import com.andy.androinfo.utils.BatteryUtils;
import com.andy.androinfo.utils.PropertyUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/6/28.
 */

public class Detecter {

    public static String getDetecterInfo(Context context) {
        StringBuilder builder = new StringBuilder("");

        builder.append("usb debug is ");
        builder.append(checkUsbDebugModeIsOn(context) ? "on" : "off");
        builder.append("\n");

        builder.append("developer mode is ");
        builder.append(isDebuggerConnected() ? "on" : "off");
        builder.append("\n");

        builder.append("cgroup file is ");
        builder.append(checkCgroupFileExist() ? "exist" : "not exist");
        builder.append("\n");

        //BatteryUtils.dump(context);

        return builder.toString();
    }

    public static boolean isDebuggerConnected() {
        return Debug.isDebuggerConnected();
    }

    public static void TestInvisibleMode(Context context) {
        byte[] props = {'m', 'i', 'c', 'r', 'o', 'v', 'i', 'r', 't', '.', 's', 's', 'i', 'd'};
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0x0);
        for (PackageInfo info : packages) {
            Log.e("TestInvisibleMode", info.packageName);
        }

        Log.e("TestInvisibleMode", new String(props));
        String value = PropertyUtil.getprop(new String(props), "no value");
        Log.e("TestInvisibleMode", value);

        File intel = new File("/init.intel.rc");
        if (intel.exists()) {
            Log.e("TestInvisibleMode", "intel is exist");
        } else {
            Log.e("TestInvisibleMode", "not is exist");
        }
    }

    private static boolean checkUsbDebugModeIsOn (Context context) {
        boolean enableAdb = (Settings.Secure.getInt(context.getContentResolver(),
                Settings.Secure.ADB_ENABLED, 0) > 0);
        return enableAdb;
    }

    private static boolean checkCgroupFileExist() {
        File cgroupFile = new File("/proc/self/cgroup");
        return cgroupFile.exists();
    }

    private static String getUsbRelatedProperties () {
        StringBuilder builder = new StringBuilder("");
        return builder.toString();

    }

}
