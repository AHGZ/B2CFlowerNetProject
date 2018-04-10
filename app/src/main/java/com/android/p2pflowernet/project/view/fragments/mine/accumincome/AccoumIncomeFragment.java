package com.android.p2pflowernet.project.view.fragments.mine.accumincome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AcmIncomBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.partnerapttitude.BuyPartnerAptitudeActiivty;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.ApplyForActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @描述:累计收益页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class AccoumIncomeFragment extends KFragment<IAccoumIncomeView, IAccoumIncomePrenter> implements IAccoumIncomeView {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;
    @BindView(R.id.accumincome_date)
    //  收益日期
            TextView accumincomeDate;
    @BindView(R.id.accumincome_amount)
    // 收益金额
            TextView accumincomeAmount;
    @BindView(R.id.consumptionincome_amount)
    // 消费收益
            TextView consumptionincomeAmount;
    @BindView(R.id.recomdrevenue_amount)
    // 推荐收益
            TextView recomdrevenueAmount;
    @BindView(R.id.tv_ghs_amount)
    //供货商销售总额
    TextView tv_ghs_amount;

    //申请供货商
    @BindView(R.id.tv_ghs_apply)
    TextView tv_ghs_apply;

    //申请生活商家
    @BindView(R.id.tv_shsj_apply)
    TextView tv_shsj_apply;

    @BindView(R.id.tv_shsj_amount)
    //生活商家销售总额
    TextView tv_shsj_amount;
    @BindView(R.id.partnerprofit_tv)
    // 申请合伙人
            TextView partnerprofitTv;
    @BindView(R.id.partnerprofit_amount)
    // 合伙人收益
            TextView partnerprofit_amount;
    @BindView(R.id.agencyincome_tv)
    // 申请代理人
            TextView agencyincomeTv;
    @BindView(R.id.agencyincome_amount)
    // 代理人收益
            TextView agencyincome_amount;
    private ShapeLoadingDialog shapeLoadingDialog;

    private String isAgent;
    private String isPartner;
    private int is_partner = -1;//是否是合伙人
    private int is_merchant = -1;//是否商家
    private int is_manufac = -1;//是否厂家
    private int is_agent = -1 ;//是否代理人


    public static AccoumIncomeFragment newIntence(String is_agent, String is_partner) {
        Bundle bundle = new Bundle();
        AccoumIncomeFragment agencyEditFragment = new AccoumIncomeFragment();
        bundle.putString("is_agent", is_agent);
        bundle.putString("is_partner", is_partner);
        agencyEditFragment.setArguments(bundle);
        return agencyEditFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAgent = getArguments().getString("is_agent");
        isPartner = getArguments().getString("is_partner");

        Log.e("TAG", "isAgent==" + isAgent);
    }

    @Override
    public IAccoumIncomePrenter createPresenter() {
        return new IAccoumIncomePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.mine_accumincome_fragment;
    }

    @Override
    public void initData() {
        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //初始化加载进度条
        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .delay(5000)
                .loadText("加载中...")
                .build();
        mPresenter.userincome();

    }


    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog.isShowing())
            shapeLoadingDialog.dismiss();
    }

    @Override
    public void onError(String s) {
        showShortToast(s);
    }

    @Override
    public void onSuccess(AcmIncomBean acmIncomBean) {
        if (acmIncomBean == null) {
            return;
        }
        is_partner = acmIncomBean.getIs_partner();
        is_merchant = acmIncomBean.getIs_merchant();
        is_manufac = acmIncomBean.getIs_manufac();
        is_agent = acmIncomBean.getIs_agent();

        // 累计收益
        accumincomeAmount.setText(TextUtils.isEmpty(acmIncomBean.getTotalincome()) ? "" : "￥" + acmIncomBean.getTotalincome());
        // 消费收益
        consumptionincomeAmount.setText(TextUtils.isEmpty(acmIncomBean.getTotalrebate()) ? "" : "￥" + acmIncomBean.getTotalrebate());
        // 推荐收益
        recomdrevenueAmount.setText(TextUtils.isEmpty(acmIncomBean.getRecomincome()) ? "" : "￥" + acmIncomBean.getRecomincome());
//        if (isAgent.equals("")) {
//            return;
//        }
//        if (isPartner.equals("")) {
//            return;
//        }
        if (is_partner == 1) {
            // 合伙人收益
            partnerprofit_amount.setText(TextUtils.isEmpty(acmIncomBean.getPartner()) ? "" : "￥" + acmIncomBean.getPartner());
            partnerprofit_amount.setVisibility(View.VISIBLE);
            partnerprofitTv.setVisibility(View.GONE);
        } else {
            partnerprofit_amount.setVisibility(View.GONE);
            partnerprofitTv.setVisibility(View.VISIBLE);
            partnerprofitTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 去申请合伙人
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                }
            });
        }
        if (is_agent == 1) {
            // 代理人收益
            agencyincome_amount.setText(TextUtils.isEmpty(acmIncomBean.getAgent()) ? "" : "￥" + acmIncomBean.getAgent());
            agencyincome_amount.setVisibility(View.VISIBLE);
            agencyincomeTv.setVisibility(View.GONE);

        } else if (is_agent == 2) {//追加金额

            agencyincomeTv.setVisibility(View.VISIBLE);
            agencyincomeTv.setText("追加金额");
            agencyincomeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //追加代理人金额
                    Intent intent = new Intent(getActivity(), BuyPartnerAptitudeActiivty.class);
                    intent.putExtra("isAdd", true);//是否追加
                    intent.putExtra("type", "1");//购买类型（0.合伙人，1.代理人）
                    startActivity(intent);
                }
            });

        } else {

            agencyincome_amount.setVisibility(View.GONE);
            agencyincomeTv.setVisibility(View.VISIBLE);
            agencyincomeTv.setText("申请代理人");
            agencyincomeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 去申请代理人
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    intent.putExtra("state", "");
                    startActivity(intent);
                }
            });
        }

        //供货商销售总额
        if (is_manufac == 1) {
            tv_ghs_amount.setText(TextUtils.isEmpty(acmIncomBean.getPartner()) ? "" : "￥" + acmIncomBean.getPartner());
            tv_ghs_amount.setVisibility(View.VISIBLE);
            tv_ghs_apply.setVisibility(View.GONE);
        } else {
            tv_ghs_amount.setVisibility(View.GONE);
            tv_ghs_apply.setVisibility(View.VISIBLE);
            tv_ghs_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 去申请厂家
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                }
            });
        }

        //生活商家销售总额
        if (is_merchant == 1) {
            tv_shsj_amount.setText(TextUtils.isEmpty(acmIncomBean.getPartner()) ? "" : "￥" + acmIncomBean.getPartner());
            tv_shsj_amount.setVisibility(View.VISIBLE);
            tv_shsj_apply.setVisibility(View.GONE);
        } else {
            tv_shsj_amount.setVisibility(View.GONE);
            tv_shsj_apply.setVisibility(View.VISIBLE);
            tv_shsj_apply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 去申请厂家
                    Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    @OnClick({R.id.im_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
        }
    }
}
