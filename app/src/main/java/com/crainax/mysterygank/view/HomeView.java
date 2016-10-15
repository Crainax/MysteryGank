package com.crainax.mysterygank.view;

import com.crainax.mysterygank.bean.DailyEntity;

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
    void showDailyDatas(List<DailyEntity> datas);

    /**
     * 显示获取网络异常时的信息
     * @param e
     */
    void showErrorMessage(Throwable e);

    /**
     * 添加主页妹子等图片等信息
     */
    void addDailyDatas(List<DailyEntity> datas);

    /**
     * 显示后台进度条
     */
    void showBackgroundProgress();

    /**
     * 隐藏后台进度条
     */
    void hideBackGroundProgress();
}
