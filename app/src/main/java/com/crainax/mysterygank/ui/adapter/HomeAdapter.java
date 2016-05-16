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
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterHolder> {

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

    @Override
    public int getItemViewType(int position) {

        if (position <= datas.size())
            return VIEW_TYPE_DATAS;
        else if (position == datas.size() + 1)
            return VIEW_TYPE_FOOTER;
        else
            throw new IllegalStateException("The View Type is Illegal!");

    }

    @Override
    public HomeAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = null;
        switch (viewType) {
            case VIEW_TYPE_DATAS:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_piece, parent, false);
                return new HomeAdapterHolder(view);
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_footer, parent, false);
                return null;
        }
        //// TODO: 2016/5/15 完成FOOTER

        return null;
    }

    @Override
    public void onBindViewHolder(final HomeAdapterHolder holder, final int position) {
        //判断缓存中是否是折叠状态去设置相应的状态
        if (isUnfold(position))
            holder.mFcHome.unfold(true);
        else
            holder.mFcHome.fold(true);

        //设置相应的数据
        holder.mTvContentTitle.setText(datas.get(position).getDesc());
        Glide.with(holder.context).load(datas.get(position).getUrl()).into(holder.mIvContentMeizhi);
        holder.mTvTitleTitle.setText(datas.get(position).getDesc());
        Glide.with(holder.context).load(datas.get(position).getUrl()).into(holder.mIvTitleMeizhi);

        holder.mFcHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mFcHome.toggle(false);
                registerToggle(position);
            }
        });
    }

    private boolean isUnfold(int position) {
        return mUnfoldedIndex.contains(position);
    }

    @Override
    public int getItemCount() {
        return isDatasNull() ? 0 : datas.size();
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

    class HomeAdapterHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView mTvContentTitle;
        private final ImageView mIvContentMeizhi;
        private final FoldingCell mFcHome;
        private final TextView mTvTitleTitle;
        private final ImageView mIvTitleMeizhi;

        public HomeAdapterHolder(View itemView) {
            super(itemView);
            mFcHome = ((FoldingCell) itemView.findViewById(R.id.fc_home));
            mTvContentTitle = (TextView) (mFcHome.findViewById(R.id.tv_content_title));
            mIvContentMeizhi = (ImageView) mFcHome.findViewById(R.id.iv_content_meizhi);
            mTvTitleTitle = (TextView) mFcHome.findViewById(R.id.tv_title_title);
            mIvTitleMeizhi = (ImageView) mFcHome.findViewById(R.id.iv_title_meizhi);
            this.context = itemView.getContext();
        }

    }

}
