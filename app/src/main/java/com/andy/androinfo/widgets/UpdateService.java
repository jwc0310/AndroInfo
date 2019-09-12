package com.andy.androinfo.widgets;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.andy.androinfo.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateService extends Service {

    public static final String UPDATE_ACTION = "com.xxx.xxx.start_widget_animation";

    private int[] bimaps = new int[] {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher2,
            R.mipmap.ic_launcher3,
            R.mipmap.ic_launcher4,
            R.mipmap.ic_launcher5
    };

    private List<Bitmap> bitmapList = new ArrayList<>();


    private void initBitmap(Context context) {
        if (bitmapList.size() > 0)
            return;
        for (int i = 0; i < bimaps.length; i++) {
            bitmapList.add(i, BitmapFactory.decodeResource(context.getResources(), bimaps[i]));
        }
    }

    private int counter;
    public UpdateService() {
        counter = 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        initBitmap(this);

        new Thread(runnable).start();

        return super.onStartCommand(intent, flags, startId);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.e("clean", "running ?  " + clean.isRunning);
            while(true) {
                Log.e("clean", "runnable " + counter);

                counter++;
                AppWidgetManager manager = AppWidgetManager.getInstance(UpdateService.this);
                ComponentName cleanWidget = new ComponentName(UpdateService.this, clean.class);

                RemoteViews views = new RemoteViews(UpdateService.this.getPackageName(), R.layout.clean);
                views.setImageViewBitmap(R.id.appwidget_image, bitmapList.get(counter % bitmapList.size()));
                manager.updateAppWidget(cleanWidget, views);

                if (counter >= 500 * bitmapList.size()) {
                    counter = 0;
                    break;
                }

                try {
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };
}
