package com.andy.androinfo.utils;

import android.os.Environment;

import com.andy.androinfo.beans.XAPKManifest;
import com.andy.androinfo.beans.XAPKManifest.ExpansionsBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//安装xapk的测试
//xapk = apk + obb
public class ZipUtils {
    private static final String TAG = ZipUtils.class.getSimpleName();

    private static void log(String content) {
        LogUtil.e(LogUtil.ZipUtils_debug, TAG, content);
    }

    private static void printZipEntry(ZipEntry zipEntry) {
        printLog(zipEntry.getName());
        printLog(zipEntry.getComment());
        printLog(zipEntry.getCompressedSize() +"");
        printLog(zipEntry.getSize() +"");
        printLog(zipEntry.getMethod() +"");
        printLog(zipEntry.getTime()+"");
        printLog(zipEntry.getCrc()+"");
        printLog(zipEntry.getExtra()+"");
    }

    private static File getExpansionsPath(XAPKManifest xapkManifest, ExpansionsBean expansionsBean) {
        File file1 = Environment.getExternalStorageDirectory();
        String str = new File(expansionsBean.getFile()).getName();
        if (!str.toLowerCase().endsWith(".obb"))
            return null;

        File file2 = new File(file1, "Android/obb/"+xapkManifest.getPackage_name()+"/"+str);
        file2.getParentFile().mkdirs();
        return file2;

    }

    private static XAPKManifest parseXAPKManifest(InputStream inputStream, int size) {
        try {
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            inputStream.close();

            String context = new String(bytes, "UTF8");

            JSONObject jsonObject = new JSONObject(context);
            //System.out.println(jsonObject.toString());

            XAPKManifest xapkManifest = new XAPKManifest();
            xapkManifest.setXapk_version(jsonObject.optInt("xapk_version"));
            xapkManifest.setPackage_name(jsonObject.optString("package_name"));
            xapkManifest.setName(jsonObject.optString("name"));
            xapkManifest.setVersion_code(jsonObject.optString("version_code"));
            xapkManifest.setVersion_name(jsonObject.optString("version_name"));
            xapkManifest.setMin_sdk_version(jsonObject.optString("min_sdk_version"));
            xapkManifest.setTarget_sdk_version(jsonObject.optString("target_sdk_version"));
            xapkManifest.setTotal_size(jsonObject.optInt("total_size"));

            JSONArray permissionsArray = jsonObject.getJSONArray("permissions");
            List<String> permissions = new ArrayList<String>();
            for (int i = 0; i < permissionsArray.length(); i++) {
                permissions.add(permissionsArray.getString(i));
            }
            xapkManifest.setPermissions(permissions);

            JSONArray expansionsArray = jsonObject.getJSONArray("expansions");
            List<ExpansionsBean> expansionsBeans = new ArrayList<ExpansionsBean>();
            for (int i = 0; i < expansionsArray.length(); i++) {
                ExpansionsBean expansionsBean = new ExpansionsBean();
                JSONObject expansionsObject = expansionsArray.getJSONObject(i);
                expansionsBean.setFile(expansionsObject.optString("file"));
                expansionsBean.setInstall_location(expansionsObject.optString("install_location"));
                expansionsBean.setInstall_path(expansionsObject.optString("install_path"));
                expansionsBeans.add(expansionsBean);
            }

            xapkManifest.setExpansions(expansionsBeans);
            //System.out.println(xapkManifest.toString());

            return xapkManifest;

        } catch (Exception e) {
            System.err.println(e.toString());

        }
        return null;

    }

    public static String runInstallXapkObb(String path) {
        try {
            ZipFile xapkFile = new ZipFile(path);
            ZipEntry zipEntry = xapkFile.getEntry("manifest.json");
            Long size = zipEntry.getSize();

            XAPKManifest manifest = parseXAPKManifest(xapkFile.getInputStream(zipEntry), size.intValue());

            if (manifest == null)
                return null;

            byte[] bytes = new byte[16384];

            Iterator iterator = manifest.getExpansions().iterator();
            while (iterator.hasNext()) {
                ExpansionsBean bean = (ExpansionsBean) iterator.next();
                ZipEntry expanEntry = xapkFile.getEntry(bean.getFile());
                if (expanEntry == null)
                    return null;
                InputStream inputStream = xapkFile.getInputStream(expanEntry);
                File obbFile = new File(Environment.getExternalStorageDirectory(), bean.getInstall_path());
                System.out.println(bean.getInstall_path());
                System.out.println(obbFile.getAbsolutePath());
                obbFile.getParentFile().mkdirs();

                FileOutputStream fileOutputStream = new FileOutputStream(obbFile);
                int read = -1;
                while ((read = inputStream.read(bytes)) != -1) {
                    fileOutputStream.write(bytes, 0, read);
                }

                fileOutputStream.close();
                inputStream.close();
            }

            //extract apk
            ZipEntry apkEntry = xapkFile.getEntry(manifest.getPackage_name() + ".apk");
            if (apkEntry == null)
                return null;

            File tmpInstallApk = new File(Environment.getExternalStorageDirectory(), "Download/" + manifest.getPackage_name()+".apk");

            InputStream apkInputStream = xapkFile.getInputStream(apkEntry);
            FileOutputStream fileOutputStream = new FileOutputStream(tmpInstallApk);
            int read = -1;
            while ((read = apkInputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, read);
            }

            fileOutputStream.close();
            apkInputStream.close();

            return tmpInstallApk.getAbsolutePath();

        } catch (Exception e) {
            System.err.println(e.toString());
        }

        return null;

    }

    public static void installXAPK(final String path) {

        if (path.endsWith(".xapk"))
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ZipFile file = new ZipFile(path);
                    ZipEntry zipEntry = file.getEntry("manifest.json");
                    zipEntry.getSize();
                    Long size = zipEntry.getSize();
                    size.intValue();
                    XAPKManifest xapkManifest = parseXAPKManifest(file.getInputStream(zipEntry));
                    log(xapkManifest.toString());

                    long l1 = getExpansionsSize(file, xapkManifest);

                    if (l1 <= 0l)
                        return;

                    log("size = " + l1);

                    byte[] bytes = new byte[16384];

                    //install obb
                    Iterator iterator = xapkManifest.getExpansions().iterator();
                    while (iterator.hasNext()) {
                        ExpansionsBean expansionsBean = (ExpansionsBean)iterator.next();
                        ZipEntry zipEntry1 = file.getEntry(expansionsBean.getFile());
                        InputStream inputStream = file.getInputStream(zipEntry1);
                        File file1 = getExpansionsPath(xapkManifest, expansionsBean);
                        log("file1 = " + file1.getAbsolutePath());
                        FileOutputStream fileOutputStream = new FileOutputStream(file1);

                        int i = -1;
                        while ((i = inputStream.read(bytes)) != -1) {
                            fileOutputStream.write(bytes, 0, i);
                        }
                        fileOutputStream.close();
                        inputStream.close();
                    }

                    log("install obb finish and start install apk");

                    //extract apk
                    ZipEntry apkEntry = file.getEntry(xapkManifest.getPackage_name()+".apk");
                    if (apkEntry == null) {
                        return;
                    }

                    File tmpInstallApk = new File(Environment.getExternalStorageDirectory(), "Download/" + xapkManifest.getPackage_name()+".apk");

                    InputStream apkInputStream = file.getInputStream(apkEntry);
                    FileOutputStream apkOutputStream = new FileOutputStream(tmpInstallApk);
                    int i = -1;
                    while ((i = apkInputStream.read(bytes)) != -1) {
                        apkOutputStream.write(bytes, 0, i);
                    }

                    apkOutputStream.flush();
                    apkOutputStream.close();
                    apkInputStream.close();

                    int result = PackageUtils.installApk(xapkManifest.getPackage_name(), tmpInstallApk.getAbsolutePath());
                    if (result == 0) {
                        log("install success");
                    } else {
                        log("install failed = " + result);
                    }
                    tmpInstallApk.delete();

                    log("install finish");

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private static long getExpansionsSize(ZipFile file, XAPKManifest xapkManifest) {
        long l = 0l;
        if (xapkManifest.getExpansions().isEmpty())
            l = 0l;

        Iterator iterator = xapkManifest.getExpansions().iterator();
        while (iterator.hasNext()) {
            ExpansionsBean expansionsBean = (ExpansionsBean) iterator.next();
            ZipEntry zipEntry = file.getEntry(expansionsBean.getFile());
            if (zipEntry == null)
                break;

            l += zipEntry.getSize();
        }

        return l;
    }

    private static XAPKManifest parseXAPKManifest(InputStream inputStream) throws UnsupportedEncodingException {
        return (XAPKManifest) new Gson().fromJson(new InputStreamReader(inputStream, "UTF8"), XAPKManifest.class);
    }

    public static void main(String[] args) throws IOException {


        ZipFile file = new ZipFile("D:\\test\\war.xapk");
        ZipEntry zipEntry = file.getEntry("manifest.json");
        printZipEntry(zipEntry);

        zipEntry = file.getEntry("com.joycity.gnss.apk");
        printZipEntry(zipEntry);

        zipEntry = file.getEntry("Android");
        printZipEntry(zipEntry);

        zipEntry = file.getEntry("icon.png");
        printZipEntry(zipEntry);




//        File file = new File("D:\\test\\test1.zip");
//        unZip(file);

//        String zipFileName = "D:\\test\\ziptest.zip";
//        String entry = "D:\\test\\chen.txt";
//
//        compress(zipFileName, entry);
//
//
//        entry = "D:\\test\\test1";
//        File file = new File(entry);
//        try {
//            ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file.getAbsolutePath()+".zip")));
//            compressZip(zipOutputStream, file, file.getName());
//            zipOutputStream.closeEntry();
//            zipOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }




    public static void unZip(File file) throws IOException {
        if (!file.exists())
            return;

        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));

        File out = null;
        String parentDir = file.getParent();
        ZipEntry zipEntry;
        while((zipEntry = zipInputStream.getNextEntry()) != null && !zipEntry.isDirectory()) {
            out = new File(parentDir, zipEntry.getName());
            if (!out.exists()) {
                (new File(out.getParent())).mkdir();
            }

            BufferedOutputStream bufferedOutputStream =new BufferedOutputStream(new FileOutputStream(out));
            int b = -1;
            byte[] bytes = new byte[1024];
            while ((b = zipInputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, b);
            }
            bufferedOutputStream.close();
        }

        zipInputStream.close();
    }


    public static void compressZip(ZipOutputStream zipOutputStream, File file, String base) throws IOException {
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File fi : files) {
                if (fi.isDirectory()) {
                    compressZip(zipOutputStream, fi, base + File.separator + fi.getName());
                } else {
                    zip(zipOutputStream, fi, base);
                }
            }
        } else {
            zip(zipOutputStream, file, base);
        }
    }

    private static void zip(ZipOutputStream zipOutputStream, File file, String base) throws IOException, FileNotFoundException {
        ZipEntry zipEntry = new ZipEntry(base + File.separator + file.getName());
        zipOutputStream.putNextEntry(zipEntry);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[1024];
        int read = 0;
        while ((read = bufferedInputStream.read(bytes)) != -1) {
            zipOutputStream.write(bytes, 0, read);
        }

        bufferedInputStream.close();
    }





    /**
     * @param zipFilename
     * @param entry
     */
    public static void compress(final String zipFilename, final String entry) {
        try {

            File entryFile = new File(entry);

            if (!entryFile.exists()) {
                printLog("not exist " + entryFile.getAbsolutePath());
                return;
            }

            ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilename)));
            zos.setLevel(Deflater.BEST_COMPRESSION);

            ZipEntry zipEntry = new ZipEntry(entryFile.getName());
            printLog(zipEntry.getName());
            printLog(zipEntry.getComment());
            printLog(zipEntry.getCompressedSize() +"");
            printLog(zipEntry.getSize() +"");
            printLog(zipEntry.getMethod() +"");
            printLog(zipEntry.getTime()+"");
            printLog(zipEntry.getCrc()+"");
            printLog(zipEntry.getExtra()+"");

            zos.putNextEntry(zipEntry);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(entryFile));
            byte[] buffer = new byte[1024];
            int count = -1;

            while ((count = bis.read(buffer)) != -1) {
                zos.write(buffer, 0, count);
            }

            bis.close();
            zos.closeEntry();
            zos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printLog(String str) {
        log(str);
    }


}
