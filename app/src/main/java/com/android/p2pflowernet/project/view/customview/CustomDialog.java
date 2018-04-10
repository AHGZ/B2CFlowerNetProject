package com.android.p2pflowernet.project.view.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.android.p2pflowernet.project.R;

/**
 * @描述:弹出对话框
 * @创建人：zhangpeisen
 * @创建时间：2017/11/2 上午10:19
 * @修改人：zhangpeisen
 * @修改时间：2017/11/2 上午10:19
 * @修改备注：
 * @throws
 */
public class CustomDialog extends Dialog {

    public CustomDialog(Context context) {
        super(context);
        this.show();
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emption_dialog);
    }
}
