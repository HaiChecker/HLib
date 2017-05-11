package com.haichecker.lib.widget.dialog.hdialog;

import android.view.View;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-26 12:20
 */

public interface OnHDialogListener {
    /**
     * 点击事件
     *
     * @param view
     * @param index
     */
    void itemClick(View view, int index);

    void itenLongClick(View view, int index);

    //确认按钮被点击
    void okClick();

    //取消按钮被点击
    void cancelClick();

}
