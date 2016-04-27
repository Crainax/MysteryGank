package com.crainax.mysterygank.view;

import rx.Subscription;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: 抽象成接口形式的可订阅形式. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/24 <br/>
 */
public interface Subscrible {
    /**
     * 保存相应的订阅数据到Activity下的CompositeSubscription中.
     */
    void saveToCompositeSubscription(Subscription subscription);

}
