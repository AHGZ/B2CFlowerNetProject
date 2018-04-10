package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.TimeTools;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.RatingBarView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 外卖明细RecyclerView适配器
 */

public class TakeFullDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ZERO = 0;//商品logo
    private static final int TYPE_ONE = 1;//商品信息
    private static final int TYPE_TWO = 2;//订单号
    private FragmentActivity activity;
    private OrderDetailsBean orderDetailsBean;
    private String countdown_time;
    //返回按钮
    OnBackClickListener onBackClickListener;
    //服务按钮
    OnServiceClickListener onServiceClickListener;
    //复制按钮
    OnCopyClickListener onCopyClickListener;

    //去付款
    OnQfkClickListener onQfkClickListener;
    //去评价
    OnQpjClickListener onQpjClickListener;
    //再来一单
    OnAgainClickListener onAgainClickListener;
    //申请退款
    OnSqtkClickListener onSqtkClickListener;
    //取消退款
    OnQxtkClickListener onQxtkClickListener;
    //取消订单
    OnQxddClickListener onQxddClickListener;
    //确认收货
    OnQrshClickListener onQrshClickListener;
    //联系送餐员
    OnCallPeopleClickListener onCallPeopleClickListener;
    //联系商家
    OnCallShopClickListener onCallShopClickListener;

    //0-待付款 1-待接单 2-待发货/跑腿取餐 3-待收货 4-已收货  10-交易完成 11-交易关闭（会员取消）
    // 12-交易关闭（客服取消） 13-交易关闭(运营后台取消) 14-交易关闭（支付超时自动关闭）15-商家拒绝接单
    private final String state;
    private final String refund_state;
    //订单号
    private String order_num;

    private MyViewHolder1 holder1;
    private TextView infoWindowTv;
    private String re_state;

    //返回按钮接口
    public interface OnBackClickListener {
        void onBackClick(View v);
    }

    //服务按钮
    public interface OnServiceClickListener {
        void onServiceClick(View v);
    }

    //复制按钮接口
    public interface OnCopyClickListener {
        void onCopyClick(View v, TextView text);
    }

    //去评价接口
    public interface OnQpjClickListener {
        void onQpjClick(View v);
    }

    //再来一单接口
    public interface OnAgainClickListener {
        void onAgainClick(View v);
    }

    //申请退款接口
    public interface OnSqtkClickListener {
        void onSqtkClick(View v);
    }

    //取消退款接口
    public interface OnQxtkClickListener {
        void onQxtkClick(View v);
    }

    //取消订单接口
    public interface OnQxddClickListener {
        void onQxddClick(View v);
    }

    //确认收货接口
    public interface OnQrshClickListener {
        void onQrshClick(View v);
    }

    //联系送餐员接口
    public interface OnCallPeopleClickListener {
        void onCallPeopleClick(View v, String tel);
    }

    //联系商家接口
    public interface OnCallShopClickListener {
        void onCallShopClick(View v, String tel);
    }

    //去付款
    public interface OnQfkClickListener {
        void onQfkClick(View view);
    }

    //设置返回按钮点击回调方法
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }

    //设置服务按钮点击回调方法
    public void setOnServiceClickListener(OnServiceClickListener onServiceClickListener) {
        this.onServiceClickListener = onServiceClickListener;
    }

    //设置复制按钮点击回调方法
    public void setOnCopyClickListener(OnCopyClickListener onCopyClickListener) {
        this.onCopyClickListener = onCopyClickListener;
    }

    //设置去评价点击回调方法
    public void setOnQpjClickListener(OnQpjClickListener onQpjClickListener) {
        this.onQpjClickListener = onQpjClickListener;
    }

    //设置再来一单点击回调方法
    public void setOnAgainClickListener(OnAgainClickListener onAgainClickListener) {
        this.onAgainClickListener = onAgainClickListener;
    }

    //设置申请退款点击回调方法
    public void setOnSqtkClickListener(OnSqtkClickListener onSqtkClickListener) {
        this.onSqtkClickListener = onSqtkClickListener;
    }

    //设置取消退款点击回调方法
    public void setOnQxtkClickListener(OnQxtkClickListener onQxtkClickListener) {
        this.onQxtkClickListener = onQxtkClickListener;
    }

    //设置取消订单点击回调方法
    public void setOnQxddClickListener(OnQxddClickListener onQxddClickListener) {
        this.onQxddClickListener = onQxddClickListener;
    }

    //设置确认收货点击回调方法
    public void setOnQrshClickListener(OnQrshClickListener onQrshClickListener) {
        this.onQrshClickListener = onQrshClickListener;
    }

    public void setOnQfkClickListener(OnQfkClickListener onQfkClickListener) {
        this.onQfkClickListener = onQfkClickListener;
    }

    //设置联系送餐员点击回调方法
    public void setOnCallPeopleClickListener(OnCallPeopleClickListener onCallPeopleClickListener) {
        this.onCallPeopleClickListener = onCallPeopleClickListener;
    }


    //设置联系商家点击回调方法
    public void setOnCallShopClickListener(OnCallShopClickListener onCallShopClickListener) {
        this.onCallShopClickListener = onCallShopClickListener;
    }

    private Timer mTimer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:

                    String currentStringTime = TimeTools.getCountTimeByLong((Long) msg.obj);
                    holder1.tvTime1.setText(currentStringTime.substring(0, 1));
                    holder1.tvTime2.setText(currentStringTime.substring(1, 2));
                    holder1.tvTime3.setText(currentStringTime.substring(3, 4));
                    holder1.tvTime4.setText(currentStringTime.substring(4, 5));

                    holder1.ddzttime1.setText(currentStringTime.substring(0, 1));
                    holder1.ddzttime2.setText(currentStringTime.substring(1, 2));
                    holder1.ddzttime3.setText(currentStringTime.substring(3, 4));
                    holder1.ddzttime4.setText(currentStringTime.substring(4, 5));

                    break;
            }
        }
    };

    public TakeFullDetailsAdapter(FragmentActivity activity, OrderDetailsBean orderDetailsBean, String order_num, String countdown_time) {
        this.activity = activity;
        this.countdown_time = countdown_time;
        this.orderDetailsBean = orderDetailsBean;
        state = orderDetailsBean.getState().toString().trim();
        refund_state = orderDetailsBean.getRefund_state().toString().trim();
        this.order_num = order_num;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //绑定商品logo
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.take_full_details_state, parent, false);
                holder = new MyViewHolder1(view);
                break;
            //商品信息
            case TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.take_out_full_detail_item_shop_info, parent, false);
                holder = new MyViewHolder2(view);
                break;
            //订单号
            case TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.take_out_full_detail_item_copy, parent, false);
                holder = new MyViewHolder3(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //商品logo
            case TYPE_ZERO:
                holder1 = (MyViewHolder1) holder;

                if (orderDetailsBean.getDistrib_mode() == 3) {
                    holder1.mapFrame.setVisibility(View.GONE);
                    holder1.mapStatelin.setVisibility(View.GONE);
                    holder1.ddztFrame.setVisibility(View.VISIBLE);
                    new GlideImageLoader().displayImage(activity, orderDetailsBean.getLogo_url(), holder1.ddztShopLogo);
                    holder1.ddztShopName.setText(orderDetailsBean.getMerch_name());
                    holder1.ddztPhone.setText(orderDetailsBean.getCustomer_tel());
                    //refund_state://0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
                    if (refund_state.equals("1") || refund_state.equals("2") || refund_state.equals("3")) {
                        re_state = orderDetailsBean.getRe_state().toString().trim();
                        //re_state: 退款申请状态 0-申请中 1-同意退款 2-拒绝退款 3-取消退款
                        if (re_state.equals("0")) {
                            //待退款
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztQxtk.setVisibility(View.VISIBLE);

                        } else if (re_state.equals("1")) {
                            //待退款
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddzttv.setText("同意退款:款项" + orderDetailsBean.getRefund_amount() + "已原路退回");
                            holder1.ddztAgaih.setVisibility(View.VISIBLE);
                        } else if (re_state.equals("2")) {
                            //待退款
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            //赋值退款原因
                            if (orderDetailsBean.getRefuse_reason().equals("")) {
                                holder1.dtkYuanyin.setText("拒绝退款:就是不给你退");
                            } else {
                                holder1.dtkYuanyin.setText("拒绝退款:"+orderDetailsBean.getRefuse_reason());
                            }
                            holder1.ddztAgaih.setVisibility(View.VISIBLE);
                        }

                    } else {

                        holder1.sytimeLin.setVisibility(View.GONE);
                        holder1.timeLin2.setVisibility(View.GONE);
                        holder1.qfkRelat.setVisibility(View.GONE);
                        holder1.mapUserInfoLin.setVisibility(View.GONE);

                        //state //0-待付款 1-待接单 2-待发货/跑腿取餐 3-待收货 4-已收货  10-交易完成 11-交易关闭（会员取消）
                        // 12-交易关闭（客服取消） 13-交易关闭(运营后台取消) 14-交易关闭（支付超时自动关闭）15-商家拒绝接单

                        if (state.equals("0")) {//待付款
                            holder1.ddzttv.setVisibility(View.GONE);
                            holder1.ddztlin.setVisibility(View.GONE);

                        } else if (state.equals("1")) {//待接单
                            //支付成功
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztQxdd.setVisibility(View.VISIBLE);

                        } else if (state.equals("2")) {//待发货
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztQxdd.setVisibility(View.VISIBLE);
                            holder1.divDdztView2.setVisibility(View.GONE);

                        } else if (state.equals("3")) {//待收货

                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztSqtk.setVisibility(View.VISIBLE);

                            holder1.ddztQrsh.setVisibility(View.VISIBLE);

                        } else if (state.equals("4") && refund_state.equals("0")) {//已收货
                            //待评价
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztQpj.setVisibility(View.VISIBLE);

                        } else if (state.equals("10") && refund_state.equals("0")) {//已完成
                            //待评价
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                            holder1.ddztQpj.setVisibility(View.VISIBLE);

                        } else if (state.equals("11") || state.equals("12") || state.equals("13") || state.equals("14")) {
                            //已关闭
                            holder1.ddztSytime.setVisibility(View.GONE);
                            holder1.ddztQfkBtn.setVisibility(View.GONE);
                        }

                    }


                }
                new GlideImageLoader().displayImage(activity, orderDetailsBean.getLogo_url(), holder1.shopImg);
                holder1.shoptv.setText(orderDetailsBean.getMerch_name());

                mTimer = new Timer();
                //刷新倒计时
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();

                        //结束时间减去当前时间
                        String endTimes = DateUtils.timesdate(countdown_time);

                        Long start = System.currentTimeMillis();
                        Long end = TimeTools.stringToLongTime(endTimes);

                        msg.what = 100;
                        msg.obj = end - start;
                        mHandler.sendMessage(msg);
                    }
                };
                //0秒之后每隔1秒执行一次run
                mTimer.schedule(timerTask, 1, 1000);
                showMap();


                //扩大返回按钮点击范围
                UIUtils.setTouchDelegate(holder1.back, 50);
                UIUtils.setTouchDelegate(holder1.ddztBack, 50);
                UIUtils.setTouchDelegate(holder1.mapBack, 50);
                //设置返回按钮监听
                holder1.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBackClickListener != null) {
                            onBackClickListener.onBackClick(v);
                        }
                    }
                });
                //设置返回按钮监听
                holder1.ddztBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBackClickListener != null) {
                            onBackClickListener.onBackClick(v);
                        }
                    }
                });
                holder1.mapBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBackClickListener != null) {
                            onBackClickListener.onBackClick(v);
                        }
                    }
                });

                //扩大服务按钮点击范围
                UIUtils.setTouchDelegate(holder1.mapService, 50);
                holder1.mapService.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onServiceClickListener != null) {
                            onServiceClickListener.onServiceClick(v);
                        }
                    }
                });


                //refund_state://0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
                if (refund_state.equals("1") || refund_state.equals("2") || refund_state.equals("3")) {
                    re_state = orderDetailsBean.getRe_state().toString().trim();
                    //re_state: 退款申请状态 0-申请中 1-同意退款 2-拒绝退款 3-取消退款
                    if (re_state.equals("0")) {
                        //待退款
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.dtkLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);
                        holder1.btnDtkAgain.setVisibility(View.GONE);

                    } else if (re_state.equals("1")) {
                        //待退款
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.dtkLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);
                        holder1.dtkText.setText("商家已同意退款");
                        holder1.dtkYuanyin.setText("款项" + orderDetailsBean.getRefund_amount() + "已原路退回");
                        holder1.btnDtkQxtk.setVisibility(View.GONE);
                    } else if (re_state.equals("2")) {
                        //待退款
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.dtkLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);
                        holder1.dtkText.setText("拒绝退款");
                        //赋值退款原因
                        if (orderDetailsBean.getRefuse_reason().equals("")) {
                            holder1.dtkYuanyin.setText("就是不给你退");
                        } else {
                            holder1.dtkYuanyin.setText(orderDetailsBean.getRefuse_reason());
                        }
                        holder1.btnDtkQxtk.setVisibility(View.GONE);
                    }

                } else {

                    //state //0-待付款 1-待接单 2-待发货/跑腿取餐 3-待收货 4-已收货  10-交易完成 11-交易关闭（会员取消）
                    // 12-交易关闭（客服取消） 13-交易关闭(运营后台取消) 14-交易关闭（支付超时自动关闭）15-商家拒绝接单

                    if (state.equals("0")) {//待付款
                        holder1.mapStatelin.setVisibility(View.VISIBLE);
                        holder1.timeLin1.setVisibility(View.GONE);
                        holder1.timeLin2.setVisibility(View.GONE);
                        holder1.stateLin.setVisibility(View.GONE);
                        holder1.sqtk.setVisibility(View.GONE);


                    } else if (state.equals("1")) {//待接单
                        //支付成功
                        holder1.mapStatelin.setVisibility(View.VISIBLE);
                        holder1.tvTitle.setText("支付成功");
                        holder1.btnDshQxdd.setVisibility(View.VISIBLE);
                        holder1.btnDshQxdd.setText("取消订单");
                        holder1.sqtk.setVisibility(View.GONE);
                        holder1.qfkRelat.setVisibility(View.GONE);
                        holder1.timeLin1.setVisibility(View.GONE);
                        holder1.stateLin.setVisibility(View.VISIBLE);
                        holder1.sytimeLin.setVisibility(View.GONE);
                        infoWindowTv.setText("等待接单");

                    } else if (state.equals("2")) {//待发货
                        holder1.mapStatelin.setVisibility(View.VISIBLE);
                        holder1.tvTitle.setText("商家已接单");
                        holder1.sqtk.setText("申请退款");
                        holder1.timeLin1.setVisibility(View.VISIBLE);
                        holder1.stateLin.setVisibility(View.VISIBLE);
                        holder1.sytimeLin.setVisibility(View.GONE);
                        holder1.timeLin2.setVisibility(View.GONE);
                        holder1.qfkRelat.setVisibility(View.GONE);
                        holder1.mapUserInfoLin.setVisibility(View.GONE);
                        infoWindowTv.setText("商家正在准备商品");

                    } else if (state.equals("3")) {//待收货

                        holder1.mapStatelin.setVisibility(View.VISIBLE);
                        holder1.tvTitle.setText("骑手正往商家赶");
                        holder1.sqtk.setText("申请退款");
                        holder1.timeLin1.setVisibility(View.VISIBLE);
                        holder1.stateLin.setVisibility(View.VISIBLE);
                        holder1.sytimeLin.setVisibility(View.GONE);
                        holder1.timeLin2.setVisibility(View.GONE);
                        holder1.mapUserInfoLin.setVisibility(View.VISIBLE);
                        holder1.mapDiv2.setVisibility(View.VISIBLE);
                        holder1.qfkRelat.setVisibility(View.GONE);
                        infoWindowTv.setText("距商家88m");


//                    holder1.mapStatelin.setVisibility(View.VISIBLE);
//                    holder1.tvTitle.setText("骑手到店取货中");
//                    holder1.sqtk.setText("申请退款");
//                    holder1.timeLin1.setVisibility(View.VISIBLE);
//                    holder1.stateLin.setVisibility(View.VISIBLE);
//                    holder1.sytimeLin.setVisibility(View.GONE);
//                    holder1.timeLin2.setVisibility(View.GONE);
//                    holder1.mapUserInfoLin.setVisibility(View.VISIBLE);
//                    holder1.mapDiv2.setVisibility(View.VISIBLE);
//                    holder1.qfkRelat.setVisibility(View.GONE);
//                      infoWindowTv.setText("已到店");
//
//                    holder1.mapStatelin.setVisibility(View.VISIBLE);
//                    holder1.tvTitle.setText("配送中");
//                    holder1.sqtk.setText("申请退款");
//                    holder1.btnDshQrsh.setVisibility(View.VISIBLE);
//                    holder1.btnDshQrsh.setText("确认收货");
//                    holder1.timeLin1.setVisibility(View.VISIBLE);
//                    holder1.stateLin.setVisibility(View.VISIBLE);
//                    holder1.sytimeLin.setVisibility(View.GONE);
//                    holder1.timeLin2.setVisibility(View.GONE);
//                    holder1.mapUserInfoLin.setVisibility(View.VISIBLE);
//                    holder1.mapDiv2.setVisibility(View.VISIBLE);
//                    holder1.qfkRelat.setVisibility(View.GONE);
//                    infoWindowTv.setText("距您88m");

                    } else if (state.equals("4") && refund_state.equals("0")) {//已收货
                        //待评价
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.ywcLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);

                    }  else if (state.equals("10") && refund_state.equals("0")) {//已完成
                        //待评价
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.ywcLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);

                    } else if (state.equals("11") || state.equals("12") || state.equals("13") || state.equals("14")) {
                        //已关闭
                        holder1.mapFrame.setVisibility(View.GONE);
                        holder1.stateRel.setVisibility(View.VISIBLE);
                        holder1.ygbLin.setVisibility(View.VISIBLE);
                        holder1.divView.setVisibility(View.VISIBLE);
                        holder1.divView2.setVisibility(View.VISIBLE);
                        holder1.userInfoLin.setVisibility(View.VISIBLE);
                        holder1.dfkLin.setVisibility(View.GONE);
                        holder1.divMapView.setVisibility(View.GONE);
                        holder1.dtkLin.setVisibility(View.GONE);
                    }

                }


                //扩大联系送餐员点击范围
                UIUtils.setTouchDelegate(holder1.callPeople, 50);
//                联系骑手方法
                holder1.callPeople.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCallPeopleClickListener != null) {
                            //将骑手电话号码回调回去
                            onCallPeopleClickListener.onCallPeopleClick(v, orderDetailsBean.getRider_phone());
                        }
                    }
                });
                //给骑手赋值名字
                if (orderDetailsBean.getRider_name().toString().trim().equals("")) {
                    holder1.peopleName.setText("无名氏");
                } else {
                    holder1.peopleName.setText(orderDetailsBean.getRider_name());
                }
                //给骑手赋值评分
                holder1.ratinStar.setStar(orderDetailsBean.getRider_score(), true);
                holder1.tvRatinStar.setText(orderDetailsBean.getRider_score() + "分");
                //给骑手赋值头像
                Glide.with(activity).load(R.mipmap.default_header).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder1.peopleImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder1.peopleImg.setImageDrawable(circularBitmapDrawable);
                    }
                });
                //扩大联系送餐员点击范围
                UIUtils.setTouchDelegate(holder1.callMapPeople, 50);
                //map布局下联系骑手方法
                holder1.callMapPeople.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCallPeopleClickListener != null) {
                            //将骑手电话号码回调回去
                            onCallPeopleClickListener.onCallPeopleClick(v, orderDetailsBean.getRider_phone());
                        }
                    }
                });
                //map布局下给骑手赋值名字
                if (orderDetailsBean.getRider_name().toString().trim().equals("")) {
                    holder1.peopleNameMap.setText("无名氏");
                } else {
                    holder1.peopleNameMap.setText(orderDetailsBean.getRider_name());
                }

                //map布局下给骑手赋值评分
                holder1.ratinStarMap.setStar(orderDetailsBean.getRider_score(), true);
                holder1.tvMapRatinStar.setText(orderDetailsBean.getRider_score() + "分");
                //map布局下给骑手赋值头像
                Glide.with(activity).load(R.mipmap.default_header).asBitmap().centerCrop().into(new BitmapImageViewTarget(holder1.peopleImgMap) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder1.peopleImgMap.setImageDrawable(circularBitmapDrawable);
                    }
                });
                //设置去评价监听
                holder1.btnDpjQpj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQpjClickListener != null) {
                            onQpjClickListener.onQpjClick(v);
                        }
                    }
                });
                holder1.ddztQpj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQpjClickListener != null) {
                            onQpjClickListener.onQpjClick(v);
                        }
                    }
                });
                //设置待评价再来一单监听
                holder1.btnDpjAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAgainClickListener != null) {
                            onAgainClickListener.onAgainClick(v);
                        }
                    }
                });
                holder1.ddztAgaih.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAgainClickListener != null) {
                            onAgainClickListener.onAgainClick(v);
                        }
                    }
                });
                //设置待退款再来一单监听
                holder1.btnDtkAgain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onAgainClickListener != null) {
                            onAgainClickListener.onAgainClick(v);
                        }
                    }
                });
                //设置申请退款监听
                holder1.btnDpjSqtk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSqtkClickListener != null) {
                            onSqtkClickListener.onSqtkClick(v);
                        }
                    }
                });

                holder1.ddztSqtk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSqtkClickListener != null) {
                            onSqtkClickListener.onSqtkClick(v);
                        }
                    }
                });
                //设置申请退款监听
                holder1.sqtk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onSqtkClickListener != null) {
                            onSqtkClickListener.onSqtkClick(v);
                        }
                    }
                });
                //设置取消退款监听
                holder1.btnDtkQxtk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQxtkClickListener != null) {
                            onQxtkClickListener.onQxtkClick(v);
                        }
                    }
                });
                holder1.ddztQxtk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQxtkClickListener != null) {
                            onQxtkClickListener.onQxtkClick(v);
                        }
                    }
                });
                //设置取消订单监听
                holder1.btnDshQxdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQxddClickListener != null) {
                            onQxddClickListener.onQxddClick(v);
                        }
                    }
                });
                holder1.ddztQxdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQxddClickListener != null) {
                            onQxddClickListener.onQxddClick(v);
                        }
                    }
                });
                //设置确认收货监听
                holder1.btnDshQrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQrshClickListener != null) {
                            onQrshClickListener.onQrshClick(v);
                        }
                    }
                });
                //设置确认收货监听
                holder1.ddztQrsh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQrshClickListener != null) {
                            onQrshClickListener.onQrshClick(v);
                        }
                    }
                });
                //设置去付款监听
                holder1.qfkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQfkClickListener != null) {
                            onQfkClickListener.onQfkClick(v);
                        }
                    }
                });
                holder1.ddztQfkBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQfkClickListener != null) {
                            onQfkClickListener.onQfkClick(v);
                        }
                    }
                });

                break;
            //商品信息
            case TYPE_ONE:
                final MyViewHolder2 holder2 = (MyViewHolder2) holder;
                //店铺名称
                holder2.shopName.setText(orderDetailsBean.getMerch_name());
                //店铺logo
                String logo_url = orderDetailsBean.getLogo_url();
                String logoUrl = ApiUrlConstant.API_IMG_URL + logo_url;
                //给店铺赋值头像
                Glide.with(activity).load(logoUrl).asBitmap().centerCrop().placeholder(R.drawable.dp).into(new BitmapImageViewTarget(holder2.shopImg) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(activity.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder2.shopImg.setImageDrawable(circularBitmapDrawable);
                    }
                });

                //赋值包装费
                holder2.bzPrice.setText("¥ " + orderDetailsBean.getBox_cost());
                //赋值配送费
                holder2.psPrice.setText("¥ " + orderDetailsBean.getDistrib_cost());

                //赋值花返优惠
                holder2.hfyuPrice.setText("花返优惠:¥ " + orderDetailsBean.getRebate_amount());
                //赋值合计
                holder2.sumPrice.setText("¥ " + orderDetailsBean.getPay_amount());

                //设置展示购买商品详情的适配器
                final List<OrderDetailsBean.GoodsListsBean> goods_lists = orderDetailsBean.getGoods_lists();
                final TakeFullDetailListviewAdapter takeFullDetailListviewAdapter = new TakeFullDetailListviewAdapter(activity, goods_lists);
                holder2.myListView.setAdapter(takeFullDetailListviewAdapter);
                if (goods_lists.size() > 2) {
                    holder2.linAll.setVisibility(View.VISIBLE);
                }
                //设置展开全部按钮监听事件
                holder2.linAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 判断getCount()数据的数量，如果等于3点击后就设置getCount()为全部数量，设置修改标识，刷新。
                        // 否则，相反。
                        if (takeFullDetailListviewAdapter.getCount() == 2) {
                            takeFullDetailListviewAdapter.addItemNum(goods_lists.size());
                            holder2.linAll.setVisibility(View.VISIBLE);
                            holder2.allTv.setText("收起全部");
                            takeFullDetailListviewAdapter.notifyDataSetChanged();
                        } else {
                            takeFullDetailListviewAdapter.addItemNum(2);
                            holder2.linAll.setVisibility(View.VISIBLE);
                            holder2.allTv.setText("展开全部");
                            takeFullDetailListviewAdapter.notifyDataSetChanged();
                        }
                    }
                });
                //设置打电话按钮监听
                holder2.callShopLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCallShopClickListener != null) {
                            //将店铺电话回调回去
                            onCallShopClickListener.onCallShopClick(v, orderDetailsBean.getManager_tel());
                        }
                    }
                });
                break;
            //订单号
            case TYPE_TWO:
                final MyViewHolder3 holder3 = (MyViewHolder3) holder;

                //refund_state://0-没退款 1-退款中 2-有退款退款中（其他退款申请） 3-有退款
                if (refund_state.equals("1") || refund_state.equals("2") || refund_state.equals("3")) {
                    //待退款
                    holder3.ddbzLin4.setVisibility(View.GONE);
                    holder3.ddsjLin1.setVisibility(View.GONE);
                }

                //state://0-待付款 1-待接单 2-待发货/跑腿取餐 3-待收货 4-已收货  10-交易完成 11-交易关闭（会员取消）
                // 12-交易关闭（客服取消） 13-交易关闭(运营后台取消) 14-交易关闭（支付超时自动关闭）15-商家拒绝接单

                if (state.equals("0")) {
                    holder3.zffsLin3.setVisibility(View.GONE);
                    holder3.zfsjLin2.setVisibility(View.GONE);
                    holder3.ddsjLin1.setVisibility(View.GONE);
                    holder3.shsjLin5.setVisibility(View.GONE);
                } else if (state.equals("1")) {//待接单
                    holder3.zfsjLin2.setVisibility(View.GONE);
                    holder3.zffsLin3.setVisibility(View.GONE);
                    holder3.shsj.setVisibility(View.GONE);
                } else if (state.equals("2")) {//待发货
                    holder3.zfsjLin2.setVisibility(View.GONE);
                    holder3.zffsLin3.setVisibility(View.GONE);
                    holder3.shsj.setVisibility(View.GONE);
                    holder3.ddbzLin4.setVisibility(View.GONE);
                } else if (state.equals("3")) {//待收货
                    holder3.zfsjLin2.setVisibility(View.GONE);
                    holder3.zffsLin3.setVisibility(View.GONE);
                    holder3.shsj.setVisibility(View.GONE);
                } else if (state.equals("4") || state.equals("10") || state.equals("11") || state.equals("12") || state.equals("13") || state.equals("14")) {//已收货 //已完成//已关闭
                    //待评价
                    holder3.ddbzLin4.setVisibility(View.GONE);
                    holder3.ddsjLin1.setVisibility(View.GONE);
                }

                //赋值收货人地址
                holder3.address.setText(orderDetailsBean.getLocation() + " " + orderDetailsBean.getDetail_address());
                //赋值收货人姓名
                holder3.customerName.setText(orderDetailsBean.getCustomer_name());
                //赋值收货人电话
                holder3.customerPhone.setText(orderDetailsBean.getCustomer_tel());

                //赋值配送服务 (1-平台配送 2-商家配送 3-到店自提)
                if (orderDetailsBean.getDistrib_mode() == 1) {
                    //赋值平台配送名称
                    holder3.psfw.setText(orderDetailsBean.getDistrib_name());
                } else if (orderDetailsBean.getDistrib_mode() == 2) {
                    holder3.psfw.setText("商家配送");
                } else if (orderDetailsBean.getDistrib_mode() == 3) {
                    holder3.psfw.setText("到店自提");
                }
                //赋值预计送达时间
                holder3.qwsj.setText(orderDetailsBean.getBooking_time());
                //赋值订单号
                holder3.orderNumber.setText(order_num);
                //赋值订单支付方式
//                0-未支付 1-微信 2-支付宝 3-银联 4-账户余额
                if (orderDetailsBean.getPay_channel() == 0) {
                    holder3.zffs.setText("未支付");
                } else if (orderDetailsBean.getPay_channel() == 1) {
                    holder3.zffs.setText("微信");
                } else if (orderDetailsBean.getPay_channel() == 2) {
                    holder3.zffs.setText("支付宝");
                } else if (orderDetailsBean.getPay_channel() == 3) {
                    holder3.zffs.setText("银联");
                } else if (orderDetailsBean.getPay_channel() == 4) {
                    holder3.zffs.setText("账户余额");
                }
                //赋值收货时间
                holder3.shsj.setText(orderDetailsBean.getConfirm_time());
                //赋值支付时间
                holder3.zfsj.setText(orderDetailsBean.getPay_time());
                //赋值订单时间
                holder3.ddsj.setText(orderDetailsBean.getCreated());
                //赋值备注
                holder3.ddbz.setText(orderDetailsBean.getNote());
                holder3.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCopyClickListener != null) {
                            onCopyClickListener.onCopyClick(v, holder3.orderNumber);
                        }
                    }
                });
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_ZERO) {
            return TYPE_ZERO;
        } else if (position == TYPE_ONE) {
            return TYPE_ONE;
        } else {
            return TYPE_TWO;
        }
    }

    //商品logo
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        private final ImageView back;//返回按钮
        private final ImageView mapBack;//返回按钮
        private final ImageView mapService;//服务按钮
        private final ImageView shopImg;//店铺logo
        private final TextView shoptv;//店铺名称
        private final LinearLayout ywcLin;//已完成linearlayout布局
        private final LinearLayout dtkLin;//待退款linearlayout布局
        //        private final LinearLayout dshLin;//待收货linearlayout布局
        private final LinearLayout ygbLin;//已关闭linearlayout布局
        private final LinearLayout userInfoLin;//联系人linearlayout布局
        private final TextView btnDpjQpj;//按钮（待评价去评价）
        private final TextView btnDpjAgain;//按钮（待评价再来一单）
        private final TextView btnDpjSqtk;//按钮（待评价申请退款）
        private final TextView btnDtkAgain;//按钮（待退款再来一单）
        private final TextView btnDtkQxtk;//按钮（待退款取消退款）
        private final TextView btnDshQxdd;//按钮（待收货取消订单）
        private final TextView btnDshQrsh;//按钮（待收货确认收货）
        private final ImageView callPeople;//联系送餐员
        private final TextView tvRatinStar;//评分中文信息描述
        private final RatingBarView ratinStar;//评分星星
        private final TextView peopleName;//送餐员名字
        private final ImageView peopleImg;//送餐员头像
        private final LinearLayout dfkLin;//待付款linearlayout布局
        private final View divView;//分割线View
        private final View divView2;//分割线View
        private final View divDdztView2;//分割线View
        private final View divMapView;//分割线View
        private final MapView mapView;
        private final FrameLayout mapFrame;
        private final LinearLayout mapUserInfoLin;
        private final ImageView callMapPeople;//联系送餐员
        private final TextView tvMapRatinStar;//评分中文信息描述
        private final RatingBarView ratinStarMap;//评分星星
        private final TextView peopleNameMap;//送餐员名字
        private final ImageView peopleImgMap;//送餐员头像
        private final LinearLayout mapStatelin;
        private final RelativeLayout stateRel;
        private final TextView tvTitle;
        private final LinearLayout stateLin;
        private final LinearLayout timeLin1;
        private final TextView yjTime;
        private final LinearLayout timeLin2;
        private final TextView sqtk;
        private final RelativeLayout qfkRelat;
        private final LinearLayout sytimeLin;
        private final View mapDiv2;
        private final FrameLayout ddztFrame;
        private final Button qfkBtn;//去付款
        private final TextView tvTime1;
        private final TextView tvTime2;
        private final TextView tvTime3;
        private final TextView tvTime4;
        private final TextView dtkText;
        private final TextView dtkYuanyin;

        private final ImageView ddztShopLogo;//到店自提店铺logo
        private final TextView ddztShopName;//到店自提店铺名称
        private final TextView ddztSqtk;//到店自提申请退款
        private final TextView ddztQrsh;//到店自提确认收货
        private final TextView ddztQpj;//到店自提去评价
        private final TextView ddztAgaih;//到店自提再来一单
        private final TextView ddztQxdd;//到店自提取消订单
        private final TextView ddztQxtk;//到店自提取消退款
        private final TextView ddztPhone;//到店自提预留电话
        private final TextView ddzttv;//到店自提文字描述
        private final TextView ddzttime1;//到店自提时间
        private final TextView ddzttime2;//到店自提时间
        private final TextView ddzttime3;//到店自提时间
        private final TextView ddzttime4;//到店自提时间
        private final View ddztBack;//到店自提返回按钮
        private final LinearLayout ddztlin;//到店自提Linearlayout布局
        private final LinearLayout ddztSytime;//到店自提剩余支付时间Linearlayout布局
        private final Button ddztQfkBtn;//到店自提去付款按钮

        public MyViewHolder1(View itemView) {
            super(itemView);
            back = (ImageView) itemView.findViewById(R.id.take_full_detail_back);
            mapBack = (ImageView) itemView.findViewById(R.id.course_order_back);
            mapService = (ImageView) itemView.findViewById(R.id.course_order_customer_service);
            shopImg = (ImageView) itemView.findViewById(R.id.take_full_detail_img);
            shoptv = (TextView) itemView.findViewById(R.id.take_full_detail_tv);
            stateRel = (RelativeLayout) itemView.findViewById(R.id.take_full_detail_state_relative);
            ywcLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_ywc_lin);
            dtkLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_dtk_lin);
            ygbLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_ygb_lin);
            userInfoLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_userinfo_lin);
            btnDpjQpj = (TextView) itemView.findViewById(R.id.take_full_detail_ywc_btn_qpj);
            btnDpjAgain = (TextView) itemView.findViewById(R.id.take_full_detail_ywc_btn_again);
            btnDpjSqtk = (TextView) itemView.findViewById(R.id.take_full_detail_ywc_btn_sqtk);
            btnDtkAgain = (TextView) itemView.findViewById(R.id.take_full_detail_dtk_btn_again);
            btnDtkQxtk = (TextView) itemView.findViewById(R.id.take_full_detail_dtk_btn_qxtk);
            btnDshQxdd = (TextView) itemView.findViewById(R.id.take_full_detail_dsh_btn_qxdd);
            btnDshQrsh = (TextView) itemView.findViewById(R.id.take_full_detail_dsh_btn_qrsh);
            callPeople = (ImageView) itemView.findViewById(R.id.take_full_detail_call_people);
            tvRatinStar = (TextView) itemView.findViewById(R.id.ratin_star_tv);
            ratinStar = (RatingBarView) itemView.findViewById(R.id.ratin_star);
            peopleName = (TextView) itemView.findViewById(R.id.take_full_detail_username);
            peopleImg = (ImageView) itemView.findViewById(R.id.take_full_detail_people_img);
            dfkLin = (LinearLayout) itemView.findViewById(R.id.take_detail_dfk_lin);
            divView = itemView.findViewById(R.id.take_out_view_div);
            divView2 = itemView.findViewById(R.id.take_out_view_div2);
            divDdztView2 = itemView.findViewById(R.id.take_out_view_ddzt_div2);
            divMapView = itemView.findViewById(R.id.take_out_view_map_div);
            mapView = (MapView) itemView.findViewById(R.id.myMapView);
            mapFrame = (FrameLayout) itemView.findViewById(R.id.map_frame);

            mapStatelin = (LinearLayout) itemView.findViewById(R.id.map_state_lin);
            tvTitle = (TextView) itemView.findViewById(R.id.take_full_detail_text);
            stateLin = (LinearLayout) itemView.findViewById(R.id.take_full_detaile_state_lin);

            timeLin1 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_time_lin1);
            yjTime = (TextView) itemView.findViewById(R.id.yj_time);
            timeLin2 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_time_lin2);
            sqtk = (TextView) itemView.findViewById(R.id.take_full_detail_dsh_btn_ssqk);
            qfkRelat = (RelativeLayout) itemView.findViewById(R.id.take_full_detail_qfk_relat);
            sytimeLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_sytime);
            mapUserInfoLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_map_userinfo_lin);
            callMapPeople = (ImageView) itemView.findViewById(R.id.take_full_detail_map_call_people);
            tvMapRatinStar = (TextView) itemView.findViewById(R.id.map_ratin_star_tv);
            ratinStarMap = (RatingBarView) itemView.findViewById(R.id.map_ratin_star);
            peopleNameMap = (TextView) itemView.findViewById(R.id.take_full_detail_map_username);
            peopleImgMap = (ImageView) itemView.findViewById(R.id.take_full_detail_map_people_img);
            mapDiv2 = itemView.findViewById(R.id.take_out_view_map_div2);
            ddztFrame = (FrameLayout) itemView.findViewById(R.id.take_full_detail_ddzt_lin);
            qfkBtn = (Button) itemView.findViewById(R.id.group_full_detail_qfk);

            tvTime1 = (TextView) itemView.findViewById(R.id.group_full_detail_time1);
            tvTime2 = (TextView) itemView.findViewById(R.id.group_full_detail_time2);
            tvTime3 = (TextView) itemView.findViewById(R.id.group_full_detail_time3);
            tvTime4 = (TextView) itemView.findViewById(R.id.group_full_detail_time4);
            dtkText = (TextView) itemView.findViewById(R.id.take_full_detail_dtk_text);
            dtkYuanyin = (TextView) itemView.findViewById(R.id.take_full_detail_dtk_yuanyin);

            ddztShopLogo = (ImageView) itemView.findViewById(R.id.group_full_detail_shop_img);
            ddztShopName = (TextView) itemView.findViewById(R.id.group_full_detail_shop_tv);
            ddztSqtk = (TextView) itemView.findViewById(R.id.ddzt_sqtk);
            ddztQrsh = (TextView) itemView.findViewById(R.id.ddzt_qrsh);
            ddztQpj= (TextView) itemView.findViewById(R.id.ddzt_qpj);
            ddztAgaih = (TextView) itemView.findViewById(R.id.ddzt_again);
            ddztQxdd = (TextView) itemView.findViewById(R.id.ddzt_qxdd);
            ddztQxtk = (TextView) itemView.findViewById(R.id.ddzt_qxtk);
            ddztQfkBtn = (Button) itemView.findViewById(R.id.ddzt_qfk);
            ddztPhone = (TextView) itemView.findViewById(R.id.take_full_detail_ddzt_phone);
            ddzttv = (TextView) itemView.findViewById(R.id.ddzt_text);
            ddzttime1 = (TextView) itemView.findViewById(R.id.ddzt_time1);
            ddzttime2 = (TextView) itemView.findViewById(R.id.ddzt_time2);
            ddzttime3 = (TextView) itemView.findViewById(R.id.ddzt_time3);
            ddzttime4 = (TextView) itemView.findViewById(R.id.ddzt_time4);
            ddztBack = itemView.findViewById(R.id.group_buying_shopdetails_back);
            ddztlin = (LinearLayout) itemView.findViewById(R.id.ddzt_lin);
            ddztSytime = (LinearLayout) itemView.findViewById(R.id.ddzy_sytime);



        }
    }

    //团单内容
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        private final MyListView myListView;
        private final LinearLayout linAll;
        private final TextView allTv;
        private final ImageView callShop;//联系商家
        private final LinearLayout callShopLin;//联系商家LinearLayout布局
        private final ImageView shopImg;//店铺logo
        private final TextView shopName;//店铺名字
        private final TextView bzPrice;//包装费
        private final TextView psPrice;//配送费
        private final TextView sumPrice;//合计
        private final TextView hfyuPrice;//花返优惠

        public MyViewHolder2(View itemView) {
            super(itemView);
            myListView = (MyListView) itemView.findViewById(R.id.take_full_detail_listview);
            linAll = (LinearLayout) itemView.findViewById(R.id.confirm_order_all_lin);
            allTv = (TextView) itemView.findViewById(R.id.confirm_order_all_tv);
            callShop = (ImageView) itemView.findViewById(R.id.take_full_detail_call_shop);
            shopImg = (ImageView) itemView.findViewById(R.id.confirm_order_shop_img);
            shopName = (TextView) itemView.findViewById(R.id.confirm_order_shop_name);
            callShopLin = (LinearLayout) itemView.findViewById(R.id.take_full_detail_call_shop_lin);
            bzPrice = (TextView) itemView.findViewById(R.id.confirm_order_shop_baozhuang_price);
            psPrice = (TextView) itemView.findViewById(R.id.confirm_order_shop_peisong_price);
            hfyuPrice = (TextView) itemView.findViewById(R.id.confirm_order_shop_hfyh);
            sumPrice = (TextView) itemView.findViewById(R.id.confirm_order_shop_sumprice);

        }
    }

    //订单号
    public class MyViewHolder3 extends RecyclerView.ViewHolder {

        private final TextView copy;//订单号复制
        private final TextView orderNumber;//订单号
        private final TextView zfsj;//支付时间
        private final TextView zffs;//支付方式
        private final TextView shsj;//收货时间
        private final TextView qwsj;//期望时间
        private final TextView ddbz;//订单备注
        private final TextView address;//配送地址
        private final TextView customerName;//配送联系人
        private final TextView customerPhone;//配送联系人电话
        private final TextView psfw;//配送服务
        private final RelativeLayout ddhRelat1;//订单号RelativeLayout布局
        private final LinearLayout ddsjLin1;//订单时间LinearLayout布局
        private final LinearLayout zfsjLin2;//支付时间LinearLayout布局
        private final LinearLayout zffsLin3;//支付方式LinearLayout布局
        private final LinearLayout ddbzLin4;//订单备注LinearLayout布局
        private final LinearLayout shsjLin5;//收货时间LinearLayout布局
        private final TextView ddsj;//订单时间


        public MyViewHolder3(View itemView) {
            super(itemView);
            copy = (TextView) itemView.findViewById(R.id.take_full_detail_copy);
            orderNumber = (TextView) itemView.findViewById(R.id.take_full_detail_ordernumber);
            zfsj = (TextView) itemView.findViewById(R.id.take_full_detail_zfsj);
            zffs = (TextView) itemView.findViewById(R.id.take_full_detail_zffs);
            shsj = (TextView) itemView.findViewById(R.id.take_full_detail_shsj);
            qwsj = (TextView) itemView.findViewById(R.id.take_full_detail_qwsj);
            address = (TextView) itemView.findViewById(R.id.take_full_detail_address);
            customerName = (TextView) itemView.findViewById(R.id.take_full_detail_customer_name);
            customerPhone = (TextView) itemView.findViewById(R.id.take_full_detail_customer_phone);
            psfw = (TextView) itemView.findViewById(R.id.take_full_detail_psfw);
            ddsj = (TextView) itemView.findViewById(R.id.take_full_detail_ddsj);
            ddbz = (TextView) itemView.findViewById(R.id.take_full_detail_ddbz);


            ddhRelat1 = (RelativeLayout) itemView.findViewById(R.id.take_full_detail_ddh_relat1);
            ddsjLin1 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_ddsj_lin1);
            zfsjLin2 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_zfsj_lin2);
            zffsLin3 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_zffs_lin3);
            ddbzLin4 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_ddbz_lin4);
            shsjLin5 = (LinearLayout) itemView.findViewById(R.id.take_full_detail_shsj_lin5);


        }
    }

    //商家与骑手头顶上弹窗
    public void showInfoWindow(BaiduMap baiduMap, double latitude, double longitude) {
        View infoWindowView = LayoutInflater.from(activity).inflate(R.layout.map_info_window, null);
        infoWindowTv = (TextView) infoWindowView.findViewById(R.id.info_window_tv);
        LatLng la1 = new LatLng(latitude, longitude);
//        LatLng la = new LatLng(latitude + 0.0004, longitude + 0.00005);
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        InfoWindow infoWindow = new InfoWindow(infoWindowView, la1, (int) (-53 * displayMetrics.density));

        baiduMap.showInfoWindow(infoWindow);
    }

    //地图
    public void showMap() {
        final BaiduMap mBaiduMap = holder1.mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        //设定中心点坐标
        double longitude = Double.parseDouble((String) SPUtils.get(activity, "longitude", ""));
        double latitude = Double.parseDouble((String) SPUtils.get(activity, "latitude", ""));
        Log.e("latitude2===========", latitude + "");
        Log.e("longitude2===========", longitude + "");
        LatLng cenpt = new LatLng(latitude, longitude);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(18)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        //我的位置
        LatLng latLng = new LatLng(latitude, longitude);
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_dw);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        mBaiduMap.addOverlay(markerOptions);

//                骑手位置
//                骑手纬度
        double rider_latitude = orderDetailsBean.getRider_latitude();
        //骑手经度
        double rider_longitude = orderDetailsBean.getRider_longitude();
        LatLng latLng1 = new LatLng(rider_latitude, rider_longitude);
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_qs);
        MarkerOptions markerOptions1 = new MarkerOptions()
                .position(latLng1)
                .icon(bitmap1);
        mBaiduMap.addOverlay(markerOptions1);
        showInfoWindow(mBaiduMap, rider_latitude, rider_longitude);

        //商家位置
        //商家纬度
        double mer_latitude = orderDetailsBean.getMer_latitude();
        //商家经度
        double mer_longitude = orderDetailsBean.getMer_longitude();
        LatLng latLng2 = new LatLng(mer_latitude, mer_longitude);
        BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_dj);
        MarkerOptions markerOptions2 = new MarkerOptions()
                .position(latLng2)
                .icon(bitmap2);
        mBaiduMap.addOverlay(markerOptions2);

        showInfoWindow(mBaiduMap, mer_latitude, mer_longitude);

    }

}