package com.andy.androinfo.utils;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

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


    public static void showWifi(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int state = wifiManager.getWifiState();
        String state_str = "unknown";
        switch (state) {
            case WifiManager.WIFI_STATE_DISABLED:
                state_str = "disabled";
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                state_str = "disabling";
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                state_str = "enabled";
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                state_str = "enabling";
                break;
        }

        Log.e("showWifi", "wifi state: " + state_str);

        if (state != 3)
            return;

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.e("showWifi", "wifi ssid: " + wifiInfo.getSSID());
        Log.e("showWifi", "wifi bssid: " + wifiInfo.getBSSID());
        Log.e("showWifi", "wifi hidebssid: " + wifiInfo.getHiddenSSID());
        Log.e("showWifi", "wifi ipv4: " + formatIpAddress(wifiInfo.getIpAddress()));
        Log.e("showWifi", "wifi mac: " + wifiInfo.getMacAddress());
        Log.e("showWifi", "wifi fre: " + wifiInfo.getFrequency());
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        Log.e("showWifi", "wifi dhcp: " + dhcpInfo.toString());
        Log.e("showWifi", "wifi gateway: " + formatIpAddress(dhcpInfo.gateway));
        Log.e("showWifi", "wifi netmask: " + formatIpAddress(dhcpInfo.netmask));

    }


    /**
     * 传入某个SSID，判断该网络的配置是不是在配置列表中
     * @param ssid
     * @return
     */
    public WifiConfiguration isSSIDExistInConfiguration(Context context, String ssid){
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configureList = manager.getConfiguredNetworks();

        if (configureList != null){
            for (WifiConfiguration configuration : configureList){
                if(configuration.SSID.equals("\""+ssid+"\"")){ //直接判断equals(ssid)肯定是不存在的，因为“XXX_WIFI”才是配置中的SSID，它外面包了双引号
                    return configuration;
                }
            }
        }
        return null;
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

    public static String getWifiScanInfo(Context context) {
        StringBuilder builder = new StringBuilder("wifi info\n");
        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> results = wifi.getScanResults();
        for (ScanResult scanResult : results) {
            builder.append(scanResult.toString());
            builder.append("\n");
        }

        return builder.toString();
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


    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }
}
