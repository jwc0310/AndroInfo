package com.andy.androinfo.features;

import android.util.Log;

import com.andy.androinfo.utils.FileOps;
import com.andy.androinfo.utils.ShellUtil;

import java.io.File;

public class Gapps {

    public static void hookGaid() {

        final String filePath = "/sdcard/Download/adid_settings.xml";
        final String tmpPath = "/sdcard/Download/tmp.xml";
        final String modifyTAG = "\"adid_key\"";

        if (!ShellUtil.exec("cp /data/data/com.google.android.gms/shared_prefs/adid_settings.xml " + filePath)) {
            Log.e("Andy hook", "copy failed");
            return;
        }

        Log.e("Andy hook", "copy success");

        if (!FileOps.modifyXml(filePath, modifyTAG, "zhangyibo")) {
            deleteFile(filePath);
            deleteFile(tmpPath);
            Log.e("Andy hook", "modify failed");
            return;
        }
        Log.e("Andy hook", "modify success");

        if (!ShellUtil.exec("cp /sdcard/Download/tmp.xml /data/data/com.google.android.gms/shared_prefs/adid_settings.xml")) {
            Log.e("Andy hook", "copy back failed");
        }
        deleteFile(filePath);
        deleteFile(tmpPath);

    }

    private static void deleteFile(String path) {
        File tmpFile = new File(path);
        if (tmpFile.exists())
            tmpFile.delete();
    }

}
