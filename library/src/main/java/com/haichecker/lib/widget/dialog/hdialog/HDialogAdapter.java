package com.haichecker.lib.widget.dialog.hdialog;

import android.view.View;
import android.widget.Button;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-26 12:22
 */

public abstract class HDialogAdapter {
    //执行item，可以设置文字颜色，大小，点击背景 等等
    public abstract void itemOnView(Button view, int index);

    //Item的个数
    public abstract int itemCount();

    /**
     * 确定与取消按钮
     *
     * @param b    按钮
     * @param type 1.取消 2.确定
     */
    public abstract void button(Button b, int type);
}
