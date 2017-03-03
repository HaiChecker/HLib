package com.haichecker.simple.m;

import android.content.Context;
import android.text.TextUtils;

import com.haichecker.lib.mvp.base.AbsModel;
import com.haichecker.lib.mvp.base.BaseModelInterFace;
import com.haichecker.simple.HomeService;
import com.haichecker.simple.c.HomeContract;

import java.util.List;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 18:16
 */

public class HomeModel extends AbsModel<HomeService> implements HomeContract.IHomeModel {


    public HomeModel(Context mContext, Class<HomeService> serivce) {
        super(mContext, serivce);
    }

    @Override
    public Retrofit getRetrofit() {
        return null;
    }

    @Override
    public void getIndexCarouselAds(BaseModelInterFace<String> callBack) {

    }
}
