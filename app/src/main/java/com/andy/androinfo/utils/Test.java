package com.andy.androinfo.utils;

import android.text.TextUtils;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Test{

    public static void main(String[] args) {
        String dd =  "userdebug -adbc =---- dddd userdebug dccc";
        String newDD = dd.replace("userdebug", "user");

        System.out.println(dd);
        System.out.println(newDD);
    }

}
