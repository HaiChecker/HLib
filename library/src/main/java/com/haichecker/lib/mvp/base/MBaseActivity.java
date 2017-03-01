package com.haichecker.lib.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.haichecker.lib.app.BaseActivity;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 10:48
 */

public abstract class MBaseActivity<T extends ViewDataBinding, P extends IBasePresenter> extends BaseActivity<T> implements IBaseView<P> {
    private P presenter;

    @Override
    public void setPresenter(P presenter) {
        /**
         * 非空判断
         */
        this.presenter = com.google.common.base.Preconditions.checkNotNull(presenter);
    }

    @Override
    public P getPresenter() {
        /**
         * 非空判断
         */
        return com.google.common.base.Preconditions.checkNotNull(presenter);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        com.google.common.base.Preconditions.checkNotNull(presenter).destroy();
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.google.common.base.Preconditions.checkNotNull(presenter).start();
    }
}
