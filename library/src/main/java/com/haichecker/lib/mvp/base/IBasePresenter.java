package com.haichecker.lib.mvp.base;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 10:45
 */

public interface IBasePresenter {

    /**
     * 界面关闭时回调
     */
    void destroy();

    /**
     * 界面开启时回调
     */
    void start();

    AbsModel<? super IBasePresenter> getModel();

    IBasePresenter getPresenter();

    void setModel(AbsModel<? super IBasePresenter> model);
}
