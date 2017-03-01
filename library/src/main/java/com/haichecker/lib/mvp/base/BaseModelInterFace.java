package com.haichecker.lib.mvp.base;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-2-6 15:01
 */

public interface BaseModelInterFace<T> {
    void onStart();

    void onError(String err);

    void onSuccess(T t);
}
