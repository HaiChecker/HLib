package com.haichecker.lib.mvp.base;

import android.app.Activity;
import android.content.Context;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 10:46
 */

public interface IBaseView<P> {
    /**
     * 设置presenter对象
     * @param presenter presenter对象
     */
    void setPresenter(P presenter);

    /**
     * 获取Presenter对象
     * @return  返回Presenter对象
     */
    P getPresenter();

    /**
     * 获取上下文
     * @return  返回上下文
     */
    Context getContext();

    /**
     * 某些界面会需要返回 Activity
     * @return 返回的Activity
     */
    Activity getActivity();
}
