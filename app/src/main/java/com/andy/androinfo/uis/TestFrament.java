package com.andy.androinfo.uis;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andy.androinfo.R;
import com.andy.androinfo.features.Gapps;
import com.andy.androinfo.hook.HookUtil;
import com.andy.androinfo.jni.TestJni;
import com.andy.androinfo.opengl.OpenGlActivity;
import com.andy.androinfo.preference.PreferenceActivityWithPreferenceFragment;
import com.andy.androinfo.utils.NotificationUtils;
import com.andy.androinfo.utils.SocketUtils;
import com.andy.detect.IEmulatorCheck;
import com.andy.detect.log.Logger;
import com.andy.detect.service.EmulatorCheckService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/14.
 */

public class TestFrament extends AndyBaseFragment implements View.OnClickListener {

    private static final String TAG = TestFrament.class.getSimpleName();

    private Button hook_onClick, hook_notify, go_prefer, go_prefer2, install_xapk;
    private Button go_opengl, go_detect;
    private int testi = 0;
    private Context context;

    public static TestFrament instance(String content) {
        TestFrament fragment = new TestFrament();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void initPrepare() {
    }

    @Override
    protected void onInvisible() {
    }

    @Override
    protected void initData() {
        Gapps.hookGaid();
//        XmlUtils.parseXml("/sdcard/Download/adid_settings.xml");
//        XmlUtils.parseXml("/sdcard/Download/person.xml");
//        FileOps.modifyXml("/sdcard/Download/adid_settings.xml", "\"adid_key\"", "addffdfdcdifsjdfkji");
    }

    @Override
    protected void onCreateInit(@Nullable Bundle savedInstanceState) {
        if (null != savedInstanceState) {
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        context = getContext();
        view = inflater.inflate(R.layout.fragment_test, null);
        hook_onClick = (Button) view.findViewById(R.id.andy_hook_onclick);
        hook_onClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "click");
            }
        });
        new HookUtil(MainActivity.class, getContext()).hookOnClickListener(hook_onClick);
        hook_notify = (Button) view.findViewById(R.id.andy_hook_notification);
        hook_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationUtils.sendNotification(testi++);
            }
        });

        go_prefer = (Button) view.findViewById(R.id.andy_prefer_setting);
        go_prefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PreferenceActivityWithPreferenceFragment.class));
            }
        });

        go_prefer2 = (Button) view.findViewById(R.id.andy_prefer_setting2);
        go_prefer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ActivityWithPreferenceFragment.class));
                SocketUtils.startInstallXapkSocket();
            }
        });

        install_xapk = (Button) view.findViewById(R.id.andy_install_xapk);
        install_xapk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ActivityWithPreferenceFragment.class));
//            SocketUtils.startInstallXapkClient("/sdcard/Download/war.xapk");
                long t1 = System.currentTimeMillis();
                TestJni.getHello();
                Log.e(TAG, "cost time = " + (System.currentTimeMillis() - t1));
            }
        });

        install_xapk.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.e("Andy453", motionEvent.getAction() +"");
                return false;
            }
        });

        setClickEvent(go_opengl, view, R.id.andy_go_opengl, this);
        setClickEvent(go_detect, view, R.id.andy_go_detect, this);
        return view;
    }


    private void setClickEvent(Button button, View view, int resId, View.OnClickListener listener) {
        if (view == null) return;
        button = (Button) view.findViewById(resId);
        button.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id) {
            case R.id.andy_go_opengl:
                startActivity(new Intent(getContext(), OpenGlActivity.class));
                break;
            case R.id.andy_go_detect:
                //setGo_detect();
                pos();
                break;
            default:
                break;
        }
    }

    LocationListener listener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            Log.e("position", "5: " + location.toString());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e("position", "6: onStatusChanged: " + provider + ", " + status);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e("position", "7: onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e("position", "8: onProviderDisabled: " + provider);
        }
    };

    private void pos() {

        Log.e("position", "\n---------- phone ----------\n");

        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        Log.e("position", "1: " + telephonyManager.getNetworkOperatorName());
        Log.e("position", "2: " + telephonyManager.getSimOperatorName());
        Log.e("position", "3: " + telephonyManager.getSimOperator());
        Log.e("position", "4: " + telephonyManager.getNetworkOperator());
        Log.e("position", "5: " + telephonyManager.getSimCountryIso());
        Log.e("position", "6: " + telephonyManager.getNetworkCountryIso());

        List<NeighboringCellInfo> list = telephonyManager.getNeighboringCellInfo();
        if (list != null) {
            for (NeighboringCellInfo neighboringCellInfo : list) {
                Log.e("position", "7: " + neighboringCellInfo.toString());
            }
        } else {
            Log.e("position", "7: getNeighboringCellInfo");
        }

        List<CellInfo> list1 = telephonyManager.getAllCellInfo();
        if (list1 != null) {
            for (CellInfo info : list1) {
                Log.e("position", "8: " + info.toString());
            }
        } else {
            Log.e("position", "8: getAllCellInfo == null");
        }

        CellLocation location = telephonyManager.getCellLocation();
        Log.e("position", "9: " + location.toString());

        Log.e("position", "10: " + telephonyManager.getNetworkType());
        Log.e("position", "11: " + telephonyManager.getPhoneType());
//        Log.e("position", "12: " + telephonyManager);

        Log.e("position", "\n---------- wifi ----------\n");

        WifiManager wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> results = wifiManager.getScanResults();

        for (ScanResult scanResult : results) {
            Log.e("position", "1: " + scanResult.toString());
        }

        WifiInfo info = wifiManager.getConnectionInfo();
        if (info != null) {

            Log.e("position", "2: " + info.getMacAddress());
            Log.e("position", "3: " + info.getBSSID());
            Log.e("position", "4: " + info.getSSID());
        }


        Log.e("position", "\n---------- location ----------\n");

        LocationManager locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        GpsStatus status = locationManager.getGpsStatus(null);

        Log.e("position", "1: " +status.getMaxSatellites() + "");
        Log.e("position", "2: " +status.getTimeToFirstFix() + "");
        Log.e("position", "3: " +locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) + "");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0,listener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null)
            Log.e("position", "4: " + lastKnownLocation.toString());
        //locationManager.removeUpdates(listener);



        Geocoder geocoder = new Geocoder(getContext());
        boolean flag = Geocoder.isPresent();
        if (flag) {
            try {
                List<Address> addresses = geocoder.getFromLocation(39.345345, 116.345, 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    String sAddress;
                    if (!TextUtils.isEmpty(address.getLocality())) {
                        if (!TextUtils.isEmpty(address.getFeatureName())) {
                            //市和周边地址
                            sAddress = address.getLocality() + " " + address.getFeatureName();
                        } else {
                            sAddress = address.getLocality();
                        }
                    } else {
                        sAddress = "定位失败";
                    }
                    Log.e("gzq", "sAddress：" + sAddress);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setGo_detect() {
        Intent intent = new Intent(getContext(), EmulatorCheckService.class);
        getActivity().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IEmulatorCheck iEmulatorCheck = IEmulatorCheck.Stub.asInterface(service);
            if (iEmulatorCheck != null) {
                try {
                    int pid = Process.myPid();
                    Logger.e("request pid = " + pid);
                    iEmulatorCheck.isEmulator();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

}
