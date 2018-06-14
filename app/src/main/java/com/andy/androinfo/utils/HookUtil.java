package com.andy.androinfo.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/26.
 */

public class HookUtil {


    private Class<?> proxyActivity;
    private Context context;

    public HookUtil(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookAms() {
        //一路反射知道拿到IActivityManager
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultField = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultField.setAccessible(true);
            Object defaultValue = defaultField.get(null);

            //反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            //拿到ActivityManager
            Object iActivityManagerObject = mInstance.get(defaultValue);


            //开始动态代理
            Class<?> IActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            AmsInvocationHandler handler = new AmsInvocationHandler(iActivityManagerObject, context);

            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{IActivityManagerIntercept}, handler);

            mInstance.set(defaultValue, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleOpenGLInfo() {
        try {
            Log.e("Andy666", "handleBuildInfo");
            Class<?> systemPropertiesClass = Class.forName("android.os.Build$VERSION");
            Field board = systemPropertiesClass.getDeclaredField("SDK_INT");
            board.setAccessible(true);
            board.set(systemPropertiesClass, 25);

            Field version = systemPropertiesClass.getDeclaredField("RELEASE");
            version.setAccessible(true);
            version.set(systemPropertiesClass, "7.1.2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleBuildinfo() {
        try {
            Log.e("Andy666", "handleBuildInfo");
            Class<?> systemPropertiesClass = Class.forName("android.os.Build");
            Field board = systemPropertiesClass.getDeclaredField("BRAND");
            board.setAccessible(true);
            board.set(systemPropertiesClass, "Andy's board");
            Log.e("Andy666", Build.BRAND);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hookFuncs() {
        try {
            //com.andy.androinfo.utils.Test2
            //test
            Class<?> apiClass = Class.forName("com.andy.androinfo.utils.Test2");
            Method testMethod = apiClass.getDeclaredMethod("test");
            testMethod.setAccessible(true);
            Object result = testMethod.invoke(apiClass);

            Field tt = apiClass.getDeclaredField("tt");
            tt.set(apiClass, true);

            Field aa = apiClass.getDeclaredField("aa");
            aa.set(apiClass, -1);

            Log.e("Andy", "hookFuncs = " + result);

            //AmsInvocationHandler handler = new AmsInvocationHandler(apiClass, context);
            //Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
            //        new Class<?>[]{apiClass}, handler);

        } catch (Exception e) {
        }
    }

    public void handleSystemHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //获取主线程对象
            Object activityThread = currentActivityThreadMethod.invoke(null);

            //获取mH字段
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);

            //获取handler
            Handler handler = (Handler) mH.get(activityThread);

            //获取原始的mCallback字段
            Field mCallback = Handler.class.getDeclaredField("mCallback");
            mCallback.setAccessible(true);
            //这里设置我们自己实现的callback
            mCallback.set(handler, new ActivityThreadHandlerCallback(handler));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}