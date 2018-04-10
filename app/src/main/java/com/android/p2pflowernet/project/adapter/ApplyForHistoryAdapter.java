package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ApplyForHistoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/14.
 * by--申请历史适配器
 */

public class ApplyForHistoryAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<ApplyForHistoryBean.ListsBean> data;
    private OnStaueClickListener onStaueClickListener;

    public ApplyForHistoryAdapter(FragmentActivity activity, List<ApplyForHistoryBean.ListsBean> lists) {
        this.mContext = activity;
        this.data = lists;
    }


    public void setOnStaueClickListener(OnStaueClickListener onStaueClickListener) {
        this.onStaueClickListener = onStaueClickListener;
    }

    public interface OnStaueClickListener {

        void onStatueClickListener(View view, int position);
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

            convertView = View.inflate(mContext, R.layout.item_appfor_history_table, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        //设置数据
        viewHolder.tvName.setText(data.get(position).getIdentity());
        viewHolder.tvNumber.setText(data.get(position).getBuy_num());
        viewHolder.tvTime.setText(data.get(position).getTime());
        String state = data.get(position).getState();

        // state(1:是该身份,2:该身份信息被驳回,3:排队中, 4:审核中,6:退出代理审核中,7:已退出代理)。

        if (state.equals("0")) {

            viewHolder.tvElse.setText("去申请");
            viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvElse.setTextSize(13);
            viewHolder.tvElse.setPadding(0, 16, 0, 16);
            viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));

        } else if (state.equals("1")) {

            if (data.get(position).getIdentity().equals("代理人")) {

                viewHolder.tvElse.setText("退出代理");
                viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
                viewHolder.tvElse.setTextSize(13);
                viewHolder.tvElse.setPadding(0, 16, 0, 16);
                viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));

            } else {

                viewHolder.tvElse.setText("已通过");
                viewHolder.tvElse.setTextColor(Color.parseColor("#4b4b4b"));
            }

        } else if (state.equals("2")) {

            viewHolder.tvElse.setText("修改信息");
            viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvElse.setTextSize(13);
            viewHolder.tvElse.setPadding(0, 16, 0, 16);
            viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));

        } else if (state.equals("3")) {

            viewHolder.tvElse.setText("排行榜");
            viewHolder.tvElse.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvElse.setTextSize(13);
            viewHolder.tvElse.setPadding(0, 16, 0, 16);
            viewHolder.tvElse.setTextColor(Color.parseColor("#FF2E00"));

        } else if (state.equals("4")) {

            viewHolder.tvElse.setText("审核中");
            viewHolder.tvElse.setTextSize(15);
            viewHolder.tvElse.setTextColor(Color.parseColor("#4b4b4b"));

        } else if (state.equals("6")) {

            viewHolder.tvElse.setText("审核中");
            viewHolder.tvElse.setTextSize(15);
            viewHolder.tvElse.setTextColor(Color.parseColor("#4b4b4b"));

        } else if (state.equals("7")) {

            viewHolder.tvElse.setText("已退出");
            viewHolder.tvElse.setTextSize(15);
            viewHolder.tvElse.setTextColor(Color.parseColor("#4b4b4b"));
        }

        //设置状态值得点击事件
        viewHolder.tvElse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onStaueClickListener.onStatueClickListener(v, position);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
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
