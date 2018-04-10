package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeCateThreeBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.view.customview.RatingBarView;

import java.util.List;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 美食RecyclerView适配器
 */

public class CateAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private FragmentActivity activity;
    private List<TakeCateThreeBean.ListBean> list;

    OnEveryItemClickListener onEveryItemClickListener;
    public interface OnEveryItemClickListener{
        void onItemClick(View view,int position);
    }

    public void setOnEveryItemClickListener(OnEveryItemClickListener onEveryItemClickListener){
        this.onEveryItemClickListener=onEveryItemClickListener;
    }

    public CateAdapter(FragmentActivity activity, List<TakeCateThreeBean.ListBean> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.item_nearby_shop, parent, false);
        RecyclerView.ViewHolder holder = new MyViewHolder1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //店铺信息功能布局的viewholder
        MyViewHolder1 holder1 = (MyViewHolder1) holder;

        //赋值店铺logo
        String file_path = ApiUrlConstant.API_IMG_URL + list.get(position).getFile_path();
        new GlideImageLoader().displayImage(activity, file_path, holder1.ivImg);
        //赋值店铺名称
        holder1.tvNick.setText(list.get(position).getMerch_name());
        //赋值店铺距离
        holder1.tvDistance.setText(list.get(position).getDistance()+"m");
        //赋值起送金额
        holder1.qis.setText("起送 ¥"+list.get(position).getDistrib_quota());
        //赋值预计送达时间
        holder1.tvMinue.setText(list.get(position).getService_time()+"分钟");
        //赋值配送费用
        holder1.peis.setText("配送 ¥"+list.get(position).getDistrib_money());
        //赋值月售
        holder1.yuSale.setText("月售"+list.get(position).getMonth_sale());
        // 赋值店铺星级
        holder1.ratinStar.setStar((int) Float.parseFloat(list.get(position).getEval_score()),true);
        //发票 0:不允许，1允许
        if (list.get(position).getInvoice_setting()==0){
            holder1.ivByTicket.setVisibility(View.GONE);
        }else{
            holder1.ivByTicket.setVisibility(View.VISIBLE);
        }

        //自取 0：不允许，1允许
        if (list.get(position).getSelf_pick_setting()==0){
            holder1.tvBySelf.setVisibility(View.GONE);
        }else{
            holder1.tvBySelf.setVisibility(View.VISIBLE);
        }

        //设置每个item的点击事件
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEveryItemClickListener!=null){
                    onEveryItemClickListener.onItemClick(v,position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    //店铺信息功能布局的viewholder
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

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
        private final View itemView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            this.itemView=itemView;
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
