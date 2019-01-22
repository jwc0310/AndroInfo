package com.andy.androinfo.utils;

import android.text.TextUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketUtils {

    //安装xapk的测试
    //xapk = apk + obb
    public static void startInstallXapkClient(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String tmp = null;
                try {
                    String format = "abc:" + path+"\n";
                    Socket socket = new Socket("127.0.0.1", 21508);
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    outputStream.write(format.getBytes());
                    outputStream.flush();
                    String reply = inputStream.readLine();
                    if (reply != null)
                        tmp = reply;
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (TextUtils.isEmpty(tmp) || tmp.equals("null") || !tmp.endsWith(".apk")) {
                    return;
                }

                PackageUtils.installApk("", tmp);

            }
        }).start();
    }

    public static void startInstallXapkSocket() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerSocket server = null;
                Socket client = null;
                try {
                    server = new ServerSocket(21508, 0, InetAddress.getByName("127.0.0.1"));
                    while (true) {
                        try {
                            client = server.accept();
                            String result;
                            // 读取客户端数据
                            DataInputStream input = new DataInputStream(client.getInputStream());
                            String clientInputStr = input.readLine();

                            if (clientInputStr != null && clientInputStr.length() > 0) {
                                String[] split = clientInputStr.split(":");
                                if (split != null && split.length > 1) {
                                    String path = split[1];
                                    //do someThing;
                                    result = null;
                                    if (!TextUtils.isEmpty(path))
                                        result = ZipUtils.runInstallXapkObb(path);

                                    String rr = (result == null ? "null\n" : result+"\n");

                                    DataOutputStream output = new DataOutputStream(client.getOutputStream());
                                    output.write(result.getBytes());
                                    output.flush();
                                    output.close();
                                }
                                input.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (client != null) {
                                try {
                                    client.close();
                                } catch (Exception e) {
                                    client = null;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
