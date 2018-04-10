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
import com.android.p2pflowernet.project.entity.ContriRankBean;
import com.android.p2pflowernet.project.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/12/19.
 * by--贡献排行榜适配器
 */

public class MyTeamContriQueueAdapter extends HFWBaseAdapter<ContriRankBean.ListBean> {
    @Override
    public BaseHolder<ContriRankBean.ListBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mine_teamcontri_layout, parent, false);
        return new MyTeamContriViewHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<ContriRankBean.ListBean> holder, int position) {
        ContriRankBean.ListBean contriRankBean = list.get(position);
        ((MyTeamContriQueueAdapter.MyTeamContriViewHolder) holder).bindDateView(contriRankBean, position);
    }


    static class MyTeamContriViewHolder extends BaseHolder<ContriRankBean.ListBean> {
        @BindView(R.id.ranking)
        TextView ranking;
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

        MyTeamContriViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindDateView(ContriRankBean.ListBean contriRankBean, int position) {
            if (contriRankBean == null) {
                return;
            }
            if (position < 3) {
                switch (position) {
                    case 0:
                        ranking.setBackgroundResource(R.drawable.icon_number1);
                        ranking.setText("");
                        break;
                    case 1:
                        ranking.setBackgroundResource(R.drawable.icon_number2);
                        ranking.setText("");
                        break;
                    case 2:
                        ranking.setBackgroundResource(R.drawable.icon_number3);
                        ranking.setText("");
                        break;
                }

            } else {
                ranking.setText(String.valueOf(position + 1));
                ranking.setBackgroundColor(Color.WHITE);
            }

            //时间戳转字符串
            DateUtils dateUtils = new DateUtils();
            String dateToString = DateUtils.timedate(contriRankBean.getCreated());
            nickNameApplyQueue.setText(TextUtils.isEmpty(contriRankBean.getNickname()) ? "" : contriRankBean.getNickname());
            MobileApplyQueue.setText(TextUtils.isEmpty(contriRankBean.getPhone()) ? "" : contriRankBean.getPhone());
            amountApplyQueue.setText(TextUtils.isEmpty(contriRankBean.getContribution()) ? "" : "¥" + contriRankBean.getContribution());
            applyDateApplyQueue.setText(TextUtils.isEmpty(contriRankBean.getCreated()) ? "" : dateToString);
        }
    }
}
