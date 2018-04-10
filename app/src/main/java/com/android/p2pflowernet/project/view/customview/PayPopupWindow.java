package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.utils.UIUtils;

/**
 * Created by caishen on 2017/11/1.
 * by--自定义底部弹窗支付页面
 */

public class PayPopupWindow extends PopupWindow {

    private Context mContext;

    private View view;


    public PayPopupWindow(Context mContext, String Money, String YeMoney, View.OnClickListener WxOnClickListener,
                          View.OnClickListener ZfbxOnClickListener, View.OnClickListener YeOnClickListener,
                          View.OnClickListener rlBankOnClickListener, View.OnClickListener closeListener) {

        this.view = LayoutInflater.from(mContext).inflate(R.layout.pay_popup_window, null);
        RelativeLayout rl_wx = (RelativeLayout) this.view.findViewById(R.id.rl_wx);
        TextView tv_money = (TextView) this.view.findViewById(R.id.tv_money);
        TextView tv_ye_money = (TextView) this.view.findViewById(R.id.tv_ye_money);
        RelativeLayout rl_zfb = (RelativeLayout) this.view.findViewById(R.id.rl_zfb);
        RelativeLayout rl_ye = (RelativeLayout) this.view.findViewById(R.id.rl_ye);
        ImageView iv_close = (ImageView) this.view.findViewById(R.id.iv_close);
        MyListView listview = (MyListView) this.view.findViewById(R.id.listview);
        RelativeLayout rl_bank = (RelativeLayout) this.view.findViewById(R.id.rl_bank);
        ElastticityScrollView myScrollview = (ElastticityScrollView) this.view.findViewById(R.id.myScrollview);
        LinearLayout ll_pay = (LinearLayout) this.view.findViewById(R.id.ll_pay);

        //定位到第一行
        myScrollview.smoothScrollTo(0, 10);

        //设置适配器
//        AddBankListAdapter mAdapter = new AddBankListAdapter(mContext, data);
//        listview.setAdapter(mAdapter);

        //设置listview的点击事件
//        listview.setOnItemClickListener(AddBankOnItemClickListner);

        //设置点击事件
        rl_wx.setOnClickListener(WxOnClickListener);
        rl_zfb.setOnClickListener(ZfbxOnClickListener);
        rl_ye.setOnClickListener(YeOnClickListener);
        rl_bank.setOnClickListener(rlBankOnClickListener);

        //增强视图的可点击性
        UIUtils.setTouchDelegate(iv_close, 50);

        //左上角关闭按钮
        iv_close.setOnClickListener(closeListener);
        tv_money.setText(Money == null ? "¥" + "0.00" : "¥" + Money);//付款金额
        tv_ye_money.setText(YeMoney == null ? "¥" + "0.00" : "¥" + YeMoney);//余额

        // 设置外部可点击
        this.setOutsideTouchable(false);

    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        // 设置弹出窗体的宽和高
        this.setHeight(height * 3 / 4);
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);
    }
}
