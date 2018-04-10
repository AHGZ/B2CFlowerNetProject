package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.ShopCart;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by heguozhong on 2018/1/8/008.
 * 确认订单适配器
 */

public class ConfirmOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String reachTime;
    //添加地址
    OnAddressClickListener onAddressClickListener;
    //自取时间
    OnZqTimeClickListener onZqTimeClickListener;
    //餐具选择
    OnCjSelectClickListener onCjSelectClickListener;
    //到店自取选项框
    OnZQCheckClickListener onZQCheckClickListener;
    //立即送出
    OnLjscClickListener onLjscClickListener;
    // 备注/发票
    OnBzFpClickListener onBzFpClickListener;
    private MyViewHolder3 holder3;
    private MyViewHolder2 holder2;

    private TextView confirmOrderTvAddress;
    private TextView confirmOrderTvPeople;


    private TextView confirmOrderTvPhone;
    private O2oIndexBean o2oIndexBean1;


    // 餐具数量
    private TextView confirmOrderCjSum;

    public TextView getConfirmOrderTvAddress() {
        return confirmOrderTvAddress;
    }

    public void setConfirmOrderTvAddress(TextView confirmOrderTvAddress) {
        this.confirmOrderTvAddress = confirmOrderTvAddress;
    }

    public TextView getConfirmOrderTvPeople() {
        return confirmOrderTvPeople;
    }

    public void setConfirmOrderTvPeople(TextView confirmOrderTvPeople) {
        this.confirmOrderTvPeople = confirmOrderTvPeople;
    }

    public TextView getConfirmOrderTvPhone() {
        return confirmOrderTvPhone;
    }

    public void setConfirmOrderTvPhone(TextView confirmOrderTvPhone) {
        this.confirmOrderTvPhone = confirmOrderTvPhone;
    }

    public TextView getConfirmOrderCjSum() {
        return confirmOrderCjSum;
    }

    public void setConfirmOrderCjSum(TextView confirmOrderCjSum) {
        this.confirmOrderCjSum = confirmOrderCjSum;
    }

    //设置添加地址
    public interface OnAddressClickListener {
        void onAddress(View v);
    }

    //设置自取时间接口
    public interface OnZqTimeClickListener {
        void onZqTimeClick(View v);
    }

    //设置餐具选择接口
    public interface OnCjSelectClickListener {
        void onCjSelectClick(View v);
    }

    //设置到店自取选项框接口
    public interface OnZQCheckClickListener {
        void onZQCheckClick(View v, CheckBox checkBox);
    }

    //设置立即送出接口
    public interface OnLjscClickListener {
        void onLjscClick(View v);
    }

    // 备注/发票接口
    public interface OnBzFpClickListener {
        void onBzFpClick(View v);
    }

    //设置添加地址
    public void setOnAddressClickListener(OnAddressClickListener onAddressClickListener) {
        this.onAddressClickListener = onAddressClickListener;
    }

    //设置自取时间监听
    public void setOnZqTimeClickListener(OnZqTimeClickListener onZqTimeClickListener) {
        this.onZqTimeClickListener = onZqTimeClickListener;
    }

    //设置餐具选择监听
    public void setOnCjSelectClickListener(OnCjSelectClickListener onCjSelectClickListener) {
        this.onCjSelectClickListener = onCjSelectClickListener;
    }

    //设置到店自取选项框监听
    public void setOnZQCheckClickListener(OnZQCheckClickListener onZQCheckClickListener) {
        this.onZQCheckClickListener = onZQCheckClickListener;
    }

    //设置立即送出监听
    public void setOnLjscClickListener(OnLjscClickListener onLjscClickListener) {
        this.onLjscClickListener = onLjscClickListener;
    }

    //设置备注/发票监听
    public void setOnBzFpClickListener(OnBzFpClickListener onBzFpClickListener) {
        this.onBzFpClickListener = onBzFpClickListener;
    }

    private static final int TYPE_ZERO = 0;//地址
    private static final int TYPE_ONE = 1;//商品信息
    private static final int TYPE_TWO = 2;//餐具数量

    private Context mContext;
    ShopCart shopCart;
    GoPayBean.AddInfoBean mMerChantBean;
    GoPayBean goPayBean;

    public ConfirmOrderAdapter(Context context, ShopCart
            shopCart, GoPayBean.AddInfoBean merchantbean, String reachTime, O2oIndexBean o2oIndexBean1, GoPayBean goPayBean) {
        this.mContext = context;
        this.shopCart = shopCart;
        this.mMerChantBean = merchantbean;
        this.reachTime = reachTime;
        this.o2oIndexBean1 = o2oIndexBean1;
        this.goPayBean = goPayBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //地址
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_item_address, parent, false);
                holder = new MyViewHolder1(view);
                break;
            //商品信息
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_item_shop, parent, false);
                holder = new MyViewHolder2(view);
                break;
            //餐具数量
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_order_item_cjbz, parent, false);
                holder = new MyViewHolder3(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //地址
            case TYPE_ZERO:
                final MyViewHolder1 holder1 = (MyViewHolder1) holder;

                //设置预约到达事件
                holder1.confirmOrderAddressLjsctime.setText("预约送达时间" + reachTime);

                if (mMerChantBean == null) {
                    break;
                }

                // 收货地址
                holder1.confirmOrderAddressTv.setText(TextUtils.isEmpty(mMerChantBean.getName() + mMerChantBean.getLocation()) ? "" :
                        mMerChantBean.getName() + mMerChantBean.getLocation());

                // 收货联系电话
                holder1.confirmOrderAddressZqPhone.setText(TextUtils.isEmpty(mMerChantBean.getTelephone()) ? ""
                        : "自取电话：" + mMerChantBean.getTelephone());

                //是否支持到店自提0-不允许 1-允许
                if (goPayBean.getIs_self() == 0) {
                    holder1.linDdzq.setVisibility(View.GONE);
                    holder1.ddzqView.setVisibility(View.GONE);
                }

                //设置到店自取按钮点击事件，完成布局的显示与隐藏
                holder1.confirmOrderCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (onZQCheckClickListener != null) {
                            onZQCheckClickListener.onZQCheckClick(buttonView, holder1.confirmOrderCheck);
                        }
                        if (isChecked) {
                            holder1.confirmOrderRel.setVisibility(View.GONE);
                            holder1.confirmOrderLin.setVisibility(View.GONE);
                            holder1.confirmOrderAddressView.setVisibility(View.GONE);
                            holder3.confirmOrderCjbzLin.setVisibility(View.GONE);
                            holder1.confirmOrderLinAddress.setVisibility(View.VISIBLE);
                            holder1.confirmOrderLinZqphone.setVisibility(View.VISIBLE);
                            holder1.confirmOrderLinZqtime.setVisibility(View.VISIBLE);
                            holder1.confirmOrderLinAgree.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressTs.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressView2.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressView3.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressView4.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressView5.setVisibility(View.VISIBLE);
                            holder2.zczqTv.setVisibility(View.VISIBLE);
                        } else {
                            holder1.confirmOrderRel.setVisibility(View.VISIBLE);
                            holder1.confirmOrderLin.setVisibility(View.VISIBLE);
                            holder1.confirmOrderAddressView.setVisibility(View.VISIBLE);
                            holder3.confirmOrderCjbzLin.setVisibility(View.VISIBLE);
                            holder1.confirmOrderLinAddress.setVisibility(View.GONE);
                            holder1.confirmOrderLinZqphone.setVisibility(View.GONE);
                            holder1.confirmOrderLinZqtime.setVisibility(View.GONE);
                            holder1.confirmOrderLinAgree.setVisibility(View.GONE);
                            holder1.confirmOrderAddressTs.setVisibility(View.GONE);
                            holder1.confirmOrderAddressView2.setVisibility(View.GONE);
                            holder1.confirmOrderAddressView3.setVisibility(View.GONE);
                            holder1.confirmOrderAddressView4.setVisibility(View.GONE);
                            holder1.confirmOrderAddressView5.setVisibility(View.GONE);
                            holder2.zczqTv.setVisibility(View.GONE);
                        }
                    }
                });

                setConfirmOrderTvAddress(holder1.confirmOrderTvAddress);
                setConfirmOrderTvPeople(holder1.confirmOrderTvPeople);
                setConfirmOrderTvPhone(holder1.confirmOrderTvPhone);

                // 添加地址
                holder1.confirmOrderRel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAddressClickListener != null)
                            onAddressClickListener.onAddress(v);
                    }
                });
                //设置自取时间监听
                holder1.confirmOrderLinZqtime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onZqTimeClickListener != null) {
                            onZqTimeClickListener.onZqTimeClick(v);
                        }
                    }
                });
                //设置立即送出监听
                holder1.confirmOrderLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onLjscClickListener != null) {
                            onLjscClickListener.onLjscClick(v);
                        }
                    }
                });
                break;
            //商品信息
            case TYPE_ONE:
                holder2 = (MyViewHolder2) holder;
                // 店铺logo
                String logo_url = ApiUrlConstant.API_IMG_URL + o2oIndexBean1.getLogo_url();
                Glide.with(mContext).load(logo_url).asBitmap().centerCrop().placeholder(R.mipmap.default_header).into(new BitmapImageViewTarget(holder2.confirmOrderShopImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder2.confirmOrderShopImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
                holder2.confirmOrderShopName.setText(o2oIndexBean1.getBusiness_name());
//                String logo_url = ApiUrlConstant.O2OAPI_IMG_URL + mMerChantBean.getInfo().;
//                new RxImageLoader().display(holder2.confirmOrderListviewImg, logo_url,
//                        DensityUtil.getDeviceWidth(holder2.confirmOrderListviewImg.getContext()) / 3,
//                        DensityUtil.getDeviceWidth(holder2.confirmOrderListviewImg.getContext()) / 3);
                //设置展示购买商品详情的适配器
                holder2.myRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                final OrderGoodsAdapter orderGoodsAdapter = new OrderGoodsAdapter(mContext, shopCart);
                holder2.myRecyclerView.setAdapter(orderGoodsAdapter);

                if (shopCart.getDishAccount() >= 2) {
                    holder2.linAll.setVisibility(View.VISIBLE);
                }
                //设置展开全部按钮监听事件
                holder2.linAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 判断getCount()数据的数量，如果等于3点击后就设置getCount()为全部数量，设置修改标识，刷新。
                        // 否则，相反。
                        if (orderGoodsAdapter.getItemCount() == 2) {
                            holder2.linAll.setVisibility(View.VISIBLE);
                            holder2.allTv.setText("收起全部");
                            orderGoodsAdapter.notifyDataSetChanged();
                        } else {
                            holder2.linAll.setVisibility(View.VISIBLE);
                            holder2.allTv.setText("展开全部");
                            orderGoodsAdapter.notifyDataSetChanged();
                        }
                    }
                });
                break;
            //餐具数量
            case TYPE_TWO:
                holder3 = (MyViewHolder3) holder;
                //设置备注/发票监听
                holder3.confirmOrderCjLin2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBzFpClickListener != null) {
                            onBzFpClickListener.onBzFpClick(v);
                        }
                    }
                });


                //设置餐具选择监听
                holder3.confirmOrderCjLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCjSelectClickListener != null) {
                            onCjSelectClickListener.onCjSelectClick(v);
                        }
                    }
                });

                // 总价
                holder3.confirmOrderCjbzXjsum.setText(String.valueOf(shopCart.getShoppingTotalPrice()));
                holder3.confirmOrderCjbzHfyh.setText(String.valueOf(shopCart.getShoppingTotalPrice()));
                holder3.confirmOrderCjbzPsf.setText(o2oIndexBean1.getDistrib_money() == null ? "¥"
                        : "¥" + o2oIndexBean1.getDistrib_money());//配送费
                showBzf(holder3.confirmOrderCjbzBzf);
                showHfyh(holder3.confirmOrderCjbzHfyh);
                setConfirmOrderCjSum(holder3.confirmOrderCjbzXjsum);

                break;

        }
    }

    //花返优惠
    private void showHfyh(TextView confirmOrderCjbzHfyh) {

        double mtotalPrice = 0.00;
        List<O2oIndexBean.ListsBean.GoodsListBean> dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
        for (int i = 0; i < dishList.size(); i++) {

            double replace = Double.parseDouble(dishList.get(i).getHuafan().replace(",", ""));
            // 商品数量
            int num = shopCart.getShoppingSingleMap().get(dishList.get(i));
            mtotalPrice += replace * num;
            BigDecimal bg = new BigDecimal(mtotalPrice);
            mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
        confirmOrderCjbzHfyh.setText("¥" + mtotalPrice);
    }

    //包装费
    private void showBzf(TextView confirmOrderCjbzBzf) {

        double mtotalPrice = 0.00;
        int num = 0;
        List<O2oIndexBean.ListsBean.GoodsListBean> dishList = new ArrayList<>();
        dishList.addAll(shopCart.getShoppingSingleMap().keySet());
        for (int i = 0; i < dishList.size(); i++) {

            double replace = Double.parseDouble(dishList.get(i).getBox_price().replace(",", ""));
            mtotalPrice += replace * Integer.parseInt(dishList.get(i).getBox_num());
            BigDecimal bg = new BigDecimal(mtotalPrice);
            mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            num += shopCart.getShoppingSingleMap().get(dishList.get(i));
        }

        double v = mtotalPrice * num;
        BigDecimal bg = new BigDecimal(v);
        confirmOrderCjbzBzf.setText("¥" + bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ZERO) {
            return TYPE_ZERO;//地址
        } else if (position == TYPE_ONE) {
            return TYPE_ONE;//商品信息
        } else {
            return TYPE_TWO;//餐具数量
        }
    }

    //地址viewholder
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        private final RelativeLayout confirmOrderRel;
        private final LinearLayout confirmOrderLin;
        private final LinearLayout confirmOrderLinAddress;
        private final LinearLayout confirmOrderLinZqphone;
        private final LinearLayout confirmOrderLinZqtime;
        private final LinearLayout confirmOrderLinAgree;
        private final View confirmOrderAddressView;
        private final View confirmOrderAddressView2;
        private final View confirmOrderAddressView3;
        private final View confirmOrderAddressView4;
        private final View confirmOrderAddressView5;
        private final TextView confirmOrderTvAddress;//地址
        private final TextView confirmOrderTvPeople;//联系人
        private final TextView confirmOrderTvPhone;//电话
        private final TextView confirmOrderAddressLjsctime;//立即送出时间
        private final TextView confirmOrderAddressTv;//到店自提地址
        private final TextView confirmOrderAddressZqPhone;//到店自提电话
        private final TextView confirmOrderAddressZqTime;//到店自提自取时间
        private final TextView confirmOrderAddressTs;//到店自提温馨提示
        private final CheckBox confirmOrderCheck;//到店自取选择框
        private final CheckBox confirmOrderAddressCheckAgree;//到店自取同意协议
        private final LinearLayout linDdzq;//到店自取linearlayout布局
        private final View ddzqView;

        public MyViewHolder1(View itemView) {
            super(itemView);
            ddzqView = itemView.findViewById(R.id.ddzq_view);
            linDdzq = (LinearLayout) itemView.findViewById(R.id.lin_ddzq);
            confirmOrderRel = (RelativeLayout) itemView.findViewById(R.id.confirm_order_address_rel);
            confirmOrderLin = (LinearLayout) itemView.findViewById(R.id.confirm_order_address_lin);
            confirmOrderLinAddress = (LinearLayout) itemView.findViewById(R.id.confirm_order_address_lin_address);
            confirmOrderLinZqphone = (LinearLayout) itemView.findViewById(R.id.confirm_order_address_lin_zqphone);
            confirmOrderLinZqtime = (LinearLayout) itemView.findViewById(R.id.confirm_order_address_lin_zqtime);
            confirmOrderLinAgree = (LinearLayout) itemView.findViewById(R.id.confirm_order_address_lin_agree);
            confirmOrderAddressView = itemView.findViewById(R.id.confirm_order_address_view);
            confirmOrderAddressView2 = itemView.findViewById(R.id.confirm_order_address_view2);
            confirmOrderAddressView3 = itemView.findViewById(R.id.confirm_order_address_view3);
            confirmOrderAddressView4 = itemView.findViewById(R.id.confirm_order_address_view4);
            confirmOrderAddressView5 = itemView.findViewById(R.id.confirm_order_address_view5);
            confirmOrderAddressTs = (TextView) itemView.findViewById(R.id.confirm_order_tv_address_ts);
            confirmOrderTvAddress = (TextView) itemView.findViewById(R.id.confirm_order_tv_address);
            confirmOrderTvPeople = (TextView) itemView.findViewById(R.id.confirm_order_tv_people);
            confirmOrderTvPhone = (TextView) itemView.findViewById(R.id.confirm_order_tv_phone);
            confirmOrderAddressLjsctime = (TextView) itemView.findViewById(R.id.confirm_order_address_ljsctime);
            confirmOrderAddressTv = (TextView) itemView.findViewById(R.id.confirm_order_address_tv);
            confirmOrderAddressZqPhone = (TextView) itemView.findViewById(R.id.confirm_order_address_zq_phone);
            confirmOrderAddressZqTime = (TextView) itemView.findViewById(R.id.confirm_order_address_zq_time);
            confirmOrderAddressCheckAgree = (CheckBox) itemView.findViewById(R.id.confirm_order_address_check_agree);
            confirmOrderCheck = (CheckBox) itemView.findViewById(R.id.confirm_order_check);

        }
    }

    //商品信息viewholder
    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        private RecyclerView myRecyclerView;//展示商品信息的listview
        private final LinearLayout linAll;//展开全部
        private final ImageView confirmOrderShopImg;//店家照片
        private final TextView confirmOrderShopName;//店家名字
        private final TextView allTv;//全部展开
        private final TextView zczqTv;//支持自取

        public MyViewHolder2(View itemView) {
            super(itemView);
            myRecyclerView = (RecyclerView) itemView.findViewById(R.id.confirm_order_RecyclerView);
            linAll = (LinearLayout) itemView.findViewById(R.id.confirm_order_all_lin);
            confirmOrderShopImg = (ImageView) itemView.findViewById(R.id.confirm_order_shop_img);
            confirmOrderShopName = (TextView) itemView.findViewById(R.id.confirm_order_shop_name);
            allTv = (TextView) itemView.findViewById(R.id.confirm_order_all_tv);
            zczqTv = (TextView) itemView.findViewById(R.id.confirm_order_shop_zczq);
        }
    }

    //餐具数量viewholder
    public class MyViewHolder3 extends RecyclerView.ViewHolder {

        private final LinearLayout confirmOrderCjbzLin;
        private final LinearLayout confirmOrderCjLin;//餐具数量linearlayout
        private final LinearLayout confirmOrderCjLin2;//备注发票linearlayout
        private final TextView confirmOrderCjSum;//餐具数量(未选)
        private final TextView confirmOrderCjbzBzf;//包装费
        private final TextView confirmOrderCjbzPsf;//配送费
        private final TextView confirmOrderCjbzHfyh;//花返优惠
        private final TextView confirmOrderCjbzXjsum;//小计

        public MyViewHolder3(View itemView) {
            super(itemView);
            confirmOrderCjbzLin = (LinearLayout) itemView.findViewById(R.id.confirm_order_cjbz_lin);
            confirmOrderCjLin = (LinearLayout) itemView.findViewById(R.id.confirm_order_cj_lin);
            confirmOrderCjLin2 = (LinearLayout) itemView.findViewById(R.id.confirm_order_cjbz_lin2);
            confirmOrderCjSum = (TextView) itemView.findViewById(R.id.confirm_order_cj_sum);
            confirmOrderCjbzBzf = (TextView) itemView.findViewById(R.id.confirm_order_cjbz_bzf);
            confirmOrderCjbzPsf = (TextView) itemView.findViewById(R.id.confirm_order_cjbz_psf);
            confirmOrderCjbzHfyh = (TextView) itemView.findViewById(R.id.confirm_order_cjbz_hfyh);
            confirmOrderCjbzXjsum = (TextView) itemView.findViewById(R.id.confirm_order_cjbz_xjsum);
        }
    }
}
