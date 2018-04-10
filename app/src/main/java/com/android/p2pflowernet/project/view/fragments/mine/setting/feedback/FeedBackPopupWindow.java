package com.android.p2pflowernet.project.view.fragments.mine.setting.feedback;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

/**
 * Created by caishen on 2017/11/24.
 * by--
 */

public class FeedBackPopupWindow extends PopupWindow {


    private FragmentActivity mContext;
    private View view;

    public FeedBackPopupWindow(FragmentActivity activity, String tvType, String tvReply, String etFeedback, String loginName) {
        this.mContext = activity;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.feedback_popup_window, null);

        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        TextView tv_reply = (TextView) view.findViewById(R.id.tv_reply);
        TextView tv_num_text = (TextView) view.findViewById(R.id.tv_num_text);
        TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
        EditText et_feedback = (EditText) view.findViewById(R.id.et_feedback);
        TextView tv_login_name = (TextView) view.findViewById(R.id.tv_login_name);

        tv_login_name.setText(loginName);
        tv_type.setText(tvType);
        tv_num_text.setText(etFeedback.length() + "/100字");
        tv_reply.setText(tvReply);
        et_feedback.setText(etFeedback);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
         /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        // 设置弹出窗体的宽和高
        this.setHeight(height * 2 / 3);
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
