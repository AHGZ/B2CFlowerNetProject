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
 * Created by heguozhong on 2017/12/28/028.
 * 外卖RecyclerView适配器
 */

public class TakeOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentActivity activity;

    public TakeOutAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.item_nearby_shop, parent, false);
        RecyclerView.ViewHolder holder = new MyViewHolder3(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder3 holder3 = (MyViewHolder3) holder;

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    //店铺信息功能布局的viewholder
    public class MyViewHolder3 extends RecyclerView.ViewHolder {

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

        public MyViewHolder3(View itemView) {
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
