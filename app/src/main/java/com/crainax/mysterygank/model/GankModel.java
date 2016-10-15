package com.crainax.mysterygank.model;

import com.crainax.mysterygank.bean.DailyEntity;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.bean.OnDataListener;

import java.util.List;

import rx.Subscription;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface GankModel {

    /**
     * 根据页数来获取相应的每日首页的信息.
     */
    Subscription fetchGanks(OnDataListener<List<MeizhiEntity>> onDataCompleteListener, int page);

    Subscription fetchDaily(OnDataListener<List<DailyEntity>> onDataListener, int page);
}
