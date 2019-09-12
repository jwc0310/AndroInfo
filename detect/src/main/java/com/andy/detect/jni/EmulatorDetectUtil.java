package com.andy.detect.jni;

/**
 * Author: snail
 * Data: 2017/7/20 下午4:46
 * Des:
 * version:
 */

public class EmulatorDetectUtil {

    static {
        System.loadLibrary("detect");
    }

    public static native boolean detect();

    public static boolean isEmulator() {
        return detect();
    }

    public void throwNativeCrash(){

    }
}
