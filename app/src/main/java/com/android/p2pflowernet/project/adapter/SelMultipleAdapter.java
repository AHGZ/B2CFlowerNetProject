package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.DistriButionBean;
import com.android.p2pflowernet.project.event.ResetEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/1/4.
 * by--
 */

public class SelMultipleAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<DistriButionBean.DistriButionsBean.DistriBean> data;
    private OnTextClickLitener onTextClickLitener;

    public SelMultipleAdapter(FragmentActivity mContext, List<DistriButionBean.DistriButionsBean.DistriBean> distri) {
        this.mContext = mContext;
        this.data = distri;
    }

    public void setOnTextClickLitener(OnTextClickLitener onTextClickLitener) {
        this.onTextClickLitener = onTextClickLitener;
    }

    public interface OnTextClickLitener {

        void textClickLitener(View view, int position);

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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

        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_selmultiple, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }

        //设置数据
        viewHolder.tvName.setText(data.get(position).getName() == null ? "" : data.get(position).getName());

        boolean isChoose = data.get(position).ischoose();
        if (isChoose) {
            viewHolder.tvName.setTextColor(Color.parseColor("#FF1E00"));
            viewHolder.tvName.setBackgroundResource(R.drawable.shap333);
        } else {
            viewHolder.tvName.setBackgroundResource(R.drawable.shap33);
            viewHolder.tvName.setTextColor(Color.parseColor("#444444"));
        }

        //设置是否选中
        viewHolder.tvName.setTag(isChoose);
        viewHolder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).ischoose()) {
                    data.get(position).setIschoose(false);
                }else{
                    data.get(position).setIschoose(true);
                }


                boolean flag = (boolean) v.getTag();
                if (flag) {//已经选中

                    viewHolder.tvName.setTag(false);

//                    data.get(position).setIschoose(true);
//                    if (data.get(position).ischoose()==true){
//                        viewHolder.tvName.setTextColor(Color.parseColor("#FF1E00"));
//                        viewHolder.tvName.setBackgroundResource(R.drawable.shap333);
//                    }

                } else {

                    viewHolder.tvName.setTag(true);
//                    data.get(position).setIschoose(false);
//                    if (data.get(position).ischoose()==false){
//                        viewHolder.tvName.setBackgroundResource(R.drawable.shap33);
//                        viewHolder.tvName.setTextColor(Color.parseColor("#444444"));
//                    }
                }

                notifyDataSetChanged();
                if (onTextClickLitener != null) {
                    onTextClickLitener.textClickLitener(v, position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ResetEvent event) {

        notifyDataSetChanged();
    }
}
