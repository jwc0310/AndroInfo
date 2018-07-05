package com.andy.androinfo.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.andy.androinfo.IDemo;

/**
 * Created by Administrator on 2018/6/20.
 */

public class DemoService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IDemo.Stub() {
            @Override
            public void sendData(String data) throws RemoteException {
                return;
            }

            @Override
            public String getData() throws RemoteException {
                return "";
            }
        };
    }
}
