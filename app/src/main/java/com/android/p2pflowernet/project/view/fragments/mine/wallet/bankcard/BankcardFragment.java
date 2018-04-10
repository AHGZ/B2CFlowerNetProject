package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.event.CheckPayEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdFragment;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.SetPayPwdFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:银行卡页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class BankcardFragment extends KFragment<IBankcardView, IBankcardPrenter> implements IBankcardView {
    @BindView(R.id.im_back)
    // 返回按钮
            ImageView im_back;
    @BindView(R.id.tv_unbind)
    //解绑
            TextView tv_unbind;
    @BindView(R.id.bankcardname_tv)
    // 银行卡名称
            TextView bankcardname_tv;
    @BindView(R.id.bankcard_im)
    // 银行卡图片
            ImageView bankcard_im;
    @BindView(R.id.addbankcard_ly)
    // 添加银行卡
            LinearLayout addbankcard_ly;
    private ShapeLoadingDialog shapeLoadingDialog;
    private String bankId = "";
    private String tag = "";//0解绑银行卡 1添加银行卡
    private List<BankInfoBean.ListBean> list;


    public static BankcardFragment newIntence() {

        Bundle bundle = new Bundle();
        BankcardFragment walletFragment = new BankcardFragment();
        walletFragment.setArguments(bundle);
        return walletFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public IBankcardPrenter createPresenter() {
        return new IBankcardPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_bankcardlist_fragment;
    }

    @Override
    public void initData() {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(im_back, 50);
        UIUtils.setTouchDelegate(tv_unbind, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //获取银行卡信息
        mPresenter.getBankInfo();
    }

    @OnClick({R.id.im_back, R.id.tv_unbind, R.id.addbankcard_ly})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:

                // 返回按钮
                removeFragment();

                break;
            case R.id.tv_unbind:

                tag = "0";
                if (list != null && list.size() > 0) {

                    //解绑银行卡
                    showAlertMsgDialog("确认解绑当前银行卡", "解绑银行卡", "确定", "取消");

                } else {

                    showShortToast("请先添加一张银行卡");
                }

                break;
            case R.id.addbankcard_ly:

                tag = "1";
                //判断是否设置了支付密码
                mPresenter.checkPwd();

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CheckPayEvent event) {
        String invoice = event.getStr();
        if (invoice.equals("0")) {

            // 解绑银行卡
            mPresenter.delBnak();
        }
    }

    @Override
    public void showDialog() {

        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.show();
        }
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(BankInfoBean data, String message) {

        if (data != null) {

            list = data.getList();
            if (list != null && list.size() > 0) {

                bankcardname_tv.setText(list.get(0).getBank_name());
                bankId = list.get(0).getId();
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                glideImageLoader.displayImage(getActivity(), list.get(0).getBankimg(), bankcard_im);

                addbankcard_ly.setVisibility(View.GONE);

            } else {

                addbankcard_ly.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public String getBankId() {

        return bankId;
    }

    @Override
    public void onSuccessDel(String message) {
        showShortToast(message);
        removeFragment();
    }

    /**
     * 检测用户是否设置过支付密码
     *
     * @param data
     */
    @Override
    public void onSuccessCheck(CheckPwdBean data) {

        if (data != null) {

            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {

                // 去设置支付密码
                addFragment(SetPayPwdFragment.newIntence(tag));

            } else {

                //检测输入的支付密码
                addFragment(CheckPayPwdFragment.newIntence(tag));
            }
        }
    }

    @Override
    public void onSuccessed(String message) {

        showShortToast(message);
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        mPresenter.checkPwd();
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

}
