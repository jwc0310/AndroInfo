package com.andy.androinfo.uis;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.androinfo.R;

/**
 * Created by Administrator on 2018/5/14.
 */

public class SensorFrament extends AndyBaseFragment implements SensorEventListener {

    private TextView context_tv, mTxtValue1, mTxtValue2, mTxtValue3, mTxtValue4, mTxtValue5
            , mTxtValue6, mTxtValue7, mTxtValue8, mTxtValue9;
    private SensorManager sm;

    public static SensorFrament instance(String content) {
        SensorFrament fragment = new SensorFrament();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sm != null) {
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_PRESSURE), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_GRAVITY), SensorManager.SENSOR_DELAY_GAME);
            sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        if (sm != null)
            sm.unregisterListener(this);
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
        sm = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_sensor, null);
        mTxtValue1 = (TextView) view.findViewById(R.id.andy_tv_sensor1);
        mTxtValue2 = (TextView) view.findViewById(R.id.andy_tv_sensor2);
        mTxtValue3 = (TextView) view.findViewById(R.id.andy_tv_sensor3);
        mTxtValue4 = (TextView) view.findViewById(R.id.andy_tv_sensor4);
        mTxtValue5 = (TextView) view.findViewById(R.id.andy_tv_sensor5);
        mTxtValue6 = (TextView) view.findViewById(R.id.andy_tv_sensor6);
        mTxtValue7 = (TextView) view.findViewById(R.id.andy_tv_sensor7);
        mTxtValue8 = (TextView) view.findViewById(R.id.andy_tv_sensor8);
        mTxtValue9 = (TextView) view.findViewById(R.id.andy_tv_sensor9);

        return view;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;
        // 获取传感器类型
        int type = sensorEvent.sensor.getType();
        StringBuilder sb;
        switch (type){
            case Sensor.TYPE_ACCELEROMETER:
                sb = new StringBuilder();
                sb.append("加速度传感器返回数据：");
                sb.append("\nX方向的加速度：");
                sb.append(values[0]);
                sb.append("\nY方向的加速度：");
                sb.append(values[1]);
                sb.append("\nZ方向的加速度：");
                sb.append(values[2]);
                mTxtValue1.setText(sb.toString());
                break;
            case Sensor.TYPE_ORIENTATION:
                sb = new StringBuilder();
                sb.append("\n方向传感器返回数据：");
                sb.append("\n绕Z轴转过的角度：");
                sb.append(values[0]);
                sb.append("\n绕X轴转过的角度：");
                sb.append(values[1]);
                sb.append("\n绕Y轴转过的角度：");
                sb.append(values[2]);
                mTxtValue2.setText(sb.toString());
                break;
            case Sensor.TYPE_GYROSCOPE:
                sb = new StringBuilder();
                sb.append("\n陀螺仪传感器返回数据：");
                sb.append("\n绕X轴旋转的角速度：");
                sb.append(values[0]);
                sb.append("\n绕Y轴旋转的角速度：");
                sb.append(values[1]);
                sb.append("\n绕Z轴旋转的角速度：");
                sb.append(values[2]);
                mTxtValue3.setText(sb.toString());
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                sb = new StringBuilder();
                sb.append("\n磁场传感器返回数据：");
                sb.append("\nX轴方向上的磁场强度：");
                sb.append(values[0]);
                sb.append("\nY轴方向上的磁场强度：");
                sb.append(values[1]);
                sb.append("\nZ轴方向上的磁场强度：");
                sb.append(values[2]);
                mTxtValue4.setText(sb.toString());
                break;
            case Sensor.TYPE_GRAVITY:
                sb = new StringBuilder();
                sb.append("\n重力传感器返回数据：");
                sb.append("\nX轴方向上的重力：");
                sb.append(values[0]);
                sb.append("\nY轴方向上的重力：");
                sb.append(values[1]);
                sb.append("\nZ轴方向上的重力：");
                sb.append(values[2]);
                mTxtValue5.setText(sb.toString());
                break;
            case Sensor.TYPE_LINEAR_ACCELERATION:
                sb = new StringBuilder();
                sb.append("\n线性加速度传感器返回数据：");
                sb.append("\nX轴方向上的线性加速度：");
                sb.append(values[0]);
                sb.append("\nY轴方向上的线性加速度：");
                sb.append(values[1]);
                sb.append("\nZ轴方向上的线性加速度：");
                sb.append(values[2]);
                mTxtValue6.setText(sb.toString());
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sb = new StringBuilder();
                sb.append("\n温度传感器返回数据：");
                sb.append("\n当前温度为：");
                sb.append(values[0]);
                mTxtValue7.setText(sb.toString());
                break;
            case Sensor.TYPE_LIGHT:
                sb = new StringBuilder();
                sb.append("\n光传感器返回数据：");
                sb.append("\n当前光的强度为：");
                sb.append(values[0]);
                mTxtValue8.setText(sb.toString());
                break;
            case Sensor.TYPE_PRESSURE:
                sb = new StringBuilder();
                sb.append("\n压力传感器返回数据：");
                sb.append("\n当前压力为：");
                sb.append(values[0]);
                mTxtValue9.setText(sb.toString());
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
