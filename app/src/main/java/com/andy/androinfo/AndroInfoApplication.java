package com.andy.androinfo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.andy.androinfo.uis.MainActivity;
import com.andy.androinfo.hook.HookUtil;

/**
 * Created by Administrator on 2018/5/31.
 */

public class AndroInfoApplication extends Application {

    private static Context global;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //new HookUtil(MainActivity.class, this).handleOpenGLInfo();
        new HookUtil(MainActivity.class, this).hookFuncs();
        new HookUtil(MainActivity.class, this).hookNotificationManager();
        Log.e("Andy777", getPackageName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        global = this;
    }

    public static Context getGlobal() {
        return global;
    }

}
