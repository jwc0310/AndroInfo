package com.andy.androinfo.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2018/6/1.
 */

public class Test2 {

    public static boolean tt = false;

    public static boolean test() {
        log("channel2", getSystemProperty("microvirt.channel"));
        log("channel3", getChannel());
        return false;
    }

    public static void wendao(Context context) {
        final String wendao = "wendao";
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        StringBuilder builder = new StringBuilder();
        builder.append("TelephonyManager: \n");
        builder.append("电话号码：");
        builder.append(tm.getLine1Number());
        builder.append("\n");

        builder.append("运营商：");
        builder.append(tm.getNetworkOperatorName());
        builder.append("\n");

        builder.append("");
        builder.append(tm.getSimOperator());
        builder.append("\n");

        builder.append("serial number:");
        builder.append(tm.getSimSerialNumber());
        builder.append("\n");

        builder.append("sub id:");
        builder.append(tm.getSubscriberId());
        builder.append("\n");

        /*
        builder.append("SensorManager: \n");

        SensorManager sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor mag = sm.getDefaultSensor(SensorManager.SENSOR_MAGNETIC_FIELD);
        List<Sensor> msgs = sm.getSensorList(SensorManager.SENSOR_MAGNETIC_FIELD);

        SensorManager.SENSOR_ACCELEROMETER;
        SensorManager.SENSOR_ORIENTATION;
*/


        Log.e(wendao, builder.toString());




        Log.e(wendao, tm.getLine1Number());
        Log.e(wendao, tm.getNetworkOperator());
        Log.e(wendao, tm.getNetworkOperatorName());

    }

    private static int deviceType = -1;

    public static String getChannel() {
        if (deviceType == -1) {
            File file = new File("data/.conf/app.conf");
            if (file.exists()) {
                deviceType = 1;
            } else
                deviceType = 0;
        }
        String key = "microvirt.channel";
        String defaultValue = "7c8a454a";
        String channel = getprop(key, defaultValue);
        Log.e("yb", "channel = " + channel);
        Log.e("yb", "deviceType = " + deviceType);
        if (deviceType == 1)
            return channel;
        else
            return "";
    }

    public static String getprop(String key, String defaultValue) {
        String value = defaultValue;
        String TAG = "property";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            value = (String) (get.invoke(c, key, defaultValue));
        } catch (Exception e) {
            Log.d(TAG, "get property error, " + e.getMessage());
        }
        return value;
    }

    public static void main(Context context) {
        log("product", Build.PRODUCT);
        log("manufacturer", Build.MANUFACTURER);
        log("brand", Build.BRAND);
        log("device", Build.DEVICE);
        log("module", Build.MODEL);
        log("hardware", Build.HARDWARE);
        log("fingerprint", Build.FINGERPRINT);

        log("serial", Build.SERIAL);

        String androidid = Settings.System.getString(context.getContentResolver(), "android_id");
        log("android_id", androidid);

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            //String imei = telephonyManager.getImei();
            //log("imei1", imei);

            String imei2 = telephonyManager.getDeviceId();
            log("imei2", imei2);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        try {
            String command = "cat /sys/class/net/wlan0/address";
            Process process = Runtime.getRuntime().exec("sh");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.write(command.getBytes());
            outputStream.writeBytes("\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null)
                content += line;

            log("mac", content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("mac", loadData("cat /sys/class/net/wlan0/address"));
        log("cpuinfo", "cat /proc/cpuinfo");
        log("diskstats", "cat /proc/diskstats");


        log("ro.kernel.qemu", getSystemProperty("ro.kernel.qemu"));

        File qemu_pipe = new File("/dev/qemu_pipe");
        log("qemu_pipe", qemu_pipe.exists()+"");
        log("/sys/module/vboxsf", new File("/sys/module/vboxsf").exists()+"");

    }

    private static String loadData(String command) {
        try {
            //String command = "cat /sys/class/net/wlan0/address";
            Process process = Runtime.getRuntime().exec("sh");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.write(command.getBytes());
            outputStream.writeBytes("\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String content = "";
            String line;
            while ((line = reader.readLine()) != null)
                content += line;

            return content;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getSystemProperty(String key) {
        try {
            Class localClass = Class.forName("android.os.SystemProperties");
            Method getMethod = localClass.getMethod("get", new Class[] {String.class});
            getMethod.setAccessible(true);
            String string = (String) getMethod.invoke(localClass, new Object[] {key});
            return string;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {

        } catch (IllegalAccessException e) {

        } catch (InvocationTargetException e) {

        }
        return "exception no value";
    }


    private static void log(String key, String str) {
        Log.e("666666", key + ": " +str);
    }

}
