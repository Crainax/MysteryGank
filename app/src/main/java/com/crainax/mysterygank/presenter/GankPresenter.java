package com.crainax.mysterygank.presenter;

import com.crainax.mysterygank.presenter.base.MVPBasePresenter;
import com.crainax.mysterygank.view.GankView;

/**
 * Created by crainax on 2016/10/16.
 */
public class GankPresenter extends MVPBasePresenter<GankView>{

    public GankPresenter(GankView view) {
        super(view);
    }

    public void fetchGankData(int Year, int Month, int Day) {

    }
}
