package com.crainax.mysterygank;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by Crainax on 2016/4/17.
 */
public class GankModelImpl implements GankModel {

    /**
     * 从网络获取每日妹纸的图与日期等信息.
     */
    @Override
    public Subscription fetchGanks(final OnDataListener<List<MeizhiEntity>> onDataListener, final int page) {

        GankAPI gankAPI = APIFactory.getGankAPI();

        //获取休息视频与妹子的接口,并剥离其"error"外壳.
        Observable<List<RelaxVideoEntity>> oRelaxVideo = gankAPI.getRelaxVedio(page + "")
                .map(new HttpMethod<List<RelaxVideoEntity>>());
        Observable<List<MeizhiEntity>> oMeizhi = gankAPI.getMeizhi(page + "")
                .map(new HttpMethod<List<MeizhiEntity>>());

        //将休息视频的描述用zip整合 到妹子中去
        return Observable.zip(oRelaxVideo, oMeizhi, new Func2<List<RelaxVideoEntity>, List<MeizhiEntity>, List<MeizhiEntity>>() {

            @Override
            public List<MeizhiEntity> call(List<RelaxVideoEntity> relaxVideos, List<MeizhiEntity> meiZhis) {
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
                        return meiZhis;
                    }
                })
                //返回的接口,用于在Android UI线程.
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MeizhiEntity>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataListener.onDataError(e);
                    }

                    @Override
                    public void onNext(List<MeizhiEntity> meiZhis) {
                        onDataListener.onDataComplete(meiZhis);
                    }
                });

    }
}
