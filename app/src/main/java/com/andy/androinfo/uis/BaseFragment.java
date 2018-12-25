package com.andy.androinfo.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.androinfo.R;

import java.util.zip.CheckedOutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2018/5/14.
 */

public class BaseFragment extends Fragment {

    private Context mContext;
    private View mRootView;
    private TextView textView;
    private String string;
    private void initView(View view) {
        textView = (TextView) view.findViewById(R.id.andy_tv_content);
    }

    private void initData() {
        if (null != string)
            textView.setText(string);
    }

    @Override
    public void onAttach(Context context) {
//        Log.i(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.i(TAG, "onCreateView");
        if (null == mRootView) {
            mRootView = inflater.inflate(R.layout.fragment_content, null);
            initView(mRootView);
            initData();
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();

        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
//        Log.i(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
//        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
//        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
//        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
//        Log.i(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
//        Log.i(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean getUserVisibleHint() {
//        Log.i(TAG, "getUserVisibleHint");
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        Log.i(TAG, "setUserVisibleHint:" + isVisibleToUser);
    }
}
