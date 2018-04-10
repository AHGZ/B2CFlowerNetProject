package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.IndexO2oBean;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/28.
 * by--附近商家的适配器
 */

public class NearbyShopAdapter extends HFWBaseAdapter<IndexO2oBean> {
    private final FragmentActivity mContext;

    public NearbyShopAdapter(FragmentActivity mContext) {
        this.mContext = mContext;
    }

    @Override
    public BaseHolder<IndexO2oBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        return new NearbyShopViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_shop, parent, false));
    }

    @Override
    public void onViewHolderBind(BaseHolder<IndexO2oBean> holder, int position) {

        IndexO2oBean indexO2oBean = list.get(position);
        ((NearbyShopAdapter.NearbyShopViewHolder) holder).setData(indexO2oBean, position);
    }

    static class NearbyShopViewHolder extends BaseHolder<IndexO2oBean> {

        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.iv_by_ticket)
        ImageView ivByTicket;
        @BindView(R.id.tv_by_self)
        TextView tvBySelf;
        @BindView(R.id.ratin_star)
        RatingBarView ratinStar;
        @BindView(R.id.yu_sale)
        TextView yuSale;
        @BindView(R.id.tv_minue)
        TextView tvMinue;
        @BindView(R.id.tv_distance)
        TextView tvDistance;
        @BindView(R.id.qis)
        TextView qis;
        @BindView(R.id.peis)
        TextView peis;
        @BindView(R.id.rj)
        TextView rj;
        @BindView(R.id.iv_manf)
        ImageView ivManf;
        @BindView(R.id.tv_manf)
        TextView tvManf;
        @BindView(R.id.iv_height)
        ImageView ivHeight;
        @BindView(R.id.tv_height)
        TextView tvHeight;
        @BindView(R.id.tv_nick)
        TextView tvNick;

        NearbyShopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void setData(IndexO2oBean data, int position) {

            tvNick.setText(data.getNickname());
            ratinStar.setStar(5, false);
            yuSale.setText(data.getYues());
            tvMinue.setText(data.getMinue());
            qis.setText("起送" + data.getQis());
            peis.setText("配送" + data.getPeis());
            rj.setText("人均" + data.getRj());
            tvManf.setText(data.getAtivity());
            tvHeight.setText(data.getHeight());
        }
    }
}
