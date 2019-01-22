package com.andy.androinfo.utils;

import android.content.Context;
import android.os.BatteryManager;
import android.util.Log;

public class BatteryUtils {

    private static final String TAG = BatteryUtils.class.getSimpleName();

    public static void dump(Context context) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        for (int i = 0; i<5; i++) {
            LogUtil.e(LogUtil.BatteryUtils_debug, TAG, "battery = " + batteryManager.getIntProperty(i+1));
        }
    }

    public static float caculatorBattery(float initPercent,float awhilePercent) {
        // 先计算平均数
        float average = (initPercent + awhilePercent) / 2;
        LogUtil.e(LogUtil.BatteryUtils_debug, TAG, "average:"+average);
        // 计算方差
        float s = (float) ((Math.pow((initPercent - average),2) +  Math.pow((awhilePercent - average),2)) * 0.5);
        LogUtil.e(LogUtil.BatteryUtils_debug, TAG,   "CaculatorEmulatorBattery s:"+s);
        // 如果方差大于0，说明是真机返回s
        if (s > 0) {
            return s;
        }
        // 返回0表示模拟器
        return 0;

    }

}
