package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.OrderDetailItemBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/11.
 * by--订单详情的
 */

public class OrderIndentAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity mContext;
    private final OrderDetailItemBean data;
    private OnLeftItemClickListener onLeftItemClickListener;
    private OnRightItemClickListener onRightItemClickListener;
    private OnImClickListener onImClickListener;
    private OnTelClickListener onTelClickListener;

    public OrderIndentAdapter(FragmentActivity activity, OrderDetailItemBean lists) {

        this.mContext = activity;
        this.data = lists;
    }

    public void setOnImClickListener(OnImClickListener onImClickListener) {
        this.onImClickListener = onImClickListener;
    }

    public void setOnTelClickListener(OnTelClickListener onTelClickListener) {
        this.onTelClickListener = onTelClickListener;
    }

    public void setOnLeftItemClickListener(OnLeftItemClickListener onLeftItemClickListener) {
        this.onLeftItemClickListener = onLeftItemClickListener;
    }

    public void setOnRightItemClickListener(OnRightItemClickListener onRightItemClickListener) {
        this.onRightItemClickListener = onRightItemClickListener;
    }

    //即时通讯的点击事件
    public interface OnImClickListener {

        void OnImClickListener(View view, int position);
    }

    //电话客服的点击事件
    public interface OnTelClickListener {

        void OnTelClickListener(View view, int position);
    }


    //左边按钮状态点击事件
    public interface OnLeftItemClickListener {

        void OnLeftItemClickListener(TextView view, int position);
    }

    //右边按钮状态的点击事件
    public interface OnRightItemClickListener {

        void OnRightItemClickListener(TextView view, int position);
    }


    @Override
    public int getGroupCount() {

        return data == null ? 0 : 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        List<OrderDetailItemBean.ListsBean> lists = data.getLists();

        return lists.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data;
    }

    public OrderDetailItemBean getData() {

        return data;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        List<OrderDetailItemBean.ListsBean> child = data.getLists();
        return child.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_affirm_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        OrderDetailItemBean group = (OrderDetailItemBean) getGroup(groupPosition);

        //设置店铺的数据
        groupViewHolder.tvBrand.setText(group.getManufac_name());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildViewHolder childViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_order_tetail_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置下属子类的数据
        OrderDetailItemBean.ListsBean child = (OrderDetailItemBean.ListsBean) getChild(groupPosition, childPosition);
        childViewHolder.tvTitle.setText(child.getGoods_name());
        childViewHolder.tvPrice.setText("¥" + child.getGoods_price() + "");
        childViewHolder.tvNumber.setText("x" + child.getGoods_num() + "");
        childViewHolder.tvPriceRabate.setText("¥" + child.getRebate() + "");
        childViewHolder.tvDescProperty.setText(child.getGoods_spec());

        //根据状态显示按钮
        //0-待付款 1-待发货 2-待收货 3-已收货  10-交易完成 11-交易关闭（会员取消） 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
        String state = data.getState();

        if (state.equals("0")) {//待付款

            childViewHolder.llState.setVisibility(View.GONE);
            childViewHolder.tvLeftState.setVisibility(View.GONE);
            childViewHolder.tvRightState.setVisibility(View.GONE);

        } else if (state.equals("1")) {//待发货

            childViewHolder.llState.setVisibility(View.GONE);
            childViewHolder.tvLeftState.setVisibility(View.GONE);
            childViewHolder.tvRightState.setVisibility(View.GONE);

        } else if (state.equals("2")) {//待收货

            childViewHolder.llState.setVisibility(View.VISIBLE);
            childViewHolder.tvLeftState.setVisibility(View.VISIBLE);
            childViewHolder.tvRightState.setVisibility(View.VISIBLE);
            childViewHolder.tvLeftState.setText("查看物流");
            String refund_state = child.getRefund_state();
            //0-未申请 1-申请退款 3-已退款 4-拒绝退款
            if (refund_state.equals("0")) {
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("退款/退货");
            } else if (refund_state.equals("1")) {
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("退款详情");
            } else if (refund_state.equals("3")) {
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("已退款");
            } else {//拒绝退款
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("退款/退货");
            }

        } else if (state.equals("3")) {//已收货

            childViewHolder.llState.setVisibility(View.VISIBLE);
            //判断是否去评价
            String eval_state = child.getEval_state();
            //0-不可评价 1-待评价 2-已评价
            if (eval_state.equals("1")) {
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("去评价");
            } else if (eval_state.equals("2")) {
                childViewHolder.tvRightState.setVisibility(View.GONE);
            } else {
                childViewHolder.tvRightState.setVisibility(View.GONE);
            }

            String refund_state = child.getRefund_state();
            //0-未申请 1-申请退款 3-已退款 4-拒绝退款
            if (refund_state.equals("0")) {
                childViewHolder.tvLeftState.setVisibility(View.VISIBLE);
                childViewHolder.tvLeftState.setText("退款/退货");
            } else if (refund_state.equals("1")) {
                childViewHolder.tvLeftState.setVisibility(View.VISIBLE);
                childViewHolder.tvLeftState.setText("退款详情");
            } else if (refund_state.equals("3")) {
                childViewHolder.tvLeftState.setVisibility(View.VISIBLE);
                childViewHolder.tvLeftState.setText("已退款");
            } else {//拒绝退款
                childViewHolder.tvLeftState.setVisibility(View.VISIBLE);
                childViewHolder.tvLeftState.setText("退款/退货");
            }

        } else if (state.equals("10")) {//交易完成

            //判断是否去评价
            String eval_state = child.getEval_state();
            //0-不可评价 1-待评价 2-已评价
            if (eval_state.equals("1")) {
                childViewHolder.llState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setVisibility(View.VISIBLE);
                childViewHolder.tvRightState.setText("去评价");
            } else if (eval_state.equals("2")) {
                childViewHolder.llState.setVisibility(View.GONE);
                childViewHolder.tvRightState.setVisibility(View.GONE);
            } else {
                childViewHolder.llState.setVisibility(View.GONE);
                childViewHolder.tvRightState.setVisibility(View.GONE);
            }
            childViewHolder.tvLeftState.setVisibility(View.GONE);

        } else if (state.equals("11") || state.equals("12") || state.equals("13")) {//交易关闭

            childViewHolder.llState.setVisibility(View.GONE);
            childViewHolder.tvLeftState.setVisibility(View.GONE);
            childViewHolder.tvRightState.setVisibility(View.GONE);

        } else {

            childViewHolder.llState.setVisibility(View.GONE);
            childViewHolder.tvLeftState.setVisibility(View.GONE);
            childViewHolder.tvRightState.setVisibility(View.GONE);
        }

        String imgPath = ApiUrlConstant.API_IMG_URL + child.getFile_path();
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(mContext, imgPath, childViewHolder.ivGov);

        //设置左边的按钮状态的点击事件
        childViewHolder.tvLeftState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onLeftItemClickListener != null) {
                    onLeftItemClickListener.OnLeftItemClickListener(childViewHolder.tvLeftState, childPosition);
                }
            }
        });

        //设置右边的按钮状态的点击事件
        childViewHolder.tvRightState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onRightItemClickListener != null) {
                    onRightItemClickListener.OnRightItemClickListener(childViewHolder.tvRightState, childPosition);
                }
            }
        });

        //设置im的点击事件
        childViewHolder.llim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onImClickListener != null) {
                    onImClickListener.OnImClickListener(v, childPosition);
                }
            }
        });

        //设置电话客服的点击事件
        childViewHolder.lltel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onTelClickListener != null) {
                    onTelClickListener.OnTelClickListener(v, childPosition);
                }
            }
        });

        int childrenCount = getChildrenCount(groupPosition);
        //判断是子类的最后一个就显示所有详细信息
        if (childPosition == childrenCount - 1) {
            double mtotalPrice = 0.00;
            childViewHolder.llRabate.setVisibility(View.VISIBLE);
            childViewHolder.llDetail.setVisibility(View.VISIBLE);
            childViewHolder.allNumber.setText("共" + data.getGoods_total_num() + "件商品");
            childViewHolder.allInvoce.setText("¥" + data.getInvoice_cost());//发票金额
            childViewHolder.allMoney.setText("¥" + getTotalPrice());//商品金额
            childViewHolder.allPractical.setText("¥" + data.getPay_amount());//实付金额
            childViewHolder.tvFreight.setText("¥" + data.getFreight());//运费

            List<OrderDetailItemBean.ListsBean> lists = data.getLists();
            for (int i = 0; i < lists.size(); i++) {

                String rebate = lists.get(i).getRebate();
                mtotalPrice += Double.parseDouble(rebate) * lists.get(i).getGoods_num();
                BigDecimal bg = new BigDecimal(mtotalPrice);
                mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            childViewHolder.allRabate.setText("花返" + "¥" + mtotalPrice);

        } else {

            childViewHolder.llRabate.setVisibility(View.GONE);
            childViewHolder.llDetail.setVisibility(View.GONE);
        }

        return convertView;
    }

    /***
     * 返回总价的回调
     * @return
     */
    private double getTotalPrice() {

        double mtotalPrice = 0.00;
        List<OrderDetailItemBean.ListsBean> lists = data.getLists();
        for (int i = 0; i < lists.size(); i++) {

            OrderDetailItemBean.ListsBean listsBean = lists.get(i);
            mtotalPrice += listsBean.getGoods_price() * listsBean.getGoods_num();
        }

        return mtotalPrice;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    static class GroupViewHolder {
        @BindView(R.id.view_gray)
        View viewGray;
        @BindView(R.id.iv_brand)
        ImageView ivBrand;
        @BindView(R.id.tv_brand)
        TextView tvBrand;
        @BindView(R.id.ll_head)
        LinearLayout llHead;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.iv_stockout)
        ImageView ivStockout;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_color)
        TextView tvColor;
        @BindView(R.id.tv_desc_property)
        TextView tvDescProperty;
        @BindView(R.id.tv_left_state)
        TextView tvLeftState;
        @BindView(R.id.tv_right_state)
        TextView tvRightState;
        @BindView(R.id.ll_state)
        LinearLayout llState;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv01)
        TextView tv01;
        @BindView(R.id.tv_price_rabate)
        TextView tvPriceRabate;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.all_rabate)
        TextView allRabate;
        @BindView(R.id.ll_rabate)
        LinearLayout llRabate;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_invoice)
        TextView tvInvoice;
        @BindView(R.id.tv_invoice_detail)
        TextView tvInvoiceDetail;
        @BindView(R.id.ll_invoice)
        RelativeLayout llInvoice;
        @BindView(R.id.all_number)
        TextView allNumber;
        @BindView(R.id.tv_freight)
        TextView tvFreight;
        @BindView(R.id.all_invoce)
        TextView allInvoce;
        @BindView(R.id.all_money)
        TextView allMoney;
        @BindView(R.id.all_practical)
        TextView allPractical;
        @BindView(R.id.iv_kf)
        ImageView ivKf;
        @BindView(R.id.tv_kf)
        TextView tvKf;
        @BindView(R.id.iv_phone)
        ImageView ivPhone;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.ll_kf)
        LinearLayout llKf;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.ll_im)
        LinearLayout llim;
        @BindView(R.id.ll_tel)
        LinearLayout lltel;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
