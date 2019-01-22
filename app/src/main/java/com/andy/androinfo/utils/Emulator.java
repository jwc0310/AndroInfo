package com.andy.androinfo.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;
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

    public static void run(Context context) {
        test(context);
        isEmulator(context);
    }

    public static boolean isEmulator(Context context){
        boolean notHasBlueTooth = notHasBlueTooth();
        boolean notHasLightSensorManager = notHasLightSensorManager(context);
        boolean isFeatures = isFeatures();
        LogUtil.e(LogUtil.Emulator_debug, "notHasBlueTooth=====", String.valueOf(notHasBlueTooth));
        LogUtil.e(LogUtil.Emulator_debug, "notHaanager=====", String.valueOf(notHasLightSensorManager));
        LogUtil.e(LogUtil.Emulator_debug, "isFeatures=====", String.valueOf(isFeatures));
        if(notHasBlueTooth || notHasLightSensorManager || isFeatures){
            LogUtil.e(LogUtil.Emulator_debug, "isEmulator=========","true");
            return true;
        }
        LogUtil.e(LogUtil.Emulator_debug, "isEmulator=========","false");
        return false;
    }

    /**
     * 判断是否存在光传感器来判断是否为模拟器
     * 部分真机也不存在温度和压力传感器。其余传感器模拟器也存在。
     * @return true 为模拟器
     */
    public static Boolean notHasLightSensorManager(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor8 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (null == sensor8) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /**
     * 判断蓝牙是否有效来判断是否为模拟器
     *
     * @return true 为模拟器
     */
    public static boolean notHasBlueTooth() {
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if (ba == null) {
            return true;
        } else {
            // 如果有蓝牙不一定是有效的。获取蓝牙名称，若为null 则默认为模拟器
            String name = ba.getName();
            if (TextUtils.isEmpty(name)) {
                return true;
            } else {
                LogUtil.e(LogUtil.Emulator_debug, TAG, "bluetooth name is = " + name);
                return false;
            }
        }
    }

    public static void test(Context context) {
        int[] nums = InputDevice.getDeviceIds();
        LogUtil.e(LogUtil.Emulator_debug, "AndyEmulator", "nums = " + nums.length);
        for (int num : nums) {
            LogUtil.e(LogUtil.Emulator_debug,"AndyEmulator", "num = " + num);
            InputDevice inputDevice = InputDevice.getDevice(num);
            LogUtil.e(LogUtil.Emulator_debug, TAG, inputDevice.getName());
            LogUtil.e(LogUtil.Emulator_debug, TAG, inputDevice.getDescriptor());
            LogUtil.e(LogUtil.Emulator_debug, TAG, inputDevice.getControllerNumber()+"");
        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        LogUtil.e(LogUtil.Emulator_debug, TAG, "hasVibrator = " + vibrator.hasVibrator());

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
        LogUtil.e(LogUtil.Emulator_debug, TAG, file.getAbsolutePath());
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                LogUtil.e(LogUtil.Emulator_debug, TAG, file.getAbsolutePath() +" "+line);
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
            LogUtil.e(LogUtil.Emulator_debug, TAG, "query = null");
            return;
        }

        if (list.size() == 0) {
            LogUtil.e(LogUtil.Emulator_debug, TAG, "query size null");
        }

        for (ResolveInfo resolveInfo : list) {
            LogUtil.e(LogUtil.Emulator_debug, TAG, "query = " + resolveInfo.activityInfo.packageName);
        }

    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
