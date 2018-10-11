package com.andy.androinfo.java;

public class Student {

    public static final String[] names = {"a","b", "c"};
    public static final String TT = "ddddd";

    public  static void print() {
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }
    }

    public static void print2() {
        System.out.println(TT);
    }
}
