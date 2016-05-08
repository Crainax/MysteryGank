package com.crainax.mysterygank;

import com.crainax.mysterygank.api.GankRetrofit;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.bean.OnDataListener;
import com.crainax.mysterygank.model.GankModelImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank <br/>
 * Description: Testing for the NetWork using JUnit + Mockito. <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/5/8 <br/>
 */
public class GankNetWorkTest {


    private static final int PAGE_TEST1 = 1;
    private static final int PAGE_TEST2 = 2;

    private GankModelImpl mGankModel;

    @Mock
    private OnDataListener<List<MeizhiEntity>> mListener;

    @Captor
    private ArgumentCaptor<List<MeizhiEntity>> mMeizhiCaptor;

    @Before
    public void beforeTest() {
        MockitoAnnotations.initMocks(this);

        mGankModel = new GankModelImpl(true);

    }

    @Test
    public void testFetchGanksAtHomeAct() {

        mGankModel.fetchGanks(mListener, PAGE_TEST1);

        //验证成功获取数据
        verify(mListener).onDataComplete(mMeizhiCaptor.capture());
        verify(mListener, never()).onDataError(any(Throwable.class));

        //验证获取到的数据量是10条
        List<MeizhiEntity> meizhiEntityList = mMeizhiCaptor.getValue();
        assertThat(meizhiEntityList.size(), is(GankRetrofit.NUMBER_PER_PAGE));

        System.out.println("Gank获取数据 : " + meizhiEntityList);
        //验证数据已经排序
        for (int i = 0; i < meizhiEntityList.size() - 1; i++) {
            assertThat(meizhiEntityList.get(i).compareTo(meizhiEntityList.get(i + 1)), is(1));
        }

        //清除之前的mock数据并进行第二次获取数据
        clearInvocations(mListener);
        mGankModel.fetchGanks(mListener, PAGE_TEST2);

        //验证获取第二页的数据与第一页的不一样
        verify(mListener).onDataComplete(mMeizhiCaptor.capture());
        List<MeizhiEntity> otherMeizhiEntityList = mMeizhiCaptor.getValue();
        System.out.println("Gank获取第二页数据 : " + otherMeizhiEntityList);
        assertNotEquals(meizhiEntityList, otherMeizhiEntityList);

    }

}
