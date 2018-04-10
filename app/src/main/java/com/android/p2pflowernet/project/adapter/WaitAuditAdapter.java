package com.android.p2pflowernet.project.adapter;

import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AgentOfficeBean;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.TimeTools;

import java.util.List;

/**
 * Created by caishen on 2017/11/29.
 * by--待审核的列表适配器
 */

public class WaitAuditAdapter extends BaseAdapter {

    private final FragmentActivity mContext;
    private final List<AgentOfficeBean.NotauditedBean> data;
    //用于退出 Activity,避免 Countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownCounters;
    private int lastPosition = -1;//定义一个标记为最后选择的位置
    private CheckBoxSelectorListener checkBoxSelectorListener;

    public WaitAuditAdapter(FragmentActivity activity, List<AgentOfficeBean.NotauditedBean> notaudited) {
        this.mContext = activity;
        this.countDownCounters = new SparseArray<>();
        this.data = notaudited;
    }

    public void setCheckBoxSelectorListener(CheckBoxSelectorListener checkBoxSelectorListener) {
        this.checkBoxSelectorListener = checkBoxSelectorListener;
    }

    public interface CheckBoxSelectorListener {

        void checkBoxSelectorListener(View view, int position);
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    public void setSeclection(int position) {
        lastPosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_wait_audit, null);
            viewHolder = new ViewHolder();
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvDesc = (TextView) convertView.findViewById(R.id.tv_desc);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        CountDownTimer countDownTimer = countDownCounters.get(viewHolder.tvDate.hashCode());
        if (countDownTimer != null) {
            //将复用的倒计时清除
            countDownTimer.cancel();
        }

        viewHolder.tvName.setText(data.get(position).getNickname());
        viewHolder.tvDesc.setText(data.get(position).getPartner_qual_amount());

        long timer = DateUtils.dataOne(data.get(position).getEndtime());
        timer = timer - System.currentTimeMillis();
        //expirationTime 与系统时间做比较，timer 小于零，则此时倒计时已经结束。
        if (timer > 0) {

            countDownTimer = new CountDownTimer(timer, 1000) {

                public void onTick(long millisUntilFinished) {

                    viewHolder.tvDate.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
                }

                public void onFinish() {
                    notifyDataSetChanged();
                    viewHolder.tvDate.setText("00:00");
                }
            }.start();

            //将此 countDownTimer 放入list.
            countDownCounters.put(viewHolder.tvDate.hashCode(), countDownTimer);

        } else {
            viewHolder.tvDate.setText("00:00");
        }

        //设置单选
        if (lastPosition == position) {
            viewHolder.checkbox.setChecked(true);
        } else {
            viewHolder.checkbox.setChecked(false);
        }

        //设置单选的点击事件
        viewHolder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkBoxSelectorListener.checkBoxSelectorListener(v, position);
            }
        });

        return convertView;
    }

    public class ViewHolder {

        CheckBox checkbox;
        TextView tvName;
        TextView tvDesc;
        TextView tvDate;
    }
}
