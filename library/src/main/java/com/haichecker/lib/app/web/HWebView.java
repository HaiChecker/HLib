package com.haichecker.lib.app.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-7 18:47
 */

public class HWebView extends WebView {
    public HWebView(Context context) {
        super(context);
    }

    public HWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
        getSettings().setJavaScriptEnabled(true);
        addJavascriptInterface(new JsClass(getContext()), "hc");
    }

    public HWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


}
