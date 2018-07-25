package com.andy.androinfo.utils;

import android.os.Build;

import com.andy.androinfo.beans.Properys;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertyUtil {

    public static List<Properys> getPropertyList() {
        List<Properys> list = new ArrayList<>();
        list.add(new Properys("ro.product.name", Build.PRODUCT));
        list.add(new Properys("ro.product.device", Build.DEVICE));
        list.add(new Properys("ro.product.board", Build.BOARD));
        list.add(new Properys("ro.product.manufacturer", Build.MANUFACTURER));
        return list;
    }

    public static String getprop(String key, String defaultValue) {
        String value = defaultValue;
        String TAG = "property";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }


    public static boolean setprop(String key, String value) {
        boolean result = true;
        try {
            Class<?> clazz = Class.forName("android.os.SystemProperties");
            Method set = clazz.getMethod("set", String.class, String.class);
            set.invoke(clazz, key, value);
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return result;
    }


}
