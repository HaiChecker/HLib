package com.haichecker.lib.mvp.base;

import android.content.Context;

import com.google.common.base.Preconditions;

import retrofit2.Retrofit;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-1 11:09
 */

public abstract class AbsModel<T> implements IBaseModel {


    private Context mContext;
    private CompositeSubscription mCompositeSubscription;

    private T apiSerivce;

    /**
     * 获取API调用接口
     *
     * @return 返回当前
     */
    public T getApiSerivce() {
        return apiSerivce;
    }

    /**
     * 自动设置API
     *
     * @param serivce 设置的API的类
     */
    private void setSerivce(Class<T> serivce) {
        apiSerivce = getRetrofit().create(serivce);
    }

    /**
     * 初始化函数
     *
     * @param mContext 上下文
     * @param serivce  API接口的Class
     */
    public AbsModel(Context mContext, Class<T> serivce) {
        this.mContext = mContext;
        setSerivce(serivce);
    }

    /**
     * 需要指定网络请求管理
     *
     * @return Retrofit对象
     */
    public abstract Retrofit getRetrofit();

    //提供给子类的方法
    public Subscription addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
        return s;
    }


    @Override
    public void destroy() {
        if (!mCompositeSubscription.isUnsubscribed())
            mCompositeSubscription.unsubscribe();
    }

    /**
     * 删除
     * @param s
     */
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
