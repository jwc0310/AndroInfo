package com.andy.androinfo.binder;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.andy.androinfo.IDemo;
import com.andy.androinfo.utils.LogUtil;

/**
 * Created by Administrator on 2018/6/20.
 */

public class IDemoConnection implements ServiceConnection {
    private static final String TAG = IDemoConnection.class.getSimpleName();

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LogUtil.e(TAG, "IDemo connected");
        IDemo demo = IDemo.Stub.asInterface(service);
        try{
            demo.sendData("demo send data");
            String get = demo.getData();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        LogUtil.e(TAG, "IDemo disconnected");
    }

    @Override
    public void onBindingDied(ComponentName name) {
        LogUtil.e(TAG, "IDemo Died");
    }
}
