package com.andy.androinfo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.andy.androinfo.AndroInfoApplication;
import com.andy.androinfo.R;

/**
 * Created by Administrator on 2018/6/20.
 */

public class NotificationUtils {

    public static void sendNotification(int testi) {
        Context context = AndroInfoApplication.getGlobal();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify1");
        builder.setContentTitle("test title")
                .setContentText("test text " + testi)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_foreground))
                .setSmallIcon(context.getApplicationInfo().icon)  //if set resource id  directly , maybe cause RemoteServiceException
                .setWhen(System.currentTimeMillis())
                .setTicker("I am test notification")
                .setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(10, builder.build());
    }

}
