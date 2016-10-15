package com.crainax.mysterygank.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.crainax.mysterygank.R;
import com.crainax.mysterygank.bean.DailyEntity;
import com.crainax.mysterygank.presenter.GankPresenter;
import com.crainax.mysterygank.ui.base.BaseActivity;
import com.crainax.mysterygank.view.GankView;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by crainax on 2016/10/16.
 */
public class GankActivity extends BaseActivity<GankView, GankPresenter>
        implements GankView {

    private static final String EXTRA_YEAR = "year";
    private static final String EXTRA_MONTH = "month";
    private static final String EXTRA_IMAGE_URL = "image_url";
    private static final String EXTRA_DAY = "day";
    private static final String EXTRA_TITLE = "title";
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mImageUrl;
    private String mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank);
        Intent intent = getIntent();
        parseIntent(intent);
        getPresenter().fetchGankData(mYear,mMonth,mDay);
    }

    private void parseIntent(Intent intent) {
        mYear = intent.getIntExtra(EXTRA_YEAR, 0);
        mMonth = intent.getIntExtra(EXTRA_MONTH, 0);
        mDay = intent.getIntExtra(EXTRA_DAY, 0);
        mImageUrl = intent.getStringExtra(EXTRA_IMAGE_URL);
        mTitle = intent.getStringExtra(EXTRA_TITLE);
    }

    /**
     * The Activity launcher.
     */
    public static void start(Context context, DailyEntity dailyEntity,View transitionView) {
        Intent starter = new Intent(context, GankActivity.class);

        Date date = dailyEntity.getPublishedAt();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        starter.putExtra(EXTRA_YEAR, calendar.get(Calendar.YEAR));
        starter.putExtra(EXTRA_MONTH, calendar.get(Calendar.MONTH));
        starter.putExtra(EXTRA_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        starter.putExtra(EXTRA_IMAGE_URL, dailyEntity.getImageUrl());
        starter.putExtra(EXTRA_TITLE, dailyEntity.getTitle());

        context.startActivity(starter);
    }

    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(this);
    }
}
