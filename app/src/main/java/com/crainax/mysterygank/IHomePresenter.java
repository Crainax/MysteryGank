package com.crainax.mysterygank;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: HomePresenter的抽象接口形式. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/24 <br/>
 */
public interface IHomePresenter {
    /**
     * 获取主页的信息.
     */
    void getGanksData(int page);
}
