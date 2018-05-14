package com.andy.androinfo.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator on 2018/5/9.
 */

public class StorageUtil {

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

        Log.e("Andy", availableStr1 +"/" +totalStr1);
    }

}
