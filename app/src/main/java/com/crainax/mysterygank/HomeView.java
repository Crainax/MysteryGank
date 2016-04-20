package com.crainax.mysterygank;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface HomeView {
    void showProgress();
    void hideProgress();
    void showGanksData(List<MeizhiEntity> datas);
}
