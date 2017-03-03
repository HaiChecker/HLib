package com.haichecker.lib.setting;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 16-11-11.
 * <p>
 * 设置相关
 */

public class SettingInstance {
    private static SettingInstance instance;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences sharedPreferencesSetting;
    private final String ShareFileName = "HaiCheckerFile";

    public static SettingInstance getInstance(Context mContext) {
        if (instance == null || instance.getContext() == null)
            instance = new SettingInstance(mContext);
        return instance;
    }

    public SettingInstance(Context mContext) {
        this.context = mContext;
    }

    /**
     * 获取默认
     *
     * @return  返回SharedPreferences对象
     */
    public SharedPreferences getSharedPreferences() {
        if (sharedPreferencesSetting == null)
            sharedPreferencesSetting = context.getSharedPreferences(getShareFileName(), Context.MODE_PRIVATE);
        return sharedPreferencesSetting;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 设置存储名称
     *
     * @param name 存储的名称
     */
    public void setShareFileName(String name) {
        getSharedPreferencesOnlineFile().edit().putString(ShareFileName, name).apply();
    }

    /**
     * 获取存储名字
     *
     * @return  返回当前名称的对象
     */
    public String getShareFileName() {
        return getSharedPreferencesOnlineFile().getString(ShareFileName, "HaiCheckerFile");
    }

    public SharedPreferences getSharedPreferencesOnlineFile() {
        if (sharedPreferences != null) {
            return sharedPreferences;
        } else {
            sharedPreferences = context.getSharedPreferences("HaiCheckerFile", Context.MODE_PRIVATE);
            return sharedPreferences;
        }
    }
}
