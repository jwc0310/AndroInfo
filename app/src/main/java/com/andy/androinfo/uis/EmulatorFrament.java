package com.andy.androinfo.uis;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.XmlResourceParser;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
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
import com.andy.androinfo.detect.MlbCheck;
import com.andy.androinfo.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

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


    private String checkFileAndCat() {
        StringBuilder builder = new StringBuilder();
        String path = "/sys/block/mmcblk0/device/";
        File file = new File(path+"type");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"type"));
            builder.append("\n");
        } else {
            builder.append("mmcblk0 type not exists\n");
        }

        file = new File(path+"name");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"name"));
            builder.append("\n");
        } else {
            builder.append("mmcblk0 name not exists\n");
        }

        file = new File(path+"cid");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"cid"));
            builder.append("\n");
        } else {
            builder.append("mmcblk0 cid not exists\n");
        }

        path = "/sys/class/power_supply/";
        file = new File(path+"ac/online");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"ac/online"));
            builder.append("\n");
        } else {
            builder.append("power_supply ac/online not exists\n");
        }

        file = new File(path+"usb/online");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"usb/online"));
            builder.append("\n");
        } else {
            builder.append("power_supply usb/online not exists\n");
        }

        file = new File(path+"battery/capacity");
        if (file.exists()) {
            builder.append(ShellUtils.do_exec("cat " + path +"battery/capacity"));
            builder.append("\n");
        } else {
            builder.append("power_supply battery/capacity not exists\n");
        }
        builder.append("\n");

        return builder.toString();
    }

    /**
     * 传入某个SSID，判断该网络的配置是不是在配置列表中
     * @param ssid
     * @return
     */
    public WifiConfiguration isSSIDExistInConfiguration(String ssid){
        WifiManager manager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configureList = manager.getConfiguredNetworks();

        if (configureList != null){
            for (WifiConfiguration configuration : configureList){
                Log.e("xyandy emulator", configuration.toString());
                if(configuration.SSID.equals("\""+ssid+"\"")){ //直接判断equals(ssid)肯定是不存在的，因为“XXX_WIFI”才是配置中的SSID，它外面包了双引号
                    return configuration;
                }
            }
        }
        Log.e("xyandy emulator", "null");
        return null;
    }

    private void getShowMsg() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                FileUtil.readFile("/data/.conf/app.conf");
            }
        }).start();

        isSSIDExistInConfiguration("ssid");
        WifiUtils.showWifi(getContext());


        StringBuilder proContent = new StringBuilder("");
        proContent.append("timezone: " + java.util.TimeZone.getDefault().getDisplayName());
        proContent.append("Launcher Home: " + PackageUtils.getLauncherPackageName(getContext()) +" \n");
        proContent.append("Install Channel: " + PackageUtils.getInstallChannel(getContext()) +" \n");
        proContent.append(Detecter.getDetecterInfo(getContext()));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append(PackageUtils.getInstallPackages(getContext()));
        proContent.append("\n");
        proContent.append(ActivityUtils.showRunningAppProcess(getContext()));
        proContent.append("\n");
        proContent.append(ShellUtils.do_exec("id"));
        proContent.append("\n");
        proContent.append(ShellUtils.do_su_exec("id"));
        proContent.append("\n");
        proContent.append(ShellUtils.do_exec("netcfg"));
        int pid = android.os.Process.myPid();
        proContent.append(ShellUtils.do_exec("ps | grep " + pid));
        proContent.append("\n");
        proContent.append("network type:");
        proContent.append("\n");
        proContent.append(NetworkUtils.GetNetworkType(getContext()));
        proContent.append("\n");
        proContent.append(ShellUtils.do_exec("ps"));
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
                proContent.append("\n");
                proContent.append(inputDevice.getName());
                proContent.append("\n");
                proContent.append(inputDevice.toString());
            }
        }
        proContent.append("\n");
        proContent.append(checkFileAndCat());
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

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            proContent.append("\n blue adapter is null");
        } else {
            proContent.append("\n adapter is enable ? = " + adapter.isEnabled());
        }
        if (BluetoothAdapter.getDefaultAdapter().getAddress() == null) {
            proContent.append("\n");
            proContent.append("bluetooth addr is null");
        } else {
            proContent.append("\n");
            proContent.append("bluetooth addr is not null = " +BluetoothAdapter.getDefaultAdapter().getAddress());
        }
        proContent.append("\n");proContent.append("\n");

        proContent.append("\n");
        proContent.append("\n");
        proContent.append("os version = " + System.getProperty("os.version"));
        proContent.append("\n");
        proContent.append("\n");
        proContent.append("os arch = " + System.getProperty("os.arch"));
        if (Build.VERSION.SDK_INT >= 21) {
            File[] files = getContext().getExternalMediaDirs();
            for (File file : files) {
                if (file == null)
                    continue;
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
            proContent.append("\n");
            proContent.append("     " +entry.getKey() +"     " + entry.getValue());
        }

        proContent.append("\n");
        proContent.append("\n");
        //proContent.append(TestJni.checkQemuBreakpoint() ? "I am emulator" : "I am not emulator");
        proContent.append("\n");

        proContent.append("\n");
        //proContent.append(TestJni.checkQemuFingerPrint() +"");
        proContent.append("\n");
        //proContent.append(TestJni.checkDetect() +"");
        proContent.append("\n");
        proContent.append("opengl = " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER));

        proContent.append("\n");
        proContent.append("GL_RENDERER: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_RENDERER));
        proContent.append("\n");
        proContent.append("GL_VENDOR: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VENDOR));
        proContent.append("\n");
        proContent.append("GL_VERSION: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_VERSION));
        proContent.append("\n");
        proContent.append("20， GL_EXTENSIONS: " + android.opengl.GLES20.glGetString(android.opengl.GLES20.GL_EXTENSIONS));
        proContent.append("\n");
        proContent.append("30， GL_EXTENSIONS: " + android.opengl.GLES30.glGetString(android.opengl.GLES20.GL_EXTENSIONS));
        proContent.append("\n");
        proContent.append("31， GL_EXTENSIONS: " + android.opengl.GLES31.glGetString(android.opengl.GLES20.GL_EXTENSIONS));
        proContent.append("\n");
        proContent.append("\n");
        ActivityUtils.printAllServices(getContext());
//        ShellUtils.do_exec("toolbox mount");
//        ShellUtils.do_exec("cat /proc/mounts");
//        ShellUtils.do_exec("mount");
        listFile();

        content_tv.setText(proContent.toString());
        Log.e("Package", "getThirdInstalledApps11111");
        PackageUtils.getThirdInstalledApps();
    }

    private void listFile() {
        File file = new File("/sdcard/Android/data/");
        File files[] = file.listFiles();
        for (File tmp : files) {
            Log.e("xxx", tmp.getAbsolutePath());
        }

        if (new File("/system/lib/libhoudini.so").exists()) {
            Log.e("xxx", "houdini is exist");
        } else {
            Log.e("xxx", "houdini is not exist");
        }

        file = new File("/system/lib/arm");
        File file1[] = file.listFiles();
        for (File tmp : file1) {
            Log.e("xxx", tmp.getAbsolutePath());
        }
    }

    @Override
    protected void initData() {
        XmlResourceParser parser = null;
        try {
            parser = getContext().getAssets().openXmlResourceParser("abc.xml");
            String abc = parser.getAttributeValue("resources", "abc1111");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getShowMsg();

        MlbCheck.show(getContext());

        property_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  batteryStatus =  AndroInfoApplication.getGlobal().registerReceiver(null,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int batteryPct = level*100 / scale;
                awhileBattery = batteryPct;

                float s = BatteryUtils.caculatorBattery(initBattery,awhileBattery);
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
