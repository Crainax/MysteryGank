package com.crainax.mysterygank.presenter.base;

import java.lang.ref.WeakReference;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.presenter.base <br/>
 * Description: MVP架构中的BasePresenter <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/27 <br/>
 */
public class MVPBasePresenter<T> {

    private WeakReference<T> viewRef;

    public MVPBasePresenter(T view) {
        viewRef = new WeakReference<>(view);
    }

    public void attachView(T view) {
        viewRef = new WeakReference<>(view);
    }

    public boolean isAttach() {
        return viewRef != null && viewRef.get() != null;
    }

    protected T getView() {
        return viewRef.get();
    }

    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

}
