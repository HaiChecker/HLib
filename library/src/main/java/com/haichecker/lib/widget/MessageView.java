package com.haichecker.lib.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-17 14:55
 */

public class MessageView extends FrameLayout {
    private TextView messageText;


    public MessageView(@NonNull Context context) {
        super(context);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
