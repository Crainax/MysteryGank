package com.crainax.mysterygank.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class GankRetrofit {

    private Retrofit retrofit;
    /**
     * 定义在APi里面的常数
     */
    public static final int NUMBER_PER_PAGE = 10;
    private final GankAPI gankAPI;

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public GankAPI getGankAPI() {
        return gankAPI;
    }

    private GankRetrofit() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'14:42:21.265Z")
                .serializeNulls()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                //这里不能省略,用于适配RxJava的接口
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        gankAPI = retrofit.create(GankAPI.class);
    }

    private static class SingletonHolder {
        private static final GankRetrofit INSTANCE = new GankRetrofit();
    }

    /**
     * 返回单例
     */
    public static GankRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
