package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforwait;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AppForWaitForAdapter;
import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.event.AuthenticationEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.AgencyActivity;
import com.android.p2pflowernet.project.view.activity.AgencyApplyQueueActivity;
import com.android.p2pflowernet.project.view.activity.AuthenticationActivity;
import com.android.p2pflowernet.project.view.activity.MerchantActivity;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud.ApplyForCloudActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.manufac.ManufacActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ApplyForPartnerActivity;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;


/**
 * Created by caishen on 2017/11/14.
 * by--待申请页面
 */

public class ApplyForWaitFragment extends KFragment<IApplyWaitForView, IApplyWaitForPrenter>
        implements IApplyWaitForView {

    @BindView(R.id.listview)
    MyListView listview;
    private String isChecked = "0";
    private ShapeLoadingDialog shapeLoadingDialog;
    private Intent intent;
    private IdEntityBean idEntityBean;
    private int is_checked;

    @Override
    public IApplyWaitForPrenter createPresenter() {

        return new IApplyWaitForPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_wait;
    }

    @Override
    public void initData() {

        //初始化待申请的状态值
        mPresenter.waitApplyFor();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
        //判断是否实名认证过。
        mPresenter.checkIdentity();
    }

    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative, final String state) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //排行榜的页面
                        Intent intent = new Intent(getActivity(), AgencyApplyQueueActivity.class);
                        intent.putExtra("state", state);
                        intent.putExtra("id", "");
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

    public static KFragment newIntence() {

        ApplyForWaitFragment applyForWaitFragment = new ApplyForWaitFragment();
        Bundle bundle = new Bundle();
        applyForWaitFragment.setArguments(bundle);
        return applyForWaitFragment;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.waitApplyFor();
    }

    @Override
    public String getUserId() {
        return "1";
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {

            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void setApplyForWaitSuccess(ApplyForWaitBean data) {

        if (data != null) {

            final List<ApplyForWaitBean.ListsBean> lists = data.getLists();

            if (lists != null && lists.size() > 0) {

                //设置适配器
                AppForWaitForAdapter mAdapter = new AppForWaitForAdapter(getActivity(), lists);
                listview.setAdapter(mAdapter);

                //设置状态值得点击事件
                mAdapter.setOnStaueClickListener(new AppForWaitForAdapter.OnStaueClickListener() {
                    @Override
                    public void onStatueClickListener(View view, int position) {

                        if (lists == null || lists.size() < 0) {

                            return;
                        }

                        ApplyForWaitBean.ListsBean listsBean = lists.get(position);
                        String identity = listsBean.getIdentity();
                        if (is_checked == 1) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("idEntityBean", idEntityBean);
                            if (identity.equals("合伙人")) {

                                if (view.getTag().equals("1")) {

                                    //追加股份
                                    Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                                    intent.putExtra("isAdd", true);//是否追加
                                    intent.putExtra("type", "0");//购买类型（0.合伙人，1.代理人）
                                    startActivity(intent);

                                } else {

                                    //去申请合伙人
                                    intent = new Intent(getActivity(), ApplyForPartnerActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                    //友盟统计
                                    MobclickAgent.onEvent(getActivity(), "applyforP");
                                }

                            } else if (identity.equals("代理人")) {

                                if (view.getTag().equals("1")) {//再次购买代理人资质

                                    //再次购买
                                    Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                                    intent.putExtra("isAdd", true);//是否追加
                                    intent.putExtra("type", "1");//购买类型（0.合伙人，1.代理人）
                                    startActivity(intent);

                                } else if (view.getTag().equals("2")) {//追加代理人资质金额

                                showShortToast("追加金额");
                                Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                                intent.putExtra("isAdd", true);//是否追加
                                intent.putExtra("type", "1");//购买类型（0.合伙人，1.代理人）
                                startActivity(intent);

                                } else if (view.getTag().equals("3")) {//申请排队

                                    showAlertMsgDialog("非常抱歉，您的帐号所属地已有区域代理人" +
                                                    "。您可以【申请排队】、或拨打400XXxxxXX客服热线，申请成为其他地区代理人",
                                            "温馨提示", "去排队", "取消", lists.get(position).getState());

                                } else if (view.getTag().equals("0")) {

                                    //申请
                                    intent = new Intent(getActivity(), AgencyActivity.class);
                                    intent.putExtra("state", lists.get(position).getState());
                                    intent.putExtras(bundle);
                                    startActivity(intent);

                                    //友盟统计
                                    MobclickAgent.onEvent(getActivity(), "applyforA");
                                }

                            } else if (identity.equals("生活商家")) {

                                //商家
                                intent = new Intent(getActivity(), MerchantActivity.class);
                                intent.putExtra("state", lists.get(position).getState());
                                intent.putExtra("id", "");
                                intent.putExtras(bundle);
                                startActivity(intent);

                                //友盟统计
                                MobclickAgent.onEvent(getActivity(), "applyforM");

                            } else if (identity.equals("云工")) {

//                            if (isChecked.equals("0")) {//未实名认证
//
//                                showAlertMsgDialog("抱歉，您还未实名认证，不能 申请成为云工，现在就去 绑定实名认证。",
//                                        "温馨提示", "确定", "取消");
//                            } else {

                                //判断是否绑定过银行卡
                                mPresenter.isbindCard();
//                            }
                            } else if (identity.equals("厂家")) {

                                intent = new Intent(getActivity(), ManufacActivity.class);
                                intent.putExtra("state", lists.get(position).getState());
                                intent.putExtra("id", "");
                                intent.putExtras(bundle);
                                startActivity(intent);

                                //友盟统计
                                MobclickAgent.onEvent(getActivity(), "applyforMa");
                            }
                        }else{//去实名认证
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_authentication,null);
                            TextView cancelView = (TextView) dialogView.findViewById(R.id.tv_cancel);
                            TextView sureView = (TextView) dialogView.findViewById(R.id.tv_sure);
                            builder.setView(dialogView);
                            final AlertDialog dialog = builder.show();
                            cancelView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (dialog != null && dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                }
                            });

                            sureView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (dialog != null && dialog.isShowing()) {
                                        dialog.dismiss();
                                    }
                                    startActivity(new Intent(getActivity(), AuthenticationActivity.class));
                                }
                            });
                        }
                    }
                });
            }
        }
    }

    /***
     * 判断是否绑定过银行卡
     * @param data
     * @param message
     */
    @Override
    public void onCardSuccess(BankInfoBean data, String message) {

        if (data != null) {

            List<BankInfoBean.ListBean> list = data.getList();

            if (list != null && list.size() > 0) {//绑定过银行

                Intent intent = new Intent(getActivity(), ApplyForCloudActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtra("cloudId", "");
                intent.putExtra("isUpdata", "0");
                intent.putExtra("state", "");
                bundle.putSerializable("idEntityBean",idEntityBean);
                intent.putExtras(bundle);
                startActivity(intent);

            } else {//未绑定过银行卡

                //判断是否设置了支付密码
                mPresenter.checkPwd();
            }
        }
    }

    @Override
    public void onSuccessCheck(CheckPwdBean data) {

        if (data != null) {

            //0: 否，1： 是
            int is_set = data.getIs_set();
            if (is_set == 0) {

                // 去设置支付密码
                // 去设置支付密码
                Intent intent = new Intent(getActivity(), SetPayPwdActivity.class);
                intent.putExtra("tag", "8");
                startActivity(intent);

            } else {

                //检测输入的支付密码
                Intent intent = new Intent(getActivity(), CheckPayPwdActivity.class);
                intent.putExtra("tag", "8");
                startActivity(intent);
            }
        }
    }

    @Override
    public void onSuccess(String message) {

    }

    /**
     * 检测是否实名认证
     *
     * @param data
     */
    @Override
    public void setGetIdentitySuccess(IdEntityBean data) {
        idEntityBean = data;
        is_checked = data.getIs_checked();
    }

    /**
     * 实名认证成功刷新
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AuthenticationEvent event){
        mPresenter.checkIdentity();
    }
}
