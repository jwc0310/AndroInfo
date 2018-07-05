package com.andy.androinfo.emulator;

import android.os.Debug;

/**
 * Created by Administrator on 2018/6/28.
 */

public class Detecter {

    public static boolean isDebuggerConnected() {
        return Debug.isDebuggerConnected();
    }

}
