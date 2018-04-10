package com.android.p2pflowernet.project.view.fragments.goods.detail.huafan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GoodsInfoBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.ApplyForActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.android.p2pflowernet.project.R.id.agency_amount;
import static com.android.p2pflowernet.project.R.id.applyagency_tv;
import static com.android.p2pflowernet.project.R.id.applypartner_tv;
import static com.android.p2pflowernet.project.R.id.hf_allamount;
import static com.android.p2pflowernet.project.R.id.ordinarymember_amount;
import static com.android.p2pflowernet.project.R.id.ordinarymember_manfanamount;
import static com.android.p2pflowernet.project.R.id.partner_amount;
import static com.android.p2pflowernet.project.R.id.referee_amount;

/**
 * Created by caishen on 2018/1/18.
 * by--商品详情的返润
 */

public class GoodsHuaFanFragment extends KFragment<IGoodsHuaFanView, IGoodsHuaFanPrenter> implements IGoodsHuaFanView {
    @BindView(hf_allamount)
    TextView hfAllamount;
    @BindView(ordinarymember_manfanamount)
    TextView ordinarymemberManfanamount;
    @BindView(applypartner_tv)
    TextView applypartnerTv;
    @BindView(partner_amount)
    TextView partnerAmount;
    @BindView(ordinarymember_amount)
    TextView ordinarymemberAmount;
    @BindView(applyagency_tv)
    TextView applyagencyTv;
    @BindView(agency_amount)
    TextView agencyAmount;
    @BindView(referee_amount)
    TextView refereeAmount;
    private String goods_id = "";

    @Override
    public IGoodsHuaFanPrenter createPresenter() {
        return new IGoodsHuaFanPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goods_id = getArguments().getString("goods_Id");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_huafan;
    }

    @Override
    public void initData() {

        mPresenter.goodsXq();

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

    }

    public static KFragment newInstance(String goods_Id) {

        GoodsHuaFanFragment goodsHuaFanFragment = new GoodsHuaFanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("goods_Id", goods_Id);
        goodsHuaFanFragment.setArguments(bundle);
        return goodsHuaFanFragment;
    }

    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public String goodsId() {
        return goods_id;
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void onSuccess(String message) {


    }

    @Override
    public void successGsInfo(GoodsInfoBean data) {

        if (data != null) {

            GoodsInfoBean.GoodsBean dataGoods = data.getGoods();

            // 总返润金额
            hfAllamount.setText(dataGoods.getHuafan());
            // 合伙人金额
            partnerAmount.setText(dataGoods.getPartner());
            // 代理人返润
            agencyAmount.setText(dataGoods.getAgent());
            // 推荐人返润
            refereeAmount.setText(dataGoods.getTuijian());
            // 普通会员返润
            ordinarymemberAmount.setText(dataGoods.getPutong());
            // 普通会员满8000返润
            ordinarymemberManfanamount.setText(dataGoods.getGaoji());
            // 是否合伙人 0-不是 1-是'
            if (dataGoods.getIs_partner().equals("1")) {
                applypartnerTv.setVisibility(View.GONE);
            } else {
                applypartnerTv.setVisibility(View.VISIBLE);
            }
            // 是否代理人 0-不是 1-是'
            if (dataGoods.getIs_agent().equals("1")) {
                applyagencyTv.setVisibility(View.GONE);
            } else {
                applyagencyTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @OnClick({R.id.applypartner_tv, R.id.applyagency_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.applypartner_tv://申请合伙人

                Intent intent = new Intent(getActivity(), ApplyForActivity.class);
                startActivity(intent);
                break;
            case R.id.applyagency_tv://申请代理人
                intent = new Intent(getActivity(), ApplyForActivity.class);
                startActivity(intent);
                break;
        }
    }
}
