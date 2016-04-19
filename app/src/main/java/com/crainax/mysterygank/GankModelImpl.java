package com.crainax.mysterygank;

import android.util.Log;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Crainax on 2016/4/17.
 */
public class GankModelImpl implements GankModel {
    private static final String TAG = "Crainax.GankModelImpl";

    /**
     * 从网络获取每日妹纸的图与日期等信息.
     * // TODO: 2016/4/19 完成Subscription的取订还有MVP部分.
     */
    @Override
    public void fetchGanks(final OnDataCompleteListener<List<MeizhiEntity>> onDataCompleteListener) {


        GankAPI gankAPI = APIFactory.getGankAPI();

        //获取休息视频与妹子的接口,并剥离其"error"外壳.
        Observable<List<RelaxVideoEntity>> oRelaxVideo = gankAPI.getRelaxVedio("1")
                .map(new HttpMethod<List<RelaxVideoEntity>>());
        Observable<List<MeizhiEntity>> oMeizhi = gankAPI.getMeizhi("1")
                .map(new HttpMethod<List<MeizhiEntity>>());

        //不使用匿名内部类的形式,方便返回
        Subscriber<List<MeizhiEntity>> subscriber = new Subscriber<List<MeizhiEntity>>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted.");
            }

            @Override
            public void onError(Throwable e) {
                Log.w(TAG, "onError: ", e);
            }

            @Override
            public void onNext(List<MeizhiEntity> meiZhis) {
                for (MeizhiEntity meiZhi : meiZhis) {
                    Log.i(TAG, "onNext: " + meiZhi);
                }
                Log.i(TAG, "onNext Thread: " + Thread.currentThread());
            }
        };
        //将休息视频的描述用zip整合到妹子中去
        Observable.zip(oRelaxVideo, oMeizhi, new Func2<List<RelaxVideoEntity>, List<MeizhiEntity>, List<MeizhiEntity>>() {

            @Override
            public List<MeizhiEntity> call(List<RelaxVideoEntity> relaxVideos, List<MeizhiEntity> meiZhis) {
                Log.i(TAG, "call: " + Thread.currentThread().getName());
                for (MeizhiEntity meiZhi : meiZhis) {

                    for (RelaxVideoEntity relaxVideo : relaxVideos) {
                        if (meiZhi.getPublishedAt().equals(relaxVideo.getPublishedAt())) {
                            meiZhi.setDesc(meiZhi.getDesc() + " : " + relaxVideo.getDesc());
                        }
                    }

                }
                return meiZhis;
            }

        })
                //获取网络数据,我们在IO线程中.
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                //用于排序的Computation线程.
                .map(new Func1<List<MeizhiEntity>, List<MeizhiEntity>>() {
                    @Override
                    public List<MeizhiEntity> call(List<MeizhiEntity> meiZhis) {
                        Log.i(TAG, "sorting in :" + Thread.currentThread().getName());
                        return meiZhis;
                    }
                })
                //返回的接口,用于在Android UI线程.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
