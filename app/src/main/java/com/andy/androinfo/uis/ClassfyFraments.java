package com.andy.androinfo.uis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.androinfo.R;

/**
 * Created by Administrator on 2018/5/14.
 */

public class ClassfyFraments extends AndyBaseFragment {

    private TextView context_tv;

    public static ClassfyFraments instance(String content) {
        ClassfyFraments fragment = new ClassfyFraments();
        Bundle args = new Bundle();
        args.putString(TYPE, content);
        fragment.setArguments(args);
        return fragment;
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
        context_tv.setText(content);
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
        View view = inflater.inflate(R.layout.fragment_content, null);
        context_tv = (TextView) view.findViewById(R.id.andy_tv_content);
        return view;
    }
}
