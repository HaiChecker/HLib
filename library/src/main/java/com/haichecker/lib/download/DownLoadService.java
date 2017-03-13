package com.haichecker.lib.download;

import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.haichecker.lib.download.db.DownLoadContentProvider;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-10 13:09
 */
public class DownLoadService extends IntentService {

    /**
     * 开始下载
     */
    public static final String START = "com.haichecker.lib.update.START";

    /**
     * 暂停下载
     */
    public static final String SUSPEND = "com.haichecker.lib.update.SUSPEND";

    /**
     * 继续下载
     */
    public static final String CONTINUE = "com.haichecker.lib.update.CONTINUE";
    /**
     * 停止下载
     */
    public static final String STOP = "com.haichecker.lib.update.STOP";

    /**
     * 停止下载，并删除下载的文件
     */
    public static final String STOP_AND_DELTE = "com.haichecker.lib.update.STOP_AND_DELTE";


    /**
     * 开始下载文件
     *
     * @param url        文件url地址
     * @param savePath   文件保存路径
     * @param isContinue 如果本文件存在，是否重新下载，或继续下载，true 继续下载
     */
    public static int startDownLoad(String url, String savePath, boolean isContinue, Context mContext) {
        ContentValues values = new ContentValues();
        values.put("downLoadUrl", url);
        values.put("savePath", savePath);
        values.put("startDownLoadTime", System.currentTimeMillis());
        values.put("currState", 5);
        Uri uri = mContext.getContentResolver().insert(Uri.parse("content://" + DownLoadContentProvider.AUTHORITY + "/download"), values);
        int id = uri == null || uri.getPathSegments() == null ? -1 : Integer.parseInt(uri.getPathSegments().get(0));


        Intent intent = new Intent(mContext, DownLoadService.class);
        intent.putExtra("_id", id);
        mContext.startService(intent);
        return id;
    }


    public static void continueDownLoad(int id, Context mContext) {

    }


    public DownLoadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    @Override
    public void onCreate() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CONTINUE);
        intentFilter.addAction(STOP);
        intentFilter.addAction(SUSPEND);
        intentFilter.addAction(STOP_AND_DELTE);
        intentFilter.addAction(START);
        registerReceiver(br, intentFilter);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(br);
        super.onDestroy();
    }

    /**
     * 用于监听下载时的各种操作
     */
    private BroadcastReceiver br = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

}
