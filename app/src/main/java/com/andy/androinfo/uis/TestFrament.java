package com.andy.androinfo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.androinfo.R;
import com.andy.androinfo.features.Gapps;
import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.preference.PreferenceActivityWithPreferenceFragment;
import com.andy.androinfo.utils.NotificationUtils;
import com.andy.androinfo.utils.SocketUtils;

/**
 * Created by Administrator on 2018/5/14.
 */

public class TestFrament extends AndyBaseFragment {

    private static final String TAG = TestFrament.class.getSimpleName();

    private Button hook_onClick, hook_notify, go_prefer, go_prefer2, install_xapk;
    private int testi = 0;
    private Context context;

    public static TestFrament instance(String content) {
        TestFrament fragment = new TestFrament();
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

    @Override
    protected void initData() {
        Gapps.hookGaid();
//        XmlUtils.parseXml("/sdcard/Download/adid_settings.xml");
//        XmlUtils.parseXml("/sdcard/Download/person.xml");
//        FileOps.modifyXml("/sdcard/Download/adid_settings.xml", "\"adid_key\"", "addffdfdcdifsjdfkji");
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_test, null);
        hook_onClick = (Button) view.findViewById(R.id.andy_hook_onclick);
        hook_onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "click");
            }
        });
        new HookUtil(MainActivity.class, getContext()).hookOnClickListener(hook_onClick);
        hook_notify = (Button) view.findViewById(R.id.andy_hook_notification);
        hook_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils.sendNotification(testi++);
            }
        });

        go_prefer = (Button) view.findViewById(R.id.andy_prefer_setting);
        go_prefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PreferenceActivityWithPreferenceFragment.class));
            }
        });

        go_prefer2 = (Button) view.findViewById(R.id.andy_prefer_setting2);
        go_prefer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ActivityWithPreferenceFragment.class));
                SocketUtils.startInstallXapkSocket();
            }
        });

        install_xapk = (Button) view.findViewById(R.id.andy_install_xapk);
//        install_xapk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                startActivity(new Intent(getContext(), ActivityWithPreferenceFragment.class));
//            SocketUtils.startInstallXapkClient("/sdcard/Download/war.xapk");
//            }
//        });

        install_xapk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("Andy453", motionEvent.getAction() +"");
                return false;
            }
        });

        return view;
    }
}
