package com.crainax.mysterygank;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface GankModel {

    /**
     *
     */
    void fetchGanks(OnDataCompleteListener<List<MeizhiEntity>> onDataCompleteListener);
}
