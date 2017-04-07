package com.haichecker.lib.app;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import java.util.ArrayList;
import java.util.List;

//import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by root on 16-11-11.
 */

public abstract class BaseApplication extends MultiDexApplication {
    private List<BaseActivity> allActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        allActivity = new ArrayList<>();
    }

    public List<BaseActivity> getAllActivity() {
        return allActivity;
    }
}
