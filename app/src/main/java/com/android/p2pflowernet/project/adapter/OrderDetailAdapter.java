package com.android.p2pflowernet.project.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.OrderListBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.fragments.mine.orderflow.orderitemdetail.OrderItemDetailActivity;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.R.id.hebdomad_tv;
import static com.android.p2pflowernet.project.utils.ConvertUtils.fmtMoney;

/**
 * author: zhangpeisen
 * created on: 2017/11/7 上午11:18
 * description: 订单详情适配器
 */
public class OrderDetailAdapter extends HFWBaseAdapter<OrderListBean.ListsBean> {
    private ButtonLeftClickListener mButtonLeftClickListener;
    private ButtonCenterClickListener mButtonCenterClickListener;
    private ButtonRightClickListener mButtonRightClickListener;
    private OnItemDetailClickListener onItemDetailClickListener;


    public void setOnItemDetailClickListener(OnItemDetailClickListener onItemDetailClickListener) {
        this.onItemDetailClickListener = onItemDetailClickListener;
    }

    public void setButtonLeftClickListener(ButtonLeftClickListener listener) {
        mButtonLeftClickListener = listener;
    }

    public void setButtonCenterClickListener(ButtonCenterClickListener listener) {
        mButtonCenterClickListener = listener;
    }

    public void setButtonRightClickListener(ButtonRightClickListener listener) {
        mButtonRightClickListener = listener;
    }

    @Override
    public BaseHolder<OrderListBean.ListsBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_merchandise, parent, false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<OrderListBean.ListsBean> holder, int position) {
        OrderListBean.ListsBean listsBean = list == null ? null : list.get(position);
        ((OrderDetailHolder) holder).bindDateView(listsBean);
    }

    /**
     * (左)订单按钮的点击监听
     */
    public interface ButtonLeftClickListener {
        void onClick(int position, OrderListBean.ListsBean data);
    }

    /**
     * (左)订单按钮的点击监听
     */
    public interface OnItemDetailClickListener {
        void onClick(View view, int position);
    }

    /**
     * (中)订单按钮的点击监听
     */
    public interface ButtonCenterClickListener {
        void onClick(int position, OrderListBean.ListsBean data);
    }

    /**
     * (右)订单按钮的点击监听
     */
    public interface ButtonRightClickListener {
        void onClick(int position, OrderListBean.ListsBean data);
    }

    class OrderDetailHolder extends BaseHolder<OrderListBean.ListsBean> {
        @BindView(R.id.tv_storename)
        TextView tvStorename;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.ll_photos)
        LinearLayout llPhotos;
        @BindView(R.id.hs_photos)
        HorizontalScrollView hsPhotos;
        @BindView(R.id.tv_merchandise_name)
        TextView tvMerchandiseName;
        @BindView(R.id.tv_colorattr)
        TextView tvColorattr;
        @BindView(hebdomad_tv)
        TextView hebdomadTv;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.goodsattr_tv)
        TextView goodsattrTv;
        @BindView(R.id.backmoney_tv)
        TextView backmoneyTv;
        @BindView(R.id.tv_merchandise_price)
        TextView tvMerchandisePrice;
        @BindView(R.id.tv_left_button)
        TextView tvLeftButton;
        @BindView(R.id.tv_center_button)
        TextView tvCenterButton;
        @BindView(R.id.tv_right_button)
        TextView tvRightButton;
        @BindView(R.id.ll_detail)
        LinearLayout lldetail;

        OrderDetailHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            tvLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mButtonLeftClickListener != null)
                        mButtonLeftClickListener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
            tvCenterButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mButtonCenterClickListener != null)
                        mButtonCenterClickListener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
            tvRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mButtonRightClickListener != null)
                        mButtonRightClickListener.onClick(getAdapterPosition(), list.get(getAdapterPosition()));
                }
            });
        }

        void bindDateView(final OrderListBean.ListsBean listsBean) {
            TransitionManager.beginDelayedTransition((ViewGroup) itemView,
                    new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_KEEP));

            tvMerchandiseName.setText(listsBean.getGoods_name());
            tvStorename.setText(listsBean.getManufac_name());
            showState(listsBean.getIs_return(), TextUtils.isEmpty(listsBean.getState()) ? "" : listsBean.getState(), tvState);
            tvMerchandisePrice.setText(String.format(
                    getContext().getResources().getString(R.string.str_merchandise_price),
                    TextUtils.isEmpty(listsBean.getGoods_total_num()) ? "" : listsBean.getGoods_total_num(),
                    TextUtils.isEmpty(listsBean.getRebate_amount()) ? "" : listsBean.getRebate_amount()
                    , fmtMoney(TextUtils.isEmpty(listsBean.getPay_amount()) ? "" : listsBean.getPay_amount())));
            tvColorattr.setText(listsBean.getSpec_name());
            if (listsBean.getLabel() == 0) {
                hebdomadTv.setVisibility(View.GONE);
            } else if (listsBean.getLabel() == 1) {
                hebdomadTv.setText("七天退货");
            } else {
                hebdomadTv.setText("正品");
            }

            backmoneyTv.setText(TextUtils.isEmpty(listsBean.getRebate_amount()) ? "" : "花返 ￥" + listsBean.getRebate_amount());
            // 底部(左)按钮状态
            showLeftButton(listsBean.getIs_return(), TextUtils.isEmpty(listsBean.getState()) ? "" : listsBean.getState(), tvLeftButton);
            // 底部(中)按钮状态
            showCenterButton(listsBean.getIs_return(), TextUtils.isEmpty(listsBean.getState()) ? "" : listsBean.getState(), tvCenterButton);
            // 底部(右)按钮状态
            showRightButton(listsBean.getIs_return(), TextUtils.isEmpty(listsBean.getState()) ? "" : listsBean.getState(), tvRightButton);
            // 扩大事件的点击范围
            UIUtils.setTouchDelegate(tvLeftButton, 50);
            UIUtils.setTouchDelegate(tvCenterButton, 50);
            UIUtils.setTouchDelegate(tvRightButton, 50);
            if (listsBean.getImg_path().isEmpty()) {
                return;
            }

            //动态加载图片
            List<String> img_path = listsBean.getImg_path();
            if (img_path != null && img_path.size() > 1) {
                llTitle.setVisibility(View.GONE);//是否显示标题
            } else {
                llTitle.setVisibility(View.VISIBLE);
            }

            llPhotos.removeAllViews();
            for (int i = 0; i < img_path.size(); i++) {

                //使用inflate获取phtoview布局里面的myGallery视窗
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_order_detail_item_photos, llPhotos, false);
                ImageView img = (ImageView) view.findViewById(R.id.imageview_1);
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                if (img_path.get(i) != null) {
                    glideImageLoader.displayImage(getContext(), ApiUrlConstant.API_IMG_URL + img_path.get(i), img);
                } else {
                    glideImageLoader.displayImage(getContext(), R.mipmap.default_image, img);
                }
                llPhotos.addView(view);  //把添加过资源后的view视图重新添加到myGallery中
            }
            llPhotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), OrderItemDetailActivity.class);
                    intent.putExtra("order_id", listsBean.getId());
                    intent.putExtra("state", listsBean.getState());
                    intent.putExtra("isreturn", listsBean.getIs_return());
                    getContext().startActivity(intent);
                }
            });
        }

        /**
         * 根据订单状态修改样式
         * 订单状态0-待付款 1-待发货 2-待收货 3-已收货  10-交易完成 11-交易关闭（会员取消）
         * 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
         *
         * @param state
         * @param textView
         */
        private void showState(String isReturn, String state, TextView textView) {
            String text = "";
            int color = Color.BLACK;
            if ("0".equals(state)) {
                text = "待付款";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("1".equals(state)) {
                text = "待发货";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("2".equals(state)) {
                text = "待收货";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("3".equals(state)) {
                text = "已收货";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("10".equals(state)) {
                text = "交易完成";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("12".equals(state)) {
                // -交易关闭（客服取消）
                text = "交易关闭";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("13".equals(state)) {
                // 交易关闭（支付超时)
                text = "交易关闭";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            } else if ("11".equals(state)) {
                // 交易关闭（支付超时)
                text = "交易关闭";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            }
            textView.setText(text);
            textView.setTextColor(color);
        }

        /**
         * 根据订单状态修改按钮样式
         *
         * @param state
         * @param textView
         */
        private void showLeftButton(String isReturn, String state, TextView textView) {
            String text = "";
            int visible = View.GONE;
            int color = Color.BLACK;
            if ("1".equals(state)) {
                text = "";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.GONE;
            } else if ("2".equals(state)) {
                text = "";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.GONE;
            } else if ("3".equals(state)) {
                text = "";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.GONE;
            } else if ("4".equals(state)) {
                text = "";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.GONE;
            }
            textView.setText(text);
            textView.setTextColor(color);
            textView.setVisibility(visible);
        }

        /**
         * 根据订单状态修改按钮样式
         * 订单状态0-待付款 1-待发货 2-待收货 3-已收货  10-交易完成
         * 11-交易关闭（会员取消） 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
         *
         * @param state
         * @param textView
         */
        private void showCenterButton(String isReturn, String state, TextView textView) {
            String text = "";
            int visible = View.GONE;
            int color = Color.BLACK;
            if ("11".equals(state) || "12".equals(state) || "13".equals(state)) {
                text = "删除订单";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("2".equals(state)) {
                text = "查看物流";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("4".equals(state)) {
                text = "再次购买";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("0".equals(state)) {
                text = "取消订单";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            }

            textView.setText(text);
            textView.setTextColor(color);
            textView.setVisibility(visible);
        }

        /**
         * 根据订单状态修改按钮样式
         * 订单状态0-待付款 1-待发货 2-待收货 3-已收货  10-交易完成
         * 11-交易关闭（会员取消） 12-交易关闭（客服取消） 13-交易关闭（支付超时自动关闭）
         *
         * @param state
         * @param textView
         */
        private void showRightButton(String isReturn, String state, TextView textView) {
            String text = "";
            int visible = View.GONE;
            int color = Color.BLACK;
            if ("0".equals(state)) {
                text = "付款";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("1".equals(state)) {

                if (isReturn.equals("0")) {
                    text = "申请退款";
                } else {
                    text = "退款中";
                }
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("2".equals(state)) {
                text = "确认收货";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("3".equals(state)) {
                text = "查看详情";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("4".equals(state)) {
                text = "删除订单";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            } else if ("10".equals(state)) {
                text = "查看详情";
                color = ContextCompat.getColor(getContext(), R.color.colorPrimary);
                visible = View.VISIBLE;
            }
            textView.setText(text);
            textView.setTextColor(color);
            textView.setVisibility(visible);
        }
    }
}
