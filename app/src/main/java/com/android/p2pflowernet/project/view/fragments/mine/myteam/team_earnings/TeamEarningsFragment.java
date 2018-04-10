package com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.MyTeamProfitBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.view.customview.CircleImageView;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.team_earnings.contribution.ContributionRankActivity;
import com.caimuhao.rxpicker.utils.DensityUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by caishen on 2017/11/28.
 * by--团队收益
 */

public class TeamEarningsFragment extends KFragment<ITeamEarningsView, ITeamEarningsPrenter>
        implements ITeamEarningsView {

    @BindView(R.id.iv_tj)
    CircleImageView ivTj;
    @BindView(R.id.tv_tj)
    TextView tvTj;
    @BindView(R.id.iv_b_tj)
    ImageView ivBTj;
    @BindView(R.id.tv_b_t_j)
    TextView tvBTJ;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.iv_hj)
    ImageView ivHj;
    @BindView(R.id.tv_num_all)
    TextView tvNumAll;
    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.iv_hy)
    ImageView ivHy;
    @BindView(R.id.tv_hy_num_all)
    TextView tvHyNumAll;
    @BindView(R.id.tv_hy_moey_all)
    TextView tvHyMoeyAll;
    @BindView(R.id.iv_partner)
    ImageView ivPartner;
    @BindView(R.id.tv_partener_num_all)
    TextView tvPartenerNumAll;
    @BindView(R.id.tv_partener_money_all)
    TextView tvPartenerMoneyAll;
    @BindView(R.id.iv_agence)
    ImageView ivAgence;
    @BindView(R.id.tv_angent_money_all)
    TextView tvAgentMoneyAll;
    @BindView(R.id.tv_agent_num_all)
    TextView tv_agent_num_all;
    @BindView(R.id.iv_merchant)
    ImageView ivMerchant;
    @BindView(R.id.tv_sj_num_all)
    TextView tvSjNumAll;
    @BindView(R.id.tv_sj_money_all)
    TextView tvSjMoneyAll;
    @BindView(R.id.iv_jj_tj)
    ImageView ivJjTj;
    @BindView(R.id.tv_jj_tj_num_all)
    TextView tvJjTjNumAll;
    @BindView(R.id.tv_jj_tj_money_all)
    TextView tvJjTjMoneyAll;
    @BindView(R.id.rl_all)
    RelativeLayout rlAll;
    @BindView(R.id.rl_hy)
    RelativeLayout rlHy;
    @BindView(R.id.rl_partener)
    RelativeLayout rlPartener;
    @BindView(R.id.rl_agentce)
    RelativeLayout rlAgentce;
    @BindView(R.id.rl_mer)
    RelativeLayout rlMer;
    @BindView(R.id.rl_jjtj)
    RelativeLayout rlJjtj;
    @BindView(R.id.redpackage_value1)
    TextView redpackageValue1;
    @BindView(R.id.redpackage_value2)
    TextView redpackageValue2;
    @BindView(R.id.redpackage_value3)
    TextView redpackageValue3;
    @BindView(R.id.nohistory_ly)
    LinearLayout nohistoryLy;
    @BindView(R.id.ll_redenvelopes)
    LinearLayout llRedenvelopes;
    private ShapeLoadingDialog shapeLoadingDialog;
    private int imageSize;

    @Override
    public ITeamEarningsPrenter createPresenter() {

        return new ITeamEarningsPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_team_earnings;
    }

    @Override
    public void initData() {

        mPresenter.getTeamProfit();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();
        imageSize = DensityUtil.getDeviceWidth(ivUser.getContext()) / 3;

        initData();

    }

    public static Fragment newIntence() {

        TeamEarningsFragment teamEarningsFragment = new TeamEarningsFragment();
        Bundle bundle = new Bundle();
        teamEarningsFragment.setArguments(bundle);
        return teamEarningsFragment;
    }

    ////1为会员2为合伙人3为代理人4为生活商家5为间接推荐
    @OnClick({R.id.rl_all, R.id.rl_hy, R.id.rl_partener, R.id.rl_agentce, R.id.rl_mer, R.id.rl_jjtj})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_all://总计

                Intent intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "1");
                startActivity(intent);


                break;
            case R.id.rl_hy://会员
                intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "1");
                startActivity(intent);

                break;
            case R.id.rl_partener://合伙人

                intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "2");
                startActivity(intent);
                break;
            case R.id.rl_agentce://代理人
                intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "3");
                startActivity(intent);

                break;
            case R.id.rl_mer://商家
                intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "4");
                startActivity(intent);

                break;
            case R.id.rl_jjtj://间接推荐

                intent = new Intent(getActivity(), ContributionRankActivity.class);
                intent.putExtra("state", "5");
                startActivity(intent);
                break;
        }
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
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void successTeamProfit(MyTeamProfitBean data) {

        if (data != null) {

            //设置数据
            RxImageLoader rxImageLoader = new RxImageLoader();
            String user_img = ApiUrlConstant.API_IMG_URL + data.getUser_img();
            String reference_img = ApiUrlConstant.API_IMG_URL + data.getReference_img();
            rxImageLoader.display(ivUser, data.getUser_img() == null ? "" : user_img, imageSize, imageSize);
            rxImageLoader.display(ivTj, data.getReference_img() == null ? "" : reference_img, imageSize, imageSize);
            tvTj.setText(data.getReference_nickname() == null ? "" : data.getReference_nickname());
            tvUser.setText(data.getUser_nickname() == null ? "" : data.getUser_nickname());//昵称
            tvNumAll.setText(data.getUser_count() == null ? "" : data.getUser_count() + "人");//总数
            tvHyNumAll.setText(data.getUser_count() == null ? "" : data.getUser_count() + "人");//会员数
            tvHyMoeyAll.setText(data.getRecom_rebate_profit() + "");//会员收益
            tvPartenerNumAll.setText(data.getPartner_count() == null ? "" : data.getPartner_count() + "人");//合伙人数
            tvPartenerMoneyAll.setText(data.getRecom_partner_profit() + "");//合伙人收益
            tv_agent_num_all.setText(data.getAgent_count() == null ? "" : data.getAgent_count() + "人");//代理人数
            tvAgentMoneyAll.setText(data.getRecom_agent_profit() + "");//代理人收益
            tvSjNumAll.setText(data.getMerch_count() == null ? "" : data.getMerch_count() + "人");//商家人数
            tvSjMoneyAll.setText(data.getRecom_merch_profit() + "");//商家收益
            tvJjTjNumAll.setText(data.getIndirect_partner_count() == null ? "" : data.getIndirect_partner_count() + "人");//借鉴推荐人数
            tvJjTjMoneyAll.setText(data.getIndirect_partner_profit() + "");//间接推荐收益
            //计算总收益
            tvAllMoney.setText(data.getRecom_rebate_profit() + data.getRecom_partner_profit()
                    + data.getRecom_agent_profit() + data.getRecom_merch_profit()
                    + data.getIndirect_partner_profit() + "");

            //设置红包的适配器
            String redenvelopes = data.getRedenvelopes();
            if (redenvelopes.equals("0")) {//无红包
                nohistoryLy.setVisibility(View.VISIBLE);
                llRedenvelopes.setVisibility(View.GONE);
            } else {//有红包
                nohistoryLy.setVisibility(View.GONE);
                llRedenvelopes.setVisibility(View.VISIBLE);
                redpackageValue1.setText("¥" + data.getRedenvelopes());
            }
        }
    }
}
