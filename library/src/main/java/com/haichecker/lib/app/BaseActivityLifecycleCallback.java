package com.haichecker.lib.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * TODO:需要继续研究实现
 * <p>
 * 封装的BaseActivity，添加Databinding支持
 * <p>
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-5-15 16:57
 */

public class BaseActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {


    /**
     * 更改状态栏
     */
    private SystemBarTintManager tintManager;

    /**
     * Activity创建
     *
     * @param activity           activity
     * @param savedInstanceState 状态
     */
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }


    /**
     * onStart
     *
     * @param activity activity
     */
    @Override
    public void onActivityStarted(Activity activity) {

    }

    /**
     * onResume
     *
     * @param activity activity
     */
    @Override
    public void onActivityResumed(Activity activity) {

    }

    /**
     * Pause
     *
     * @param activity activity
     */
    @Override
    public void onActivityPaused(Activity activity) {

    }

    /**
     * onStop
     *
     * @param activity activity
     */
    @Override
    public void onActivityStopped(Activity activity) {

    }

    /**
     * saave
     *
     * @param activity
     * @param outState
     */
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    /**
     * destroed
     *
     * @param activity
     */
    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
