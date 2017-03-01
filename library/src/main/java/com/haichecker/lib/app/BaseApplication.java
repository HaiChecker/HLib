package com.haichecker.lib.app;

import android.app.Application;

//import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by root on 16-11-11.
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
//        CrashReport.initCrashReport(this, buglyKey(), true);
        super.onCreate();
    }
    /**
     * 返回腾讯ＢｕｇｌｙＫｅｙ
     *
     * @return
     */
    public abstract String buglyKey();
}
