package com.andy.androinfo.uis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.andy.androinfo.utils.UpdateAppConf;

public class UpdateAppConfReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.e("UpdateAppConf", "package add " + action);

        if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
            String pkgname = intent.getData().getSchemeSpecificPart();
            Log.e("UpdateAppConf", "package add " + pkgname);
            UpdateAppConf updateAppConf = new UpdateAppConf(pkgname);
            run(updateAppConf);

        } else if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.e("UpdateAppConf", "boot completed");
            UpdateAppConf updateAppConf = new UpdateAppConf(1);
            run(updateAppConf);
        }
    }

    private void run(Runnable runnable) {
        new Thread(runnable).start();
    }


}
