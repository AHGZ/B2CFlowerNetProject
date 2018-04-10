package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 团队优惠适RecyclerView适配器
 */

public class GroupBuyingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<GroupHomeBean.ListBean> listBeans;

    public GroupBuyingAdapter(Context mContext, List<GroupHomeBean.ListBean> listBeans) {
        this.mContext = mContext;
        this.listBeans = listBeans;
    }

    //item点击
    OnEveryItemClickListener onEveryItemClickListener;

    public interface OnEveryItemClickListener {
        void onEveryItemClick(View v, int position);
    }

    //item点击调用方法
    public void setOnEveryItemClickListener(OnEveryItemClickListener onEveryItemClickListener) {
        this.onEveryItemClickListener = onEveryItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定食品信息布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_recyclerview_item_food, parent, false);
        RecyclerView.ViewHolder holder = new MyViewHolder1(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //食品信息Viewholder
        MyViewHolder1 holder1 = (MyViewHolder1) holder;
        //item点击事件
        holder1.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEveryItemClickListener != null) {
                    onEveryItemClickListener.onEveryItemClick(v, position);
                }
            }
        });
        GroupHomeBean.ListBean bean = listBeans.get(position);
        new GlideImageLoader().displayImage(mContext, ApiUrlConstant.API_IMG_URL + bean.getImgs(),holder1.groupBuyingItemIvImg);
        holder1.groupBuyingItemTvNick.setText(TextUtils.isEmpty(bean.getTitle()) ? "" : bean.getTitle());
        holder1.groupBuyingItemMeter.setText(bean.getDistance()+ "米");
        holder1.groupBuyingItemYuSale.setText(TextUtils.isEmpty(bean.getConsume_avg()) ? "" :"¥" + bean.getConsume_avg() + "/人");
        holder1.groupBuyingItemYisNum.setText("已售" + bean.getSold_num());
        holder1.groupBuyingItemXianjia.setText(TextUtils.isEmpty(bean.getPrice()) ? "" : "¥" + bean.getPrice());
        holder1.groupBuyingItemMsj.setText(TextUtils.isEmpty(bean.getMarket_price()) ? "" : "门市价：¥" + bean.getMarket_price());
        holder1.groupBuyingItemHuafanPrice.setText(TextUtils.isEmpty(bean.getHuafan()) ? "" : "花返：¥" + bean.getHuafan() + "元");
        holder1.groupBuyingItemShopName.setText(TextUtils.isEmpty(bean.getMerch_name())? "" : bean.getMerch_name());
        if (!TextUtils.isEmpty(bean.getIs_new())) {
            if ("0".equals(bean.getIs_new())) {
                holder1.groupBuyingItemTextView.setVisibility(View.GONE);
            }else if ("1".equals(bean.getIs_new())){
                holder1.groupBuyingItemTextView.setVisibility(View.VISIBLE);
            }
        }else{
            holder1.groupBuyingItemTextView.setVisibility(View.GONE);
        }
        //给textview设置删除线
        holder1.groupBuyingItemMsj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return listBeans.size() > 0 ? listBeans.size() : 0;
    }
    //食品信息Viewholder
    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        private final ImageView groupBuyingItemIvImg;//商品图标
        private final TextView groupBuyingItemTvNick;//商品名称
        private final ImageView groupBuyingItemIvByTicket;//距离图标
        private final TextView groupBuyingItemMeter;//距离米数
        private final TextView groupBuyingItemShopName;//商店名称
        private final TextView groupBuyingItemYuSale;//人均钱数
        private final ImageView groupBuyingItemYisImg;//已售图标
        private final TextView groupBuyingItemYisNum;//已售数量
        private final TextView groupBuyingItemXianjia;//商品现价
        private final TextView groupBuyingItemMsj;//商品门市价
        private final TextView groupBuyingItemHuafanPrice;//商品花返价格
        private final TextView groupBuyingItemTextView;//是否新品
        private View itemView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            this.itemView = itemView;
            groupBuyingItemIvImg = (ImageView) itemView.findViewById((R.id.group_buying_item_iv_img));
            groupBuyingItemTvNick = (TextView) itemView.findViewById((R.id.group_buying_item_tv_nick));
            groupBuyingItemIvByTicket = (ImageView) itemView.findViewById((R.id.group_buying_item_iv_by_ticket));
            groupBuyingItemMeter = (TextView) itemView.findViewById((R.id.group_buying_item_meter));
            groupBuyingItemShopName = (TextView) itemView.findViewById((R.id.group_buying_item_shop_name));
            groupBuyingItemYuSale = (TextView) itemView.findViewById((R.id.group_buying_item_yu_sale));
            groupBuyingItemYisImg = (ImageView) itemView.findViewById((R.id.group_buying_item_yis_img));
            groupBuyingItemYisNum = (TextView) itemView.findViewById((R.id.group_buying_item_yis_num));
            groupBuyingItemXianjia = (TextView) itemView.findViewById((R.id.group_buying_item_xianjia));
            groupBuyingItemMsj = (TextView) itemView.findViewById((R.id.group_buying_item_msj));
            groupBuyingItemHuafanPrice = (TextView) itemView.findViewById((R.id.group_buying_item_huafan_price));
            groupBuyingItemTextView = (TextView) itemView.findViewById(R.id.group_buying_item_tv_new);
        }
    }
}
