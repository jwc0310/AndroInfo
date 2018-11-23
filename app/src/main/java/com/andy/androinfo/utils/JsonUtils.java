package com.andy.androinfo.utils;


import com.google.gson.JsonIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JsonUtils {




    public static void main(String[] args) {
        String file = "D:\\test\\aa\\manifest.json";
        File jsonFile = new File(file);
        Long filelength = jsonFile.length();
        byte[] bytes = new byte[filelength.intValue()];

        try {

            FileInputStream fileInputStream = new FileInputStream(jsonFile);
            fileInputStream.read(bytes);
            fileInputStream.close();

            String context = new String(bytes, "UTF8");

            System.out.println(context);

        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
