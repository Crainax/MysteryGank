package com.crainax.mysterygank.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.crainax.mysterygank.R;
import com.crainax.mysterygank.bean.DailyEntity;
import com.crainax.mysterygank.presenter.GankPresenter;
import com.crainax.mysterygank.ui.base.BaseActivity;
import com.crainax.mysterygank.view.GankView;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by crainax on 2016/10/16.
 */
public class GankActivity extends BaseActivity<GankView, GankPresenter>
        implements GankView {

    private static final String EXTRA_YEAR = "year";
    private static final String EXTRA_MONTH = "month";
    private static final String EXTRA_IMAGE = "image_url";
    private static final String EXTRA_DAY = "day";
    private static final String EXTRA_TITLE = "title";
    @BindView(R.id.iv_gank)
    ImageView mIvGank;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.coll_layout_gank)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.fab_gank)
    FloatingActionButton mFabGank;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String mImageUrl;
    private String mTitle;

    /**
     * The Activity launcher.
     */
    public static void start(Activity activity, DailyEntity dailyEntity, View transitionView) {
        Intent starter = new Intent(activity, GankActivity.class);

        Date date = dailyEntity.getPublishedAt();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        starter.putExtra(EXTRA_YEAR, calendar.get(Calendar.YEAR));
        starter.putExtra(EXTRA_MONTH, calendar.get(Calendar.MONTH));
        starter.putExtra(EXTRA_DAY, calendar.get(Calendar.DAY_OF_MONTH));
        starter.putExtra(EXTRA_IMAGE, dailyEntity.getImageUrl());
        starter.putExtra(EXTRA_TITLE, dailyEntity.getTitle());

        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, EXTRA_IMAGE);
        ActivityCompat.startActivity(activity, starter, options.toBundle());
    }

    /**
     * 初始化位移动画
     */
    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            //这里的appbar在动画的时候有问题。查明原因是appbar加上一个background transparent就行了。。。。
            // TODO: 2016/10/16 记录这里
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            transition.excludeTarget(R.id.appbar_gank, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    private void parseIntent(Intent intent) {
        mYear = intent.getIntExtra(EXTRA_YEAR, 0);
        mMonth = intent.getIntExtra(EXTRA_MONTH, 0);
        mDay = intent.getIntExtra(EXTRA_DAY, 0);
        mImageUrl = intent.getStringExtra(EXTRA_IMAGE);
        mTitle = intent.getStringExtra(EXTRA_TITLE);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTransition();
        setContentView(R.layout.activity_gank);
        ButterKnife.bind(this);

        ViewCompat.setTransitionName(mIvGank, EXTRA_IMAGE);
        supportPostponeEnterTransition();
        mCollapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Intent intent = getIntent();
        parseIntent(intent);
        getPresenter().fetchGankData(mYear, mMonth, mDay);

        initData();
    }

    /**
     * Initialize the data. etc image.
     */
    private void initData() {
        Glide.with(this).load(mImageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                mIvGank.setImageBitmap(resource);

                Bitmap bitmap = ((BitmapDrawable) mIvGank.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    @Override
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }

                });
            }
        });
        mCollapsingToolbar.setTitle(mTitle);


    }

    /**
     * 调整色调
     */
    private void applyPalette(Palette palette) {

        int colorPrimary = getResources().getColor(R.color.colorPrimary);
        int colorPrimaryDark = getResources().getColor(R.color.colorPrimaryDark);
        mCollapsingToolbar.setContentScrimColor(palette.getMutedColor(colorPrimary));
        mCollapsingToolbar.setStatusBarScrimColor(palette.getDarkMutedColor(colorPrimaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab_gank), palette);
        // TODO: 2016/10/16 记录这个supportStartPostPonedEnter的使用。
        supportStartPostponedEnterTransition();

    }

    /**
     * Set the FAB Color By The Palette.
     */
    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.colorAccent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }


    @Override
    protected GankPresenter createPresenter() {
        return new GankPresenter(this);
    }
}
