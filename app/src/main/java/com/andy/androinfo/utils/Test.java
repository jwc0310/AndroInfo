package com.andy.androinfo.utils;

import android.database.sqlite.SQLiteStatement;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Test{

        static String[] a = { "droid", "VM-", "micro", "stat", "shutdown", "r", "nemu", "devices", "input", "bluetooth", "tem", "excluded", "debug", "jni", "qemu", "etc", "bst", "virt", "trace", "noxd", "vm", "lib", "4x", "so", "tt", "bin", "malloc", "xml", "prop", "sys", "geny", "vbox", "sf", "motion" };
        static char[] b = { 46, 95, 47, 45, 115, 99 };

        protected static String[] a()
        {
            String str = b[2] + a[29] + a[10] + b[2];
            String[] arrayOfString = new String[7];
            arrayOfString[0] = (str + a[25] + b[2] + a[0] + a[22] + b[3] + a[28]);
            arrayOfString[1] = (str + a[21] + b[2] + a[21] + a[19] + b[0] + a[23]);
            arrayOfString[2] = (str + a[25] + b[2] + a[24] + a[1] + a[28]);
            arrayOfString[3] = (str + a[25] + b[2] + a[16] + a[4]);
            arrayOfString[4] = (str + a[25] + b[2] + a[2] + a[17] + b[3] + a[28]);
            arrayOfString[5] = (str + a[25] + b[2] + a[6] + a[1] + a[28]);
            arrayOfString[6] = (str + a[25] + b[2] + a[30] + a[33] + b[3] + a[31] + b[3] + a[32]);
            for (String string : arrayOfString)
                System.out.println("======simulatorArray simulatorArray:" + string);
            return arrayOfString;
        }

        protected static String[] b()
        {
            String str = b[2] + a[29] + a[10] + b[2] + a[25] + b[2];
            String[] arrayOfString = new String[2];
            arrayOfString[0] = (str + a[5]);
            arrayOfString[1] = (str + a[20] + a[3]);
            for (String string : arrayOfString)
                System.out.println("======simulatorArray simulatorArray:" + string);
            return arrayOfString;
        }

        protected static String[] c()
        {
            String[] arrayOfString = new String[3];
            arrayOfString[0] = (b[2] + a[29] + a[10] + b[2] + a[25] + b[2] + a[14] + b[3] + a[28] + b[4]);
            arrayOfString[1] = (b[2] + a[29] + a[10] + b[2] + a[21] + b[2] + a[21] + b[5] + b[1] + a[26] + b[1] + a[12] + b[1] + a[14] + b[0] + a[23]);
            arrayOfString[2] = (b[2] + a[29] + b[2] + a[14] + b[1] + a[18]);
            for (String string : arrayOfString)
                System.out.println("======qemuFile qemuArray:" + string);
            return arrayOfString;
        }

        protected static String d()
        {
            String str = b[2] + a[29] + a[10] + b[2] + a[21] + b[2] + a[21] + a[9] + b[1] + a[13] + b[0] + a[23];
            System.out.println("======bluetooth bluetooth:" + str);
            return str;
        }

    public static void main(String[] args) {
            a();
            b();
            c();
            d();
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
