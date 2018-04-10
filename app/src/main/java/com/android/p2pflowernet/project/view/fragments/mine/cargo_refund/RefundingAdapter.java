package com.android.p2pflowernet.project.view.fragments.mine.cargo_refund;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.RefundBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.transitionseverywhere.ChangeText;
import com.transitionseverywhere.TransitionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.R.id.hebdomad_tv;
import static com.android.p2pflowernet.project.R.id.tv_colorattr;
import static com.android.p2pflowernet.project.R.id.tv_storename;

/**
 * Created by caishen on 2017/11/17.
 * by--退换货的适配器
 */

public class RefundingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnItemClickViewListener onItemClickViewListener;
    private OnCancleRefundClickListener onCancleRefundClick;
    private OnAppforArbitrationClickListener onAppforArbitrationClickListener;
    private OnDetailonClickListener onDetailonClickListener;
    private OnApplyForRefundonClickListener onApplyForRefundonClickListener;
    private OnWlDetailonClickListener onWlDetailonClick;
    List<RefundBean.ListsBean> mRefundBeanLists;

    public RefundingAdapter(List<RefundBean.ListsBean> refundBeanLists) {
        this.mRefundBeanLists = refundBeanLists;
    }

    //recyclerview的点击回调接口
    public interface OnItemClickViewListener {

        void onItemClick(View view, int position);//单击
    }

    /**
     * 取消退款
     */
    public interface OnCancleRefundClickListener {

        void onCancleRefundClick(View view, int position);//取消退款

    }

    /**
     * 申请仲裁
     */
    public interface OnAppforArbitrationClickListener {

        void onAppforArbitrationClick(View view, int position);//申请仲裁
    }

    /**
     * 查看详情
     */
    public interface OnDetailonClickListener {

        void onDetailonClick(View view, int position);//查看详情
    }

    /**
     * 申请退款
     */
    public interface OnApplyForRefundonClickListener {

        void onApplyForRefundonClick(View view, int position);//申请退款
    }

    /**
     * 完善物流信息
     */
    public interface OnWlDetailonClickListener {

        void onWlDetailonClick(View view, int position);//完善物流信息
    }


    public void setOnItemViewListener(OnItemClickViewListener mOnRecyclerViewListener) {

        this.onItemClickViewListener = mOnRecyclerViewListener;
    }

    public void setOnCancleRefundClick(OnCancleRefundClickListener onCancleRefundClick) {
        this.onCancleRefundClick = onCancleRefundClick;
    }

    public void setOnAppforArbitrationClickListener(OnAppforArbitrationClickListener onAppforArbitrationClickListener) {
        this.onAppforArbitrationClickListener = onAppforArbitrationClickListener;
    }

    public void setOnDetailonClickListener(OnDetailonClickListener onDetailonClickListener) {
        this.onDetailonClickListener = onDetailonClickListener;
    }

    public void setOnApplyForRefundonClickListener(OnApplyForRefundonClickListener onApplyForRefundonClickListener) {
        this.onApplyForRefundonClickListener = onApplyForRefundonClickListener;
    }

    public void setOnWlDetailonClick(OnWlDetailonClickListener onWlDetailonClick) {
        this.onWlDetailonClick = onWlDetailonClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refunding, parent, false);
        return new OrderRefundHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        RefundBean.ListsBean listsBean = mRefundBeanLists.get(position);
        if (listsBean == null) {
            return;
        }
        ((RefundingAdapter.OrderRefundHolder) holder).bindDateView(listsBean);

        //单个订单的点击事件
        ((OrderRefundHolder) holder).llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onItemClickViewListener != null) {
                    //设置订单的点击回调
                    onItemClickViewListener.onItemClick(((OrderRefundHolder) holder).llMain, position);
                }
            }
        });


        //取消退款
        ((OrderRefundHolder) holder).tvCancleRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onCancleRefundClick != null) {
                    //设置取消退款
                    onCancleRefundClick.onCancleRefundClick(((OrderRefundHolder) holder).tvCancleRefund, position);
                }
            }
        });


        //申请仲裁
        ((OrderRefundHolder) holder).tvArbitration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onAppforArbitrationClickListener != null) {

                    //申请仲裁
                    onAppforArbitrationClickListener.onAppforArbitrationClick(((OrderRefundHolder) holder).tvArbitration, position);
                }
            }
        });

        //物流信息
        ((OrderRefundHolder) holder).tvWlInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onWlDetailonClick != null) {
                    //完善物流信息
                    onWlDetailonClick.onWlDetailonClick(((OrderRefundHolder) holder).tvWlInfo, position);
                }
            }
        });

        //申请退款
        ((OrderRefundHolder) holder).tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onWlDetailonClick != null) {

                    //申请退款
                    onApplyForRefundonClickListener.onApplyForRefundonClick(((OrderRefundHolder) holder).tvRefund, position);
                }
            }
        });

        //查看详情
        ((OrderRefundHolder) holder).tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onWlDetailonClick != null) {
                    //查看详情
                    onDetailonClickListener.onDetailonClick(((OrderRefundHolder) holder).tvDetail, position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mRefundBeanLists.size();
    }

    static class OrderRefundHolder extends BaseHolder<RefundBean.ListsBean> {

        @BindView(tv_storename)
        TextView tvStorename;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.ll_more)
        LinearLayout llMore;
        @BindView(R.id.tv_merchandise_name)
        TextView tvMerchandiseName;
        @BindView(tv_colorattr)
        TextView tvColorattr;
        @BindView(hebdomad_tv)
        TextView hebdomadTv;
        @BindView(R.id.goodsattr_tv)
        TextView goodsattrTv;
        @BindView(R.id.backmoney_tv)
        TextView backmoneyTv;
        @BindView(R.id.ll_one)
        LinearLayout llOne;
        @BindView(R.id.tv_refund)
        TextView tvRefund;
        @BindView(R.id.tv_arbitration)
        TextView tvArbitration;
        @BindView(R.id.tv_wl_info)
        TextView tvWlInfo;
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_rcancle_refund)
        TextView tvCancleRefund;
        @BindView(R.id.tv_statue1)
        TextView tvStatue1;
        @BindView(R.id.ll_main)
        LinearLayout llMain;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.ll_photos)
        LinearLayout llPhotos;
        @BindView(R.id.hs_photos)
        HorizontalScrollView hsPhotos;

        OrderRefundHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindDateView(RefundBean.ListsBean listsBean) {

            TransitionManager.beginDelayedTransition((ViewGroup) itemView,
                    new ChangeText().setChangeBehavior(ChangeText.CHANGE_BEHAVIOR_KEEP));
            // 商品名称
            tvMerchandiseName.setText(listsBean.getGoods_name());
            //  厂商名称
            tvStorename.setText(TextUtils.isEmpty(listsBean.getManufac_name()) ? "" : listsBean.getManufac_name());
            // 退款状态
            showState(tvState, listsBean.getRefund_state(), listsBean);
            // 商品规格
            tvColorattr.setText(TextUtils.isEmpty(listsBean.getGoods_spec()) ? "" : listsBean.getGoods_spec());
            hebdomadTv.setText("七天退货");
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
        }

        //根据商家的状态改变底部的按钮状态值及颜色值
        private void showState(TextView tvState, String refundstate, RefundBean.ListsBean listsBean) {

            switch (refundstate) {
                case "0":
                    //0-未退款
                    tvState.setText("未退款");
                    tvArbitration.setVisibility(View.GONE);//申请仲裁
                    tvDetail.setVisibility(View.GONE);//查看详情
                    tvRefund.setVisibility(View.GONE);//申请退款
                    tvCancleRefund.setVisibility(View.GONE);//取消退款
                    tvWlInfo.setEnabled(true);
                    break;
                case "1":
                    //申请退款
                    tvState.setText("退款申请中");
                    tvCancleRefund.setText("取消退款");
                    tvArbitration.setVisibility(View.GONE);//申请仲裁
                    tvCancleRefund.setVisibility(View.VISIBLE);//取消退款
                    tvWlInfo.setEnabled(true);
                    break;
                case "2":
                    // 待提交物流信息
                    tvState.setText("待提交物流信息");
                    tvWlInfo.setVisibility(View.VISIBLE);//物流信息
                    tvWlInfo.setText("完善物流信息");
                    tvArbitration.setVisibility(View.GONE);//申请仲裁
                    tvCancleRefund.setVisibility(View.GONE);//取消退款
                    tvWlInfo.setEnabled(true);
                    break;
                case "3":
                    // 待退款
                    tvState.setText("待退货");
                    tvStatue1.setText("仅退款  待退货");
                    tvDetail.setVisibility(View.GONE);//查看详情
                    tvRefund.setVisibility(View.GONE);//申请退款
                    tvDetail.setVisibility(View.VISIBLE);//查看详情
                    tvDetail.setText("查看详情");
                    tvState.setText("待商家同意");
                    tvStatue1.setText("仅退款");
                    tvArbitration.setVisibility(View.GONE);//申请仲裁
                    tvCancleRefund.setVisibility(View.GONE);//取消退款
                    tvWlInfo.setEnabled(true);
                    break;
                case "9":
                    // 取消退款
                    tvState.setText("退款关闭");
                    tvCancleRefund.setVisibility(View.GONE);//取消退款
                    tvArbitration.setVisibility(View.GONE);//申请仲裁
                    tvWlInfo.setVisibility(View.GONE);
                    tvWlInfo.setEnabled(false);
                    break;
                case "10":
                    // 退款成功(厂家确认收货)
                    tvState.setText("退款成功");
                    tvCancleRefund.setVisibility(View.GONE);//取消退款
                    tvStatue1.setText("仅退款 退款完成");
                    tvWlInfo.setVisibility(View.GONE);
                    tvWlInfo.setEnabled(false);
                    break;
                case "11":
                    // 拒绝退款
                    tvState.setText("拒绝退款");
                    if (listsBean.getArbit_id().equals("0")) {
                        tvArbitration.setVisibility(View.VISIBLE);//申请仲裁
                        tvCancleRefund.setVisibility(View.GONE);//取消退款
                        tvArbitration.setText("申请仲裁");
                        tvState.setText("商家拒绝");
                    } else {
                        tvCancleRefund.setText("取消仲裁");
                        tvArbitration.setVisibility(View.GONE);//申请仲裁
                        tvCancleRefund.setVisibility(View.VISIBLE);//取消退款
                        tvState.setText("已申请仲裁");
                    }
                    tvStatue1.setText("退款退货  待退款退货");
                    tvWlInfo.setEnabled(false);
                    tvWlInfo.setVisibility(View.GONE);
                    break;
            }
        }
    }
}
