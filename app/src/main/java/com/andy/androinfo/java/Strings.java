package com.andy.androinfo.java;

public class Strings {

    public static void main(String[] args) {
        String string = "/proc/version";
        String s1 = mlb_string(string, 0x29, 0x5c);
        String s2 = mlb_string(s1, 0x29, 0x5c);
        System.out.println(s1);
        System.out.println(s2);
    }

    public static String mlb_string(String str, int srt1, int srt2) {
        int i = str.length();
        char []arrayOfChar = new char[i];
        i--;
        while (i >= 0) {
            int j = str.charAt(i);
            int k = i -1;
            arrayOfChar[i] = ((char)(j ^ srt1));
            if (k < 0) {
                break;
            }

            i = k - 1;
            arrayOfChar[k] = ((char)(str.charAt(k) ^ srt2));
        }
        return new String(arrayOfChar);
    }

}
