package com.crainax.mysterygank;

import android.app.Application;

import com.orhanobut.logger.AndroidLogTool;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/20 <br/>
 */
public class APP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ///初始化Logger
        Logger.init("Crainax")
                .logTool(new AndroidLogTool());

        //初始化LeakCanary
        LeakCanary.install(this);

    }
}
