package com.andy.androinfo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.androinfo.R;
import com.andy.androinfo.binder.DemoService;
import com.andy.androinfo.binder.IDemoConnection;
import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.utils.NotificationUtils;

/**
 * Created by Administrator on 2018/5/14.
 */

public class BinderFrament extends AndyBaseFragment {

    private static final String TAG = BinderFrament.class.getSimpleName();

    private Context context;

    public static BinderFrament instance(String content) {
        BinderFrament fragment = new BinderFrament();
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
        Log.e("Andy", "add binder");
        Intent intent = new Intent(getContext(), DemoService.class);
        context.bindService(intent, new IDemoConnection(), Context.BIND_AUTO_CREATE);
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
        view = inflater.inflate(R.layout.fragment_binder, null);
        return view;
    }
}
