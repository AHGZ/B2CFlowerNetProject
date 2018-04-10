package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/6.
 * by--团购订单适配器
 */

public class OrderTakeOutGroupAdapter extends HFWBaseAdapter<TakeOutOrderGroupBean.ListBean> {
    private final FragmentActivity mContext;
    private OnLeftButtonStateClickLitener onLeftButtonStateClickLitener;
    private OnRightButtonStateClickLitener onRightButtonStateClickLitener;

    public OrderTakeOutGroupAdapter(FragmentActivity activity) {
        this.mContext = activity;
    }

    public void setOnRightButtonStateClickLitener(OnRightButtonStateClickLitener onRightButtonStateClickLitener) {
        this.onRightButtonStateClickLitener = onRightButtonStateClickLitener;
    }

    public void setOnLeftButtonStateClickLitener(OnLeftButtonStateClickLitener onLeftButtonStateClickLitener) {
        this.onLeftButtonStateClickLitener = onLeftButtonStateClickLitener;
    }

    public interface OnLeftButtonStateClickLitener {

        void leftButtonStateLitener(View view, int position);
    }

    public interface OnRightButtonStateClickLitener {

        void rightButtonStateLitener(View view, int position);
    }

    @Override
    public BaseHolder<TakeOutOrderGroupBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_take_out_group, parent, false);
        return new OrderTakeOutGroupViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<TakeOutOrderGroupBean.ListBean> holder, int position) {

        TakeOutOrderGroupBean.ListBean listBean = list.get(position);
        ((OrderTakeOutGroupViewHolder) holder).bindDateView(listBean, mContext, position);
    }

    class OrderTakeOutGroupViewHolder extends BaseHolder<TakeOutOrderGroupBean.ListBean> {
        @BindView(R.id.tv_storename)
        TextView tvStorename;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.iv_img)
        ImageView ivImg;
        @BindView(R.id.tv_merchandise_name)
        TextView tvMerchandiseName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_huafan)
        TextView tvHuafan;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.ll_title)
        LinearLayout llTitle;
        @BindView(R.id.ll_one)
        LinearLayout llOne;
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.tv_left_button)
        TextView tvLeftButton;
        @BindView(R.id.tv_right_button)
        TextView tvRightButton;
        @BindView(R.id.ll_button)
        LinearLayout llButton;
        @BindView(R.id.tv_ordernum)
        TextView orderNum;

        OrderTakeOutGroupViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindDateView(TakeOutOrderGroupBean.ListBean orderTakeOutBean, FragmentActivity mContext, final int position) {

            llOne.setVisibility(View.VISIBLE);
            String state = orderTakeOutBean.getState();
            String eval_state = orderTakeOutBean.getEval_state();
            showStateButton(state, eval_state);
            DateUtils dateUtils = new DateUtils();
            tvMerchandiseName.setText(orderTakeOutBean.getTitle() == null ? "" : orderTakeOutBean.getTitle());
            tvPrice.setText(orderTakeOutBean.getPrice() == null ? "¥ 0" : "¥" + orderTakeOutBean.getPrice());
            tvHuafan.setText(orderTakeOutBean.getRebate() == null ? "花返：¥ 0" : "花返：¥" + orderTakeOutBean.getRebate());
            tvNum.setText(orderTakeOutBean.getNum() == null ? "x 0" : "x" + orderTakeOutBean.getNum());
            orderNum.setText(orderTakeOutBean.getOrder_num() == null ? "订单号：" : "订单号：" + orderTakeOutBean.getOrder_num());//订单号
            tvDate.setText("有效期：" + DateUtils.timedate(orderTakeOutBean.getStarttime() == null ? "" : orderTakeOutBean.getStarttime()) + "-" +
                    DateUtils.timedate(orderTakeOutBean.getEndtime() == null ? "" : orderTakeOutBean.getEndtime()));//有效期
            new GlideImageLoader().displayImage(mContext, ApiUrlConstant.API_IMG_URL + orderTakeOutBean.getFile_path(), ivImg);

            //设置按钮状态的点击事件
            tvLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onLeftButtonStateClickLitener != null) {

                        onLeftButtonStateClickLitener.leftButtonStateLitener(v, position);
                    }
                }
            });

            tvRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onRightButtonStateClickLitener != null) {

                        onRightButtonStateClickLitener.rightButtonStateLitener(v, position);
                    }
                }
            });
        }

        //0-没付款 1-待使用 2-(全部份数)已使用 3-已退款',
        private void showStateButton(String state, String eval_state) {

            switch (state) {

                case "0"://待付款

                    tvState.setText("待付款");
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvLeftButton.setTag("3");
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setTag("4");
                    tvLeftButton.setText("取消订单");
                    tvRightButton.setText("去支付");

                    break;
                case "1"://待使用

                    tvState.setText("待使用");
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvLeftButton.setTag("1");
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setTag("2");
                    tvLeftButton.setText("申请退款");
                    tvRightButton.setText("查看劵码");

                    break;
                case "2"://已使用

                    //0-不可评价 1-可评价 2-已评价
                    tvState.setText("已使用");

                    if (eval_state.equals("1")) {

                        tvRightButton.setVisibility(View.VISIBLE);
                        tvRightButton.setText("去评价");
                        tvRightButton.setTag("5");

                    } else {

                        tvRightButton.setVisibility(View.GONE);
                    }

                    tvLeftButton.setVisibility(View.GONE);

                    break;
                case "3"://已退款

                    tvState.setText("已退款");
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.GONE);

                    break;
                default:

                    tvState.setText("已使用");
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.GONE);

                    break;
            }
        }
    }
}
