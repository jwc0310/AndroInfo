package com.andy.androinfo.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XmlUtils {

    public static void parseXml(String path) {
        File file = new File(path);
        if (!file.exists())
            return;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(fileInputStream, "UTF-8");

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case (XmlPullParser.START_DOCUMENT):
                        break;
                    case (XmlPullParser.START_TAG):
                        String tagName = parser.getName();
                        Log.e("AndyXml", "  tag = " + tagName);
                        //if (tagName.equals("string") &&)

                        if (tagName.equals("string")) {
                            String name = parser.getAttributeValue(0);
                            Log.e("AndyXml", "name = " + name);
                            if (name.equals("\"adid_key\"")){
                                String value = parser.nextText();
                                Log.e("AndyXml", "value = " + value);
                            }
                        }

                        break;
                    case (XmlPullParser.END_TAG):
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
