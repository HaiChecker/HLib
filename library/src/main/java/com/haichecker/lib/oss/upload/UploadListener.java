package com.haichecker.lib.oss.upload;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-5-8 15:33
 */

public abstract class UploadListener {
    private UploadArgs args;

    public void setArgs(UploadArgs args) {
        this.args = args;
    }

    public UploadArgs getArgs() {
        return args;
    }


    /**
     * 开始上传
     *
     * @param fileSize 上传文件的大小
     */
    public abstract void start(long fileSize);

    /**
     * 上传文件成功
     *
     * @param args 文件的参数 {@link UploadArgs}
     */
    public abstract void success(UploadArgs args);

    /**
     * 上传文件失败
     *
     * @param error 失败原因
     */
    public abstract void error(String error);

    /**
     * 上传文件进度
     *
     * @param countSize 文件总大小
     * @param currSize  文件当前上传的大小
     */
    public abstract void progress(long countSize, long currSize);
}
