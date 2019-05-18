package com.andy.androinfo;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.uis.MainActivity;
import com.andy.androinfo.utils.LogUtil;

/**
 * Created by Administrator on 2018/5/31.
 */

public class AndroInfoApplication extends Application {

    private static Context global;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initial();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        global = getApplicationContext();
    }

    public static Context getGlobal() {
        return global;
    }

    private void initial() {
        //new HookUtil(MainActivity.class, this).handleOpenGLInfo();
        new HookUtil(MainActivity.class, this).hookFuncs();
        new HookUtil(MainActivity.class, this).hookNotificationManager();
        LogUtil.setDebug(true);

        ApplicationInfo applicationInfo = getApplicationInfo();
        Log.e("Application", applicationInfo.packageName+", " + applicationInfo.dataDir+", " + applicationInfo.nativeLibraryDir+", "
        + applicationInfo.sourceDir);

    }

}
