package com.andy.androinfo.hook;

import android.view.View;

import com.andy.androinfo.utils.LogUtil;

/**
 * Created by Administrator on 2018/6/20.
 */

public class HookOnClickListener implements View.OnClickListener {

    private View.OnClickListener origin;

    public HookOnClickListener(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        LogUtil.e("hookutil", "before click");
        if (origin != null)
            origin.onClick(v);
        LogUtil.e("hookutil", "after click");
    }
}
