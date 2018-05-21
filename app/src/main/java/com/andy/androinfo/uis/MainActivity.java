package com.andy.androinfo.uis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.androinfo.R;
import com.andy.androinfo.beans.TitleBean;
import com.andy.androinfo.utils.StorageUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleBeans = new ArrayList<>();
        fragmentList = new ArrayList<>();
        String[] ts = getResources().getStringArray(R.array.andy_titles);
        for (int i = 0; i < ts.length; i++) {
            TitleBean titleBean = new TitleBean();
            titleBean.setSubTitle(ts[i]);
            titleBeans.add(titleBean);
            fragmentList.add(ClassfyFraments.instance(ts[i]));
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
                Log.e("Andy", "pos = " + position +" posOffset = " +positionOffset +" posOffsetPixel = " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("Andy", "selected " + position);
                titleAdapter.tabChange(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("Andy", "state = "+state);
            }
        });

        titleAdapter.setClickListener(new TitleClickListener() {
            @Override
            public void titleClick(TitleHolder holder, int pos) {
                content_vp.setCurrentItem(pos, true);
            }
        });
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
}
