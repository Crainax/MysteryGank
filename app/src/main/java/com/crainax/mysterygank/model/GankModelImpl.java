package com.crainax.mysterygank.model;

import com.crainax.mysterygank.api.APIFactory;
import com.crainax.mysterygank.api.GankAPI;
import com.crainax.mysterygank.bean.DailyEntity;
import com.crainax.mysterygank.bean.HttpMethod;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.bean.OnDataListener;
import com.crainax.mysterygank.bean.RelaxVideoEntity;
import com.crainax.mysterygank.bean.comparator.DailyComparator;
import com.crainax.mysterygank.bean.comparator.MeizhiSoringComparator;

import java.util.Calendar;
import java.util.Collections;
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
     * 用于标志是否为测试的类
     */
    private final boolean isTesting;

    public GankModelImpl(boolean isTesting) {
        this.isTesting = isTesting;
    }

    public GankModelImpl() {
        this(false);
    }

    /**
     * 从网络获取每日妹纸的图与日期等信息.
     */
    @Override
    public Subscription fetchGanks(final OnDataListener<List<MeizhiEntity>> onDataListener, final int page) {

        GankAPI gankAPI = APIFactory.getGankAPI();

        //获取标题与妹子的接口,并剥离其"error"外壳.
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
                            meiZhi.setDesc(relaxVideo.getDesc());
                        }
                    }

                }
                return meiZhis;
            }

        })
                //获取网络数据,我们在IO线程中.
                .subscribeOn(isTesting ? Schedulers.immediate() : Schedulers.io())
                .observeOn(isTesting ? Schedulers.immediate() : Schedulers.computation())
                //用于排序的Computation线程.
                .map(new Func1<List<MeizhiEntity>, List<MeizhiEntity>>() {
                    @Override
                    public List<MeizhiEntity> call(List<MeizhiEntity> meiZhis) {
                        Collections.sort(meiZhis, new MeizhiSoringComparator());
                        return meiZhis;
                    }
                })
                //返回的接口,用于在Android UI线程.
                .observeOn(isTesting ? Schedulers.immediate() : AndroidSchedulers.mainThread())
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

    /**
     * 从网络获取每日信息.
     */
    @Override
    public Subscription fetchDaily(final OnDataListener<List<DailyEntity>> onDataListener, final int page) {

        GankAPI gankAPI = APIFactory.getGankAPI();

        //获取标题与妹子的接口,并剥离其"error"外壳
        Observable<List<DailyEntity>> oDaily = gankAPI.getDailyEntity(page + "")
                .map(new HttpMethod<List<DailyEntity>>());
        Observable<List<MeizhiEntity>> oMeizhi = gankAPI.getMeizhi(page + "")
                .map(new HttpMethod<List<MeizhiEntity>>());


        //将休息视频的描述用zip整合 到妹子中去
        return Observable.zip(oDaily, oMeizhi, new Func2<List<DailyEntity>, List<MeizhiEntity>, List<DailyEntity>>() {
            @Override
            public List<DailyEntity> call(List<DailyEntity> dailyEntities, List<MeizhiEntity> meizhiEntities) {
                for (MeizhiEntity meizhiEntity : meizhiEntities) {

                    for (DailyEntity dailyEntity : dailyEntities) {

                        // Daily数据里面的日期
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.setTime(dailyEntity.getPublishedAt());
                        // Meizhi数据里面的日期
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTime(meizhiEntity.getPublishedAt());

                        boolean bYear = calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
                        boolean bMonth = calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
                        boolean bDay = calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);

                        if (bYear && bMonth && bDay) {
                            dailyEntity.setImageUrl(meizhiEntity.getUrl());
                        }

                    }

                }
                return dailyEntities;
            }
        })
                //获取网络数据,我们在IO线程中.
                .subscribeOn(isTesting ? Schedulers.immediate() : Schedulers.io())
                //用于排序的Computation线程.
                .observeOn(isTesting ? Schedulers.immediate() : Schedulers.computation())
                .map(new Func1<List<DailyEntity>, List<DailyEntity>>() {
                    @Override
                    public List<DailyEntity> call(List<DailyEntity> dailyEntities) {
                        //排序还有获取妹子的图片信息。
                        Collections.sort(dailyEntities, new DailyComparator());
                        return dailyEntities;
                    }
                })
                //返回的接口,用于在Android UI线程.
                .observeOn(isTesting ? Schedulers.immediate() : AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DailyEntity>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDataListener.onDataError(e);
                    }

                    @Override
                    public void onNext(List<DailyEntity> dailyEntities) {
                        onDataListener.onDataComplete(dailyEntities);
                    }
                });

    }


}
