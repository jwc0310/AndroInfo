package com.andy.androinfo.uis;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.androinfo.R;

import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class CameraFrament extends AndyBaseFragment {

    private CameraManager cameraManager;

    public static CameraFrament instance(String content) {
        CameraFrament fragment = new CameraFrament();
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

    @Override
    protected void initData() {
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }

        Bundle bundle = getArguments();
        content = bundle.getString(TYPE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
//        } else {
//
//        }
//
//        Camera camera = Camera.open();
//        List<Camera.Size> sizeList = camera.getParameters().getSupportedPreviewSizes();
//        for (Camera.Size size : sizeList) {
//            Log.e("Andycamera", size.width +" x " +size.height);
//        }
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
            view = inflater.inflate(R.layout.fragment_camera, null);
        return view;
    }
}
