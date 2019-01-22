package com.andy.androinfo.utils;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.util.Date;

public class LocationUtils {

    private static String TAG = LocationUtils.class.getSimpleName();

    private static void log(String content) {
        LogUtil.e(LogUtil.LocationUtils_debug, TAG, content);
    }

    public static boolean isMockLocation(Context context, Location location) {
        if (location.isFromMockProvider()) {
            log("[Location] blocked as mock location. REASON : isFromMockProvider.");
            return true;
        }

        try {
            if (Settings.Secure.getInt(context.getContentResolver(), "mock_location") == 1) {
                log("[Location] blocked as mock location. REASON : ALLOW_MOCK_LOCATION.");
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private boolean IsValidLocation(Location paramLocation)
    {
        if ((new Date().getTime() - paramLocation.getTime() > 300000L) || (!paramLocation.hasAccuracy()) || (paramLocation.getAccuracy() > 100.0D))
            return false;
        return true;
    }


    private static boolean isDeviceRoot() {

        String str = Build.TAGS;
        if ((str != null) && (str.contains("test-keys")))
        {
            log("[Location] WARNING : buildTags contains test-keys.");
            return true;
        }
        try
        {
            if (new File("/system/app/Superuser.apk").exists())
            {
                log("[Location] WARNING : Superuser.apk is existed.");
                return true;
            }
        }
        catch (Exception localException)
        {
            if ((canExecuteCommand("/system/xbin/which su")) || (canExecuteCommand("/system/bin/which su")) || (canExecuteCommand("which su")))
            {
                log("[Location] WARNING : super-user command can execute.");
                return true;
            }
        }
        return false;
    }

    private static boolean canExecuteCommand(String paramString)
    {
        try
        {
            Runtime.getRuntime().exec(paramString);
            return true;
        }
        catch (Exception localException)
        {
        }
        return false;
    }

}
