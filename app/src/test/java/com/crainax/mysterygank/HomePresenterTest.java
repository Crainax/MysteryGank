package com.crainax.mysterygank;

import android.accounts.NetworkErrorException;

import com.crainax.mysterygank.api.GankRetrofit;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.bean.OnDataListener;
import com.crainax.mysterygank.model.GankModelImpl;
import com.crainax.mysterygank.presenter.HomePresenter;
import com.crainax.mysterygank.ui.HomeActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: Using Junit and Mockito <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/5/7 <br/>
 */
public class HomePresenterTest {

    private static final int PAGE = 1;

    @Mock
    private HomeActivity mHomeActivity;

    @Mock
    private GankModelImpl mGankModel;
    /**
     * 相应的参数捕获器.用于验证回调接口.
     */
    @Captor
    private ArgumentCaptor<OnDataListener<List<MeizhiEntity>>> mCallbackCaptor;

    private HomePresenter homePresenter;

    private static List<MeizhiEntity> MEIZHIS;

    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);

        homePresenter = new HomePresenter(mHomeActivity, mGankModel);

        //毕竟我们是要验证Presenter的逻辑,所以Model层的返回的数据我们可以伪造.
        MEIZHIS = new ArrayList<>();
        for (int i = 0; i < GankRetrofit.NUMBER_PER_PAGE; i++) {
            MEIZHIS.add(new MeizhiEntity(i + ""));
        }
    }

    @Test
    public void testGetHomeMeizhiDatas() {
        //测试页数与顺序验证初始化
        InOrder inOrder = inOrder(mHomeActivity, mGankModel);

        homePresenter.getGanksData(PAGE);

        //按照调用顺序依次验证View层和Model层是否调用:
        //1.显示进度条
        //2.从Model层获取Meizhi数据并回调给Presenter.
        inOrder.verify(mHomeActivity).showProgress();
        inOrder.verify(mGankModel).fetchGanks(mCallbackCaptor.capture(), eq(PAGE));
        mCallbackCaptor.getValue().onDataComplete(MEIZHIS);

        //3.隐藏进度条+显示数据.
        inOrder.verify(mHomeActivity).hideProgress();
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        verify(mHomeActivity).showGanksData(listCaptor.capture());

        //4.验证View层的数据是否为相应的组数
        assertEquals(listCaptor.getValue().size(), GankRetrofit.NUMBER_PER_PAGE);
    }

    @Test
    public void testGetHomeMeizhiDatasFail() {

        InOrder inOrder = inOrder(mHomeActivity, mGankModel);

        homePresenter.getGanksData(PAGE);

        inOrder.verify(mHomeActivity).showProgress();
        inOrder.verify(mGankModel).fetchGanks(mCallbackCaptor.capture(), eq(PAGE));
        mCallbackCaptor.getValue().onDataError(new NetworkErrorException());

        //下面两个逻辑不一定谁先发生,所以只需要验证其中一个的与上面的顺序一致即可.
        inOrder.verify(mHomeActivity).hideProgress();
        verify(mHomeActivity).showErrorMessage();
    }

}
