package com.andy.androinfo.jni;

public class TestJni {

    static {
        System.loadLibrary("MyLibrary");
    }

    public static int checkDetect() {
        return detect();
    }

    public static boolean checkQemuBreakpoint() {
        boolean hit_breakpoint = false;
        int result = qemuBkpt();
        if (result > 0)
            hit_breakpoint = true;
        return hit_breakpoint;
    }// Potentially you may want to see if this is a specific value 

    public static  double checkQemuFingerPrint() {
        return qemuFingerPrint();
    }

    public static native String getHello();
    public static native String getHello(String paras);
    public native static int qemuBkpt();

    //以下两者不是很准  平板
    private native static double qemuFingerPrint();
    public static native int detect();
}
