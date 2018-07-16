package com.andy.androinfo.utils;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/24.
 */

public class HasFeature {

    public static void showHasFeature(Context context) {
        PackageManager pm = context.getPackageManager();
        showFeature(pm, PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS);
        showFeature(pm, PackageManager.FEATURE_APP_WIDGETS);
        showFeature(pm, PackageManager.FEATURE_AUDIO_LOW_LATENCY);
        showFeature(pm, PackageManager.FEATURE_AUDIO_OUTPUT);
        showFeature(pm, PackageManager.FEATURE_AUDIO_PRO);
        showFeature(pm, PackageManager.FEATURE_AUTOFILL);
        showFeature(pm, PackageManager.FEATURE_AUTOMOTIVE);
        showFeature(pm, PackageManager.FEATURE_BACKUP);
        showFeature(pm, PackageManager.FEATURE_BLUETOOTH);
        showFeature(pm, PackageManager.FEATURE_BLUETOOTH_LE);
        showFeature(pm, PackageManager.FEATURE_CAMERA);
        showFeature(pm, PackageManager.FEATURE_CAMERA_ANY);
        showFeature(pm, PackageManager.FEATURE_CAMERA_AUTOFOCUS);
        showFeature(pm, PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_POST_PROCESSING);
        showFeature(pm, PackageManager.FEATURE_CAMERA_CAPABILITY_RAW);
        showFeature(pm, PackageManager.FEATURE_CAMERA_CAPABILITY_MANUAL_SENSOR);
        showFeature(pm, PackageManager.FEATURE_CAMERA_EXTERNAL);
        showFeature(pm, PackageManager.FEATURE_CAMERA_FLASH);
        showFeature(pm, PackageManager.FEATURE_CAMERA_FRONT);
        showFeature(pm, PackageManager.FEATURE_CAMERA_LEVEL_FULL);
        showFeature(pm, PackageManager.FEATURE_COMPANION_DEVICE_SETUP);
        showFeature(pm, PackageManager.FEATURE_CONNECTION_SERVICE);
        showFeature(pm, PackageManager.FEATURE_CONSUMER_IR);
        showFeature(pm, PackageManager.FEATURE_DEVICE_ADMIN);
        showFeature(pm, PackageManager.FEATURE_EMBEDDED);
        showFeature(pm, PackageManager.FEATURE_ETHERNET);
        showFeature(pm, PackageManager.FEATURE_FAKETOUCH);
        showFeature(pm, PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_DISTINCT);
        showFeature(pm, PackageManager.FEATURE_FAKETOUCH_MULTITOUCH_JAZZHAND);
        showFeature(pm, PackageManager.FEATURE_FINGERPRINT);
        showFeature(pm, PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT);
        showFeature(pm, PackageManager.FEATURE_GAMEPAD);
        showFeature(pm, PackageManager.FEATURE_HIFI_SENSORS);
        showFeature(pm, PackageManager.FEATURE_HOME_SCREEN);
        showFeature(pm, PackageManager.FEATURE_INPUT_METHODS);
        showFeature(pm, PackageManager.FEATURE_LEANBACK);
        showFeature(pm, PackageManager.FEATURE_LEANBACK_ONLY);
        showFeature(pm, PackageManager.FEATURE_LIVE_TV);
        showFeature(pm, PackageManager.FEATURE_LIVE_WALLPAPER);
        showFeature(pm, PackageManager.FEATURE_LOCATION);
        showFeature(pm, PackageManager.FEATURE_LOCATION_GPS);
        showFeature(pm, PackageManager.FEATURE_LOCATION_NETWORK);
        showFeature(pm, PackageManager.FEATURE_MANAGED_USERS);
        showFeature(pm, PackageManager.FEATURE_MICROPHONE);
        showFeature(pm, PackageManager.FEATURE_MIDI);
        showFeature(pm, PackageManager.FEATURE_NFC);
        showFeature(pm, PackageManager.FEATURE_NFC_HOST_CARD_EMULATION);
        showFeature(pm, PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF);
        showFeature(pm, PackageManager.FEATURE_OPENGLES_EXTENSION_PACK);
        showFeature(pm, PackageManager.FEATURE_PICTURE_IN_PICTURE);
        showFeature(pm, PackageManager.FEATURE_PRINTING);
        showFeature(pm, PackageManager.FEATURE_SCREEN_LANDSCAPE);
        showFeature(pm, PackageManager.FEATURE_SCREEN_PORTRAIT);
        showFeature(pm, PackageManager.FEATURE_SECURELY_REMOVES_USERS);
        showFeature(pm, PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        showFeature(pm, PackageManager.FEATURE_SENSOR_AMBIENT_TEMPERATURE);
        showFeature(pm, PackageManager.FEATURE_SENSOR_BAROMETER);
        showFeature(pm, PackageManager.FEATURE_SENSOR_COMPASS);
        showFeature(pm, PackageManager.FEATURE_SENSOR_GYROSCOPE);
        showFeature(pm, PackageManager.FEATURE_SENSOR_HEART_RATE);
        showFeature(pm, PackageManager.FEATURE_SENSOR_HEART_RATE_ECG);
        showFeature(pm, PackageManager.FEATURE_SENSOR_LIGHT);
        showFeature(pm, PackageManager.FEATURE_SENSOR_PROXIMITY);
        showFeature(pm, PackageManager.FEATURE_SENSOR_RELATIVE_HUMIDITY);
        showFeature(pm, PackageManager.FEATURE_SENSOR_STEP_COUNTER);
        showFeature(pm, PackageManager.FEATURE_SENSOR_STEP_DETECTOR);
        showFeature(pm, PackageManager.FEATURE_SIP);
        showFeature(pm, PackageManager.FEATURE_SIP_VOIP);
        showFeature(pm, PackageManager.FEATURE_TELEPHONY);
        showFeature(pm, PackageManager.FEATURE_TELEPHONY_CDMA);
        showFeature(pm, PackageManager.FEATURE_TELEPHONY_GSM);
        showFeature(pm, PackageManager.FEATURE_TOUCHSCREEN);
        showFeature(pm, PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        showFeature(pm, PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_DISTINCT);
        showFeature(pm, PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH_JAZZHAND);
        showFeature(pm, PackageManager.FEATURE_USB_ACCESSORY);
        showFeature(pm, PackageManager.FEATURE_USB_HOST);
        showFeature(pm, PackageManager.FEATURE_VERIFIED_BOOT);
        showFeature(pm, PackageManager.FEATURE_VR_HEADTRACKING);
        showFeature(pm, PackageManager.FEATURE_VR_MODE);
        showFeature(pm, PackageManager.FEATURE_VR_MODE_HIGH_PERFORMANCE);
        showFeature(pm, PackageManager.FEATURE_VULKAN_HARDWARE_COMPUTE);
        showFeature(pm, PackageManager.FEATURE_VULKAN_HARDWARE_LEVEL);
        showFeature(pm, PackageManager.FEATURE_VULKAN_HARDWARE_VERSION);
        showFeature(pm, PackageManager.FEATURE_WATCH);
        showFeature(pm, PackageManager.FEATURE_WEBVIEW);
        showFeature(pm, PackageManager.FEATURE_WIFI);
        showFeature(pm, PackageManager.FEATURE_WIFI_AWARE);
        showFeature(pm, PackageManager.FEATURE_WIFI_DIRECT);

        FeatureInfo[] featureInfos = pm.getSystemAvailableFeatures();
        for (int i = 0; i < featureInfos.length; i++) {
            String name = featureInfos[i].name;
//            if (null != name)
//                Log.e("Andy_featureList", name);
//            else
//                Log.e("Andy_featureList", "null");
        }
    }

    public static FeatureInfo[] getDeviceAvailableFeatures(Context context) {
        PackageManager pm = (PackageManager) context.getPackageManager();
        FeatureInfo[] featureInfos = pm.getSystemAvailableFeatures();
        return featureInfos;
    }

    private static void showFeature(PackageManager pm, String feature) {
        String tmp = feature;
        if (tmp.startsWith("android.hardware.") || tmp.startsWith("android.software.")) {
            tmp = tmp.substring(17);
        }

        if (pm.hasSystemFeature(feature)) {
            //Log.e("Andy_feature", "Feature: " + tmp +"\ntrue\n");
        } else {
            //Log.e("Andy_feature", "Feature: " + tmp +"\nfalse\n");
        }

    }

}
