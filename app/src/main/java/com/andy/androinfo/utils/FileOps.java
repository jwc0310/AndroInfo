package com.andy.androinfo.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

public class FileOps {

    public static boolean modifyXml(String path, String tag, String value) {
        File file = new File(path);
        if (!file.exists())
            return false;

        try {
            File tmp = new File("/sdcard/Download/tmp.xml");
            if (!file.exists())
                tmp.createNewFile();

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tmp));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("AndyModify", "line = " + line);
                if (line.contains(tag)) {
                    int modifyStart = line.indexOf(">");
                    if (modifyStart == -1)
                        continue;
                    int modifyEnd = line.indexOf("<", modifyStart);
                    if (modifyEnd == -1 || modifyEnd < modifyStart)
                        continue;

                    String newline = line.substring(0, modifyStart+1) +
                            value + line.substring(modifyEnd);
                    Log.e("AndyModify", "new line = "+newline);
                    writer.write(newline);
                } else {
                    writer.write(line);
                }
                writer.write("\n");
                writer.flush();
            }
            writer.close();
            reader.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
