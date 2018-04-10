package com.android.p2pflowernet.project.adapter;

import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 团队优惠适RecyclerView适配器
 */

public class GroupBuyingShopDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ZERO = 0;//商品轮播图
    private static final int TYPE_ONE = 1;//团单内容
    private static final int TYPE_TWO = 2;//商品数量与价格
    private static final int TYPE_THREE = 3;//购买须知
    private static final int TYPE_FOUR = 4;//评价内容
    private static final int TYPE_NINE = 5;//查看全部评价
    private FragmentActivity activity;
    private GroupBuyingBean.InfoBean info;
    //查看图文详情
    OnPhoteDetailsClickListener onPhoteDetailsClickListener;
    //查看评论详情按钮
    OnSelectAllClickListener onSelectAllClickListener;
    //返回按钮
    OnBackClickListener onBackClickListener;
    //分享按钮
    OnShapeClickListener onShapeClickListener;
    //打电话按钮
    OnCallClickListener onCallClickListener;
    private double priceSum;
    private double numSum;
    private MyViewHolder6 holder6;

    //查看图文详情接口
    public interface OnPhoteDetailsClickListener {
        void onPhoteDetailsClick(View v);
    }

    //查看评论详情按钮接口
    public interface OnSelectAllClickListener {
        void onSelectAllClick(View v);
    }

    //返回按钮接口
    public interface OnBackClickListener {
        void onBackClick(View v);
    }

    //分享按钮接口
    public interface OnShapeClickListener {
        void onShapeClick(View v);
    }

    //打电话按钮接口
    public interface OnCallClickListener {
        void onCallClick(View v, String tel);
    }

    //设置查看图文详情点击回调方法
    public void setOnPhoteDetailsClickListener(OnPhoteDetailsClickListener onPhoteDetailsClickListener) {
        this.onPhoteDetailsClickListener = onPhoteDetailsClickListener;
    }

    //设置查看评论详情按钮点击回调方法
    public void setOnSelectAllClickListener(OnSelectAllClickListener onSelectAllClickListener) {
        this.onSelectAllClickListener = onSelectAllClickListener;
    }

    //设置返回按钮点击回调方法
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    //设置分享按钮点击回调方法
    public void setOnShapeClickListenerr(OnShapeClickListener onShapeClickListener) {
        this.onShapeClickListener = onShapeClickListener;
    }

    //设置打电话按钮点击回调方法
    public void setOnCallClickListener(OnCallClickListener onCallClickListener) {
        this.onCallClickListener = onCallClickListener;
    }


    public GroupBuyingShopDetailsAdapter(FragmentActivity activity, GroupBuyingBean.InfoBean info) {
        this.activity = activity;
        this.info = info;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //绑定商品轮播图
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_banner, parent, false);
                holder = new MyViewHolder1(view);
                break;
            //绑定团单内容
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_listview, parent, false);
                holder = new MyViewHolder2(view);
                break;
            //商品数量与价格
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_sum, parent, false);
                holder = new MyViewHolder3(view);
                break;
            //购买须知
            case TYPE_THREE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_gmxz, parent, false);
                holder = new MyViewHolder4(view);
                break;
            //评价内容
            case TYPE_FOUR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_evaluation, parent, false);
                holder = new MyViewHolder5(view);
                break;
            //查看全部评价
            case TYPE_NINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_selectall, parent, false);
                holder = new MyViewHolder6(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //绑定商品轮播图
            case TYPE_ZERO:
                MyViewHolder1 holder1 = (MyViewHolder1) holder;
                List<String> img_list = info.getImg_list();
                ArrayList<String> strings = new ArrayList<>();
                for (int i = 0; i < img_list.size(); i++) {
                    String s = ApiUrlConstant.API_IMG_URL + img_list.get(i);
                    strings.add(s);
                }
                holder1.banner.setImageLoader(new GlideImageLoader())
                        .setImages(strings)
                        .setDelayTime(5000)
                        .start();
                //扩大返回按钮点击范围
                UIUtils.setTouchDelegate(holder1.back, 50);
                //设置返回按钮监听
                holder1.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackClickListener.onBackClick(v);
                    }
                });
                //扩大分享按钮点击范围
                UIUtils.setTouchDelegate(holder1.shape, 50);
                //设置分享按钮监听
                holder1.shape.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShapeClickListener.onShapeClick(v);
                    }
                });
                //给textview设置删除线
                holder1.groupBuyingShopdetailsShopMsj.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //赋值门市价
                holder1.groupBuyingShopdetailsShopMsj.setText("门市价:" + info.getMarket_price() + "元");
                //赋值售价
                holder1.groupBuyingShopdetailsShopPrice.setText(info.getPrice() + "");
                //赋值花返价
                holder1.groupBuyingShopdetailsShopHuafanPrice.setText("花返:" + String.format("%.2f", (Double.parseDouble(info.getPrice()) - Double.parseDouble(info.getSupply_price()))) + "元");
                //赋值标题
                holder1.title.setText(info.getTitle());
                //赋值已售份数
                holder1.groupBuyingShopdetailsShopYs.setText("已售" + info.getSold_num());
                //是否支持打包(0不支持1支持)
                if (info.getIs_takeaway() == 0) {
                    holder1.zcdbRelat.setVisibility(View.GONE);
                } else {
                    holder1.zcdbRelat.setVisibility(View.VISIBLE);
                }
                break;
            //绑定团单内容
            case TYPE_ONE:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;
                //赋值商家名称
                holder2.groupBuyingShopdetailsShopName.setText(info.getMerch_info().getName());
                //赋值商家地址
                holder2.groupBuyingShopdetailsShopAddress.setText(info.getMerch_info().getAddress());
                //赋值距离商家距离
                holder2.groupBuyingShopdetailsShopMi.setText(info.getMerch_info().getDistance() + "m");

                //设置打电话按钮监听
                holder2.callPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCallClickListener != null) {
                            //将店铺电话回调
                            onCallClickListener.onCallClick(v, info.getMerch_info().getTel());
                        }
                    }
                });
                List<GroupBuyingBean.InfoBean.GoodsListBean> goodsList = info.getGoods_list();
                GroupBuyingShopListAdapter groupBuyingShopListAdapter = new GroupBuyingShopListAdapter(goodsList);
                holder2.listView.setAdapter(groupBuyingShopListAdapter);
                break;
            //商品数量与价格
            case TYPE_TWO:
                MyViewHolder3 holder3 = (MyViewHolder3) holder;
                List<GroupBuyingBean.InfoBean.GoodsListBean> goods_list = info.getGoods_list();
                for (int i = 0; i < goods_list.size(); i++) {
                    Double price = Double.parseDouble(goods_list.get(i).getPrice());
                    double num = Double.parseDouble(goods_list.get(i).getNum());
                    priceSum = priceSum + price;
                    numSum = numSum + num;
                }
                holder3.groupBuyingShopdetailsSumPrice.setText("¥" + String.format("%.2f", priceSum));
                holder3.groupBuyingShopdetailsSumNum.setText(String.format("%.0f", numSum) + "份");
                holder3.groupBuyingShopdetailsTwxq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPhoteDetailsClickListener.onPhoteDetailsClick(v);
                    }
                });
                break;
            //购买须知
            case TYPE_THREE:
                MyViewHolder4 holder4 = (MyViewHolder4) holder;
                List<GroupBuyingBean.InfoBean.NoticeBean> notice = info.getNotice();
                GoShoppingKnowAdapter goShoppingKnowAdapter = new GoShoppingKnowAdapter(notice);
                holder4.myListView.setAdapter(goShoppingKnowAdapter);
                break;
            //评价内容
            case TYPE_FOUR:
                MyViewHolder5 holder5 = (MyViewHolder5) holder;
                if (info.getEval_list().size() <=0){
                    holder5.pingfenLin.setVisibility(View.GONE);
                }
                if (info.getEval_list().size() >= 3) {
                    holder6.selectAll.setVisibility(View.VISIBLE);
                }

                holder5.count.setText(info.getEval_score());
                GroupEvaluationListiviewAdapter groupEvaluationListiviewAdapter = new GroupEvaluationListiviewAdapter(activity, info);
                holder5.listView.setAdapter(groupEvaluationListiviewAdapter);
                break;

            //查看全部评价
            case TYPE_NINE:
                holder6 = (MyViewHolder6) holder;

                holder6.selectAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSelectAllClickListener != null) {
                            onSelectAllClickListener.onSelectAllClick(v);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ZERO) {
            return TYPE_ZERO;
        } else if (position == TYPE_ONE) {
            return TYPE_ONE;
        } else if (position == TYPE_TWO) {
            return TYPE_TWO;
        } else if (position == TYPE_THREE) {
            return TYPE_THREE;
        } else if (position == TYPE_FOUR) {
            return TYPE_FOUR;
        } else {
            return TYPE_NINE;
        }
    }

    //商品轮播图
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        private final Banner banner;//商品展示轮播
        private final ImageView back;//返回按钮
        private final ImageView shape;//分享按钮
        private final TextView title;//商品标题
        private final TextView groupBuyingShopdetailsShopPrice;//商品价格
        private final TextView groupBuyingShopdetailsShopHuafanPrice;//商品花返金额
        private final TextView groupBuyingShopdetailsShopMsj;//商品门市价
        private final TextView groupBuyingShopdetailsShopYs;//商品已售数量

        private final RelativeLayout zcdbRelat;//支持打包RelativeLayout

        public MyViewHolder1(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.group_buying_shopdetails_banner);
            back = (ImageView) itemView.findViewById(R.id.group_buying_shopdetails_back);
            shape = (ImageView) itemView.findViewById(R.id.group_buying_shopdetails_shape);
            title = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_title);
            groupBuyingShopdetailsShopPrice = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_price);
            groupBuyingShopdetailsShopHuafanPrice = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_huafan_price);
            groupBuyingShopdetailsShopMsj = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_msj);
            groupBuyingShopdetailsShopYs = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_ys);
            zcdbRelat = (RelativeLayout) itemView.findViewById(R.id.group_buying_shopdetails_zcdb_relat);
        }
    }

    //团单内容
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        private final MyListView listView;//用来展示团单内容的listview
        private final TextView groupBuyingShopdetailsShopName;//商店名称
        private final TextView groupBuyingShopdetailsShopAddress;//商店地址
        private final TextView groupBuyingShopdetailsShopMi;//商店距离
        private final ImageView callPhone;//打电话

        public MyViewHolder2(View itemView) {
            super(itemView);
            listView = (MyListView) itemView.findViewById(R.id.group_buying_shopdetails_tuandan_listview);
            groupBuyingShopdetailsShopName = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_name);
            groupBuyingShopdetailsShopAddress = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_address);
            groupBuyingShopdetailsShopMi = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_shop_mi);
            callPhone = (ImageView) itemView.findViewById(R.id.group_call_phone);
        }
    }

    //商品数量与价格
    public class MyViewHolder3 extends RecyclerView.ViewHolder {

        private final TextView groupBuyingShopdetailsSumNum;//购买商品的总份数
        private final TextView groupBuyingShopdetailsSumPrice;//购买商品的总价格
        private final LinearLayout groupBuyingShopdetailsTwxq;//图文详情

        public MyViewHolder3(View itemView) {
            super(itemView);
            groupBuyingShopdetailsSumNum = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_sum_num);
            groupBuyingShopdetailsSumPrice = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_sum_price);
            groupBuyingShopdetailsTwxq = (LinearLayout) itemView.findViewById(R.id.group_buying_shopdetails_twxq);
        }
    }

    //购买须知
    public class MyViewHolder4 extends RecyclerView.ViewHolder {

        private final MyListView myListView;

        public MyViewHolder4(View itemView) {
            super(itemView);
            myListView = (MyListView) itemView.findViewById(R.id.group_buying_goshoppping_know_listview);
        }
    }

    //评价内容
    public class MyViewHolder5 extends RecyclerView.ViewHolder {

        private final MyListView listView;
        private final TextView count;//评价分数
        private final TextView num;//评价条数
        private final LinearLayout pingfenLin;//评分linearlayou布局

        public MyViewHolder5(View itemView) {
            super(itemView);
            listView = (MyListView) itemView.findViewById(R.id.group_buying_shopdetails_listview);
            count = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_evaluation_count);
            num = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_evaluation_num);
            pingfenLin = (LinearLayout) itemView.findViewById(R.id.group_buying_shopdetails_pingfen_lin);
        }
    }

    //查看全部评价
    public class MyViewHolder6 extends RecyclerView.ViewHolder {

        private final TextView selectAll;//查看全部评价按钮

        public MyViewHolder6(View itemView) {
            super(itemView);
            selectAll = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_selectall);
        }
    }

}
