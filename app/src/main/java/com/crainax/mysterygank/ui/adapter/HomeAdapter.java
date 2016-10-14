package com.crainax.mysterygank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crainax.mysterygank.R;
import com.crainax.mysterygank.bean.MeizhiEntity;
import com.crainax.mysterygank.util.DateUtils;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

/**
 * Project: MysteryGank <br/>
 * Package: com.crainax.mysterygank.ui.adapter <br/>
 * Description: <br/>
 * <hr/>
 *
 * @author Crainax <br/>
 * @version 1.0 <br/>
 * @since 2016/5/13 <br/>
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_DATAS = 0;
    private static final int VIEW_TYPE_FOOTER = 1;

    private final HashSet<Integer> mUnfoldedIndex = new HashSet<>();

    private List<MeizhiEntity> datas;

    public void setDatas(List<MeizhiEntity> datas) {
        this.datas = datas;
        mUnfoldedIndex.clear();
    }

    public void setDatasAndNotify(List<MeizhiEntity> datas) {
        setDatas(datas);
        notifyDataSetChanged();
    }

    public void addDatasAndNotify(List<MeizhiEntity> datas) {

        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {

        if (position < datas.size())
            return VIEW_TYPE_DATAS;
        else if (position == datas.size())
            return VIEW_TYPE_FOOTER;
        else
            throw new IllegalStateException("The View Type is Illegal!");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = null;
        switch (viewType) {
            case VIEW_TYPE_DATAS:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_piece, parent, false);
                return new HomeAdapterHolder(view);
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_footer, parent, false);
                return new FooterViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof HomeAdapterHolder) {

            final HomeAdapterHolder homeAdapterHolder = (HomeAdapterHolder) holder;

            //判断缓存中是否是折叠状态去设置相应的状态
            if (isUnfold(position))
                homeAdapterHolder.mFcHome.unfold(true);
            else
                homeAdapterHolder.mFcHome.fold(true);

            //设置相应的数据(展开后的)
            homeAdapterHolder.mTvUnfoldTitle.setText(datas.get(position).getDesc());
            homeAdapterHolder.mTvUnfoldDate.setText(DateUtils.formatDate(datas.get(position).getPublishedAt(), DateUtils.FORMAT_YMD));
            Glide.with(homeAdapterHolder.context)
                    .load(datas.get(position).getUrl())
                    .placeholder(R.drawable.women_placeholder)
                    .into(homeAdapterHolder.mIvUnfoldMeizhi);

            //设置相应的数据(折叠时的)
            homeAdapterHolder.mTvFoldTitle.setText(datas.get(position).getDesc());
            homeAdapterHolder.mTvFoldDate.setText(DateUtils.formatDate(datas.get(position).getPublishedAt(), DateUtils.FORMAT_YMD));


            Glide.with(homeAdapterHolder.context).load(datas.get(position).getUrl()).into(homeAdapterHolder.mIvFoldMeizhi);

            homeAdapterHolder.mFcHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeAdapterHolder.mFcHome.toggle(false);
                    registerToggle(position);
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            //Do Nothing Temporary.
        }
    }

    private boolean isUnfold(int position) {
        return mUnfoldedIndex.contains(position);
    }

    @Override
    public int getItemCount() {
        return isDatasNull() ? 0 : datas.size() + 1;
    }

    public void registerToggle(int position) {
        if (mUnfoldedIndex.contains(position))
            mUnfoldedIndex.remove(position);
        else
            mUnfoldedIndex.add(position);
    }

    private boolean isDatasNull() {
        return datas == null;
    }


    private class HomeAdapterHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView mTvUnfoldTitle;
        private final ImageView mIvUnfoldMeizhi;
        private final FoldingCell mFcHome;
        private final TextView mTvFoldTitle;
        private final ImageView mIvFoldMeizhi;
        private final TextView mTvFoldDate;
        private final TextView mTvUnfoldDate;

        public HomeAdapterHolder(View itemView) {
            super(itemView);
            mFcHome = ((FoldingCell) itemView.findViewById(R.id.fc_home));
            mTvUnfoldTitle = (TextView) (mFcHome.findViewById(R.id.tv_item_home_unfold_title));
            mIvUnfoldMeizhi = (ImageView) mFcHome.findViewById(R.id.iv_item_home_unfold_meizhi);
            mTvFoldTitle = (TextView) mFcHome.findViewById(R.id.tv_item_home_fold_title);
            mIvFoldMeizhi = (ImageView) mFcHome.findViewById(R.id.iv_item_home_fold_meizhi);
            mTvFoldDate = (TextView) mFcHome.findViewById(R.id.tv_item_home_fold_date);
            mTvUnfoldDate = (TextView) mFcHome.findViewById(R.id.tv_item_home_unfold_date);
            this.context = itemView.getContext();
        }

    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
