package com.andy.androinfo.hook;

import android.util.Log;

/**
 * Created by Administrator on 2018/6/28.
 */

public class HookSingleton {

    public static HookSingleton instance = new HookSingleton();

    public static HookSingleton getInstance() {
        return instance;
    }

    public void print() {
        Log.e("HookSingleTon", "origin print");
    }

}
