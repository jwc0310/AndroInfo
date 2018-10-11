package com.andy.androinfo.utils;

import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryUtils {

    public static void dump(Context context) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService("batterymanager");
        for (int i = 0; i<5; i++) {
            Log.e("Andy", "battery = " + batteryManager.getIntProperty(i+1));
        }
    }

}
