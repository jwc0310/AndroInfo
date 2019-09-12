package com.andy.detect.log;

import android.util.Log;

public class Logger {

    private static final String TAG = "detect";

    private volatile static Logger instance = null;

    public static Logger getInstance() {

        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance  = new Logger();
                }
            }
        }

        return instance;
    }

    public void e(String content) {
        Log.e(TAG, content);
    }

    public void i(String content) {
        Log.i(TAG, content);
    }
}
