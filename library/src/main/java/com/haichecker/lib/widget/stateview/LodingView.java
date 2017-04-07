package com.haichecker.lib.widget.stateview;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.IllegalFormatException;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-1 09:46
 */

public class LodingView extends FrameLayout {

    private View messageView;

    private View contentView;

    /**
     * 设置顶层布局
     *
     * @param layout
     */
    public void setMessageView(@LayoutRes int layout) {
        messageView = LayoutInflater.from(getContext()).inflate(layout, null);
    }

    /**
     * 设置顶层布局
     *
     * @param messageView
     */
    public void setMessageView(View messageView) {
        this.messageView = messageView;
    }

    public LodingView(@NonNull Context context) {
        super(context);
    }

    public LodingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LodingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showMessageView() {
        if (messageView != null) {
            messageView.setVisibility(VISIBLE);
            if (contentView != null)
                contentView.setVisibility(GONE);
        } else {
            throw new IllegalArgumentException("没有设置MessageView");
        }
    }

    public void showContentView() {
        if (contentView != null) {
            contentView.setVisibility(VISIBLE);
            if (messageView != null) {
                messageView.setVisibility(GONE);
            }
        } else {
            throw new IllegalArgumentException("没有设置ContentView");
        }
    }

    public View getMessageView() {
        return messageView;
    }

    /**
     * 提示Ｖｉｅｗ是否显示
     *
     * @return
     */
    public boolean isShowMessageing() {
        return !(messageView == null || messageView.getVisibility() == GONE || messageView.getVisibility() == INVISIBLE);
    }

    /**
     * 内容是否显示
     *
     * @return
     */
    public boolean isShowContenting() {
        return !(contentView == null || contentView.getVisibility() == GONE || contentView.getVisibility() == INVISIBLE);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        if (getChildCount() == 2) {
            messageView = getChildAt(1);
            messageView.setVisibility(VISIBLE);
            contentView.setVisibility(GONE);
        } else {
            contentView.setVisibility(VISIBLE);
        }
    }
}
