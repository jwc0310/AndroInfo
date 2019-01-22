package com.andy.androinfo.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Administrator on 2018/5/3.
 */

public class FileUtil {

    private static final String TAG = FileUtil.class.getSimpleName();
    private static void log(String content) {
        LogUtil.e(LogUtil.FileUtil_debug, TAG, content);
    }


    public static boolean modifyXml(String path, String tag, String value) {
        File file = new File(path);
        if (!file.exists())
            return false;

        try {
            File tmp = new File("/sdcard/Download/tmp.xml");
            if (!file.exists())
                tmp.createNewFile();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(tag)) {
                    int modifyStart = line.indexOf(">");
                    if (modifyStart == -1)
                        continue;
                    int modifyEnd = line.indexOf("<", modifyStart);
                    if (modifyEnd == -1 || modifyEnd < modifyStart)
                        continue;

                    String newline = line.substring(0, modifyStart+1) +
                            value + line.substring(modifyEnd);
                    writer.write(newline);
                } else {
                    writer.write(line);
                }
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static void sdPath() {
        String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
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
