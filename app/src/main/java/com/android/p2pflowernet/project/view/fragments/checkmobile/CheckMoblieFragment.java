package com.android.p2pflowernet.project.view.fragments.checkmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.mine.setting.login.ChangePhoneActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:验证手机号页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class CheckMoblieFragment extends KFragment<ICheckMobileView, ICheckMobilePresenter> {
    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.edittext_smscode)
    // 获取短信验证码
            CustomEditText edittextSmscode;
    @BindView(R.id.Sendsmsicon_tv)
    // 发送验证码
            TextView SendsmsiconTv;
    @BindView(R.id.nextto_btn)
    // 下一步按钮
            Button nexttoBtn;
    private String str = "2";


    public static CheckMoblieFragment newIntence() {

        Bundle bundle = new Bundle();
        CheckMoblieFragment checkMoblieFragment = new CheckMoblieFragment();
        checkMoblieFragment.setArguments(bundle);
        return checkMoblieFragment;
    }

    @Override
    public ICheckMobilePresenter createPresenter() {
        return new ICheckMobilePresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.app_checkmobile_fragment;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {


    }

    @OnClick({R.id.im_back, R.id.nextto_btn})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;

            case R.id.nextto_btn:

                if (str.equals("1")) {

                    showAlertMsgDialog("银行卡预留手机号不符，请核 对后再试，若银行卡预留手机 号已变更，请更新手机号。", "温馨提示",
                            "确定", "取消");
                } else {

                    showAlertMsgDialog1("恭喜您绑定银行卡添加成功并 且已实名认证！", "温馨提示", "确定");
                }

                // 下一步按钮
                mPresenter.checkPhoneToBank();

                break;

        }
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), ChangePhoneActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog1(String msg, String title, String positive) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title)
                .setMsg(msg).setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        removeFragment();
                    }
                });
        alertDialog.show();
    }
}
