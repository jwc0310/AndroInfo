package com.andy.androinfo.utils;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Test{

    public static void main(String[] args) {
        String dd =  "userdebug -adbc =---- dddd userdebug dccc";
        String newDD = dd.replace("userdebug", "user");

        System.out.println(dd);
        System.out.println(newDD);

        long time = System.currentTimeMillis();
        System.out.println(time);

        try {
            Thread.sleep(1000);
            long time1 = System.currentTimeMillis();
            System.out.println(time1);
            if (time1 > time) {
                System.out.println("normal");
            } else {
                System.out.println("abnormal");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
