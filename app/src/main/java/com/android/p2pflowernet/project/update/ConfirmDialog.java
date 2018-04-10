package com.android.p2pflowernet.project.update;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;

/**
 * @描述: 弹出框
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 上午9:22
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 上午9:22
 * @修改备注：
 * @throws
 */
public class ConfirmDialog extends Dialog {

    ConfirmCallback callback;
    private TextView content;
    private TextView sureBtn;
    public TextView cancleBtn;

    public ConfirmDialog(Context context, ConfirmCallback callback) {
        super(context, R.style.CustomDialog);
        this.callback = callback;
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        sureBtn = (TextView) mView.findViewById(R.id.dialog_confirm_sure);
        cancleBtn = (TextView) mView.findViewById(R.id.dialog_confirm_cancle);
        content = (TextView) mView.findViewById(R.id.dialog_confirm_title);


        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.callback();
                ConfirmDialog.this.cancel();
            }
        });
        super.setContentView(mView);
    }


    public ConfirmDialog setContent(String s) {
        content.setText(s);
        return this;
    }
}
