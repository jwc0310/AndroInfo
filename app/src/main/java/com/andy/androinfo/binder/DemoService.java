package com.andy.androinfo.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.andy.androinfo.IDemo;

/**
 * Created by Administrator on 2018/6/20.
 */

public class DemoService extends Service {

    private static final String TAG = "DemoService";

    private IBinder iBinder = new IDemo.Stub() {
        @Override
        public void sendData(String data) throws RemoteException {
            Log.e(TAG, "send data: " + data);
        }

        @Override
        public String getData() throws RemoteException {
            Log.e(TAG, "get data: " + " amazing");
            return "";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }
}
