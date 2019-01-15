package com.andy.androinfo.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.andy.androinfo.R;

/**
 * Created by Administrator on 2018/5/14.
 */

public class SystemFrament extends AndyBaseFragment {

    private static final String TAG = SystemFrament.class.getSimpleName();

    private Context context;
    private TextView textView;

    public static SystemFrament instance(String content) {
        SystemFrament fragment = new SystemFrament();
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
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        StringBuilder builder = new StringBuilder("");
        int rotation = display.getRotation();
        builder.append("Rotation = " + rotation + "\n");
        int height = display.getHeight();
        int width = display.getWidth();
        builder.append("width = " + width + "\n");
        builder.append("height = " + height + "\n");
        int orientation = display.getOrientation();
        builder.append("orientation = " + orientation);

        textView.setText(builder.toString());
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
        view = inflater.inflate(R.layout.fragment_system, null);
        textView = (TextView) view.findViewById(R.id.andy_content_tv);
        return view;
    }
}
