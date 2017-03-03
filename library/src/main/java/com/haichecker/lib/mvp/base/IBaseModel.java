package com.haichecker.lib.mvp.base;

import android.content.Context;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 11:09
 */

public interface IBaseModel {


    /**
     * 界面关闭时回调
     */
    void destroy();

    /**
     * 界面开启时回调
     */
    void start();

    /**
     * 获取Context
     *
     * @return Context
     */
    Context getContext();

}
