package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.p2pflowernet.project.R;


/**
 * Created by caishen on 2017/10/19.
 * by--商品--首页的适配器
 */

public class BrandHomeadApter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final FragmentActivity mContext;
    private final LayoutInflater mLayoutInflater;
    private OnRecyclerViewListener mOnItemClickListener;

    public BrandHomeadApter(FragmentActivity activity) {
        this.mContext = activity;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //recyclerview的点击回调接口
    public interface OnRecyclerViewListener {

        void onItemClick(View view, int position);//单击

        void onItemLongClick(View view, int position);//长按

    }

    public void setOnRecyclerViewListener(OnRecyclerViewListener mOnRecyclerViewListener) {
        this.mOnItemClickListener = mOnRecyclerViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GoodsViewHolder(mLayoutInflater.inflate(R.layout.brand_home, null));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        GoodsViewHolder bannerHolder = (GoodsViewHolder) holder;
        bannerHolder.setData(mContext, position);

        if (holder != null) {//

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    private class GoodsViewHolder extends RecyclerView.ViewHolder {
        public GoodsViewHolder(View inflate) {
            super(inflate);
        }

        //设置数据
        public void setData(FragmentActivity mContext, int position) {

        }
    }
}
