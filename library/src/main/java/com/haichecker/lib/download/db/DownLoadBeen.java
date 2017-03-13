package com.haichecker.lib.download.db;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-10 13:31
 */

public class DownLoadBeen {
    //下载的Url地址
    private String downLoadUrl;
    //保存文件的地址
    private String savePath;
    //下载的Id
    private int id;
    //开始下载的时间
    private long startDownLoadTime;
    //结束下载的时间
    private long endDownLoadTime;
    //文件大小
    private long fileSize;
    //文件状态（1.正在下载、2.下载完成、3.下载失败、4、下载暂停、5.未下载）
    private int currState;

    public int getCurrState() {
        return currState;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public long getStartDownLoadTime() {
        return startDownLoadTime;
    }

    public void setStartDownLoadTime(long startDownLoadTime) {
        this.startDownLoadTime = startDownLoadTime;
    }

    public long getEndDownLoadTime() {
        return endDownLoadTime;
    }

    public void setEndDownLoadTime(long endDownLoadTime) {
        this.endDownLoadTime = endDownLoadTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
