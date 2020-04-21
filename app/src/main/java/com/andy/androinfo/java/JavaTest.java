package com.andy.androinfo.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by Administrator on 2018/6/2.
 */

public class JavaTest {

    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

    public static void main(String[] args) {

        File file1 = new File("D:/abc.txt");
        if (!file1.exists())
            System.out.println("abc not exist");
        File file2 = new File("D:/ddd.txt");
        if (file2.exists())
            file2.delete();
        file1.renameTo(file2);

        boolean AE = false;
        boolean AEK = false;
        File conf = new File("D:/app.conf");
        if (!conf.exists()) {
            try {
                conf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File tmp = new File("D:/app.conf_tmp");
        if (tmp.exists())
            tmp.delete();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(conf));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));

            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                if (line.startsWith("<AE>")) {
                    AE = true;
                    StringBuilder builder = new StringBuilder(line);
                    builder.insert(4, "99999999;");
                    writer.write(builder.toString());
                } else {
                    writer.write(line);
                    writer.write("\r\n");
                }

                if (line.startsWith("<AEK>")) {
                    AE = true;
                    StringBuilder builder = new StringBuilder(line);
                    builder.insert(4, "99999999;");
                    writer.write(builder.toString());
                } else {
                    writer.write(line);
                    writer.write("\r\n");
                }
            }

            if (!AE) {
                writer.write("<AE>99999999</AE>");
                writer.write("\r\n");
            }

            if (!AEK) {
                writer.write("<AEK>99999999</AEK>");
                writer.write("\r\n");
            }

            writer.flush();
            reader.close();
            writer.close();

            if (conf.delete()) {
                System.out.println("conf delete success");
                if (tmp.renameTo(conf)) {
                    System.out.println("conf rename success");
                } else {
                    System.out.println("conf rename failed");
                }
            } else {
                System.out.println("conf delete failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


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

        String tmp1 = "dfjkdjfkd";
        String tmp2 = "dfjkdjfkd";
        String tmp3 = "d";
        if (tmp1.compareTo(tmp2) == 0) {
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
