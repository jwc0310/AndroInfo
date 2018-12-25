package com.andy.androinfo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Vibrator;
import android.util.Log;
import android.view.InputDevice;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.List;
import java.util.regex.Pattern;

public class Emulator {
    private static final String TAG = "AndyEmulator";

    public static void test(Context context) {
        int[] nums = InputDevice.getDeviceIds();
        Log.e("AndyEmulator", "nums = " + nums.length);
        for (int num : nums) {
            Log.e("AndyEmulator", "num = " + num);
            InputDevice inputDevice = InputDevice.getDevice(num);
            Log.e(TAG, inputDevice.getName());
            Log.e(TAG, inputDevice.getDescriptor());
            Log.e(TAG, inputDevice.getControllerNumber()+"");
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Log.e(TAG, "hasVibrator = " + vibrator.hasVibrator());

//        String source = context.getPackageManager().getInstallerPackageName("com.com2us.ninepb3d.normal.freefull.google.global.android.common");
//        if (source != null) {
//            Log.e(TAG, "com2us.source = " + source);
//        }

        listProc();
        queryActivitys(context);

    }

    public static void listProc() {

        final String dir = "/proc";
        File procFile = new File("/proc");
        if (!procFile.isDirectory())
            return;

        File paths[] = procFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                if (!isNumeric(s))
                    return false;
                return true;
            }
        });

        for (File file : paths) {
            readFile(new File(file, "status"));
            readFile(new File(file, "cmdline"));
            readFile(new File(file, "stat"));
        }
    }

    private static void readFile(File file) {
        Log.e(TAG, file.getAbsolutePath());
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                Log.e(TAG, file.getAbsolutePath() +" "+line);
            }
            reader.close();
            br.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void queryActivitys(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> list =  packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list == null) {
            Log.e(TAG, "query = null");
            return;
        }

        if (list.size() == 0) {
            Log.e(TAG, "query size null");
        }

        for (ResolveInfo resolveInfo : list) {
            Log.e(TAG, "query = " + resolveInfo.activityInfo.packageName);
        }

    }


    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
