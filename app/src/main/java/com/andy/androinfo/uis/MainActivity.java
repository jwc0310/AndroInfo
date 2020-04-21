package com.andy.androinfo.uis;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.OnNmeaMessageListener;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.beans.TitleBean;
import com.andy.androinfo.devices.CameraUtils;
import com.andy.androinfo.emulator.Detecter;
import com.andy.androinfo.jni.TestJni;
import com.andy.androinfo.ms.Ms;
import com.andy.androinfo.utils.AndroFileObserver;
import com.andy.androinfo.utils.DirUtils;
import com.andy.androinfo.utils.Emulator;
import com.andy.androinfo.utils.IPInfo;
import com.andy.androinfo.utils.MemUtils;
import com.andy.androinfo.utils.NetworkUtils;
import com.andy.androinfo.utils.StorageUtil;
import com.andy.androinfo.utils.TestHideTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AndyBaseActivity {

    RecyclerView title_rv;
    LinearLayoutManager linearLayoutManager;
    TitleAdapter titleAdapter;
    ArrayList<TitleBean> titleBeans;
    ViewPager content_vp;
    ContentFragmentPagerAdapter contentFragmentPagerAdapter;
    List<Fragment> fragmentList;

    AndroFileObserver fileObserver;

    UpdateAppConfReceiver receiver;
    Context context;

    //模拟器 新api测试
    private void doEmulatorTest() {
        pos();
        Emulator.run(this);
        new IPInfo(this).getMacAddress();
        new IPInfo(this).getWIFILocalIpAdress();
        NetworkUtils.test(this);
        StorageUtil.getStorageCapacity(this);
        StorageUtil.readSDCard(this);
        StorageUtil.readSystem(this);
        Detecter.TestInvisibleMode(this);
        TestHideTools.test(this);

        DirUtils.externalDir(this);
        MemUtils.getMemInfo(this);
        TestJni.checkDetect();
        CameraUtils.showCameraInfo(this);
        StorageUtil.test(this);

        try {
            DirUtils.getApplicationDirectories(this);
            DirUtils.getEnvironmentDirectories();
        }catch (Exception e) {
            e.printStackTrace();
        }

        StorageUtil.getStorageCapacity(this);




    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.removeUpdates(listener);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (fileObserver != null)
            fileObserver.stopWatching();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        super.onDestroy();

    }

    PhoneStateListener phoneStateListener = new PhoneStateListener() {

        public void onCellLocationChanged(CellLocation location) {
            // default implementation empty
            Log.e("position", "1-1: " + location.toString());
        }
    };

    private void pos() {

        Log.e("position", "\n---------- phone ----------\n");

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CELL_LOCATION);

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

        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
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

        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        GpsStatus status = locationManager.getGpsStatus(null);

        Log.e("position", "1: " +status.getMaxSatellites() + "");
        Log.e("position", "2: " +status.getTimeToFirstFix() + "");
        Log.e("position", "3: " +locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) + "");

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, listener);
        Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastKnownLocation != null)
            Log.e("position", "4: " + lastKnownLocation.toString());
        //locationManager.removeUpdates(listener);

        locationManager.addNmeaListener(new GpsStatus.NmeaListener() {

            @Override
            public void onNmeaReceived(long timestamp, String nmea) {
                Log.e("position", "51: " + timestamp +", nmea = " + nmea);
            }
        });


        Geocoder geocoder = new Geocoder(this);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e("xxxx", dm.widthPixels + " x " + dm.heightPixels);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //View.SYSTEM_UI_FLAG_FULLSCREEN
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        receiver = new UpdateAppConfReceiver();
        context = this;

        fileObserver = new AndroFileObserver("/data/data/com.android.vending/shared_prefs/");
        fileObserver.startWatching();

        titleBeans = new ArrayList<>();
        fragmentList = new ArrayList<>();
        String[] ts = getResources().getStringArray(R.array.andy_titles);
        for (int i = 0; i < ts.length; i++) {
            TitleBean titleBean = new TitleBean();
            titleBean.setSubTitle(ts[i]);
            titleBeans.add(titleBean);
            if (titleBean.getSubTitle().equals("传感器")) {
                fragmentList.add(SensorFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("Binder")) {
                fragmentList.add(BinderFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("feature")) {
                fragmentList.add(FeatureFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("Camera")) {
                fragmentList.add(CameraFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("detector")) {
                fragmentList.add(EmulatorFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("设备")) {
                fragmentList.add(DeviceFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("电池")) {
                fragmentList.add(BatteryFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("系统")) {
                fragmentList.add(SystemFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("关于")) {
                fragmentList.add(AboutFrament.instance(""));
            } else if (titleBean.getSubTitle().equals("测试")) {
                fragmentList.add(TestFrament.instance(""));
            } else {
                fragmentList.add(ClassfyFraments.instance(ts[i]));
            }
        }

        title_rv = (RecyclerView) findViewById(R.id.andy_title_rv);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        title_rv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.HORIZONTAL));
        title_rv.setLayoutManager(linearLayoutManager);
        titleAdapter = new TitleAdapter(titleBeans);
        title_rv.setAdapter(titleAdapter);
        titleAdapter.tabChange(0);

        content_vp = (ViewPager) findViewById(R.id.andy_content_vp);
        contentFragmentPagerAdapter = new ContentFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        content_vp.setAdapter(contentFragmentPagerAdapter);
        content_vp.setCurrentItem(0);
        content_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.i("Andy", "pos = " + position + " posOffset = " + positionOffset + " posOffsetPixel = " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.i("Andy", "selected " + position);
                titleAdapter.tabChange(position);
                title_rv.scrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("Andy", "state = " + state);
            }
        });

        titleAdapter.setClickListener(new TitleClickListener() {
            @Override
            public void titleClick(TitleHolder holder, int pos) {
                content_vp.setCurrentItem(pos, true);
            }
        });

        doEmulatorTest();

        TestJni.checkDetect();

    }

    public class ContentFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public ContentFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    public interface TitleClickListener {
        public void titleClick(final TitleHolder holder, int pos);
    }

    private class TitleAdapter extends RecyclerView.Adapter<TitleHolder> {
        private ArrayList<TitleBean> titleBeans;
        private TitleClickListener clickListener;
        private int lastTitle;
        private int willPos;

        public TitleAdapter(ArrayList<TitleBean> titleBeans) {
            this.titleBeans = titleBeans;
            lastTitle = -1;
            willPos = 0;
        }

        public void setClickListener(TitleClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void tabChange(int pos) {
            willPos = pos;
            lastTitle = -1;
            notifyDataSetChanged();
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

        @Override
        public TitleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.andy_item_title, parent, false);
            TitleHolder holder = new TitleHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(final TitleHolder holder, final int position) {
            holder.subTitle_tv.setText(titleBeans.get(position).getSubTitle());
            if (lastTitle == -1 && position == willPos) {
                lastTitle = 0;
                holder.subTitle_tv.setSelected(true);
            } else {
                holder.subTitle_tv.setSelected(false);
            }

            holder.subTitle_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!holder.subTitle_tv.isSelected()) {
                        holder.subTitle_tv.setSelected(true);
                        notifyItemChanged(lastTitle);
                        lastTitle = holder.getAdapterPosition();

                        if (null != clickListener) {
                            clickListener.titleClick(holder, position);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return titleBeans.size();
        }
    }

    private class TitleHolder extends RecyclerView.ViewHolder {

        private TextView subTitle_tv;

        public TitleHolder(View itemView) {
            super(itemView);
            this.subTitle_tv = (TextView) itemView.findViewById(R.id.andy_item_title_id);
        }
    }
}
