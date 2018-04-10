package com.android.p2pflowernet.project.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.BillAllBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: zhangpeisen
 * created on: 2017/11/20 下午4:49
 * description: 账单列表适配器
 */
public class BillRecyclerAdapter extends HFWBaseAdapter<BillAllBean.ListBean> {
    @Override
    public BaseHolder<BillAllBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_billitem_layout, parent, false);
        return new BillHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<BillAllBean.ListBean> holder, int position) {
        BillAllBean.ListBean billAllBean = list.get(position);
        ((BillRecyclerAdapter.BillHolder) holder).bindDateView(billAllBean);
    }

    public class BillHolder extends BaseHolder<BillAllBean.ListBean> {
        @BindView(R.id.billtype_tv)
        // 账单类型
                TextView billtype_tv;
        @BindView(R.id.billdate_tv)
        // 账单时间
                TextView billdate_tv;
        @BindView(R.id.billamonut_tv)
        // 账单金额
                TextView billamonut_tv;

        public BillHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(BillAllBean.ListBean billBean) {
            billtype_tv.setText(TextUtils.isEmpty(billBean.getMoney()) ? "" : billBean.getType());
            billdate_tv.setText(TextUtils.isEmpty(billBean.getCreated()) ? "" : billBean.getCreated());
            billamonut_tv.setText(TextUtils.isEmpty(billBean.getMoney()) ? "" : billBean.getMoney());
        }
    }
}
