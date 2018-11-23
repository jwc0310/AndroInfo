package com.andy.androinfo.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.andy.androinfo.beans.Properys;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PropertyUtil {

    private static final String[] properities =  {
            "ro.build.id",
            "ro.build.display.id",
            "ro.build.version.incremental",
            "ro.build.version.sdk",
            "ro.build.version.preview_sdk",
            "ro.build.version.codename",
            "ro.build.version.all_codenames",
            "ro.build.version.release",
            "ro.build.version.security_patch",
            "ro.build.version.base_os",
            "ro.product.name",
            "ro.product.device",
            "ro.product.board",
            "ro.product.manufacturer",
    };

    public static List<Properys> getPropertyList() {
        String result = ShellUtil.do_exec_getprop();
        List<Properys> list = new ArrayList<>();
        if (!TextUtils.isEmpty(result)) {
            String[] lines = result.split("\n");
            for (int i = 0; i < lines.length; i++) {
                String[] pros = lines[i].split(":");
                list.add(new Properys(pros[0], pros[1]));
            }
        }
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
