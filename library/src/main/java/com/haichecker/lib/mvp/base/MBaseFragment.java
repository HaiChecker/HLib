package com.haichecker.lib.mvp.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.google.common.base.Preconditions;
import com.haichecker.lib.app.BaseFragment;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 18:18
 */

public abstract class MBaseFragment<T extends ViewDataBinding, P extends IBasePresenter> extends BaseFragment<T> implements IBaseView<P> {
    private P presenter;

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public P getPresenter() {
        return Preconditions.checkNotNull(presenter);
    }

    @Override
    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter != null)
            presenter.start();
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }
}
