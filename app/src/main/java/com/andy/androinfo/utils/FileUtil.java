package com.andy.androinfo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2018/5/3.
 */

public class FileUtil {

    public static void sdPath() {
        String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e("Andy-File",  sdpath);
    }

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
