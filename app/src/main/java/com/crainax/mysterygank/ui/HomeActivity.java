package com.crainax.mysterygank.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.crainax.mysterygank.R;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.presenter.HomePresenter;
import com.crainax.mysterygank.ui.adapter.HomeAdapter;
import com.crainax.mysterygank.ui.adapter.SpaceItemDecoration;
import com.crainax.mysterygank.ui.base.BaseActivity;
import com.crainax.mysterygank.view.HomeView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomeView, HomePresenter>
        implements HomeView, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "Crainax";
    /**
     * The flag indicate that the gank data has been load.
     */
    private boolean mHasLoad = false;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_home)
    RecyclerView mRvHome;
    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSrlHome;

    private HomePresenter mPresenter;
    private int currentPage = 1;
    private HomeAdapter mHomeAdapter;
    private LinearLayoutManager mRvLayoutManager;
    private static int NUMBER_PRELOAD = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRefreshLayout();
        mPresenter = getPresenter();

        //获取数据
        mPresenter.getGanksData(1);
    }

    private void initRefreshLayout() {

        mSrlHome.setOnRefreshListener(this);

        mHomeAdapter = new HomeAdapter();
        mRvHome.setAdapter(mHomeAdapter);
        mRvHome.setHasFixedSize(true);
        mRvLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRvHome.setLayoutManager(mRvLayoutManager);
        mRvHome.setItemAnimator(new DefaultItemAnimator());

        int spacingInPixel = getResources().getDimensionPixelSize(R.dimen.item_distance);
        mRvHome.addItemDecoration(new SpaceItemDecoration(spacingInPixel));

        mRvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mRvLayoutManager.findLastVisibleItemPosition() >= mHomeAdapter.getItemCount() - NUMBER_PRELOAD && mHasLoad) {
//                    Logger.i("mRvLayoutManager.findLastVisibleItemPosition()" + mRvLayoutManager.findLastVisibleItemPosition());
//                    Logger.i("mItemCount ::: " + mHomeAdapter.getItemCount());
                    mPresenter.updateGanksData(++currentPage);
                    System.out.println("CurrentPage :" + currentPage);
                    Log.i(TAG, "CurrentPage:" + currentPage);
                }
            }
        });

    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showProgress() {
        mHasLoad = false;
        mSrlHome.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSrlHome.setRefreshing(false);
        mHasLoad = true;
    }

    @Override
    public void showGanksData(List<MeizhiEntity> datas) {
        mHomeAdapter.setDatasAndNotify(datas);
    }

    @Override
    public void showErrorMessage(Throwable e) {
        Toast.makeText(HomeActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
        Logger.w("showErrorMessage: " + e);
    }

    @Override
    public void addGankDatas(List<MeizhiEntity> datas) {
        mHomeAdapter.addDatasAndNotify(datas);
    }

    @Override
    public void onRefresh() {
        mPresenter.getGanksData(1);
    }
}
