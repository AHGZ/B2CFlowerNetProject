package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.AuditHistoryBean;
import com.android.p2pflowernet.project.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/1.
 * by--审批历史的适配器
 */

public class AuditHitoryAdapter extends HFWBaseAdapter<AuditHistoryBean.ListBean> {
    @Override
    public BaseHolder<AuditHistoryBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_applyqueueitem_layout, parent, false);
        return new AuditHitoryViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<AuditHistoryBean.ListBean> holder, int position) {
        AuditHistoryBean.ListBean listBean = list.get(position);
        ((AuditHitoryViewHolder) holder).bindDateView(listBean, position);
    }

    static class AuditHitoryViewHolder extends BaseHolder<AuditHistoryBean.ListBean> {
        @BindView(R.id.applyQueue)
        TextView applyQueue;
        @BindView(R.id.nickName_applyQueue)
        TextView nickNameApplyQueue;
        @BindView(R.id.Mobile_applyQueue)
        TextView MobileApplyQueue;
        @BindView(R.id.amount_applyQueue)
        TextView amountApplyQueue;
        @BindView(R.id.applyDate_applyQueue)
        TextView applyDateApplyQueue;

        AuditHitoryViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        //设置数据
        public void bindDateView(AuditHistoryBean.ListBean listBean, int position) {
            if (listBean == null) {
                return;
            }

            //模拟违规与通过
            if (listBean.getAgent_appro_state().equals("1")) {

                applyDateApplyQueue.setText("已审批");
                //设置字体颜色
                nickNameApplyQueue.setTextColor(Color.parseColor("#4B4B4B"));
                MobileApplyQueue.setTextColor(Color.parseColor("#4B4B4B"));
                amountApplyQueue.setTextColor(Color.parseColor("#4B4B4B"));
                applyDateApplyQueue.setTextColor(Color.parseColor("#4B4B4B"));

            } else {
                applyDateApplyQueue.setText("违规");
                //设置字体颜色
                nickNameApplyQueue.setTextColor(Color.parseColor("#FF2E00"));
                MobileApplyQueue.setTextColor(Color.parseColor("#FF2E00"));
                amountApplyQueue.setTextColor(Color.parseColor("#FF2E00"));
                applyDateApplyQueue.setTextColor(Color.parseColor("#FF2E00"));
            }
            //设置数据
            nickNameApplyQueue.setText(TextUtils.isEmpty(listBean.getNickname()) ? "" : listBean.getNickname());
            MobileApplyQueue.setText(TextUtils.isEmpty(listBean.getPartner_qual_amount()) ? "" : listBean.getPartner_qual_amount());
            String created = listBean.getCreated();
            amountApplyQueue.setText(TextUtils.isEmpty(listBean.getCreated()) ? "" : new DateUtils().getDateToString(Long.parseLong(created)));
        }
    }
}
