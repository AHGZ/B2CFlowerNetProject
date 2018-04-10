package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

/**
 * Created by heguozhong on 2018/1/20/020.
 * 打烊适配器
 */

public class DaYangAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int TYPE_ZERO=0;
    private final static int TYPE_ONE=1;
    private FragmentActivity activity;

    public DaYangAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.da_yang_item, parent, false);
                holder = new MyViewHolder1(view);
                break;
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nearby_shop, parent, false);
                holder = new MyViewHolder2(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_ZERO:
                MyViewHolder1 holder1 = (MyViewHolder1) holder;

                break;
            case TYPE_ONE:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;

                break;

        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ZERO) {
            return TYPE_ZERO;
        } else {
            return TYPE_ONE;
        }
    }
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        public MyViewHolder1(View itemView) {
            super(itemView);
        }
    }
    //店铺信息功能布局的viewholder
    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private final ImageView ivImg;//店铺照片
        private final TextView tvNick;//店铺名称
        private final ImageView ivByTicket;//店铺票
        private final TextView tvBySelf;//店铺支持自取
        private final RatingBarView ratinStar;//店铺星级
        private final TextView yuSale;//店铺月售
        private final TextView tvMinue;//店铺配送时间
        private final TextView tvDistance;//店铺距离
        private final TextView qis;//店铺起送金额
        private final TextView peis;//店铺配送金额
        private final TextView rj;//店铺人均金额
        private final ImageView ivManf;//店铺满返照片
        private final TextView tvManf;//店铺满返内容
        private final ImageView ivHeight;//店铺花返照片
        private final TextView tvHeight;//店铺花返内容

        public MyViewHolder2(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
            tvNick = (TextView) itemView.findViewById(R.id.tv_nick);
            ivByTicket = (ImageView) itemView.findViewById(R.id.iv_by_ticket);
            tvBySelf = (TextView) itemView.findViewById(R.id.tv_by_self);
            ratinStar = (RatingBarView) itemView.findViewById(R.id.ratin_star);
            yuSale = (TextView) itemView.findViewById(R.id.yu_sale);
            tvMinue = (TextView) itemView.findViewById(R.id.tv_minue);
            tvDistance = (TextView) itemView.findViewById(R.id.tv_distance);
            qis = (TextView) itemView.findViewById(R.id.qis);
            peis = (TextView) itemView.findViewById(R.id.peis);
            rj = (TextView) itemView.findViewById(R.id.rj);
            ivManf = (ImageView) itemView.findViewById(R.id.iv_manf);
            tvManf = (TextView) itemView.findViewById(R.id.tv_manf);
            ivHeight = (ImageView) itemView.findViewById(R.id.iv_height);
            tvHeight = (TextView) itemView.findViewById(R.id.tv_height);
        }

    }

}

