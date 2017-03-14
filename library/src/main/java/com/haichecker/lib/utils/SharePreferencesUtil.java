package com.haichecker.lib.utils;

import android.content.Context;

import com.haichecker.lib.setting.SettingInstance;


/**
 * Created by root on 16-11-11.
 */

public class SharePreferencesUtil {

    public static boolean put(Context mContext, String key, String value) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().edit().putString(key, value).commit();
    }

    public static boolean put(Context mContext, String key, int value) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().edit().putInt(key, value).commit();
    }

    public static boolean put(Context mContext, String key, float value) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().edit().putFloat(key, value).commit();
    }

    public static boolean put(Context mContext, String key, boolean value) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().edit().putBoolean(key, value).commit();
    }

    public static boolean put(Context mContext, String key, long value) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().edit().putLong(key, value).commit();
    }

    public static int get(Context mContext, String key, int def) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().getInt(key, def);
    }

    public static String get(Context mContext, String key, String def) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().getString(key, def);
    }

    public static long get(Context mContext, String key, long def) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().getLong(key, def);
    }

    public static boolean get(Context mContext, String key, boolean def) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().getBoolean(key, def);
    }

    public static float get(Context mContext, String key, float def) {
        return SettingInstance.getInstance(mContext).getSharedPreferences().getFloat(key, def);
    }
}
