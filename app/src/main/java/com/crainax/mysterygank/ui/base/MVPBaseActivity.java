package com.crainax.mysterygank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crainax.mysterygank.presenter.base.MVPBasePresenter;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.ui.base <br/>
 * Description: MVP架构中的BaseActivity,类中的泛型V代表这个Activity在Presenter中的View层接口,T代表的是该
 * Activity对应的Presenter. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/27 <br/>
 */
public abstract class MVPBaseActivity<V, T extends MVPBasePresenter<V>> extends AppCompatActivity {

    private T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    public T getPresenter() {
        if (presenter == null) {
            throw new NullPointerException("The Presenter is null!");
        }

        return presenter;
    }

    /**
     * 创建返回与之绑定的Presenter.
     */
    protected abstract T createPresenter();
}
