package com.andy.androinfo.utils;

import android.util.Log;

public class CaculatorEmulatorBattery {

    public static float caculatorBattery(float initPercent,float awhilePercent) {
        // 先计算平均数
        float average = (initPercent + awhilePercent) / 2;
        Log.i("xiahuantest",   "average:"+average);
        // 计算方差
        float s = (float) ((Math.pow((initPercent - average),2) +  Math.pow((awhilePercent - average),2)) * 0.5);
        Log.i("xiahuantest",   "CaculatorEmulatorBattery s:"+s);
        // 如果方差大于0，说明是真机返回s
        if (s > 0) {
            return s;
        }
        // 返回0表示模拟器
        return 0;

    }
}
