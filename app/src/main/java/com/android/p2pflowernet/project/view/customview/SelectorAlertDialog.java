package com.android.p2pflowernet.project.view.customview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.SelectorAlertAdapter;
import com.android.p2pflowernet.project.entity.SelectorDialogBean;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/30.
 * by--
 */

public class SelectorAlertDialog {

    private final ArrayList<SelectorDialogBean> data;
    private Context context;
    private Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private Display display;
    private boolean showTitle = false;
    private ListView listView;
    private SelectorAlertAdapter mAdapter;
    private OnItemClickLitener onItemClickLitener;

    public SelectorAlertDialog(Activity activity, ArrayList<SelectorDialogBean> selectors) {
        this.context = activity;
        this.data = selectors;
        WindowManager windowManager = (WindowManager) activity.getSystemService(Context
                .WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public SelectorAlertDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(R.layout.selector_dialog, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.listView);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
       /* lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display.getWidth() * 0.8)
                , LayoutParams.WRAP_CONTENT));*/
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (display.getWidth() * 0.8);
        dialog.setCanceledOnTouchOutside(false);
        return this;
    }

    public SelectorAlertDialog setTitle(String title) {

        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("标题");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public SelectorAlertDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener dismissListener) {
        dialog.setOnDismissListener(dismissListener);
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        dialog.setOnKeyListener(onKeyListener);
    }

    public void setCanceledOnTouchOutside(boolean b) {
        dialog.setCanceledOnTouchOutside(b);
    }

    public interface OnItemClickLitener {

        void onItemClickLitener(int position, View view);
    }

    public SelectorAlertDialog setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
        return this;
    }

    private void setLayout() {

        if (!showTitle) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (data != null && data.size() > 0) {

            mAdapter = new SelectorAlertAdapter(context, data);
            listView.setAdapter(mAdapter);
        }

        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(onItemClickLitener!=null) {

                    onItemClickLitener.onItemClickLitener(position,view);
                }
                dialog.dismiss();
            }
        });
    }

    public void show() {
        setLayout();
        dialog.show();
    }
}
