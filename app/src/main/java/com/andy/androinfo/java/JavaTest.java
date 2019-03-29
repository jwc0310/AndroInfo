package com.andy.androinfo.java;

import android.content.Intent;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/6/2.
 */

public class JavaTest {
    public static void main(String[] args) {
        Student.print();
        //Student.print2();

        try {
            Class<?>  stringType = Class.forName(String.class.getName());
            Array.set(Student.names, 0, "aaaaa");
            Student.print();

        } catch (Exception e) {
            e.printStackTrace();
        }


        int aa = 1;
        int bj = aa /2;
        System.out.println("result +" + bj);

        int cj = aa / 3;
        System.out.println("result +" + cj);

        android.net.Uri uri;

        String tmp = "dfjkdjfkd";
        String tmp2 = "dfjkdjfkd";
        String tmp3 = "d";
        if (tmp.compareTo(tmp2) == 0) {
            System.out.println("====");
        } else {
            System.out.println("!!!!");
        }

    }

    public static void my(int a, int b) {
        if (a == 32) {
            a = 0;
        }

        b = a+ 1;

    }
}
