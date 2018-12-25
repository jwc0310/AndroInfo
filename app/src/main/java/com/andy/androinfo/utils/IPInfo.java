package com.andy.androinfo.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class IPInfo {
    private static String TAG = "IPinfo";
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;

    public IPInfo(Context mContext) {

        //获取wifi服务
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        mWifiInfo = mWifiManager.getConnectionInfo();
    }

    public String getWIFILocalIpAdress() {
        int ipAddress = mWifiInfo.getIpAddress();
        Log.e(TAG, formatIpAddress(ipAddress));
        return formatIpAddress(ipAddress);
    }

    private static String formatIpAddress(int ipAdress) {

        return (ipAdress & 0xFF) + "." +
                ((ipAdress >> 8) & 0xFF) + "." +
                ((ipAdress >> 16) & 0xFF) + "." +
                (ipAdress >> 24 & 0xFF);
    }

    public String getMacAddress() {
        Log.e(TAG, mWifiInfo.getMacAddress());
        Log.e(TAG, mWifiInfo.getBSSID());
        Log.e(TAG, mWifiInfo.getSSID());
        Log.e(TAG, mWifiInfo.getRssi()+"");
        Log.e(TAG, mWifiInfo.getNetworkId()+"");
        Log.e(TAG, mWifiInfo.getIpAddress()+"");

        return mWifiInfo.getMacAddress();
    }

    public void getMask() {
    }
}