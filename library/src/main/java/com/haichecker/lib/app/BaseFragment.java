package com.haichecker.lib.app;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.setting.SettingInstance;

/**
 * @param <T>
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    /**
     * fragmentBinding
     */
    public T fragmentBinding;
    /**
     * 监听数据变化对象
     */
    private SharedPreferences sharedPreferences;
    /**
     * 最顶层的View，用于加载状态布局所用
     */
    @Nullable
    private ViewGroup rootViewGroup;
    /**
     * 空布局
     */
    private View emptyView;
    /**
     * 用户的布局View
     */
    @Nullable
    private View userView;

    @IdRes
    private int emptyId = android.R.id.empty;

    private boolean emptyIsShow = false;

    @Nullable
    @Override
    public View getView() {
        return userView;
    }

    /**
     * 设置状态布局
     *
     * @param emptyView 空布局View
     */
    public void setEmptyView(@Nullable View emptyView) {
        this.emptyView = emptyView;
        setEmptyViewId(emptyView);
    }

    public void setEmptyView(@LayoutRes int emptyViewRes) {
        View tempEmptyView = LayoutInflater.from(getActivity()).inflate(emptyViewRes, rootViewGroup, false);
        setEmptyView(tempEmptyView);
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden) {
//            if (emptyIsShow) {
//                openEmptyView();
//            } else {
//                dismissEmptyView();
//            }
//        }
//    }


    /**
     * 设置状态布局Id
     *
     * @param emptyView 状态布局
     */
    private void setEmptyViewId(View emptyView) {
        if (emptyView.getId() == 0) {
            emptyView.setId(emptyId);
        } else {
            emptyId = emptyView.getId();
        }
    }

    /**
     * <p>判断是否显示状态布局中...</p>
     *
     * @return 返回状态布局是否现实  true-显示  false-没有现实
     */
    public boolean isShowingEmptyView() {
        Preconditions.checkNotNull(rootViewGroup);
        View v = rootViewGroup.findViewById(emptyId);
        return (v != null && v.getVisibility() == View.VISIBLE);
    }

    /**
     * <p>开启状态布局</p>
     */
    public void openEmptyView() {
        if (isHidden()) {
            emptyIsShow = true;
            return;
        }
        if (rootViewGroup == null || emptyView == null || userView == null) {
            return;
        }
        rootViewGroup.addView(emptyView, new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
        userView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        //把布局移动到顶部
        emptyView.bringToFront();
    }

    /**
     * <p>获取当前状态布局</p>
     *
     * @return 当前状态布局
     */
    public View getEmptyView() {
        return emptyView;
    }

    /**
     * 返回最顶层的
     *
     * @return
     */
    @Nullable
    public ViewGroup getRootViewGroup() {
        return rootViewGroup;
    }

    /**
     * <p>关闭状态布局</p>
     */
    public void dismissEmptyView() {
        if (isHidden()) {
            emptyIsShow = false;
            return;
        }
        if (rootViewGroup != null) {
            rootViewGroup.removeView(emptyView);
        }
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
        if (userView != null) {
            userView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 初始化
     */
    private void init() {
        rootViewGroup = new FrameLayout(getActivity());
        ViewGroup.LayoutParams layoutParams = rootViewGroup.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        rootViewGroup.setLayoutParams(layoutParams);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isOpenSharePreferenceChangeListener()) {
            sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        }
    }

    @Override
    public void onDestroy() {
        if (isOpenSharePreferenceChangeListener()) {
            sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferenceChangeListener);
        }
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isOpenSharePreferenceChangeListener()) {
            sharedPreferences = SettingInstance.getInstance(getActivity()).getSharedPreferences();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        fragmentBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        userView = fragmentBinding.getRoot();
//        userView = inflater.inflate(getLayoutRes(), container, false);
//        fragmentBinding = DataBindingUtil.bind(userView);
        // TODO 添加状态，在ViewPager下无界面
//        Preconditions.checkNotNull(rootViewGroup);
//        Preconditions.checkNotNull(userView);
////
//        rootViewGroup.addView(userView);
//        if (emptyView != null) {
//            emptyView.setVisibility(View.GONE);
//            rootViewGroup.addView(emptyView);
//        }
        return userView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @LayoutRes
    public abstract int getLayoutRes();


    /**
     * 是否开启监听
     *
     * @return 返回是否监听
     */
    public boolean isOpenSharePreferenceChangeListener() {
        return false;
    }

    /**
     * 收到监听的消息变化
     *
     * @param key
     * @param sharedPreferences
     */
    public void onSharedPreferenceChangeds(String key, SharedPreferences sharedPreferences) {

    }

    private SharedPreferences.OnSharedPreferenceChangeListener sharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            onSharedPreferenceChangeds(key, sharedPreferences);
        }
    };
}
