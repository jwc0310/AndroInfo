package com.andy.androinfo.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by Administrator on 2018/5/14.
 */

public class BasePagerAdapter extends PagerAdapter {

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
