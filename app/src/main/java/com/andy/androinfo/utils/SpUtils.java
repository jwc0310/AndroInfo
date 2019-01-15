package com.andy.androinfo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
    public static void Sp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("androidInfo", Context.MODE_PRIVATE);
        sharedPreferences.getString("abc", "");
        sharedPreferences.edit().putString("abc", "dec").commit();
    }
}
