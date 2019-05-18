package com.andy.androinfo.hook;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.view.View;

import com.andy.androinfo.utils.LogUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/12/26.
 */

public class HookUtil {

    private Class<?> proxyActivity;
    private Context context;
    private static final String TAG = HookUtil.class.getSimpleName();

    public HookUtil(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    public void hookNotificationManager() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        //获得系统的sService
        try {
            Method getService = NotificationManager.class.getDeclaredMethod("getService");
            getService.setAccessible(true);
            final Object sService = getService.invoke(notificationManager);

            //动态代理 INotificationManager
            Class inotifyManager = Class.forName("android.app.INotificationManager");
            Object proxyNotify = Proxy.newProxyInstance(getClass().getClassLoader(), new Class<?>[]{inotifyManager},
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            log("notifyProxy invoke method: " + method.getName());
                            if (args != null && args.length > 0) {
                                for (Object object : args) {
                                        log("notifyProxy arg = ");
                                }
                            }
                            // 操作交由 sService 处理，不拦截通知
                             return method.invoke(sService, args);
                            // 拦截通知，什么也不做
                            //return null;
                            // 或者是根据通知的 Tag 和 ID 进行筛选
                        }
                    });

            Field sServiceField = NotificationManager.class.getDeclaredField("sService");
            sServiceField.setAccessible(true);
            sServiceField.set(notificationManager, proxyNotify);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void hookOnClickListener(View view) {
        try {
            //获得ListenerInfo对象
            Method getListenerInfo = View.class.getDeclaredMethod("getListenerInfo");
            getListenerInfo.setAccessible(true);
            Object listenerInfo = getListenerInfo.invoke(view);

            //获得onClickListener对象
            Class<?> listenInfoClz = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListener = listenInfoClz.getDeclaredField("mOnClickListener");
            mOnClickListener.setAccessible(true);

            View.OnClickListener origin = (View.OnClickListener) mOnClickListener.get(listenerInfo);
            View.OnClickListener hookOnClickListener = new HookOnClickListener(origin);
            mOnClickListener.set(listenerInfo, hookOnClickListener);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
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

    public void handleBuildProperty() {
        try {
            log( "handleBuildInfo");
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
            log( "handleBuildInfo");
            Class<?> systemPropertiesClass = Class.forName("android.os.Build");
            Field board = systemPropertiesClass.getDeclaredField("BRAND");
            board.setAccessible(true);
            board.set(systemPropertiesClass, "Andy's board");
            log( Build.BRAND);
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

            log( "hookFuncs = " + result);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void log(String content) {
        LogUtil.e(LogUtil.HookUtil_debug, TAG, content);
    }
}
