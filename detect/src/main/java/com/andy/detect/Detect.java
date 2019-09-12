package com.andy.detect;

public class Detect {


    private volatile static Detect instance;

    public static Detect getInstance() {
        if (instance == null) {
            synchronized (Detect.class) {
                if (instance == null) {
                    instance = new Detect();
                }
            }
        }

        return instance;
    }








}
