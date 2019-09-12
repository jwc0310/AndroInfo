package com.andy.androinfo.ms;

public class Ms {


    public static void test() {
//        Am.getInstance().getRunningAppProcesses();
//        Am.getInstance().getRunningServices();
//        Pm.getInstance().getAppWidgetInfos();
//        Pm.getInstance().queryIntentActivities();
        Pm.getInstance().queryBroadcastReceivers();
    }


}
