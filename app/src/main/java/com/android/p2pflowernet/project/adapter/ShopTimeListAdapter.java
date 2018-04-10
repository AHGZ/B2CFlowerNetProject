package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.utils.TimeTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/13/013.
 * 营业时间适配器
 */

public class ShopTimeListAdapter extends BaseAdapter {
    private Context context;
    List<MerchantBean.InfoBean.TimeSettingBean> times;

    public ShopTimeListAdapter(Context context, List<MerchantBean.InfoBean.TimeSettingBean> times) {
        this.context = context;
        this.times = times;
    }

    @Override
    public int getCount() {
        return times == null ? 0 : times.size();
    }

    @Override
    public Object getItem(int position) {
        return times.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.shop_time_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String startTime = TimeTools.getCountTimeByLongHour(Long.parseLong(String.valueOf(times.get(position).getStarttime())));
        String endTime = TimeTools.getCountTimeByLongHour(Long.parseLong(String.valueOf(times.get(position).getEndtime())));
        viewHolder.time.setText(startTime + "~" + endTime);
        return convertView;
    }

    static class ViewHolder {
        //营业时间
        @BindView(R.id.shop_time)
        TextView time;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
