package com.haichecker.lib.widget.dialog.hdialog;

import android.content.Context;
import android.widget.TextView;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-4-21 10:02
 */

public class MessageHDialog extends HDialog {
    //消息内容
    private String message;

//    private TextView

    public MessageHDialog(Context context) {
        super(context);
    }

    /**
     * 设置消息
     *
     * @param message 消息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
