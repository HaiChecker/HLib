package com.haichecker.lib.widget.settinglist;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.common.base.Preconditions;
import com.haichecker.lib.widget.settinglist.item.BaseLine;
import com.haichecker.lib.widget.settinglist.item.DefalutTextItem;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-1-9 11:32
 */

public abstract class EditItem extends CustomItem {

    private EditText editText;


    public EditItem(Context context, String title) {
        super(context, title);
    }

    public EditItem(Context context, @StringRes int title) {
        super(context, title);
    }

    public EditItem(Context context) {
        super(context);
    }


    @Override
    public void init() {
        super.init();
        if (editText == null) {
            editText = new EditText(getContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                editText.setBackground(null);
            } else {
                editText.setBackgroundDrawable(null);
            }
            rootLayout.addView(editText, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }

    public EditText getEditText() {
        return Preconditions.checkNotNull(editText);
    }
}
