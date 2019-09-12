package com.andy.androinfo.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.RemoteViews;

import com.andy.androinfo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class clean extends AppWidgetProvider {


    public static boolean isRunning = false;
    public static final String ACTION = "com.xxxx.xxxx";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.clean);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        Intent clickIntent = new Intent();
        clickIntent.setAction(ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_ll, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);


        Log.e("clean", "updateAppWidget");
    }

    @Override
    public void onReceive(Context context , Intent intent) {

        Log.e("clean", intent.getAction());

        super.onReceive(context, intent);
    }

        @Override
        public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Log.e("clean", "update");
        }
        if (!isRunning)
            isRunning = true;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.e("clean", "onEnable");
        isRunning = true;
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.e("clean", "onDisable");
        isRunning = false;
    }

}

