package com.android.p2pflowernet.project.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ApplyForWaitBean;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by caishen on 2017/11/14.
 * by--
 */

public class AppForWaitForAdapter extends BaseAdapter {

    private final FragmentActivity mContext;
    private final List<ApplyForWaitBean.ListsBean> data;
    private OnStaueClickListener onStaueClickListener;

    public AppForWaitForAdapter(FragmentActivity activity, List<ApplyForWaitBean.ListsBean> lists) {

        this.mContext = activity;
        this.data = lists;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public void setOnStaueClickListener(OnStaueClickListener onStaueClickListener) {
        this.onStaueClickListener = onStaueClickListener;
    }

    public interface OnStaueClickListener {

        void onStatueClickListener(View view, int position);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_appfor_wait, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        //设置数据
        String identity = data.get(position).getIdentity();
        viewHolder.tvTitle.setText(identity);
        String state = data.get(position).getState();


        //tate( 0 :没有该身份,1:是该身份,2:该身份信息被驳回,3:排队中, 4:审核中,5:该地区有代理)
        if (state.equals("1")) {

            //合伙人
            if (identity.equals("合伙人")) {

                viewHolder.tvApplyFro.setTag("1");
                viewHolder.tvApplyFro.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
                viewHolder.tvApplyFro.setTextSize(13);
                viewHolder.tvApplyFro.setTextColor(Color.parseColor("#FF2E00"));
                viewHolder.tvApplyFro.setText("追加股份");

                //代理人
            } else if (identity.equals("代理人")) {

                viewHolder.tvApplyFro.setTag("1");
                viewHolder.tvApplyFro.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
                viewHolder.tvApplyFro.setTextSize(13);
                viewHolder.tvApplyFro.setTextColor(Color.parseColor("#FF2E00"));
                viewHolder.tvApplyFro.setText("再次购买");

                //商家
            } else if (identity.equals("生活商家")) {

                viewHolder.tvApplyFro.setText("");
                viewHolder.tvApplyFro.setTextColor(Color.parseColor("#4b4b4b4"));

            } else if (identity.equals("厂家")) {

                viewHolder.tvApplyFro.setText("");
                viewHolder.tvApplyFro.setTextColor(Color.parseColor("#4b4b4b4"));

            } else {

                //云工
                viewHolder.tvApplyFro.setText("");
                viewHolder.tvApplyFro.setTextColor(Color.parseColor("#4b4b4b4"));
            }

        } else if (state.equals("3")) {

            viewHolder.tvApplyFro.setTag("2");
            viewHolder.tvApplyFro.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvApplyFro.setTextSize(13);
            viewHolder.tvApplyFro.setTextColor(Color.parseColor("#FF2E00"));
            viewHolder.tvApplyFro.setText("追加金额");

        } else if (state.equals("5")) {

            viewHolder.tvApplyFro.setTag("3");
            viewHolder.tvApplyFro.setBackgroundResource(R.drawable.shap_apply_for_refund_checked);
            viewHolder.tvApplyFro.setTextSize(13);
            viewHolder.tvApplyFro.setTextColor(Color.parseColor("#FF2E00"));
            viewHolder.tvApplyFro.setText("去申请");

        } else {

            viewHolder.tvApplyFro.setTag("0");
            viewHolder.tvApplyFro.setText("去申请");
        }

        //设置数据
        if (identity.equals("合伙人")) {//合伙人

            viewHolder.ivApplyFor.setImageResource(R.drawable.icon_partner);
            viewHolder.tvDesc.setText("享受每天平台的收益分润... ");

        } else if (identity.equals("代理人")) {//代理人

            viewHolder.ivApplyFor.setImageResource(R.drawable.icon_agent);
            viewHolder.tvDesc.setText("享受所代理区域每月分润... ");

        } else if (identity.equals("生活商家")) {//生活商家

            viewHolder.ivApplyFor.setImageResource(R.drawable.icon_shangjia);
            viewHolder.tvDesc.setText("免店铺入住费... ");

        } else if (identity.equals("厂家")) {

            viewHolder.ivApplyFor.setImageResource(R.mipmap.mine_supplier_pressed_icon);
            viewHolder.tvDesc.setText("... ");

        } else {//云工

            viewHolder.ivApplyFor.setImageResource(R.drawable.icon_cloud);
            viewHolder.tvDesc.setText("享受云工底薪+平台奖励... ");
        }

        //设置状态值得点击事件
        viewHolder.tvApplyFro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onStaueClickListener.onStatueClickListener(v, position);
            }
        });

        //设置了解更多的点击事件
        viewHolder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, PublicWebActivity.class);
                intent.putExtra("publicurl", data.get(position).getUrl());
                intent.putExtra("tag", "1");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                mContext.startActivity(intent);

            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_apply_for)
        ImageView ivApplyFor;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_more)
        TextView tvMore;
        @BindView(R.id.tv_apply_fro)
        TextView tvApplyFro;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
