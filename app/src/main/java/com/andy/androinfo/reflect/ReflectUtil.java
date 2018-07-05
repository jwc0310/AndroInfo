package com.andy.androinfo.reflect;

import android.util.Log;

/**
 * Created by Administrator on 2018/6/29.
 */

public class ReflectUtil {

    public static void doReflect(ClassLoader loader) {
        try {
            ((ReflectObj)loader.loadClass("com.andy.androinfo.reflect.ReflectObj").newInstance()).print("classloader");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void doReflect2() {
        try {
            Class clzz = Class.forName("com.andy.androinfo.reflect.ReflectObj");
            try {
                ((ReflectObj)clzz.newInstance()).print("reflect");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
