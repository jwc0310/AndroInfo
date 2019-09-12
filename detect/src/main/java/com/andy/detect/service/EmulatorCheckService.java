package com.andy.detect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.andy.detect.IEmulatorCheck;

public class EmulatorCheckService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        
        return new IEmulatorCheck.Stub() {
            @Override
            public boolean isEmulator() throws RemoteException {
                return false;
            }

            @Override
            public void kill() throws RemoteException {

            }
        };
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
