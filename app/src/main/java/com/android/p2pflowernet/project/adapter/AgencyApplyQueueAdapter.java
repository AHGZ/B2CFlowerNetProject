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
import com.android.p2pflowernet.project.entity.AgentQuereBean;
import com.android.p2pflowernet.project.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: zhangpeisen
 * created on: 2017/11/24 上午11:24
 * description:
 */
public class AgencyApplyQueueAdapter extends HFWBaseAdapter<AgentQuereBean.AqBean> {

    @Override
    public BaseHolder<AgentQuereBean.AqBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_applyqueueitem_layout, parent, false);
        return new ApplyQueueHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<AgentQuereBean.AqBean> holder, int position) {
        AgentQuereBean.AqBean aqBean = list.get(position);
        ((AgencyApplyQueueAdapter.ApplyQueueHolder) holder).bindDateView(aqBean, position);
    }


    public class ApplyQueueHolder extends BaseHolder<AgentQuereBean.AqBean> {
        @BindView(R.id.applyQueue)
        // 排序号
                TextView applyQueue;
        @BindView(R.id.nickName_applyQueue)
        // 昵称
                TextView nickNameApplyQueue;
        @BindView(R.id.Mobile_applyQueue)
        // 手机号
                TextView MobileApplyQueue;
        @BindView(R.id.amount_applyQueue)
        // 金额
                TextView amountApplyQueue;
        @BindView(R.id.applyDate_applyQueue)
        // 申请日期
                TextView applyDateApplyQueue;
        @BindView(R.id.iv_logo)
        TextView ivLogo;

        public ApplyQueueHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(AgentQuereBean.AqBean applyQueueBean, int pos) {
            if (applyQueueBean == null) {
                return;
            }
            if (pos < 3) {

                switch (pos) {
                    case 0:
                        ivLogo.setBackgroundResource(R.drawable.icon_number1);
                        ivLogo.setText("");
                        break;
                    case 1:
                        ivLogo.setBackgroundResource(R.drawable.icon_number2);
                        ivLogo.setText("");
                        break;
                    case 2:
                        ivLogo.setBackgroundResource(R.drawable.icon_number3);
                        ivLogo.setText("");
                        break;
                }
                ivLogo.setText("");
            } else {
                ivLogo.setText(String.valueOf(pos + 1));
                ivLogo.setTextColor(Color.parseColor("#FF2E00"));
            }

            DateUtils datesUtils = new DateUtils();
            String timedate = DateUtils.timedate(applyQueueBean.getCreated());
            if (applyQueueBean.getNickname().toString().length() > 3) {
                nickNameApplyQueue.setText(TextUtils.isEmpty(applyQueueBean.getNickname()) ? "" : applyQueueBean.getNickname().toString().substring(0, 3) + "...");
            } else {
                nickNameApplyQueue.setText(TextUtils.isEmpty(applyQueueBean.getNickname()) ? "" : applyQueueBean.getNickname());
            }

            MobileApplyQueue.setText(TextUtils.isEmpty(applyQueueBean.getPhone()) ? "" : applyQueueBean.getPhone());
            amountApplyQueue.setText(TextUtils.isEmpty(applyQueueBean.getQual_fund_amount()) ? "" : applyQueueBean.getQual_fund_amount());
            applyDateApplyQueue.setText(TextUtils.isEmpty(timedate) ? "" : timedate);
        }
    }
}
