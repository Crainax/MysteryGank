package com.crainax.mysterygank;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public class HomePresenter implements IHomePresenter {

    private HomeView homeview;
    private GankModel gankmodel;

    public HomePresenter(HomeView homeview) {
        this.homeview = homeview;
        gankmodel = new GankModelImpl();
    }

    public void getGanksData(final int page) {
        homeview.showProgress();
        //保存到相应的订阅体中.
        homeview.saveToCompositeSubscription(gankmodel.fetchGanks(new OnDataListener<List<MeizhiEntity>>() {
            @Override
            public void onDataComplete(final List<MeizhiEntity> ganks) {
                homeview.showGanksData(ganks);
                homeview.hideProgress();
            }

            @Override
            public void onDataError(final Throwable e) {
                homeview.hideProgress();
                homeview.showErrorMessage();
            }
        }, page));

    }


}