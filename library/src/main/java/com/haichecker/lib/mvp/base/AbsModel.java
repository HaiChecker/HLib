package com.haichecker.lib.mvp.base;

import android.content.Context;

import com.google.common.base.Preconditions;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 11:09
 */

public abstract class AbsModel<P extends IBasePresenter> implements IBaseModel<P> {

    private P presenter;

    private Context mContext;
    private CompositeSubscription mCompositeSubscription;


    public AbsModel(Context mContext) {
        this.mContext = mContext;
    }

    //提供给子类的方法
    public Subscription addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
        return s;
    }

    @Override
    public P getPresenter() {
        return Preconditions.checkNotNull(presenter);
    }

    @Override
    public void destroy() {
        if (!mCompositeSubscription.isUnsubscribed())
            mCompositeSubscription.unsubscribe();
    }

    public void removeSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            return;
        }

        this.mCompositeSubscription.remove(s);
    }

    @Override
    public void start() {

    }

    @Override
    public Context getContext() {
        return Preconditions.checkNotNull(mContext);
    }
}
