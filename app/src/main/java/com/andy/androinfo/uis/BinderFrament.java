package com.andy.androinfo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.androinfo.R;
import com.andy.androinfo.binder.DemoService;
import com.andy.androinfo.binder.IDemoConnection;

import java.util.Set;

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

    private Button home;

    private void backHome(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(intent);

        String action = intent.getAction();
        Set<String> categories = intent.getCategories();

        Process.killProcess(Process.myPid());
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_binder, null);
        home = (Button) view.findViewById(R.id.Binder_home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "home click");
                Intent intent = new Intent(getContext(), DemoService.class);
                context.bindService(intent, new IDemoConnection(), Context.BIND_AUTO_CREATE);
            }
        });

        return view;
    }
}
