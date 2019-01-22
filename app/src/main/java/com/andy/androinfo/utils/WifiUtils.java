package com.andy.androinfo.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class WifiUtils {
    private static final String TAG = "WifUtils";

    private static void log(String content) {
        LogUtil.e(LogUtil.WifUtils_debug, TAG, content);
    }

    public static StringBuilder getWifiInfo(Context context) {
        StringBuilder builder = new StringBuilder();
        WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //获取已经连接wifi 信息
        WifiInfo wifiInfo = manager.getConnectionInfo();
        log(wifiInfo.toString());
        String info = wifiInfo.toString();
        String[] items = info.split(",");
        for (String string : items) {
            builder.append(string);
            builder.append("\n");
        }

        return builder;
    }

    public static String getMacAddress(Context context) {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getMacDefault(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacFromFile();
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * @return
     */
    private static String getMacFromFile() {
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }

    /**
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET" />
     * @return
     */
    private static String getMacFromHardware() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("eth0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    public static String getMacDefault(Context context) {
        String mac = "02:00:00:00:00:00";
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }
}
