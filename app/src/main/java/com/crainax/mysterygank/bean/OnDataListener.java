package com.crainax.mysterygank.bean;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface OnDataListener<T> {
    void onDataComplete(T t);
    void onDataError(Throwable e);
}
