package com.andy.androinfo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;

public class UpdateAppConf implements Runnable {

    private int update_type;
    private String packageName;

    public UpdateAppConf(String packageName) {
        this.update_type = 0;
        this.packageName = packageName;
    }

    public UpdateAppConf(int update_type) {
        this.update_type = update_type;
    }

    @Override
    public void run() {
        String content;
        synchronized (this) {
            if (this.update_type != 0) {
                content = getUpdateContent();
                if (null == content) {
                    return;
                }
            } else {
                content = this.packageName;
            }

            updateAppConf("AE", content);
        }
    }

    //开机启动后 获取所有第三方应用
    private String getUpdateContent() {
        ShellUtils.CommandResult result = ShellUtils.execCommand("pm list packages -3", false, true);
        if (result.result == 0)
            return result.successMsg;
        else
            return null;
    }

    //指定label中添加
    private String insertLabel(String labelContent, String label, String[] packages) {
        StringBuilder builder = new StringBuilder(labelContent);
        StringBuilder md5s = new StringBuilder();
        for (int i = 0; i < packages.length; i++) {
            String tmp = getMd5(packages[i]);
            if (null != tmp) {
                String md5 = tmp.substring(0, 8);
                if (labelContent.contains(md5))
                    continue;
                md5s.append(md5);
                md5s.append(";");
            }
        }

        if (md5s.length() > 0 && md5s.length() % 9 == 0)
            builder.insert(label.length() +2, md5s.toString());

        builder.append("\r\n");

        return builder.toString();
    }

    //新增label
    private String addLabel(String label, String[] packages) {

        if (packages.length <= 0)
            return null;

        StringBuilder builder = new StringBuilder();
        builder.append("<");
        builder.append(label);
        builder.append(">");

        for (int i = 0; i < packages.length; i++) {
            String tmp = getMd5(packages[i]);
            if (null != tmp) {
                String md5 = tmp.substring(0, 8);
                builder.append(md5);
                builder.append(";");
            }
        }

        builder.append("</");
        builder.append(label);
        builder.append(">");
        builder.append("\r\n");

        return builder.toString();
    }

    private void updateAppConf(String label, String content) {
        String[] packages = content.split("package:");
        if (packages.length <= 0)
            return;

        boolean AE = false;
        boolean AEK = false;
        File conf = new File("/data/.conf/app.conf");
        if (!conf.exists()) {
            try {
                conf.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File tmp = new File("/data/.conf/app.conf_tmp");
        if (!tmp.exists()) {
            try {
                tmp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(conf));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));

            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                if (line.startsWith("<AE>")) {
                    AE = true;
                    String newContent = insertLabel(line, "AE", packages);
                    writer.write(newContent);
                } else {
                    writer.write(line);
                }

                if (line.startsWith("<AEK>")) {
                    AEK = true;
                    String newContent = insertLabel(line, "AEK", packages);
                    writer.write(newContent);
                } else {
                    writer.write(line);
                }
            }

            if (!AE) {
                String newContent = addLabel("AE", packages);
                if (newContent != null) {
                    writer.write(newContent);
                }
            }

            if (!AEK) {
                String newContent = addLabel("AEK", packages);
                if (newContent != null) {
                    writer.write(newContent);
                }
            }

            writer.flush();
            reader.close();
            writer.close();

            if (conf.delete()) {
                System.out.println("conf delete success");
                if (tmp.renameTo(conf)) {
                    System.out.println("conf rename success");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getMd5(String str) {
        StringBuilder sb = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bs = md5.digest(str.getBytes());
            sb = new StringBuilder(40);
            for (byte x : bs) {
                if ((x & 0xff) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(x & 0xff));
                } else {
                    sb.append(Integer.toHexString(x & 0xff));
                }
            }
            Log.e("UpdateAppConf", str + ": " +sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb != null ? sb.toString() : null;
    }
}
