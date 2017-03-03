package com.haichecker.lib.app.actionBar;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 14:21
 */

public abstract class AbsNavigation<D extends ViewDataBinding, P extends AbsNavigation.Builder.NavigationParams> implements INavigation<D> {

    private P params;
    private D viewRoot;
//    private D

    private View view;

    public AbsNavigation(P params) {
        this.params = params;
        createAndBind();
    }

    public void createAndBind() {
        if (params == null)
            return;
        view = LayoutInflater.from(params.mContext).inflate(bindLayoutId(), null, false);
        viewRoot = DataBindingUtil.bind(view);
        params.parent.addView(viewRoot.getRoot(), 0);
    }

    public D getViewRoot() {
        return viewRoot;
    }


    public abstract static class Builder<D extends ViewDataBinding> {

        public abstract AbsNavigation<D, ?> create();

        public static class NavigationParams {
            public Context mContext;
            public ViewGroup parent;

            public NavigationParams(Context mContext, ViewGroup parent) {
                this.mContext = mContext;
                this.parent = parent;
            }
        }


    }

}
