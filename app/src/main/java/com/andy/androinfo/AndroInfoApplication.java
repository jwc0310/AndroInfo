package com.andy.androinfo;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.andy.androinfo.uis.MainActivity;
import com.andy.androinfo.utils.HookUtil;

/**
 * Created by Administrator on 2018/5/31.
 */

public class AndroInfoApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //new HookUtil(MainActivity.class, this).handleOpenGLInfo();
        new HookUtil(MainActivity.class, this).hookFuncs();
        Log.e("Andy777", getPackageName());
    }
}
