package com.haichecker.lib.mvp.base;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-20 15:17
 */

public interface ICallBackView<P> extends IBaseView<P> {
    /**
     * 开始
     *
     * @param type 唯一标识符
     */
    void start(int type);

    /**
     * 请求发生错误
     *
     * @param type 唯一标识符
     * @param err  错误信息
     */
    void error(int type, String err);

    /**
     * 请求成功
     *
     * @param type 唯一标识符
     * @param o    获取的数据，需要自己转换
     */
    void success(int type, Object o);

}
