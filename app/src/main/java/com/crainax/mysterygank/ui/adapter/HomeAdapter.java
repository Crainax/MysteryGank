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

    private OnHomeItemClickListener onHomeItemClickListener;

    public interface OnHomeItemClickListener {
        void onHomeItemClick(View view, MeizhiEntity meizhiEntity);

        void onAvatarClick(View view, String imageUrl);
    }

    /**
     * OnHomeItemClickListener的Setter
     */
    public void setOnHomeItemClickListener(OnHomeItemClickListener l) {
        this.onHomeItemClickListener = l;
    }

    private static final int VIEW_TYPE_DATAS = 0;

    private static final int VIEW_TYPE_FOOTER = 1;

    private List<MeizhiEntity> datas;

    public void setDatas(List<MeizhiEntity> datas) {
        this.datas = datas;
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
                return new ViewHolder(view);
            case VIEW_TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.item_home_footer, parent, false);
                return new FooterViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {


        if (holder instanceof ViewHolder) {

            final ViewHolder homeAdapterHolder = (ViewHolder) holder;

            //设置相应的数据(展开后的)
            homeAdapterHolder.mTvTitle.setText(datas.get(position).getDesc());
            homeAdapterHolder.mTvDate.setText(
                    DateUtils.formatDate(datas.get(position).getPublishedAt(), DateUtils.FORMAT_YMD));
            Glide.with(homeAdapterHolder.context)
                    .load(datas.get(position).getUrl())
                    .centerCrop()
                    .into(homeAdapterHolder.mIvAvatar);
            attachListener((HomeAdapter.ViewHolder) holder, datas.get(position));

        } else if (holder instanceof FooterViewHolder) {
            //Do Nothing Temporary.
        }
    }

    private void attachListener(ViewHolder holder, final MeizhiEntity meizhiEntity) {
        holder.mClickAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHomeItemClickListener != null) {
                    onHomeItemClickListener.onAvatarClick(v, meizhiEntity.getUrl());
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHomeItemClickListener != null){
                    onHomeItemClickListener.onHomeItemClick(v, meizhiEntity);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return isDatasNull() ? 0 : datas.size() + 1;
    }

    private boolean isDatasNull() {
        return datas == null;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;
        private final TextView mTvTitle;
        private final ImageView mIvAvatar;
        private final TextView mTvDate;
        private final View mClickAvatar;

        public ViewHolder(View itemView) {
            super(itemView);
            mClickAvatar = itemView.findViewById(R.id.click_bg_avatar);
            mTvTitle = (TextView) (itemView.findViewById(R.id.tv_item_home_title));
            mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_home_item_avatar);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_item_home_date);
            this.context = itemView.getContext();
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
