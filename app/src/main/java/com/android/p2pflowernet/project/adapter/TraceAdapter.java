package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.LogisticsDetailBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/20.
 * by--追踪物流信息
 */

public class TraceAdapter extends BaseAdapter {

    private ArrayList<LogisticsDetailBean.ListsBean> listsBeanArrayList = null;
    private LayoutInflater inflater;
    private Context context;


    public TraceAdapter(ArrayList<LogisticsDetailBean.ListsBean> tradeLists, Context context) {
        this.listsBeanArrayList = tradeLists;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listsBeanArrayList == null ? 0 : listsBeanArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listsBeanArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.trace_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            listsBeanArrayList.get(position).setHead(true);
        } else {
            listsBeanArrayList.get(position).setHead(false);
        }
        String content = listsBeanArrayList.get(position).getContext();
        viewHolder.tvTraceInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                CharSequence text = ((TextView) v).getText();
                Spannable stext = Spannable.Factory.getInstance().newSpannable(
                        text);
                TextView widget = (TextView) v;
                int action = event.getAction();
                //根据点击判断是否在spannable对象上；
                if (action == MotionEvent.ACTION_UP
                        || action == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();

                    x -= widget.getTotalPaddingLeft();
                    y -= widget.getTotalPaddingTop();

                    x += widget.getScrollX();
                    y += widget.getScrollY();

                    Layout layout = widget.getLayout();
                    int line = layout.getLineForVertical(y);
                    int off = layout.getOffsetForHorizontal(line, x);
                    ClickableSpan[] link = stext.getSpans(off, off,
                            ClickableSpan.class);
                    if (link.length != 0) {
                        if (action == MotionEvent.ACTION_UP) {
                            link[0].onClick(widget);
                        }
                        ret = true;
                    }
                }
                return ret;
            }
        });
        if (listsBeanArrayList.get(position).isHead()) {
            viewHolder.vUpLine.setVisibility(View.GONE);
            viewHolder.tvTraceInfo.setText(content);

            viewHolder.tvTraceInfo.setTextColor(Color.parseColor("#FF2E00"));
            viewHolder.tvTraceTime.setText(listsBeanArrayList.get(position).getTime());
            viewHolder.tvTraceTime.setTextColor(Color.parseColor("#4B4B4B"));
            viewHolder.vDownLine.setVisibility(View.VISIBLE);
            viewHolder.ivState.setImageResource(R.drawable.icon_now);

        } else if (listsBeanArrayList.size() == (position + 1)) {

            viewHolder.tvTraceInfo.setText(content);
            viewHolder.tvTraceTime.setText(listsBeanArrayList.get(position).getTime());
            viewHolder.vDownLine.setVisibility(View.GONE);
            viewHolder.ivState.setImageResource(R.drawable.icon_passed);
            viewHolder.tvTraceTime.setTextColor(Color.parseColor("#9F9F9F"));
            viewHolder.tvTraceInfo.setTextColor(Color.parseColor("#9F9F9F"));

        } else {
            viewHolder.ivState.setImageResource(R.drawable.icon_passed);
            viewHolder.tvTraceInfo.setText(content);
            viewHolder.tvTraceTime.setText(listsBeanArrayList.get(position).getTime());
            viewHolder.vDownLine.setVisibility(View.VISIBLE);
            viewHolder.tvTraceTime.setTextColor(Color.parseColor("#9F9F9F"));
            viewHolder.tvTraceInfo.setTextColor(Color.parseColor("#9F9F9F"));
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.v_up_line)
        View vUpLine;
        @BindView(R.id.iv_state)
        ImageView ivState;
        @BindView(R.id.tv_trace_info)
        TextView tvTraceInfo;
        @BindView(R.id.tv_trace_time)
        TextView tvTraceTime;
        @BindView(R.id.v_down_line)
        View vDownLine;
        @BindView(R.id.rl_trace_item)
        RelativeLayout rlTraceItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
