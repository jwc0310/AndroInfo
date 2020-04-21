package com.andy.detect.log

import android.util.Log

object Logger {
    private const val TAG = "detect"

    @JvmStatic
    fun e(content: String?) {
        Log.e(TAG, content)
    }

    fun i(content: String?) {
        Log.i(TAG, content)
    }
}