package com.andy.androinfo.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andy.androinfo.R;
import com.andy.androinfo.adapters.PropertyListAdapter;
import com.andy.androinfo.beans.Properys;
import com.andy.androinfo.binder.DemoService;
import com.andy.androinfo.binder.IDemoConnection;
import com.andy.androinfo.utils.PropertyUtil;
import com.andy.androinfo.utils.StorageUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class DeviceFrament extends AndyBaseFragment {

    private static final String TAG = DeviceFrament.class.getSimpleName();

    private Context context;
    private RecyclerView rv_list;
    private LinearLayoutManager linearLayoutManager;
    private PropertyListAdapter propertyListAdapter;
    private List<Properys> properysList;

    public static DeviceFrament instance(String content) {
        DeviceFrament fragment = new DeviceFrament();
        return fragment;
    }

    private void initList() {
        properysList.clear();
        properysList.addAll(PropertyUtil.getPropertyList());
        propertyListAdapter.notifyDataSetChanged();
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
        initList();
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
        view = inflater.inflate(R.layout.fragment_device, null);
        rv_list = (RecyclerView) view.findViewById(R.id.andy_property_list_rv);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        properysList = new ArrayList<>();
        propertyListAdapter = new PropertyListAdapter(getContext(), properysList);
        rv_list.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setAdapter(propertyListAdapter);
        return view;
    }
}
