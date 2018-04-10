package com.android.p2pflowernet.project.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.StakeBean;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.customview.DragIndicatorView;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ImproveGuaranteeActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/23.
 * by--入股明细的适配器
 */

public class StakeAdapter extends BaseAdapter {

    private final FragmentActivity mContext;
    private final List<StakeBean.ListsBean> data;

    public StakeAdapter(FragmentActivity activity, List<StakeBean.ListsBean> lists) {

        this.mContext = activity;
        this.data = lists;
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_appfor_stake_table, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvNumber.setText(data.get(position).getPartner_qual_num());//购买数量
        viewHolder.tvTime.setText(data.get(position).getDate());
        String policy_id = data.get(position).getPolicy_id();

        if (policy_id.equals("0")) {

            viewHolder.tvElse.setText("填写保单");
            viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvElse.setTextSize(13);
            viewHolder.tvElse.setPadding(0, 16, 0, 16);
            viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));

        } else {

            viewHolder.tvElse.setText("查看保单");
            viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvElse.setTextSize(13);
            viewHolder.tvElse.setPadding(0, 16, 0, 16);
            viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));
        }


        //设置填写保单的点击事件
        viewHolder.tvElse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (policy_id.equals("0")) {

                    Intent intent = new Intent(mContext, ImproveGuaranteeActivity.class);
                    intent.putExtra("record_id", data.get(position).getRecord_id());
                    mContext.startActivity(intent);

                } else {

                    //查看保单信息
                    Intent intent = new Intent(mContext, PublicWebActivity.class);
                    intent.putExtra("publicurl", ApiUrlConstant.STREAKDETAIL +
                            "token=" + Constants.TOKEN + "&policy_id=" + data.get(position).getPolicy_id());
                    intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                    mContext.startActivity(intent);

                }
            }
        });


        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_notic)
        DragIndicatorView tvNotic;
        @BindView(R.id.fl_notic)
        FrameLayout flNotic;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_else)
        TextView tvElse;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
