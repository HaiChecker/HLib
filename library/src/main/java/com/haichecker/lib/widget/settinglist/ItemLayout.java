package com.haichecker.lib.widget.settinglist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.R;
import com.haichecker.lib.widget.settinglist.item.BaseItem;

import java.util.ArrayList;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-6 15:39
 */

public abstract class ItemLayout extends LinearLayout {

    private View footerView;
    private LinearLayout.LayoutParams footerLayoutParams;

    private LinearLayout headerView;

    private int p = -1;

    public void setFooterView(View footerView, LinearLayout.LayoutParams layoutParams) {
        this.footerLayoutParams = layoutParams;
        this.footerView = footerView;
    }

    public <I extends BaseItem> void addItem(I item) {
        p++;
        addView(getView(this, item, p).getView(), new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item.getHeight()));
    }

    public ItemLayout(Context context) {
        super(context);
        init();
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LinearLayout getRootHeaderView() {
        return Preconditions.checkNotNull(headerView);
    }

    private void init() {
        headerView = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams.topMargin = 10;
//        layoutParams.bottomMargin = 10;
        headerView.setPadding(0, 10, 0, 10);
        headerView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.test_bfbfb));
        addView(headerView, layoutParams);
        setHeadView(headerView);
    }

    public ItemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract <B extends BaseItem> B getView(ViewGroup group, B itemView, int p);

    public abstract void setHeadView(LinearLayout rootHeadView);

}
