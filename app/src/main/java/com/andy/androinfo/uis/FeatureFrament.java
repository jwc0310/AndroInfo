package com.andy.androinfo.uis;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.utils.HasFeature;
import com.andy.androinfo.utils.NotificationUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class FeatureFrament extends AndyBaseFragment {

    private static final String TAG = FeatureFrament.class.getSimpleName();

    private TextView featureList;
    private int testi = 0;
    private Context context;

    public static FeatureFrament instance(String content) {
        FeatureFrament fragment = new FeatureFrament();
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
        Log.e("Andy", "initPrepare");
    }

    @Override
    protected void onInvisible() {
        Log.e("Andy", "onInvisible");
    }

    @Override
    protected void initData() {
        Log.e("Andy", "add content: " + content);

        FeatureInfo[] featureInfos = HasFeature.getDeviceAvailableFeatures(getContext());
        StringBuffer sb = new StringBuffer("");
        for (FeatureInfo featureInfo : featureInfos) {
            sb.append(featureInfo.name);
            sb.append("\n");
        }
        featureList.setText(sb.toString());

        WifiManager wifiManager = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configurations =  wifiManager.getConfiguredNetworks();
        Log.e("Andy7777", "wifi config size = " + configurations.size());

        for (WifiConfiguration configuration : configurations) {
            Log.e("Andy7777", configuration.toString());
        }

        List<ScanResult> scanResults = wifiManager.getScanResults();
        Log.e("Andy7777", "wifi scan result size = " + scanResults.size());

        for (ScanResult scanResult : scanResults)
            Log.e("Andy7777", scanResult.toString());


        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo info = pm.getApplicationInfo("com.microvirt.market", 0);
            Log.e("Andy777", info.loadLabel(pm).toString());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {

            TextUtils.isEmpty("ccc");
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_feature, null);
        featureList = (TextView) view.findViewById(R.id.feature_list);
        return view;
    }
}
