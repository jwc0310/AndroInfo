package com.andy.detect.jni;

/**
 * Author: snail
 * Data: 2017/7/20 上午9:11
 * Des:
 * version:
 */

public class Property {

    static{
        System.loadLibrary("property");
    }

    public static String getString(String key){
        return native_get(key);
    }
    public static String getString(String key,String def){
        return native_get(key,def);
    }


    private static native String native_get(String key);
    private static native String native_get(String key,String def);


}
