package com.andy.androinfo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Administrator on 2018/5/3.
 */

public class FileUtil {

    public static void sdPath() {
        String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e("Andy-File",  sdpath);
    }

    public static boolean checkFileExist(String fileOrDir) {
        File tmpFile = new File(fileOrDir);
        return tmpFile.exists();
    }

    public static String checkExistAndRead(String file) {
        File tmpFile = new File(file);
        if (!tmpFile.exists())
            return null;

        if (tmpFile.isDirectory())
            return null;

        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String content = bufferedReader.readLine();
            reader.close();
            bufferedReader.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
