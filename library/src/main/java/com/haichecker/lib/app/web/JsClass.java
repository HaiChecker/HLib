package com.haichecker.lib.app.web;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.webkit.JavascriptInterface;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-7 18:49
 */

public class JsClass {
    // 上下文
    private Context mContext;

    /**
     * @param mContext 上下文
     */
    public JsClass(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void alert(final String content) {
        new Handler(mContext.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(mContext);
                dialog.setTitle(content);
                dialog.show();
            }
        });
    }

    @JavascriptInterface
    public String getStr(String str) {
        return str;
    }


    public static interface JSCallBack {

        void startActivity();

        void onBack();

        void onNext();


    }

    public static final String web = "<html>\n" +
            "\n" +
            "<body>\n" +
            "<input onclick=\"window.hc.alert('JS Test')\" type=\"submit\"></input>\n" +
            "</body>\n" +
            "\n" +
            "</html>";
}
