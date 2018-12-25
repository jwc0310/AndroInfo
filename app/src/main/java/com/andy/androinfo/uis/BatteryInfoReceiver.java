package com.andy.androinfo.uis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;

public class BatteryInfoReceiver extends BroadcastReceiver {

    private Handler handler;

    private BatteryInfoReceiver() {

    }
    public BatteryInfoReceiver(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        int currLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);  //当前电量
        int total = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 1);      //总电量
        int technology= intent.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, 2);
        //...还可以获得很多信息
        //剩余电量dianchi
        int percent = currLevel * 100 / total;

        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append("总电量: " + total);
        stringBuilder.append("\n");
        stringBuilder.append("电池型号： " + technology);
        stringBuilder.append("\n");
        stringBuilder.append("当前电量： " + currLevel);
        stringBuilder.append("\n");
        stringBuilder.append("health: " + intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0));
        stringBuilder.append("\n");
        stringBuilder.append("plugged: " + intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0));
        stringBuilder.append("\n");
        stringBuilder.append("present: " + intent.getIntExtra(BatteryManager.EXTRA_PRESENT, 0));
        stringBuilder.append("\n");
        stringBuilder.append("status: " + intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0));
        stringBuilder.append("\n");
        stringBuilder.append("temperature: " + intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0));
        stringBuilder.append("\n");
        stringBuilder.append("valtage: " + intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0));
        stringBuilder.append("\n");

        Message msg = new Message();
        msg.what = 0x1;
        msg.obj = stringBuilder.toString();
        handler.sendMessage(msg);

    }
}
