package com.andy.androinfo.java;

import android.util.Log;

public class SmaliTest {

    public static void main(String args[]) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("abc");
        stringBuilder.append("efg");
        Log.e("Andy", stringBuilder.toString());
    }

}
