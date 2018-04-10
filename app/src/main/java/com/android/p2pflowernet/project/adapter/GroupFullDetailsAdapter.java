package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.TimeTools;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by heguozhong on 2017/12/28/028.
 * 团购明细RecyclerView适配器
 */

public class GroupFullDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ZERO = 0;//商品logo
    private static final int TYPE_ONE = 1;//团单内容
    private static final int TYPE_TWO = 2;//商品数量与价格
    private static final int TYPE_THREE = 3;//购买须知
    private static final int TYPE_FOUR=4;//团购兑换码
    private FragmentActivity activity;
    private String order_num;
    private GroupFullDetailBean groupFullDetailBean;
    private TimerTask timerTask;
    private Timer mTimer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:

                    String currentStringTime = TimeTools.getCountTimeByLong((Long) msg.obj);
                    holder1.time1.setText(currentStringTime.substring(0, 1));
                    holder1.time2.setText(currentStringTime.substring(1, 2));
                    holder1.time3.setText(currentStringTime.substring(3, 4));
                    holder1.time4.setText(currentStringTime.substring(4, 5));

                    break;
            }
        }
    };

    //查看图文详情
    OnPhoteDetailsClickListener onPhoteDetailsClickListener;
    //返回按钮
    OnBackClickListener onBackClickListener;
    //复制按钮
    OnCopyClickListener onCopyClickListener;

    //打电话按钮
    OnCallClickListener onCallClickListener;

    //去评价
    OnQpjClickListener onQpjClickListener;
    //去付款
    OnQfkClickListener onQfkClickListener;

    private MyViewHolder5 holder5;

    //状态
    private final int state;
    private final GroupFullDetailBean.GroupBean group;
    private final GroupFullDetailBean.OrderBean order;
    private TakeOutOrderGroupBean.ListBean group_listBean;
    private MyViewHolder1 holder1;

    public GroupFullDetailsAdapter(FragmentActivity activity, GroupFullDetailBean groupFullDetailBean, String order_num, TakeOutOrderGroupBean.ListBean group_listBean) {
        this.activity = activity;
        this.groupFullDetailBean=groupFullDetailBean;
        this.order_num=order_num;
        this.group_listBean=group_listBean;
        order = groupFullDetailBean.getOrder();
        state = groupFullDetailBean.getOrder().getState();
        group = groupFullDetailBean.getGroup();
    }


    //查看图文详情接口
    public interface OnPhoteDetailsClickListener {
        void onPhoteDetailsClick(View v);
    }

    //返回按钮接口
    public interface OnBackClickListener {
        void onBackClick(View v);
    }
    //复制按钮接口
    public interface OnCopyClickListener {
        void onCopyClick(View v, TextView text);
    }

    //打电话按钮接口
    public interface OnCallClickListener {
        void onCallClick(View v,String tel);
    }
    //去评价
    public interface OnQpjClickListener{
        void onQpjClick(View view);
    }
    //去付款
    public interface OnQfkClickListener{
        void onQfkClick(View view);
    }

    //设置查看图文详情点击回调方法
    public void setOnPhoteDetailsClickListener(OnPhoteDetailsClickListener onPhoteDetailsClickListener) {
        this.onPhoteDetailsClickListener = onPhoteDetailsClickListener;
    }

    //设置返回按钮点击回调方法
    public void setOnBackClickListener(OnBackClickListener onBackClickListener) {
        this.onBackClickListener = onBackClickListener;
    }
    //设置复制按钮点击回调方法
    public void setOnCopyClickListener(OnCopyClickListener onCopyClickListener) {
        this.onCopyClickListener = onCopyClickListener;
    }

    //设置打电话按钮点击回调方法
    public void setOnCallClickListener(OnCallClickListener onCallClickListener) {
        this.onCallClickListener = onCallClickListener;
    }
    //去评价
    public void setOnQpjClickListener(OnQpjClickListener onQpjClickListener){
        this.onQpjClickListener=onQpjClickListener;
    }
    //去付款
    public void setOnQfkClickListener(OnQfkClickListener onQfkClickListener){
        this.onQfkClickListener=onQfkClickListener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            //绑定商品logo
            case TYPE_ZERO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_full_detail_item_shop, parent, false);
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
            //团购兑换码
            case TYPE_FOUR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_buying_shopdetails_item_tgdhm, parent, false);
                holder = new MyViewHolder5(view);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            //绑定商品logo
            case TYPE_ZERO:
                holder1 = (MyViewHolder1) holder;
                //赋值店铺logo
                String img = ApiUrlConstant.API_IMG_URL + group.getMerch_info().getFile_path();
                Glide.with(activity).load(img).asBitmap().placeholder(R.mipmap.default_header).into(holder1.shopImg);

                //赋值店铺名称
                holder1.shoptv.setText(group.getMerch_info().getName());
                //设置适配器
                GroupGullDetailsListviewAdapter groupGullDetailsListviewAdapter = new GroupGullDetailsListviewAdapter(activity,group_listBean);
                holder1.mylistview.setAdapter(groupGullDetailsListviewAdapter);

                //扩大返回按钮点击范围
                UIUtils.setTouchDelegate(holder1.back, 50);
                //设置返回按钮监听
                holder1.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onBackClickListener!=null){
                            onBackClickListener.onBackClick(v);
                        }
                    }
                });
                List<GroupFullDetailBean.OrderBean.CodeBean> code = order.getCode();

                if (code!=null){
                    GroupGullDetailsTGListviewAdapter groupGullDetailsTGListviewAdapter = new GroupGullDetailsTGListviewAdapter(activity,code);
                    holder1.tgdhmListview.setAdapter(groupGullDetailsTGListviewAdapter);
                }

                //是否支持打包(0不支持1支持)
                if (group.getIs_takeaway()==0){
                    holder1.zcdbRelat.setVisibility(View.GONE);
                }else{
                    holder1.zcdbRelat.setVisibility(View.VISIBLE);
                }


                //0-没付款 1-待使用 2-(全部份数)已使用 3-已退款
                if (state==0){//待付款
                    if (code==null){
                        holder1.linTgdhm.setVisibility(View.GONE);
                    }
                    holder1.linDfk.setVisibility(View.VISIBLE);
                    holder1.btnQfk.setVisibility(View.VISIBLE);
                    mTimer = new Timer();
                    //刷新倒计时
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain();

                            //结束时间减去当前时间
                            String endTimes = DateUtils.timesdate(order.getSy_time()+"");

                            Long start = System.currentTimeMillis();
                            Long end = TimeTools.stringToLongTime(endTimes);

                            msg.what = 100;
                            msg.obj = end - start;
                            mHandler.sendMessage(msg);
                        }
                    };
                    //0秒之后每隔1秒执行一次run
                    mTimer.schedule(timerTask, 1, 1000);

                }else if (state==1){//待使用
                    if (code.size()==0){
                        holder1.linTgdhm.setVisibility(View.GONE);
                    }else{
                        holder1.linTgdhm.setVisibility(View.VISIBLE);
                    }
                    holder1.linDsy.setVisibility(View.VISIBLE);
                    holder1.linSst.setBackgroundColor(Color.parseColor("#F7F7F7"));

                }else if (state==2){//已使用
                    if (code.size()==0){
                        holder1.linTgdhm.setVisibility(View.GONE);
                    }else{
                        holder1.linTgdhm.setVisibility(View.VISIBLE);
                    }
                    holder1.linDpj.setVisibility(View.VISIBLE);
                    holder1.linSst.setBackgroundColor(Color.parseColor("#F7F7F7"));

                }else if (state==3){//已退款
                    if (code.size()==0){
                        holder1.linTgdhm.setVisibility(View.GONE);
                    }else{
                        holder1.linTgdhm.setVisibility(View.VISIBLE);
                    }
                    holder1.linGqtk.setVisibility(View.VISIBLE);
                    holder1.linSst.setBackgroundColor(Color.parseColor("#F7F7F7"));

                }

                //去付款监听调用方法
                holder1.btnQfk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQfkClickListener!=null){
                            onQfkClickListener.onQfkClick(v);
                        }
                    }
                });
                //去评价监听调用方法
                holder1.qpj.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onQpjClickListener!=null){
                            onQpjClickListener.onQpjClick(v);
                        }
                    }
                });

                break;
            //绑定团单内容
            case TYPE_ONE:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;
                //赋值商家名称
                holder2.groupBuyingShopdetailsShopName.setText(group.getMerch_info().getName());
                //赋值商家地址
                holder2.groupBuyingShopdetailsShopAddress.setText(group.getMerch_info().getAddress());
                //赋值距离商家距离
                holder2.groupBuyingShopdetailsShopMi.setText(group.getMerch_info().getDistance()+"m");

                //设置打电话按钮监听
                holder2.callPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCallClickListener!=null){
                            //将店铺电话回调
                            onCallClickListener.onCallClick(v,group.getMerch_info().getTel());
                        }
                    }
                });
                List<GroupFullDetailBean.GroupBean.GoodsListBean> goods_list = group.getGoods_list();
                GroupShopDetailListAdapter groupShopDetailListAdapter = new GroupShopDetailListAdapter(goods_list);
                holder2.listView.setAdapter(groupShopDetailListAdapter);
                break;
            //商品数量与价格
            case TYPE_TWO:
                MyViewHolder3 holder3 = (MyViewHolder3) holder;
                holder3.groupBuyingShopdetailsSumPrice.setText("¥" +group.getZprice());
                holder3.groupBuyingShopdetailsSumNum.setText(group.getZnum() + "份");
                holder3.groupBuyingShopdetailsTwxq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onPhoteDetailsClickListener.onPhoteDetailsClick(v);
                    }
                });
                //设置图文详情监听
                holder3.groupBuyingShopdetailsTwxq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onPhoteDetailsClickListener != null) {
                            onPhoteDetailsClickListener.onPhoteDetailsClick(v);
                        }

                    }
                });
                break;
            //购买须知
            case TYPE_THREE:
                MyViewHolder4 holder4 = (MyViewHolder4) holder;
                List<GroupFullDetailBean.GroupBean.NoticeBean> notice = group.getNotice();
                ShoppingKnowAdapter shoppingKnowAdapter = new ShoppingKnowAdapter(notice);
                holder4.myListView.setAdapter(shoppingKnowAdapter);
                break;
            //团购兑换码
            case TYPE_FOUR:
                holder5 = (MyViewHolder5) holder;

                //赋值购买数量
                holder5.buyNum.setText(order.getNum());
                //赋值支付方式
                //0-未支付 1-微信 2-支付宝 3-银联 4-账户余额
                if (order.getPay_channel()==0){
                    holder5.zffs.setText("未支付");
                }else if (order.getPay_channel()==1){
                    holder5.zffs.setText("微信支付");
                }else if (order.getPay_channel()==2){
                    holder5.zffs.setText("支付宝支付");
                }else if (order.getPay_channel()==3){
                    holder5.zffs.setText("银联支付");
                }else if (order.getPay_channel()==4){
                    holder5.zffs.setText("账户余额支付");
                }
                //赋值订单号
                holder5.orderNumber.setText(order.getOrder_num());
                //赋值支付时间
                String timedate = DateUtils.timedate(order.getPay_time());
                holder5.zfsj.setText(timedate);

                //设置订单号复制按钮监听
                holder5.copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCopyClickListener!=null){
                            onCopyClickListener.onCopyClick(v, holder5.orderNumber);
                        }
                    }
                });
                //0-没付款 1-待使用 2-(全部份数)已使用 3-已退款
                if (state==0){//待付款
                    holder5.relat2.setVisibility(View.VISIBLE);
                    holder5.lin3.setVisibility(View.VISIBLE);
                    holder5.copy.setVisibility(View.VISIBLE);

                }else if (state==1){//待使用
                    holder5.relat2.setVisibility(View.VISIBLE);
                    holder5.lin1.setVisibility(View.VISIBLE);
                    holder5.lin2.setVisibility(View.VISIBLE);
                    holder5.lin3.setVisibility(View.VISIBLE);
                    holder5.copy.setVisibility(View.VISIBLE);

                }else if (state==2){//已使用
                    holder5.relat2.setVisibility(View.VISIBLE);
                    holder5.lin1.setVisibility(View.VISIBLE);
                    holder5.lin2.setVisibility(View.VISIBLE);
                    holder5.lin3.setVisibility(View.VISIBLE);
                    holder5.copy.setVisibility(View.VISIBLE);

                }else if (state==3){//已退款
                    holder5.relat2.setVisibility(View.VISIBLE);
                    holder5.lin1.setVisibility(View.VISIBLE);
                    holder5.lin2.setVisibility(View.VISIBLE);
                    holder5.lin3.setVisibility(View.VISIBLE);
                    holder5.copy.setVisibility(View.VISIBLE);

                }

                break;

        }
    }

    @Override
    public int getItemCount() {
        return 5;
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
        } else {
            return TYPE_FOUR;
        }
    }

    //商品logo
    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        private final ImageView back;//返回按钮
        private final ImageView shopImg;//店铺logo
        private final TextView shoptv;//店铺名称
        private final TextView qpj;//去评价按钮
        private final MyListView mylistview;
        private final Button btnQfk;
        private final LinearLayout linSst;
        private final LinearLayout linTgdhm;
        private final LinearLayout linDfk;//待付款linearlayout布局
        private final LinearLayout linDpj;//（已使用）待评价linearlayout布局
        private final LinearLayout linGqtk;//过期退款linearlayout布局
        private final LinearLayout linDsy;//待使用linearlayout布局
        private final RelativeLayout zcdbRelat;//支持打包
        private final RelativeLayout sctRelat;//随时退
        private final RelativeLayout dqzdtRelat;//到期自动退
        private final MyListView tgdhmListview;//团购兑换劵码listview
        private final TextView time1;
        private final TextView time2;
        private final TextView time3;
        private final TextView time4;

        public MyViewHolder1(View itemView) {
            super(itemView);
            back = (ImageView) itemView.findViewById(R.id.group_buying_shopdetails_back);
            mylistview = (MyListView) itemView.findViewById(R.id.group_full_detail_listview);
            shopImg = (ImageView) itemView.findViewById(R.id.group_full_detail_shop_img);
            shoptv= (TextView) itemView.findViewById(R.id.group_full_detail_shop_tv);
            qpj= (TextView) itemView.findViewById(R.id.group_full_detail_qbj);
            btnQfk =(Button) itemView.findViewById(R.id.group_full_detail_qfk);
            linSst = (LinearLayout) itemView.findViewById(R.id.group_full_detail_sst_lin);
            linTgdhm = (LinearLayout) itemView.findViewById(R.id.group_full_detail_tgdhm_lin);
            linDfk = (LinearLayout) itemView.findViewById(R.id.group_full_detail_dfk_lin);
            time1 = (TextView) itemView.findViewById(R.id.group_full_detail_time1);
            time2 = (TextView) itemView.findViewById(R.id.group_full_detail_time2);
            time3 = (TextView) itemView.findViewById(R.id.group_full_detail_time3);
            time4 = (TextView) itemView.findViewById(R.id.group_full_detail_time4);
            linDpj = (LinearLayout) itemView.findViewById(R.id.group_full_detail_dpj_lin);
            linGqtk = (LinearLayout) itemView.findViewById(R.id.group_full_detail_gqtk_lin);
            linDsy = (LinearLayout) itemView.findViewById(R.id.group_full_detail_dsy_lin);
            zcdbRelat = (RelativeLayout) itemView.findViewById(R.id.group_buying_shopdetails_zcdb_relat);
            sctRelat = (RelativeLayout) itemView.findViewById(R.id.group_buying_shopdetails_sct_relat);
            dqzdtRelat = (RelativeLayout) itemView.findViewById(R.id.group_buying_shopdetails_dqzdt_relat);
            tgdhmListview = (MyListView) itemView.findViewById(R.id.group_full_detail_tgdhm_listview);
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
        private final TextView groupShopName;//购买商品的名称
        private final LinearLayout groupBuyingShopdetailsTwxq;//图文详情

        public MyViewHolder3(View itemView) {
            super(itemView);
            groupBuyingShopdetailsSumNum = (TextView) itemView.findViewById(R.id.group_buying_shopdetails_sum_num);
            groupShopName = (TextView) itemView.findViewById(R.id.group_full_detail_name);
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
    //团购兑换码
    public class MyViewHolder5 extends RecyclerView.ViewHolder {

        private final TextView copy;//订单号复制
        private final TextView orderNumber;//订单号
        private final TextView zfsj;//支付时间
        private final TextView zffs;//支付方式
        private final TextView buyNum;//购买数量
        private final RelativeLayout relat2;//订单号RelativeLayout布局
        private final LinearLayout lin1;//支付时间LinearLayout布局
        private final LinearLayout lin2;//支付方式LinearLayout布局
        private final LinearLayout lin3;//购买数量LinearLayout布局

        public MyViewHolder5(View itemView) {
            super(itemView);
            copy = (TextView) itemView.findViewById(R.id.group_full_detail_copy);
            orderNumber = (TextView) itemView.findViewById(R.id.group_full_detail_ordernumber);
            zfsj = (TextView) itemView.findViewById(R.id.group_full_detail_zfsj);
            zffs = (TextView) itemView.findViewById(R.id.group_full_detail_zffs);
            buyNum = (TextView) itemView.findViewById(R.id.group_full_detail_buy_num);
            relat2 =(RelativeLayout) itemView.findViewById(R.id.group_full_detail_relat2);
            lin1 =(LinearLayout) itemView.findViewById(R.id.group_full_detail_lin1);
            lin2 =(LinearLayout) itemView.findViewById(R.id.group_full_detail_lin2);
            lin3 =(LinearLayout) itemView.findViewById(R.id.group_full_detail_lin3);
        }
    }

}
