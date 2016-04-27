package com.crainax.mysterygank.bean;

import com.crainax.mysterygank.api.exception.ApiException;

import rx.functions.Func1;

/**
 * Created by Crainax on 2016/4/18.
 */
public class HttpMethod<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(final HttpResult<T> httpResult) {
        // 如果返回Json异常,则抛异常,让Subscriber处理.
        if (httpResult.isError())
            throw new ApiException();
        //如果正常则返回剥离外层的数据.
        return httpResult.getResults();
    }
}
