package com.haichecker.lib.widget.viewtoast;

import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * createTime 2017/7/19 8:01
 * <p>
 * devUser 石文平
 * <p>
 * classDetail
 */
public abstract class ToastCallBack {
    /**
     * 用户设置的View，如果设置的全屏显示，则该对象为NULL
     */
    private View currView;

    /**
     * 获取toasts
     */
    private Toasts toasts;

    public void setToasts(Toasts toasts) {
        this.toasts = toasts;
    }

    public Toasts getToasts() {
        return toasts;
    }

    public View getCurrView() {
        return currView;
    }

    public void setCurrView(View currView) {
        this.currView = currView;
    }

    /**
     * 对话框显示
     */
    abstract void onShow(ViewGroup parent, boolean isShowParent, long time, DialogInterface.OnShowListener onShowListener);

    /**
     * 对话框隐藏
     */
    abstract void onHide(long time, DialogInterface.OnDismissListener onDismissListener);

    /**
     * 开始初始化
     *
     * @param rootView 需要添加的View
     */
    abstract void onCreate(View rootView);

    /**
     * 当前View
     *
     * @return 返回内容View
     */
    abstract View getView();

    /**
     * 状态改变
     *
     * @param style 当前状态
     */
    abstract void styleChange(Style style);

}
