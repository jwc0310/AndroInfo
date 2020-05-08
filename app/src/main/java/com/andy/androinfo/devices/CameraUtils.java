package com.andy.androinfo.devices;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.util.Log;

import java.util.LinkedHashMap;
import java.util.List;

public class CameraUtils {

    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_NONE = 2;

    private static final String TAG = "CameraUtils";

    private static LinkedHashMap<String, String> mMap = new LinkedHashMap<>();

    private static void test() {
        mMap.clear();
        mMap.containsValue("");
        mMap.get("");
        mMap.entrySet();
        mMap.keySet();
        mMap.values();

    }

    private static void log(String str) {
        //Log.e(TAG, str);
    }

    public static void showCameraInfo(Context context) {
        if (!hasCameraSupport(context)) {
            log("not support camera");
            return;
        }

        int number = getNumberOfCameras(context);
        log("support camera number: " + number);


        for (int i = 0; i < number; i++) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CAMERA_FACING_FRONT) {
                log("front: -------> ");
                
            } else if (cameraInfo.facing == CAMERA_FACING_BACK) {
                log("back: -------> ");
            }
            //showCameraInfo(context, cameraInfo.facing);
        }
        log("");
    }

    public static void showCameraInfo(Context context, int id) {
        Camera camera = Camera.open(id);
        Camera.Parameters parameters = camera.getParameters();
        log("camera " + id + ", dump: " + parameters.flatten());
        List<Camera.Size> picSizes = parameters.getSupportedPictureSizes();

        if (picSizes == null) {
            log("pic not supported size !");
        } else {
            for (Camera.Size size : picSizes) {
                log("pic supported size: " + size.width + " x " + size.height);
            }
        }

        List<Camera.Size> vidSizes = parameters.getSupportedVideoSizes();
        if (vidSizes == null) {
            log("vid not supported size !");
        } else {
            for (Camera.Size size : vidSizes) {
                log("vid supported size: " + size.width + " x " + size.height);
            }
        }

        log("isVideoSnapshotSupported: " + parameters.isVideoSnapshotSupported());
        log("isVideoStabilizationSupported: " + parameters.isVideoStabilizationSupported());

        log("getFocalLength: " + parameters.getFocalLength());
        log("isZoomSupported: " + parameters.isZoomSupported());
        log("isSmoothZoomSupported: " + parameters.isSmoothZoomSupported());
        log("isAutoExposureLockSupported: " + parameters.isAutoExposureLockSupported());
        log("isAutoWhiteBalanceLockSupported: " + parameters.isAutoWhiteBalanceLockSupported());

        List<String> flashModes = parameters.getSupportedFlashModes();

        if (flashModes == null) {
            log("has no flash Modes !");
        } else {
            log("flash modes: -----> ");
            for (String string: flashModes) {
                log(string);
            }
        }

        List<String> focusModes = parameters.getSupportedFocusModes();

        if (flashModes == null) {
            log("has no focus Modes !");
        } else {
            log("focus modes: -----> ");
            for (String string: focusModes) {
                log(string);
            }
        }

    }

    public static int getNumberOfCameras(Context context) {
        return Camera.getNumberOfCameras();
    }

    //check camera exist
    public static boolean hasCameraSupport(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }


}
