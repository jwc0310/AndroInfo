package com.andy.androinfo.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 懒加载
 * Created by Administrator on 2018/5/14.
 */

public abstract class AndyBaseFragment extends Fragment {

    private static final String TAG  = AndyBaseActivity.class.getName();
    protected static final String TYPE = "type";
    protected View mRootView;
    protected Context mContext;
    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;
    protected String content;

    //-----------------------------------------------------------------------
    private void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst)
            return;

        initData();
        isFirst = false;
    }

    //在onActivityCreated中调用的方法，可以用来进行初始化操作
    protected abstract void initPrepare();

    //fragment被设置为不可见时调用
    protected abstract void onInvisible();

    //这里获取数据，刷新界面
    protected abstract void initData();

    //
    protected abstract void onCreateInit(@Nullable Bundle savedInstanceState);

    /**
     * 初始化布局，请不要把耗时操作放在这个方法里，这个方法用来提供一个
     * 基本的布局而非一个完整的布局，以免ViewPager预加载消耗大量的资源。
     */
    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container,
                                     @Nullable Bundle savedInstanceState);

    //-----------------------------------------------------------------------

    @Override
    public void onAttach(Context context) {
        Log.i(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            content = savedInstanceState.getString(TYPE);
        }
        mContext = getActivity();
        onCreateInit(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        if (null == mRootView) {
            mRootView = initView(inflater, container, savedInstanceState);
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();

        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        initPrepare();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.i(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TYPE, content);
    }

    @Override
    public boolean getUserVisibleHint() {
        Log.i(TAG, "getUserVisibleHint");
        return super.getUserVisibleHint();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG, "setUserVisibleHint:" + isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }
}
