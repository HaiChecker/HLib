package com.haichecker.lib.mvp.base;

import com.google.common.base.Preconditions;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 10:53
 */

public abstract class AbsPresenter<V extends IBaseView, M extends IBaseModel> implements IBasePresenter<M> {

    private V iView;

    private M model;

    public AbsPresenter(V iView) {
        this.iView = iView;
        iView.setPresenter(this);
    }


    @Override
    public void setModel(M model) {
        this.model = model;
    }

    @Override
    public M getModel() {
        return model;
    }


    public V getView() {
        return iView;
    }

    @Override
    public void destroy() {
        if (model != null)
            model.destroy();
        iView = null;
    }

    @Override
    public void start() {
        if (model != null)
            model.start();
    }
}
