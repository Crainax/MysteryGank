package com.crainax.mysterygank.api.exception;

/**
 * Created by Crainax on 2016/4/18.
 */
public class ApiException extends RuntimeException {

    /**
     * 当出现异常时,在Subscriber的onError()中调用这个方法获取异常错误信息.
     *
     * @return 返回的错误信息.
     */
    public String getAPIException() {
        return "接口出现异常";
    }
}
