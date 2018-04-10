package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MealsTypesBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/8/008.
 * //团购筛选中的gridview的适配器
 */

public class GroupBuyingFiltrateAdapter extends BaseAdapter {

    private FragmentActivity activity;
    private List<MealsTypesBean> mealsTypes;

    public GroupBuyingFiltrateAdapter(FragmentActivity activity,List<MealsTypesBean> mealsTypes) {
        this.activity = activity;
        this.mealsTypes = mealsTypes;
    }

    @Override
    public int getCount() {
        return mealsTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return mealsTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(activity, R.layout.item_selmultiple, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvName.setText(mealsTypes.get(position).getName());

        if (mealsTypes.get(position).isSelected()) {
            viewHolder.tvName.setTextColor(Color.parseColor("#FF1E00"));
            viewHolder.tvName.setBackgroundResource(R.drawable.shap333);
        }else{
            viewHolder.tvName.setBackgroundResource(R.drawable.shap33);
            viewHolder.tvName.setTextColor(Color.parseColor("#444444"));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
