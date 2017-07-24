package com.haichecker.lib.widget.viewtoast;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haichecker.lib.R;

import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_BAR;
import static com.haichecker.lib.widget.viewtoast.Style.STYLE_PROGRESS_CIR;

/**
 * createTime 2017/7/24 10:59
 * <p>
 * devUser 石文平
 * <p>
 * classDetail
 */
public class DToast extends ToastCallBack {
    private Context mContext;

    public DToast(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 显示的进度条
     */
    private ProgressBar mProgressBarH, mProgressBarO;
    /**
     * 显示的当前进度
     */
    private int progress;
    /**
     * 显示的总进度
     */
    private int max;
    /**
     * 文本对象
     */
    private TextView mTextView;
    /**
     * 显示类型
     */
    private Style style;
    /**
     * 文本内容
     */
    private String text;
    /**
     * 文本颜色
     */
    private int textColor = Color.WHITE;
    /**
     * 背景颜色
     */
    private int color = 0x000000;

    @Override
    void onShow(ViewGroup parent, boolean isShowParent, long time, DialogInterface.OnShowListener onShowListener) {

    }

    @Override
    void onHide(long time, DialogInterface.OnDismissListener onDismissListener) {

    }

    @Override
    void onCreate(View rootView) {
        mProgressBarH = (ProgressBar) getCurrView().findViewById(R.id.progressh);
        mProgressBarH.setMax(max);
        mProgressBarH.setProgress(progress);
        mProgressBarH.setVisibility(View.GONE);
        mProgressBarO = (ProgressBar) getCurrView().findViewById(R.id.progressc);
        mProgressBarO.setMax(max);
        mProgressBarO.setProgress(progress);
        mProgressBarO.setVisibility(View.GONE);
        mTextView = (TextView) getCurrView().findViewById(R.id.text);
        mTextView.setText(text);
        mTextView.setTextColor(textColor);
    }

    @Override
    View getView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return layoutInflater.inflate(R.layout.defalut_toast_view, null, false);
    }

    @Override
    void styleChange(Style style) {
        switch (style) {
            case STYLE_PROGRESS_BAR:
            case STYLE_PROGRESS_CIR:
                if (mProgressBarH != null && style == STYLE_PROGRESS_BAR)
                    mProgressBarH.setVisibility(View.VISIBLE);

                if (mProgressBarO != null && style == STYLE_PROGRESS_CIR)
                    mProgressBarO.setVisibility(View.VISIBLE);
                break;
            case STYLE_TEXT:
                if (mProgressBarH != null)
                    mProgressBarH.setVisibility(View.GONE);

                if (mProgressBarO != null)
                    mProgressBarO.setVisibility(View.GONE);
                break;
        }
    }
}
