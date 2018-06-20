package com.andy.androinfo.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.androinfo.R;
import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.utils.NotificationUtils;

/**
 * Created by Administrator on 2018/5/14.
 */

public class TestFrament extends AndyBaseFragment {

    private static final String TAG = TestFrament.class.getSimpleName();

    private Button hook_onClick, hook_notify;
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
        Log.e("Andy", "initPrepare");
    }

    @Override
    protected void onInvisible() {
        Log.e("Andy", "onInvisible");
    }

    @Override
    protected void initData() {
        Log.e("Andy", "add content: " + content);
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
        return view;
    }
}
