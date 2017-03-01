package com.haichecker.lib.mvp.base;

import com.google.common.base.Preconditions;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 10:53
 */

public abstract class AbsPresenter implements IBasePresenter {

    private IBaseView<? super IBasePresenter> iView;

    private AbsModel<? super IBasePresenter> model;

    public AbsPresenter(IBaseView<? super IBasePresenter> iView) {
        this.iView = iView;
        iView.setPresenter(this);
    }

    @Override
    public void setModel(AbsModel<? super IBasePresenter> model) {
        this.model = model;
    }

    @Override
    public IBasePresenter getPresenter() {
        return this;
    }

    @Override
    public AbsModel<? super IBasePresenter> getModel() {
        return Preconditions.checkNotNull(model);
    }

    public IBaseView<? super IBasePresenter> getView(){
        return Preconditions.checkNotNull(iView);
    }
}
