package com.android.p2pflowernet.project.adapter;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.TakeOutBean;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.TimeTools;
import com.android.p2pflowernet.project.view.customview.CountDownView;
import com.android.p2pflowernet.project.view.customview.SuperExpandableListView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.utils.ConvertUtils.fmtMoney;

/**
 * Created by caishen on 2018/1/5.
 * by--外卖订单
 */

public class OrderTakeOutAdapter extends HFWBaseAdapter<TakeOutBean.ListBean> {
    private final FragmentActivity mContext;
    private OnLeftButtonStateClickLitener onLeftButtonStateClickLitener;
    private OnRightButtonStateClickLitener onRightButtonStateClickLitener;
    private LayoutInflater mLayoutInflater;
    private int id;

    public OrderTakeOutAdapter(FragmentActivity activity, int id) {

        this.mContext = activity;
        mLayoutInflater = LayoutInflater.from(activity);
        this.id = id;
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
    public BaseHolder<TakeOutBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.item_take_out_all, parent, false);
        return new OrderTakeOutViewHolder(view);
    }

    @Override
    public void onViewHolderBind(final BaseHolder<TakeOutBean.ListBean> holder, int position) {

        TakeOutBean.ListBean orderTakeOutBean = list.get(position);
        ((OrderTakeOutViewHolder) holder).bindDateView(holder, orderTakeOutBean, mContext, position);
    }

    public class OrderTakeOutViewHolder extends BaseHolder<TakeOutBean.ListBean> {

        @BindView(R.id.tv_storename)
        TextView tvStorename;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.ex_listView)
        SuperExpandableListView exListView;
        @BindView(R.id.ll_more)
        LinearLayout llMore;
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
        @BindView(R.id.tv_time1)
        TextView tvTime1;
        @BindView(R.id.tv_time2)
        TextView tvTime2;
        @BindView(R.id.tv_time3)
        TextView tvTime3;
        @BindView(R.id.tv_time4)
        TextView tvTime4;
        @BindView(R.id.ll_dealin_time)
        LinearLayout llDealinTime;
        @BindView(R.id.tv_merchandise_price)
        TextView tvMerchandisePrice;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.tv_left_button)
        TextView tvLeftButton;
        @BindView(R.id.tv_right_button)
        TextView tvRightButton;
        @BindView(R.id.iv_shop_img)
        ImageView shopImg;
        @BindView(R.id.tv_time5)
        TextView tvTime5;
        @BindView(R.id.item_countdown)
        CountDownView countDownView;
        private Timer mTimer;
        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:

                        String currentStringTime = TimeTools.getCountTimeByLong((Long) msg.obj);
                        tvTime1.setText(currentStringTime.substring(0, 1));
                        tvTime2.setText(currentStringTime.substring(1, 2));
                        tvTime3.setText(currentStringTime.substring(3, 4));
                        tvTime4.setText(currentStringTime.substring(4, 5));

                        break;
                }
            }
        };


        OrderTakeOutViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


        //绑定数据
        public void bindDateView(BaseHolder<TakeOutBean.ListBean> holder, final TakeOutBean.ListBean orderTakeOutBean, FragmentActivity mContext, final int position) {

            tvStorename.setText(orderTakeOutBean.getManager_name());
            String file_path = ApiUrlConstant.API_IMG_URL + orderTakeOutBean.getFile_path();
            new GlideImageLoader().displayImage(mContext, file_path, shopImg);
            tvMerchandisePrice.setText(String.format(

                    getContext().getResources().getString(R.string.str_merchandise_price),
                    TextUtils.isEmpty(orderTakeOutBean.getGoods_count()) ? "" : orderTakeOutBean.getGoods_count(),
                    TextUtils.isEmpty(orderTakeOutBean.getRebate_amount()) ? "" : orderTakeOutBean.getRebate_amount()
                    , fmtMoney(TextUtils.isEmpty(orderTakeOutBean.getPay_amount()) ? "" : orderTakeOutBean.getPay_amount())));

            mTimer = new Timer();
            //刷新倒计时
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = Message.obtain();

                    //结束时间减去当前时间
                    String endTimes = DateUtils.timesdate(orderTakeOutBean.getCountdown_time());

                    Long start = System.currentTimeMillis();
                    Long end = TimeTools.stringToLongTime(endTimes);

                    msg.what = 100;
                    msg.obj = end - start;
                    mHandler.sendMessage(msg);
                }
            };
            //0秒之后每隔1秒执行一次run
            mTimer.schedule(timerTask, 1, 1000);

//            String timesdate = DateUtils.timesdate(orderTakeOutBean.getCreated());
//            String endTimes = DateUtils.timesdate(orderTakeOutBean.getCountdown_time());
//
//            Long start = TimeTools.stringToLongTime(timesdate);
//            Long end = TimeTools.stringToLongTime(endTimes);
//            //倒计时
//            countDownView.setLeftTime(end - start);
//            countDownView.start();


            String state = orderTakeOutBean.getState();

            List<TakeOutBean.ListBean.GoodsBean> goods = orderTakeOutBean.getGoods();
            if (goods != null && goods.size() > 1) {//多余一个商品的显示

                llMore.setVisibility(View.VISIBLE);
                llOne.setVisibility(View.GONE);

                //设置多余一件的适配器
                final TakeOutMoreAdapter mAdapter = new TakeOutMoreAdapter(mContext, goods);
                exListView.setAdapter(mAdapter);

                //隐藏下拉按钮
                exListView.setGroupIndicator(null);

                //让exListView不能被点击
                exListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                        return true;
                    }
                });

//                //关闭
                for (int i = 0; i < mAdapter.getGroupCount(); i++) {

                    exListView.collapseGroup(i);
                }

                //设置展开更多的点击事件
                mAdapter.setOnShowMoreListClickLitener(new TakeOutMoreAdapter.onShowMoreListClickLitener() {
                    @Override
                    public void showMoreListLitener(View view, int position, boolean isShow) {

                        exListView.expandGroup(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });


                //收起的点击事件
                mAdapter.setOnHideMoreListClickLitener(new TakeOutMoreAdapter.onHideMoreListClickLitener() {
                    @Override
                    public void hideMoreListLitener(View view, int position, boolean isShow) {

                        exListView.collapseGroup(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });

            } else {//小于一个商品显示

                llMore.setVisibility(View.GONE);
                llOne.setVisibility(View.VISIBLE);

                tvMerchandiseName.setText(goods.get(0).getGoods_name());
                tvPrice.setText("¥" + goods.get(0).getGoods_price());
                tvHuafan.setText("花返：¥" + goods.get(0).getRebate());
                tvNum.setText("x" + goods.get(0).getGoods_num());
                new GlideImageLoader().displayImage(mContext, goods.get(0).getFile_path(), ivImg);
            }

            String eval_state = orderTakeOutBean.getEval_state();
            String refund_state = orderTakeOutBean.getRefund_state();

            //根据state显示按钮状态
            showStateButton(state, eval_state, refund_state);

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

        private void showStateButton(String state, String eval_state, String refund_state) {

            //tag值（自定义用于区别按钮状态值） 0-取消订单 1-去支付 2再来一单 3-确认收货 4-去评价 5-取消退款6 申请退款
            //tag值（自定义用于区别按钮状态值） 0-取消订单 1-去支付 2再来一单 3-确认收货 4-去评价 5-取消退款 6申请退款

//            0-待付款 1-待接单 2-待发货/跑腿取餐 3-待收货 4-已收货  10-交易完成 11-交易关闭（会员取消）
//            12-交易关闭（客服取消） 13-交易关闭(运营后台取消) 14-交易关闭（支付超时自动关闭）

            if (id == 5) {
                if (refund_state.equals("0")) {
                    tvState.setText("申请中");
                    tvRightButton.setVisibility(View.VISIBLE);
                    llDealinTime.setVisibility(View.GONE);
                    tvRightButton.setText("取消退款");
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setTag("5");
                } else if (refund_state.equals("1")) {
                    tvState.setText("同意退款");
                    tvLeftButton.setVisibility(View.GONE);
                    llDealinTime.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("再来一单");
                    tvRightButton.setTag("2");
                } else if (refund_state.equals("2")) {
                    tvState.setText("拒绝退款");
                    tvLeftButton.setVisibility(View.GONE);
                    llDealinTime.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("申请退款");
                    tvRightButton.setTag("6");
                } else {
                    tvState.setText("已退款");
                }
            } else {
                if (state.equals("0")) {
                    tvState.setText("待付款");
                    llDealinTime.setVisibility(View.VISIBLE);
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvLeftButton.setText("取消订单");
                    tvRightButton.setText("去支付");
                    tvRightButton.setTag("1");
                    tvLeftButton.setTag("0");
                } else if (state.equals("1")) {//待接单

                    tvState.setText("待商家接单");
                    llDealinTime.setVisibility(View.GONE);
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("取消订单");
                    tvRightButton.setTag("0");
                } else if (state.equals("2")) {//待发货
                    tvState.setText("待商家发货");
                    llDealinTime.setVisibility(View.GONE);
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("取消订单");
                    tvRightButton.setTag("0");

                } else if (state.equals("3")) {//

                    tvState.setText("骑手派送中");
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvLeftButton.setText("申请退款");
                    tvLeftButton.setTag("6");
                    llDealinTime.setVisibility(View.GONE);
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("确认收货");
                    tvRightButton.setTag("3");
                } else if (state.equals("10")) {//交易完成
                    tvState.setText("已完成");
                    llDealinTime.setVisibility(View.GONE);
                    tvLeftButton.setVisibility(View.GONE);

                    //0-不可评价 1-可评价 2-已评价
                    if (eval_state.equals("1")) {

                        tvRightButton.setVisibility(View.VISIBLE);
                        tvRightButton.setText("去评价");
                        tvRightButton.setTag("4");

                    } else {

                        tvRightButton.setVisibility(View.GONE);
                    }
                } else if (state.equals("4")) {//已收货

                    tvState.setText("已收货");
                    tvLeftButton.setVisibility(View.VISIBLE);
                    tvLeftButton.setText("申请退款");
                    tvLeftButton.setTag("6");
                    llDealinTime.setVisibility(View.GONE);

                    //0-不可评价 1-可评价 2-已评价
                    if (eval_state.equals("1")) {

                        tvRightButton.setVisibility(View.VISIBLE);
                        tvRightButton.setText("去评价");
                        tvRightButton.setTag("4");

                    } else {

                        tvRightButton.setVisibility(View.GONE);
                    }

                } else {//交易关闭

                    tvState.setText("已关闭");

                    llDealinTime.setVisibility(View.GONE);
                    tvLeftButton.setVisibility(View.GONE);
                    tvRightButton.setVisibility(View.VISIBLE);
                    tvRightButton.setText("再来一单");
                    tvRightButton.setTag("2");
                }
            }


//            if (state.equals("0")) {
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("待付款");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                } else {
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.VISIBLE);
//                tvLeftButton.setVisibility(View.VISIBLE);
//                tvRightButton.setVisibility(View.VISIBLE);
//                tvLeftButton.setText("取消订单");
//                tvRightButton.setText("去支付");
//                tvRightButton.setTag("1");
//                tvLeftButton.setTag("0");

//            } else if (state.equals("1")) {//待接单
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("待商家接单");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                } else {
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.GONE);
//                tvLeftButton.setVisibility(View.GONE);
//                tvRightButton.setVisibility(View.VISIBLE);
//                tvRightButton.setText("取消订单");
//                tvRightButton.setTag("0");

//            } else if (state.equals("2")) {//待发货
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("待商家发货");
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("申请退款");
//                    tvRightButton.setTag("6");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("取消退款");
//                    tvRightButton.setTag("5");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("取消退款");
//                    tvRightButton.setTag("5");
//                } else {
//                    tvRightButton.setVisibility(View.GONE);
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.GONE);
//                tvLeftButton.setVisibility(View.GONE);
//

//                } else if (state.equals("3")) {//
//
//                    //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                    if (refund_state.equals("0")) {
//                        tvState.setText("骑手派送中");
//                        tvLeftButton.setVisibility(View.VISIBLE);
//                        tvLeftButton.setText("申请退款");
//                        tvLeftButton.setTag("6");
//                    } else if (refund_state.equals("1")) {
//                        tvState.setText("退款中");
//                        tvLeftButton.setVisibility(View.VISIBLE);
//                        tvLeftButton.setText("取消退款");
//                        tvLeftButton.setTag("5");
//                    } else if (refund_state.equals("2")) {
//                        tvLeftButton.setVisibility(View.VISIBLE);
//                        tvLeftButton.setText("取消退款");
//                        tvLeftButton.setTag("5");
//                        tvState.setText("有退款退款中");
//                    } else {
//                        tvLeftButton.setVisibility(View.GONE);
//                        tvState.setText("已退款");
//                    }
//
//                    llDealinTime.setVisibility(View.GONE);
//                    tvLeftButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("确认收货");
//                    tvRightButton.setTag("3");

//            } else if (state.equals("10")) {//交易完成
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("已完成");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                } else {
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.GONE);
//                tvLeftButton.setVisibility(View.GONE);
//                tvLeftButton.setText("申请退款");
//                tvLeftButton.setTag("6");

            //0-不可评价 1-可评价 2-已评价
//                if (eval_state.equals("1")) {
//
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("去评价");
//                    tvRightButton.setTag("4");
//
//                } else {
//
//                    tvRightButton.setVisibility(View.GONE);
//                }

//            } else if (state.equals("4")) {//已收货
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("已收货");
//                    tvLeftButton.setVisibility(View.VISIBLE);
//                    tvLeftButton.setText("申请退款");
//                    tvLeftButton.setTag("6");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                    tvLeftButton.setVisibility(View.VISIBLE);
//                    tvLeftButton.setText("取消退款");
//                    tvLeftButton.setTag("5");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                    tvLeftButton.setVisibility(View.VISIBLE);
//                    tvLeftButton.setText("取消退款");
//                    tvLeftButton.setTag("5");
//                } else {
//                    tvLeftButton.setVisibility(View.GONE);
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.GONE);
//
//                //0-不可评价 1-可评价 2-已评价
//                if (eval_state.equals("1")) {
//
//                    tvRightButton.setVisibility(View.VISIBLE);
//                    tvRightButton.setText("去评价");
//                    tvRightButton.setTag("4");
//
//                } else {
//
//                    tvRightButton.setVisibility(View.GONE);
//                }
//
//            } else {//交易关闭
//
//                //0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
//                if (refund_state.equals("0")) {
//                    tvState.setText("已关闭");
//                } else if (refund_state.equals("1")) {
//                    tvState.setText("退款中");
//                } else if (refund_state.equals("2")) {
//                    tvState.setText("有退款退款中");
//                } else {
//                    tvState.setText("已退款");
//                }
//
//                llDealinTime.setVisibility(View.GONE);
//                tvLeftButton.setVisibility(View.GONE);
//                tvRightButton.setVisibility(View.VISIBLE);
//                tvRightButton.setText("再来一单");
//                tvRightButton.setTag("2");
//            }
        }
    }
}
