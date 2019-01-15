package com.andy.androinfo.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyUtils {

    private static final String TAG = "TelephonyUtils";

    public static StringBuilder getPhoneInfo(Context context) {
        StringBuilder builder = new StringBuilder();
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return builder;
    }

}
