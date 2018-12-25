package com.andy.androinfo.jni;

public class TestJni {

    static {
        System.loadLibrary("MyLibrary");
    }

    public static native String getHello();
    public static native String getHello(String paras);
}
