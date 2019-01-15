package com.andy.androinfo.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class XmlUtils {
    private static final String TAG = "XmlUtils";

    public static String getAndroidId() {
        Log.e(TAG, "start get Android Id");
        String name = null;

        try {
            File file = new File("/sdcard/Download/settings_secure.xml");
            if (!file.exists())
                return null;
            Log.e(TAG, "start get Android Id 2");
            FileInputStream in = new FileInputStream(file);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(in, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = parser.getName();
//                        Log.e(TAG, "tagName = " + tagName);
//                        for (int i = 0; i < parser.getAttributeCount(); i++) {
//                            Log.e(TAG, parser.getAttributeName(i) +", " + parser.getAttributeValue(i) +" i = " + i);
//                        }

                        if (tagName.equals("setting")  && "android_id".equals(parser.getAttributeValue(1)))
                            Log.e(TAG, parser.getAttributeValue(2));

                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return name;
    }

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
