package com.crainax.mysterygank;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public class MainPresenter {

    private HomeView homeview;
    private GankModel gankmodel;

    public MainPresenter(HomeView homeview) {
        this.homeview = homeview;
        gankmodel = new GankModelImpl();
    }

    public void refreshGanks() {
        homeview.showProgress();
        gankmodel.fetchGanks(new OnDataCompleteListener<List<MeizhiEntity>>() {
            @Override
            public void onDataComplete(List<MeizhiEntity> ganks) {
                homeview.showGanksData(ganks);
                homeview.hideProgress();
            }
        });
    }



}