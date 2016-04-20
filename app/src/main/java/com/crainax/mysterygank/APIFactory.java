package com.crainax.mysterygank;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description:获取Gank单例的方法. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/19 <br/>
 */
public class APIFactory {

    public static GankAPI getGankAPI(){
        return GankRetrofit.getInstance().getGankAPI();
    }


}
