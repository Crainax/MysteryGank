package com.crainax.mysterygank;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface OnDataListener<T> {
    void onDataComplete(T t);
    void onDataError(Throwable e);
}
