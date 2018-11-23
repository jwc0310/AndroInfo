package com.andy.androinfo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.andy.androinfo.AndroInfoApplication;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2018/6/19.
 */

public class PackageUtils {

    private static final String TAG = PackageUtils.class.getSimpleName();

    public static String getInstalledPackages(Context context) {
        JSONArray jas = new JSONArray();
        PackageManager packageManager = context.getPackageManager();
        List<ApplicationInfo> pinfo = packageManager.getInstalledApplications(0x2000);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String packageName = pinfo.get(i).packageName;
                jas.put(packageName);
            }
        }

        return jas.toString();
    }

    public static void getInstallPackages(Context context) {
        PackageManager  packageManager  = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN", null);
        intent.addCategory("android.intent.category.LAUNCHER");
        List list = packageManager.queryIntentActivities(intent, 0);
        if (list != null) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Log.e("Andy package", ((ResolveInfo)iterator.next()).activityInfo.packageName);
            }
        }
    }


    /**
     * check the apk installed
     *
     * @param packageName
     * @return
     */
    public static boolean isApkInstalled(String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager pm = AndroInfoApplication.getGlobal().getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            if (info != null) {
                return true;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return false;
    }

    /**
     * 静默安装
     *
     * @param filePath
     * @return
     */
    public static int installApk(String packageName, String filePath) {
        File file;
        if (filePath == null || filePath.length() == 0 || (file = new File(filePath)) == null || file.length() <= 0 || !file.exists() || !file.isFile()) {
            return 1;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ShellUtils.CommandResult commandResult;
            String[] str = {"pm", "install", "-i", "com.andy.androinfo", "--user", "0", filePath};
            commandResult = ShellUtils.execCommand(str, false);
            int result;
            if (commandResult == null || commandResult.successMsg == null)
                return 2;
            if (commandResult.successMsg.contains("Success") || commandResult.successMsg.contains("success")) {
                result = 0;
            } else {
                result = 2;
            }
            return result;
        } else {
            String[] args = {"pm", "install", "-r", filePath};
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder errorMsg = new StringBuilder();
            int result;
            try {
                Log.i("Download", "Start installing package: " + packageName);
                process = processBuilder.start();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }

                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            } catch (Exception e) {
                e.printStackTrace();
                result = 2;
            } finally {
                try {
                    if (successResult != null) {
                        successResult.close();
                    }
                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (process != null) {
                    process.destroy();
                }
            }

            // TODO should add memory is not enough here
            if (successMsg.toString().contains("Success") || successMsg.toString().contains("success")) {
                result = 0;
            } else {
                result = 2;
            }
            Log.e("result", result + "");
            Log.d("installSlient", "successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
            return result;
        }
    }

    /**
     * 静默卸载
     *
     * @return
     */

    public static int uninstallApk(String packageName) {
        String[] args = {"pm", "uninstall", packageName};
        ProcessBuilder processBuilder = new ProcessBuilder(args);

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        int result;
        try {
            process = processBuilder.start();
            successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String s;
            while ((s = successResult.readLine()) != null) {
                successMsg.append(s);
            }

            while ((s = errorResult.readLine()) != null) {
                errorMsg.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = 2;
        } catch (Exception e) {
            e.printStackTrace();
            result = 2;
        } finally {
            try {
                if (successResult != null) {
                    successResult.close();
                }
                if (errorResult != null) {
                    errorResult.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (process != null) {
                process.destroy();
            }
        }

        // TODO should add memory is not enough here
        if (successMsg.toString().contains("Success") || successMsg.toString().contains("success")) {
            result = 0;
        } else {
            result = 2;
        }
        Log.d("installSlient", "successMsg:" + successMsg + ", ErrorMsg:" + errorMsg);
        return result;
    }


}
