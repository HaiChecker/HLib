package com.haichecker.simple.p;

import com.haichecker.lib.mvp.base.AbsPresenter;
import com.haichecker.simple.c.HomeContract;
import com.haichecker.simple.m.HomeModel;


/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 18:17
 */

public class HomePresenter extends AbsPresenter<HomeContract.IHomeView, HomeContract.IHomeModel> implements HomeContract.IHomePresenter {
    private HomeModel model;

    public HomePresenter(HomeContract.IHomeView iView) {
        super(iView);
        model = new HomeModel(iView.getContext());
        setModel(model);
    }

    @Override
    public void getIndexCarouselAds() {
    }

}
