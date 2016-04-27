package com.crainax.mysterygank.view;

import com.crainax.mysterygank.bean.MeizhiEntity;

import java.util.List;

/**
 * Created by Crainax on 2016/4/17.
 */
public interface HomeView extends Subscrible {
    /**
     * 显示进度条
     */
    void showProgress();

    /**
     * 隐藏进度条
     */
    void hideProgress();

    /**
     * 显示妹子图片等信息
     */
    void showGanksData(List<MeizhiEntity> datas);

    /**
     * 显示获取网络异常时的信息
     */
    void showErrorMessage();
}
