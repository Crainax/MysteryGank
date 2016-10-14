package com.crainax.mysterygank.presenter;

import android.support.annotation.NonNull;

import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.bean.OnDataListener;
import com.crainax.mysterygank.model.GankModel;
import com.crainax.mysterygank.model.GankModelImpl;
import com.crainax.mysterygank.presenter.base.MVPBasePresenter;
import com.crainax.mysterygank.view.HomeView;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public class HomePresenter extends MVPBasePresenter<HomeView> {

    private final GankModel gankmodel;

    public HomePresenter() {
        this(new GankModelImpl());
    }

    public HomePresenter(@NonNull GankModel gankmodel) {
        this(null, gankmodel);
    }

    public HomePresenter(HomeView homeView, @NonNull GankModel gankmodel) {
        super(homeView);
        this.gankmodel = gankmodel;
    }

    public void getGanksData(final int page) {

        getView().showProgress();
        //保存到相应的订阅体中.
        getView().saveToCompositeSubscription(gankmodel.fetchGanks(new OnDataListener<List<MeizhiEntity>>() {
            @Override
            public void onDataComplete(final List<MeizhiEntity> ganks) {
                getView().showGanksData(ganks);
                getView().hideProgress();
            }

            @Override
            public void onDataError(final Throwable e) {
                getView().hideProgress();
                getView().showErrorMessage(e);
            }
        }, page));

    }

    public void updateGanksData(int page) {
        getView().showProgress();
        getView().saveToCompositeSubscription(gankmodel.fetchGanks(new OnDataListener<List<MeizhiEntity>>() {
            @Override
            public void onDataComplete(List<MeizhiEntity> meizhiEntities) {
                getView().hideProgress();
                getView().addGankDatas(meizhiEntities);
            }

            @Override
            public void onDataError(Throwable e) {
                getView().showErrorMessage(e);
                getView().hideProgress();
            }
        }, page));
    }


}