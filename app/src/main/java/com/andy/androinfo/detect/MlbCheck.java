package com.andy.androinfo.detect;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MlbCheck {

    private static final String TAG = "MLBCheck";

    private static void log(String str) {
        Log.e(TAG, str);
    }

    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    public static void show(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            log("wifi enable");
            WifiInfo info = wifiManager.getConnectionInfo();
            if (info != null) {
                log("wifi mac: " + info.getMacAddress());
                log("wifi ip: " + formatIpAddress(info.getIpAddress()));
                log("wifi info: " + info.toString());
            }
        } else {
            log("wifi not enable");
        }

        log("Build.CPU_ABI: " + Build.CPU_ABI);
        log("Build.BRAND: " + Build.BRAND);
        log("Build.MANUFACTURER: " + Build.MANUFACTURER);
        log("Build.MODEL: " + Build.MODEL);

        log("Serial Number: " + telephonyManager.getSimSerialNumber());
        log("Operator: " + telephonyManager.getNetworkOperator());
        log("Operator: " + telephonyManager.getNetworkOperator());


        log("RELEASE: " + Build.VERSION.RELEASE);

        Account[] accounts = accountManager.getAccounts();
        for (int i = 0; i < accounts.length; i++) {
            log("account " + i +": " + accounts[i].name);
        }

        StatFs statFs = new StatFs(Environment.getDataDirectory().getPath());
        long l;
        if (Build.VERSION.SDK_INT > 18) {
            l = statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong();
        } else {
            l = statFs.getAvailableBlocks() * statFs.getBlockSize();
        }

        if (l > 2097152L) {
            log("1 data size = " + l / 1024 / 1024 +" MB");
        } else {
            log("0 data size = " + l / 1024 / 1024 +" MB");
        }

        if (Build.VERSION.SDK_INT > 18) {
            log("locale: " + context.getResources().getConfiguration().locale.getCountry());
        } else {
            log("locale: " + context.getResources().getConfiguration().getLocales().get(0).getCountry());
        }

    }


}
