package com.andy.androinfo.utils;

import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2018/5/3.
 */

public class FileUtil {

    public static void checkFileExist(String fileOrDir) {
        try {
            File tmpFile = new File(fileOrDir);
            if (tmpFile.exists())
                Log.e("Andy-File", fileOrDir + " is exist");
            else
                Log.e("Andy-File", fileOrDir + " is not exist");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
