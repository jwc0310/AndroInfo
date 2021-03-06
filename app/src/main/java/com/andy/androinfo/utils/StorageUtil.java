package com.andy.androinfo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by Administrator on 2018/5/9.
 */

public class StorageUtil {

    private static final String TAG = StorageUtil.class.getSimpleName();

    private static void log(String str) {
        LogUtil.e(LogUtil.StorageUtil_debug, TAG, str);
    }

    public static void test(Context context) {
        log("isExternalStorageEmulated = " + Environment.isExternalStorageEmulated());
        try {
            File file = context.getFilesDir();
            File file1 = file.getCanonicalFile();
            log("file path = " + file1.getAbsolutePath());
            File[] files = android.support.v4.content.ContextCompat.getObbDirs(context);
            log("obb dirs size = " + files.length);
//            for (File tmp : files) {
//                log(tmp == null ? "is null" :tmp.getAbsolutePath());
//            }

            File external = context.getExternalFilesDir(null);
            if (external == null) {
                log("external is null");
            } else {
                log("external is " + external.getAbsolutePath());
            }

            File[] externals = context.getExternalFilesDirs(null);
            if (externals == null) {
                log("externals is null");
            } else {
                log("externals size is " + externals.length);
                for (File tmp : externals) {
                    log(tmp == null ? "is null" : tmp.getAbsolutePath());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static StringBuilder printStorageDir(Context context) {
        String internalPath = Environment.getRootDirectory().getPath();
        String externalPath = Environment.getExternalStorageDirectory().getPath();
        String downloadCache = Environment.getDownloadCacheDirectory().getAbsolutePath();

        StringBuilder builder = new StringBuilder("");
        builder.append("rootdir: " + internalPath);
        builder.append("\n");
        builder.append("externalPath: " + externalPath);
        builder.append("\n");
        builder.append("downloadCache: " + downloadCache);
        builder.append("\n");

        builder.append("mount stat : " + Environment.getExternalStorageState());
        builder.append("\n");

        return builder;
    }

    public static void getStorageCapacity(Context context) {
        //internal
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        //文件系统的块的大小（byte）
        long blockSize1 = stat.getBlockSize();
        //文件系统的总的块数
        long totalBlocks1 = stat.getBlockCount();
        //文件系统上空闲的可用于程序的存储块数
        long availableBlocks1 = stat.getAvailableBlocks();

        //总的容量
        long totalSize1 = blockSize1*totalBlocks1;
        long availableSize1 = blockSize1*availableBlocks1;

        String totalStr1 = Formatter.formatFileSize(context, totalSize1);
        String availableStr1 = Formatter.formatFileSize(context, availableSize1);

        log(availableStr1 +"/" +totalStr1);
    }


    public static void readSDCard(Context context) {
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long blockCount = sf.getBlockCount();
            long availCount = sf.getAvailableBlocks();
            log("block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/(1024 * 1024)+"MB");
            log("可用的block数目：:"+ availCount+",剩余空间:"+ availCount*blockSize/(1024 * 1024)+"MB");


            String totalStr1 = Formatter.formatFileSize(context, blockSize * blockCount);
            String availableStr1 = Formatter.formatFileSize(context, availCount * blockSize);

            log(availableStr1 +"/" +totalStr1);
        }
    }

    public static void readSystem(Context context) {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long blockCount = sf.getBlockCount();
        long availCount = sf.getAvailableBlocks();
        log("block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");
        log("可用的block数目：:"+ availCount+",可用大小:"+ availCount*blockSize/1024+"KB");

        String totalStr1 = Formatter.formatFileSize(context, blockSize * blockCount);
        String availableStr1 = Formatter.formatFileSize(context, availCount * blockSize);

        log(availableStr1 +"/" +totalStr1);
    }

    public static void sdcardInfo() {
        Object localOb;
        String str1 = null;

        try {
            localOb = new BufferedReader(new FileReader("/sys/block/mmcblk0/device/type"))
                    .readLine().toLowerCase().contentEquals("sd");

            if (localOb != null) {
                str1 = "/sys/block/mmcblk0/device/";
                log("0");
                log(new BufferedReader(new FileReader("/sys/block/mmcblk0/device/type"))
                        .readLine().toLowerCase().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            localOb = new BufferedReader(new FileReader("/sys/block/mmcblk1/device/type"))
                    .readLine().toLowerCase().contentEquals("sd");

            if (localOb != null) {
                str1 = "/sys/block/mmcblk1/device/";
                log("1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            localOb = new BufferedReader(new FileReader("/sys/block/mmcblk2/device/type"))
                    .readLine().toLowerCase().contentEquals("sd");

            if (localOb != null) {
                str1 = "/sys/block/mmcblk2/device/";
                log("2");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (str1 == null) {
            log("has no mmcblk");
            return;
        } else {
            log(str1);
        }

        localOb = "";

        try {
            localOb = new FileReader(str1 + "name"); // 厂商
            String sd_name = new BufferedReader((Reader) localOb).readLine();
            log("name: " + sd_name);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "cid"); // SD Card ID
            String sd_cid = new BufferedReader((Reader) localOb).readLine();
            log("cid: " + sd_cid);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "csd");
            String sd_csd = new BufferedReader((Reader) localOb).readLine();
            log("csd: " + sd_csd);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "fwrev"); // 固件编号
            String sd_fwrev = new BufferedReader((Reader) localOb).readLine();
            log("fwrev: " + sd_fwrev);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "hwrev"); // 硬件版本
            String sd_hwrev = new BufferedReader((Reader) localOb).readLine();
            log("hwrev: " + sd_hwrev);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "manfid"); // manufacture 制造
            String sd_manfid = new BufferedReader((Reader) localOb).readLine();
            log("manfid: " + sd_manfid);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "oemid"); // 原始设备制造商
            String sd_oemid = new BufferedReader((Reader) localOb).readLine();
            log("oemid: " + sd_oemid);
        } catch (Exception e1) {
            log(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "scr");
            String sd_scr = new BufferedReader((Reader) localOb).readLine();
            log("scr: " + sd_scr);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "serial"); // 串号/序列号
            String sd_serial = new BufferedReader((Reader) localOb).readLine();
            log("serial: " + sd_serial);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        try {
            localOb = new FileReader(str1 + "date"); // 生产日期
            String sd_date = new BufferedReader((Reader) localOb).readLine();
            log("date: " + sd_date);
        } catch (Exception e1) {
            System.out.println(e1.getMessage());
        }

        if (FileUtil.checkFileExist("/sys/block/mmcblk0/device/type")) {
            log("type exists");
        } else {
            log( "type not exists");
        }

        if (FileUtil.checkFileExist("/sys/block/mmcblk0/device/name")) {
            log( "name exists");
        } else {
            log( "name not exists");
        }

        if (FileUtil.checkFileExist("/sys/block/mmcblk0/device/cid")) {
            log( "cid exists");
        } else {
            log( "cid not exists");
        }

        String ac = FileUtil.checkExistAndRead("/sys/class/power_supply/ac/online");
        if (ac != null) {
            log( "ac/online = " + ac);
        } else {
            log( "ac/online not exists");
        }

        String usb = FileUtil.checkExistAndRead("/sys/class/power_supply/usb/online");

        if (usb != null) {
            log( "usb/online = " + usb);
        } else {
            log( "usb/online not exists");
        }

        String battery_cap = FileUtil.checkExistAndRead("/sys/class/power_supply/battery/capacity");
        if (battery_cap != null) {
            log( "battery/capacity = " + battery_cap);
        } else {
            log( "battery/capacity not exists");
        }
    }

}
