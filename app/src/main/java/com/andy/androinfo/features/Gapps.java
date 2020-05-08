package com.andy.androinfo.features;

import com.andy.androinfo.utils.FileUtil;
import com.andy.androinfo.utils.LogUtil;
import com.andy.androinfo.utils.ShellUtils;

import java.io.File;

/**
 * 获取google play 的gaid
 */
public class Gapps {
    private static final String TAG = Gapps.class.getSimpleName();

    private static void log(String content) {
        LogUtil.e(LogUtil.Gapps_debug, TAG, content);
    }

    public static void hookGaid() {

        final String filePath = "/sdcard/Download/adid_settings.xml";
        final String tmpPath = "/sdcard/Download/tmp.xml";
        final String modifyTAG = "\"adid_key\"";

        if (!ShellUtils.exec("cp /data/data/com.google.android.gms/shared_prefs/adid_settings.xml " + filePath)) {
            log("copy failed");
            return;
        }

        log("copy success");

        if (!FileUtil.modifyXml(filePath, modifyTAG, "zhangyibo")) {
            deleteFile(filePath);
            deleteFile(tmpPath);
            log("modify failed");
            return;
        }
        log("modify success");

        if (!ShellUtils.exec("cp /sdcard/Download/tmp.xml /data/data/com.google.android.gms/shared_prefs/adid_settings.xml")) {
            log("copy back failed");
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
