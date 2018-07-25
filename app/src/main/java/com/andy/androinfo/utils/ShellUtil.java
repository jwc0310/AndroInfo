package com.andy.androinfo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ShellUtil {

    public static void test(String command) {
        exec(command, false);
    }

    public static boolean exec(String command) {
        return exec(command, true);
    }

    public static boolean exec(String command, boolean isRoot) {
        int result = -1;
        StringBuilder builder = new StringBuilder("");
        try {
            Process process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.write(command.getBytes());
            outputStream.writeBytes("\n");
            outputStream.flush();
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            result = process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            Log.e("tmp success", command+": "+builder.toString());
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errormsg = new StringBuilder("");
            while((line = reader1.readLine()) != null) {
                errormsg.append(line);
                errormsg.append("\n");
            }
            Log.e("tmp failed", command+": "+errormsg.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result == 0;
    }

}
