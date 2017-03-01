package com.haichecker.lib.widget.settinglist.item;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.common.base.Preconditions;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-5 19:52
 */

public abstract class DefalutTextItem extends BaseItem {


    /**
     * titleStr
     */
    private String title;

    /**
     * titleText
     */
    private TextView titleText;

    /**
     * init {@link DefalutTextItem}
     *
     * @param context context
     * @param title   string title
     */
    public DefalutTextItem(Context context, String title) {
        super(context);
        Preconditions.checkNotNull(context);
        this.title = title;
    }

    /**
     * init {@link DefalutTextItem}
     *
     * @param context context
     * @param title   id string title
     */
    public DefalutTextItem(Context context, @StringRes int title) {
        super(context);
        Preconditions.checkNotNull(context);
        this.title = getContext().getString(title);

    }

    public DefalutTextItem(Context context) {
        super(context);
        Preconditions.checkNotNull(context);
    }

    @Override
    public void init() {
        if (titleText == null) {
            titleText = new TextView(getContext());
        }
        getRootView().addView(titleText, getDefalutLayoutParams());
    }

    /**
     *
     * @param layoutParams
     */
    public void setLayoutParams(LinearLayout.LayoutParams layoutParams) {
        Preconditions.checkNotNull(layoutParams);
        Preconditions.checkNotNull(titleText);
        getRootView().removeView(titleText);
        getRootView().addView(titleText, layoutParams);
    }

    /**
     * get defalut layoutParams
     *
     * @return defalut layoutParams
     */
    public LinearLayout.LayoutParams getDefalutLayoutParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        return layoutParams;
    }

    /**
     * @return titleText
     */
    public TextView getTitleText() {
        Preconditions.checkNotNull(titleText);
        return titleText;
    }

    /**
     * set titletext title string
     *
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
        if (titleText != null) {
            titleText.setText(title);
        }
    }

    /**
     * set titletext title id string
     *
     * @param title title
     */
    public void setTitle(@StringRes int title) {
        Preconditions.checkNotNull(getContext());
        this.title = getContext().getString(title);
        if (titleText != null) {
            titleText.setText(this.title);
        }
    }
}
