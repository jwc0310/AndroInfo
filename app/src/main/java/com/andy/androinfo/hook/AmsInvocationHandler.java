package com.andy.androinfo.hook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.andy.androinfo.uis.MainActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/12/26.
 */

public class AmsInvocationHandler implements InvocationHandler {

    private Object iActivityManagerObject;
    private Context context;

    public AmsInvocationHandler(Object iActivityManagerObject, Context context) {
        this.iActivityManagerObject = iActivityManagerObject;
        this.context = context;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("HookUtil", method.getName());
        if ("test".equals(method.getName())) {
            //我要在这里搞点事情
            return true;
        } else if ("startActivity".contains(method.getName())) {
            Log.e("HookUtil","Activity已经开始启动");
            //换掉
            Intent intent = null;
            int index = 0;
            for (int i = 0; i < args.length; i++){
                Object arg = args[i];
                if (arg instanceof Intent) {
                    //说明找到了startActivity的Intent参数
                    intent = (Intent) args[i];
                    //这个意图是不能被启动的，因为Acitivity没有在清单文件中注册
                    index = i;
                }
            }

            //伪造一个代理的Intent, 代理Intent启动的proxyActivity
            Intent proxyIntent = new Intent();
            ComponentName componentName = new ComponentName(context, MainActivity.class);
            proxyIntent.setComponent(componentName);
            proxyIntent.putExtra("oldIntent", intent);
            args[index] = proxyIntent;

        }
        return method.invoke(iActivityManagerObject, args);
    }
}
