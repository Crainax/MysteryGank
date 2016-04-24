package com.crainax.mysterygank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: The base activity to handler repetitive code. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/4/22 <br/>
 */
public class BaseActivity extends AppCompatActivity implements Subscrible {

    //组合在一起的Subscription,方便一键取订.
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void addSubscription(@NonNull Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    @Override
    public void saveToCompositeSubscription(Subscription subscription) {
        addSubscription(subscription);
    }
}
