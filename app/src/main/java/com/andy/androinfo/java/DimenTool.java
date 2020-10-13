package com.andy.androinfo.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 直接运行main方法将values文件中的dimen缩放到对应文件夹下
 */
public class DimenTool {

    private static final String PROJECT_PATH = "F:\\projects\\launcher\\app\\src\\main\\res\\";

    private static void gen() {

//        File file = new File("./app/src/main/res/values/dimens.xml");
        File file = new File(PROJECT_PATH + "values-sw600dp-land\\dimens.xml");
        BufferedReader reader = null;
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder w820 = new StringBuilder();


        try {
            System.out.println("生成不同分辨率：");
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;

            while ((tempString = reader.readLine()) != null) {

                if (tempString.contains("</dimen>")) {
                    //tempString = tempString.replaceAll(" ", "");
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    float num = Float.valueOf(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));

                    sw480.append(start).append((int) Math.round(num * 0.8)).append(end).append("\n");
//                    sw600.append(start).append((int) Math.round(num * 1.0)).append(end).append("\n");
//                    sw720.append(start).append((int) Math.round(num * 0.9)).append(end).append("\n");
//                    sw720.append(tempString).append("\n");
//                    sw800.append(tempString).append("\n");
//                    w820.append(tempString).append("\n");

                } else {
                    sw480.append(tempString).append("\n");
//                    sw600.append(tempString).append("\n");
//                    sw720.append(tempString).append("\n");
//                    sw800.append(tempString).append("\n");
//                    w820.append(tempString).append("\n");
                }
                line++;
            }
            reader.close();
            System.out.println("<!--  sw480 -->");
            System.out.println(sw480);
            System.out.println("<!--  sw600 -->");
            System.out.println(sw600);

            System.out.println("<!--  sw720 -->");
            System.out.println(sw720);
            System.out.println("<!--  sw800 -->");
            System.out.println(sw800);

            String sw480file = PROJECT_PATH + "values-sw600dp-land\\x_dimens.xml";
//            String sw600file = PROJECT_PATH + "values-sw600dp\\xyf_dimens.xml";
//            String sw720file = PROJECT_PATH + "values-sw720dp\\dimens.xml";
//            String sw800file = PROJECT_PATH + "values-sw800dp\\dimens.xml";
//            String w820file =  PROJECT_PATH + "values-w820dp\\dimens.xml";
            writeFile(sw480file, sw480.toString());
//            writeFile(sw600file, sw600.toString());
//            writeFile(sw720file, sw720.toString());
//            writeFile(sw800file, sw800.toString());
//            writeFile(w820file, w820.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.close();
    }

    public static void main(String[] args) {
        gen();
    }
}