package com.haichecker.lib.oss.upload;

import android.content.Context;
import android.content.res.Resources;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.haichecker.lib.oss.config.OSSConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * OSS文件上传封装类
 * <p>
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-5-8 15:29
 */

public class OSSUploadManager {

    /**
     * Key通过Rxjava获取
     *
     * @param context        上下文 初始化oss
     * @param keys           key
     * @param filePath       文件路径
     * @param uploadTag      上传tag
     * @param uploadListener 上传回调
     */
    public static void upload(final Context context, final Observable<String> keys, final String filePath, final Object uploadTag, final UploadListener uploadListener) {
        keys.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (uploadListener != null)
                            uploadListener.error(e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        upload(context, s, filePath, uploadTag, uploadListener);
                    }
                });
    }


    /**
     * 上传文件
     *
     * @param context   上下文，用于初始化OSS的
     * @param uploadKey 上传文件需要的Key，可通过服务器返回，也可以自己定义
     * @param filePath  上传的文件
     * @param uploadTag 当前上传的文件标识
     * @param listener  上传文件的回调
     */
    public static void upload(final Context context, final String uploadKey, final String filePath, final Object uploadTag, final UploadListener listener) {
        Observable.create(new Observable.OnSubscribe<UploadArgs>() {
            @Override
            public void call(final Subscriber<? super UploadArgs> subscriber) {
                subscriber.onStart();
                final UploadArgs args = new UploadArgs(context);
                final File file = new File(filePath);
                if (file.isDirectory()) {
                    listener.error("file is directory");
                    return;
                }
                args.setFilePath(filePath);
                args.setTag(uploadTag);
                args.setKey(uploadKey);
                args.setUploadListener(listener);
                listener.setArgs(args);
                subscriber.onStart();
                listener.start(file.length());
                //
                PutObjectRequest put = new PutObjectRequest(OSSConfig.BUCKETNAME, args.getKey(), filePath);
                //设置上传回调
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest putObjectRequest, long l, long l1) {
                        if (args.getUploadListener() != null) {
                            args.getUploadListener().progress(file.length(), l1);
                            subscriber.onCompleted();
                        }
                    }
                });
                OSSAsyncTask task = args.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                        if (args.getUploadListener() != null) {
                            args.getUploadListener().success(args);
                        }
                        subscriber.onCompleted();
                    }

                    @Override
                    public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1) {
                        if (args.getUploadListener() != null) {
                            if (e != null) {
                                args.getUploadListener().error(e.getMessage());
                            } else if (e1 != null) {
                                args.getUploadListener().error(e1.getMessage());
                            } else {
                                args.getUploadListener().error("未知错误");
                            }
                        }
                        subscriber.onError(e == null ? e1 : e);
                        subscriber.onCompleted();
                    }
                });
                args.setTask(task);
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<UploadArgs>() {
                    @Override
                    public void call(UploadArgs args) {

                    }
                });
    }
}
