package com.andy.androinfo.uis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.androinfo.R;
import com.andy.androinfo.emulator.Detecter;
import com.andy.androinfo.utils.Androinfo;
import com.andy.androinfo.utils.PropertyUtil;
import com.andy.androinfo.utils.ShellUtil;
import com.andy.androinfo.utils.StorageUtil;

/**
 * Created by Administrator on 2018/5/14.
 */
public class EmulatorFrament extends AndyBaseFragment {

    private TextView content_tv;
    private EditText property_key, property_value;
    private Button property_set, property_get;

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
        proContent.append(Androinfo.deviceInfo(getContext()));
        proContent.append("\n");
        proContent.append(ShellUtil.do_exec("netcfg"));
        int pid = android.os.Process.myPid();
        proContent.append(ShellUtil.do_exec("ps | grep " + pid));
        proContent.append("\n");
        proContent.append(StorageUtil.printStorageDir(getContext()).toString());
        content_tv.setText(proContent.toString());
    }

    @Override
    protected void initData() {
        getShowMsg();

        property_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
