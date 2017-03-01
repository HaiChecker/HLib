package com.haichecker.lib.widget.settinglist;

import android.content.Context;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.widget.settinglist.item.DefalutTextItem;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-9 11:41
 */

public abstract class CustomItem extends DefalutTextItem {

    protected LinearLayout rootLayout;

    public CustomItem(Context context, String title) {
        super(context, title);
    }

    public CustomItem(Context context, @StringRes int title) {
        super(context, title);
    }

    public CustomItem(Context context) {
        super(context);
    }

    @Override
    public void init() {
        super.init();
        if (rootLayout == null) {
            rootLayout = new LinearLayout(getContext());
            rootLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            getRootView().setOrientation(LinearLayout.HORIZONTAL);
            getRootView().addView(rootLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public LinearLayout getRightCustomView() {
        return Preconditions.checkNotNull(rootLayout);
    }
}
