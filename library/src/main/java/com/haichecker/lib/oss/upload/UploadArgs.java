package com.haichecker.lib.oss.upload;

import android.content.Context;

import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.haichecker.lib.oss.config.OSSConfig;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-5-8 15:34
 */

public class UploadArgs {
    private OSS oss;
    private String key;
    private String filePath;
    private Object tag;
    private UploadListener uploadListener;
    private OSSAsyncTask task;
    private OSSCredentialProvider credentialProvider;

    /**
     * 初始化OSS参数
     *
     * @param mContext 上下文
     */
    public UploadArgs(Context mContext) {
        credentialProvider = new OSSPlainTextAKSKCredentialProvider(OSSConfig.ACCESSKEYID, OSSConfig.SECRETACCESSKEY);
        oss = new OSSClient(mContext, OSSConfig.ENDPOINT, credentialProvider);
    }

    public OSS getOss() {
        return oss;
    }

    public void setOss(OSS oss) {
        this.oss = oss;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public UploadListener getUploadListener() {
        return uploadListener;
    }

    public void setUploadListener(UploadListener uploadListener) {
        this.uploadListener = uploadListener;
    }

    public OSSAsyncTask getTask() {
        return task;
    }

    public void setTask(OSSAsyncTask task) {
        this.task = task;
    }

    public OSSCredentialProvider getCredentialProvider() {
        return credentialProvider;
    }

    public void setCredentialProvider(OSSCredentialProvider credentialProvider) {
        this.credentialProvider = credentialProvider;
    }
}
