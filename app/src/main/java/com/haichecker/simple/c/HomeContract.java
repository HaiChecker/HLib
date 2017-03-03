package com.haichecker.simple.c;

import com.haichecker.lib.mvp.base.BaseModelInterFace;
import com.haichecker.lib.mvp.base.IBaseModel;
import com.haichecker.lib.mvp.base.IBasePresenter;
import com.haichecker.lib.mvp.base.IBaseView;

import java.util.List;


/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 18:15
 */

public interface HomeContract {
    public interface IHomePresenter extends IBasePresenter<IHomeModel> {
        void getIndexCarouselAds();
    }

    public interface IHomeView extends IBaseView<IHomePresenter> {
        void onAdsSuccess(String data);

        void onAdsError(String error);

        void onAdsStart();
    }

    public interface IHomeModel extends IBaseModel {
        void getIndexCarouselAds(BaseModelInterFace<String> callBack);
    }
}
