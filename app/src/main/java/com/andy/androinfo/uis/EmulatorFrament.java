package com.andy.androinfo.uis;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.XmlResourceParser;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.androinfo.AndroInfoApplication;
import com.andy.androinfo.R;
import com.andy.androinfo.emulator.Detecter;
import com.andy.androinfo.jni.TestJni;
import com.andy.androinfo.utils.ActivityUtils;
import com.andy.androinfo.utils.Androinfo;
import com.andy.androinfo.utils.CaculatorEmulatorBattery;
import com.andy.androinfo.utils.PackageUtils;
import com.andy.androinfo.utils.PropertyUtil;
import com.andy.androinfo.utils.ShellUtil;
import com.andy.androinfo.utils.StorageUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Created by Administrator on 2018/5/14.
 */
public class EmulatorFrament extends AndyBaseFragment {

    private TextView content_tv;
    private EditText property_key, property_value;
    private Button property_set, property_get;

    float initBattery, awhileBattery;

    public EmulatorFrament() {
        Intent  batteryStatus =  AndroInfoApplication.getGlobal().registerReceiver(null,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        float level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        float scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level*100 / scale;
        initBattery = batteryPct;
    }

    public static EmulatorFrament instance(String content) {
        EmulatorFrament fragment = new EmulatorFrament();
        Bundle args = new Bundle();
        args.putString(TYPE, content);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void initPrepare() {
    }

    @Override
    protected void onInvisible() {
    }

    private void getShowMsg() {
        StringBuilder proContent = new StringBuilder("");
        proContent.append(Detecter.getDetecterInfo(getContext()));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(Androinfo.deviceInfo(getContext()));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(ShellUtil.do_exec("netcfg"));
        int pid = android.os.Process.myPid();
        proContent.append(ShellUtil.do_exec("ps | grep " + pid));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(ShellUtil.do_exec("ps"));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(StorageUtil.printStorageDir(getContext()).toString());
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(PackageUtils.getInstalledApps2(getContext()));
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int i : deviceIds) {
            InputDevice inputDevice = InputDevice.getDevice(i);
            if (inputDevice != null) {
                Log.e("Andy2", inputDevice.toString());
                proContent.append("\n");
                proContent.append(inputDevice.toString());
            }
        }

        proContent.append("\n");
        proContent.append("\n");
        Properties properties = System.getProperties();
        Iterator<String>it = properties.stringPropertyNames().iterator();
        try {
            while (it.hasNext()) {
                String tmp = it.next();
                proContent.append("\n");
                proContent.append(tmp + "   " + System.getProperty(tmp));
            }
        }catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        proContent.append("\n");
        proContent.append("\n");
        if (BluetoothAdapter.getDefaultAdapter().getAddress() == null) {
            Log.e("Andy2", "bluetooth addr is null");
            proContent.append("\n");
            proContent.append("bluetooth addr is null");
        } else {
            Log.e("Andy2", "bluetooth addr is not null");
            proContent.append("\n");
            proContent.append("bluetooth addr is not null = " +BluetoothAdapter.getDefaultAdapter().getAddress());
        }
        proContent.append("\n");proContent.append("\n");

        Log.e("Andy2", "os version = " + System.getProperty("os.version"));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append("os version = " + System.getProperty("os.version"));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append("os arch = " + System.getProperty("os.arch"));
        if (Build.VERSION.SDK_INT >= 21) {
            File[] files = getContext().getExternalMediaDirs();
            Log.e("Andy3", "mediadirs = " + files.length);
            for (File file : files) {
                if (file == null)
                    continue;
                Log.e("Andy3", file.getAbsolutePath());
            }
        }
        String strVrsion = ActivityUtils.gles(getContext());
        proContent.append("\n");
        proContent.append("\n");
        proContent.append("OpenGl version= " + strVrsion);
        proContent.append("System env:\n");
        Iterator<Map.Entry<String, String>> it2 = System.getenv().entrySet().iterator();
        while (it2.hasNext()) {
            Map.Entry<String, String> entry = it2.next();
            Log.e("Andy5", entry.getKey()+" ," +entry.getValue());
            proContent.append("\n");
            proContent.append("     " +entry.getKey() +"     " + entry.getValue());
        }

        proContent.append("\n");
        proContent.append("\n");
        proContent.append(TestJni.checkQemuBreakpoint() ? "I am emulator" : "I am not emulator");
        proContent.append("\n");

        proContent.append("\n");
        proContent.append(TestJni.checkQemuFingerPrint() +"");
        proContent.append("\n");
        proContent.append(TestJni.checkDetect() +"");
        proContent.append("\n");
        proContent.append("opengl = " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER));

        proContent.append("\n");
        proContent.append("GL_RENDERER: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER));
        proContent.append("\n");
        proContent.append("GL_VENDOR: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VENDOR));
        proContent.append("\n");
        proContent.append("GL_VERSION: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VERSION));
        proContent.append("\n");
        proContent.append("GL_EXTENSIONS: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_EXTENSIONS));
        proContent.append("\n");
        proContent.append("\n");
        ActivityUtils.printAllServices(getContext());

        content_tv.setText(proContent.toString());
    }

    @Override
    protected void initData() {
        XmlResourceParser parser = null;
        try {
            parser = getContext().getAssets().openXmlResourceParser("ab1c.xml");
            String abc = parser.getAttributeValue("resources", "abc1111");
            Log.e("Andy11", "acb = " + abc);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Andy11", "acb = exception");
        }
        getShowMsg();

        property_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  batteryStatus =  AndroInfoApplication.getGlobal().registerReceiver(null,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPct = level*100 / scale;
                awhileBattery = batteryPct;
                Log.i("xiahuantest",    "点击百分比："+batteryPct+"%");
                Log.i("xiahuantest",    "initBattery："+initBattery);
                Log.i("xiahuantest",    "awhileBattery："+awhileBattery);

                float s = CaculatorEmulatorBattery.caculatorBattery(initBattery,awhileBattery);
                Log.i("xiahuantest",    "方差："+s);
                if (s>0) {
                    Toast.makeText(getActivity(), "真机", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "模拟器", Toast.LENGTH_SHORT).show();
                }

                String key = property_key.getText().toString().trim();
                if (TextUtils.isEmpty(key))
                    return;
                property_value.setText(PropertyUtil.getprop(key, "no value"));
            }
        });

        property_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = property_key.getText().toString().trim();
                String value = property_value.getText().toString().trim();
                if (TextUtils.isEmpty(key))
                    return;
                if (TextUtils.isEmpty(value))
                    return;

                if (!PropertyUtil.setprop(key, value)) {
                    Toast.makeText(mContext, "set failed", Toast.LENGTH_SHORT).show();
                } else {
                    getShowMsg();
                    property_key.setText("");
                    property_value.setText("");
                }

            }
        });

    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }

        Bundle bundle = getArguments();
        content = bundle.getString(TYPE);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_emulator, null);
        content_tv = (TextView) view.findViewById(R.id.andy_tv_content_emulator);
        property_key = (EditText) view.findViewById(R.id.andy_property_key);
        property_value = (EditText) view.findViewById(R.id.andy_property_value);
        property_set = (Button) view.findViewById(R.id.andy_property_set);
        property_get = (Button) view.findViewById(R.id.andy_property_get);
        return view;
    }
}
