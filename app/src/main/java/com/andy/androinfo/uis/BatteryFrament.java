package com.andy.androinfo.uis;

import android.Manifest;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.media.MediaActivity;
import com.andy.androinfo.utils.ShellUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2018/5/14.
 */

public class BatteryFrament extends AndyBaseFragment {

    private static final String TAG = BatteryFrament.class.getSimpleName();
    private Button button, settings, media;
    private TextView battery_info_tv;

    private Context context;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            String text = (String) message.obj;
            battery_info_tv.setText(text);
        }
    };

    private BatteryInfoReceiver receiver = new BatteryInfoReceiver(handler);

    public static BatteryFrament instance(String content) {
        BatteryFrament fragment = new BatteryFrament();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onPause() {
        getContext().unregisterReceiver(receiver);
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

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }
    }

    @Override
    protected View initView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_battery, null);
        button = (Button) view.findViewById(R.id.click_me);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        settings = (Button)view.findViewById(R.id.start_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings"));
                startActivity(intent);
////                ShellUtils.CommandResult result = ShellUtils.execCommand("chmod 777 /data/mycpuinfo", false, false);
////                Log.e("BatteryFragment", result.result + "");
////                Log.e("BatteryFragment", result.successMsg +"");
////                Log.e("BatteryFragment", result.errorMsg +"");
////                result = ShellUtils.execCommand("/data/data/com.tencent.yoozoo.got.wintercoming/files/mycpuinfo" , false, true);
////                Log.e("BatteryFragment", result.result + "");
////                Log.e("BatteryFragment", result.successMsg +"");
////                Log.e("BatteryFragment", result.errorMsg+"");
//
//
//                ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
//                List<ActivityManager.RunningAppProcessInfo> list =  am.getRunningAppProcesses();
//                Log.e("BatteryFragment", "list =  " + (list == null ? "null" : list.size()));



            }
        });

        battery_info_tv = (TextView) view.findViewById(R.id.andy_battery_info);

        media = (Button) view.findViewById(R.id.start_media);
        media.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MediaActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void requestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.setLogging(true);
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.e("Andy1", "aBoolean = " + aBoolean);
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("Andy1", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Andy1", "error");
                    }
                });
    }
}
