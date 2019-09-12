package com.andy.detect.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.andy.detect.IEmulatorCheck;
import com.andy.detect.jni.EmulatorDetectUtil;
import com.andy.detect.jni.Property;
import com.andy.detect.log.Logger;

public class EmulatorCheckService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        
        return new IEmulatorCheck.Stub() {
            @Override
            public boolean isEmulator() throws RemoteException {
                int pid = Process.myPid();
                Logger.getInstance().e("response pid = " + pid);
                String name = Property.getString("ro.product.name");
                Logger.getInstance().e("response name = " + name);
                return EmulatorDetectUtil.isEmulator();
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
