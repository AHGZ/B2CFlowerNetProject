package com.android.p2pflowernet.project.view.fragments.mine.applyfor.applyforhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.ApplyForHistoryAdapter;
import com.android.p2pflowernet.project.entity.ApplyForHistoryBean;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.AgencyActivity;
import com.android.p2pflowernet.project.view.activity.AgencyApplyQueueActivity;
import com.android.p2pflowernet.project.view.activity.MerchantActivity;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.SetPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.affirm.pay.check.CheckPayPwdActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.cloud.ApplyForCloudActivity;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ApplyForPartnerActivity;

import java.util.List;

import butterknife.BindView;


/**
 * Created by caishen on 2017/11/14.
 * by--申请历史的页面
 */

public class AppForHistoryFragment extends KFragment<IApplyForHistoryView, IApplyForHistoryPrenter>
        implements IApplyForHistoryView, ApplyForHistoryAdapter.OnStaueClickListener {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_else)
    TextView tvElse;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.ll_table)
    LinearLayout llTable;
    @BindView(R.id.ll_empt)
    LinearLayout llEmpt;
    @BindView(R.id.view_gray)
    View viewGray;

    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ApplyForHistoryBean.ListsBean> lists;
    private String agenceId = "";

    @Override
    public IApplyForHistoryPrenter createPresenter() {

        return new IApplyForHistoryPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_applyfor_history;
    }

    @Override
    public void initData() {

        mPresenter.applyHistory();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
    }

    public static KFragment newIntence() {

        AppForHistoryFragment appForHistoryFragment = new AppForHistoryFragment();
        Bundle bundle = new Bundle();
        appForHistoryFragment.setArguments(bundle);
        return appForHistoryFragment;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String getUserId() {
        return "1";
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }


    @Override
    public void setApplyForHistorySuccess(ApplyForHistoryBean data) {

        if (data != null) {

            lists = data.getLists();

            if (lists != null && lists.size() > 0) {

                listview.setVisibility(View.VISIBLE);
                llEmpt.setVisibility(View.GONE);
                llTable.setVisibility(View.VISIBLE);
                viewGray.setVisibility(View.VISIBLE);

                //设置适配器
                llTable.setVisibility(View.VISIBLE);
                ApplyForHistoryAdapter mAdapter = new ApplyForHistoryAdapter(getActivity(), lists);
                listview.setAdapter(mAdapter);
                mAdapter.setOnStaueClickListener(this);

            } else {

                listview.setVisibility(View.GONE);
                llEmpt.setVisibility(View.VISIBLE);
                llTable.setVisibility(View.GONE);
                viewGray.setVisibility(View.GONE);

                //空数据
                llTable.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public String getAgenceId() {
        return agenceId;
    }

    @Override
    public void onSuccess(String message) {

        showShortToast("退出成功！");
        mPresenter.applyHistory();
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
                intent.putExtra("cloudId", "");
                intent.putExtra("isUpdata", "0");
                intent.putExtra("state", "");
                startActivity(intent);

            } else {//未绑定过银行卡

                //判断是否设置了支付密码
                mPresenter.checkPwd();

            }
        }
    }

    /***
     * 检测是否设置过支付密码
     * @param data
     */
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
    public void onSuccessed(String message) {

    }


    @Override
    public void onResume() {
        super.onResume();

        mPresenter.applyHistory();
    }

    /**
     * adapter的状态值监听
     *
     * @param view
     * @param position
     */
    @Override
    public void onStatueClickListener(View view, int position) {

        if (lists != null && lists.size() > 0) {

            String state = lists.get(position).getState();
            String identity = lists.get(position).getIdentity();

            if (state.equals("0")) {//去申请

                if (identity.equals("合伙人")) {

                    Intent intent = new Intent(getActivity(), ApplyForPartnerActivity.class);
                    startActivity(intent);

                } else if (identity.equals("代理人")) {

                    Intent intent = new Intent(getActivity(), AgencyActivity.class);
                    intent.putExtra("state", lists.get(position).getState());
                    intent.putExtra("id", "");
                    startActivity(intent);

                } else if (identity.equals("生活商家")) {

                    Intent intent = new Intent(getActivity(), MerchantActivity.class);
                    intent.putExtra("state", lists.get(position).getState());
                    intent.putExtra("id", "");
                    startActivity(intent);

                } else {//云工

                    //判断是否绑定过银行卡
                    mPresenter.isbindCard();
                }

            } else if (state.equals("1")) {//已通过

                if (identity.equals("代理人")) {

                    agenceId = lists.get(position).getId();
                    //退出代理人
                    showAlertMsgDialog("您的（退出代理资质）申请已 提交成功，" +
                            "花返网客服会在三 个工作日之内处理您的申请， 请您耐心等待。", "温馨提示", "确定", "取消");
                }

            } else if (state.equals("2")) {//修改信息

                if (identity.equals("合伙人")) {

                } else if (identity.equals("代理人")) {

                    // 编辑代理人
                    Intent intent = new Intent(getActivity(), AgencyActivity.class);
                    intent.putExtra("state", "2");
                    intent.putExtra("id", lists.get(position).getId());
                    startActivity(intent);

                } else if (identity.equals("生活商家")) {

                    // 修改生活商家
                    Intent intent = new Intent(getActivity(), MerchantActivity.class);
                    intent.putExtra("state", lists.get(position).getState());
                    intent.putExtra("id", lists.get(position).getId());
                    startActivity(intent);

                } else {//云工

                    Intent intent = new Intent(getActivity(), ApplyForCloudActivity.class);
                    intent.putExtra("cloudId", lists.get(position).getId());
                    intent.putExtra("isUpdata", "1");
                    intent.putExtra("state", lists.get(position).getState());
                    startActivity(intent);
                }

            } else if (state.equals("3")) {//排行榜

                if (identity.equals("合伙人")) {

                } else if (identity.equals("代理人")) {

                    Intent intent = new Intent(getActivity(), AgencyApplyQueueActivity.class);
                    intent.putExtra("state", lists.get(position).getState());
                    intent.putExtra("id", lists.get(position).getId());
                    startActivity(intent);
                }
            } else if (state.equals("4")) {//审核中

            } else if (state.equals("7")) {//已退出代理资质

            }
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

                        //退出代理资质
                        mPresenter.exitAgent();
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
