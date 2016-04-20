package com.crainax.mysterygank;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface GankAPI {

    @GET("/api/data/{type}/{num}/{page}")
    Observable<HttpResult<List<MeizhiEntity>>> getGankData(@Path("type") String type, @Path("num") String num, @Path("page") String page);

    @GET("/api/data/福利/" + GankRetrofit.NUMBER_PER_PAGE + "/{page}")
    Observable<HttpResult<List<MeizhiEntity>>> getMeizhi(@Path("page") String page);

    @GET("/api/data/休息视频/" + GankRetrofit.NUMBER_PER_PAGE + "/{page}")
    Observable<HttpResult<List<RelaxVideoEntity>>> getRelaxVedio(@Path("page") String page);

    @GET("/api/data/Android/" + GankRetrofit.NUMBER_PER_PAGE + "/{page}")
    Observable<HttpResult<List<AndroidEntity>>> getAndroid(@Path("page") String page);

}
