package com.andy.androinfo.uis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.beans.TitleBean;
import com.andy.androinfo.emulator.Detecter;
import com.andy.androinfo.reflect.ReflectUtil;
import com.andy.androinfo.utils.*;

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
    BatteryBcr batteryBcr;

    AndroFileObserver fileObserver;

    private void doEmulatorTest() {
        Emulator.run(this);
        new IPInfo(this).getMacAddress();
        new IPInfo(this).getWIFILocalIpAdress();
    }

    @Override
    public void onResume() {
        super.onResume();
        ReflectUtil.doReflect(this.getClassLoader());
        ReflectUtil.doReflect2();
        XmlUtils.getAndroidId();
    }

    @Override
    public void onDestroy() {
        if (batteryBcr != null)
            unregisterReceiver(batteryBcr);
        if (fileObserver != null)
            fileObserver.stopWatching();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.e("xxxx", dm.widthPixels + " x " + dm.heightPixels);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        HasFeature.showHasFeature(this);

        fileObserver = new AndroFileObserver("/data/data/com.android.vending/shared_prefs/");
        fileObserver.startWatching();

        batteryBcr = new BatteryBcr();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBcr, filter);

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

        StorageUtil.getStorageCapacity(this);

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
        DirUtils.externalDir(this);
        MemUtils.getMemInfo(this);
        
        try {
            DirUtils.getApplicationDirectories(this);
            DirUtils.getEnvironmentDirectories();
        }catch (Exception e) {
            e.printStackTrace();
        }

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


    private class BatteryBcr extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                String action = intent.getAction();
                switch (action) {
                    case Intent.ACTION_BATTERY_CHANGED:

                        Bundle bundle = intent.getExtras();
                        Log.e("Andy", "battery change = " + intent.toString() + " " + bundle.toString());

                        Log.e("Andy_battery", "battery level = " + intent.getIntExtra("level", 0));
                        Log.e("Andy_battery", "battery scale = " + intent.getIntExtra("scale", 0));
                        break;
                }
            }

        }
    }
}
