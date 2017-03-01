package com.haichecker.lib.widget.settinglist.item;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-6 16:28
 */

public class ItemUtils {
    public static RelativeLayout.LayoutParams getTopLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams getTopLayoutParams(int ml) {
        RelativeLayout.LayoutParams layoutParams = getTopLayoutParams();
        layoutParams.leftMargin = ml;
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams getBottomLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        return layoutParams;
    }

    public static RelativeLayout.LayoutParams getBottomLayoutParams(int ml) {
        RelativeLayout.LayoutParams layoutParams = getBottomLayoutParams();
        layoutParams.leftMargin = ml;
        return layoutParams;
    }

}
