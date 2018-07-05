package com.andy.androinfo.utils;

import android.os.FileObserver;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/6/27.
 */

public class AndroFileObserver extends FileObserver {

    private static final String TAG = AndroFileObserver.class.getSimpleName();

    public AndroFileObserver(String path) {
        super(path);
    }

    @Override
    public void onEvent(int event, @Nullable String path) {

        if (event == FileObserver.CREATE) {
            Log.e(TAG, path +" is created");
        }
    }
}
