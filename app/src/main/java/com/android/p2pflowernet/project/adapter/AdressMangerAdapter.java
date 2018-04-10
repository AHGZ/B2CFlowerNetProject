package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.AdressMangerBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/23.
 * by--管理地址的适配器
 */

public class AdressMangerAdapter extends BaseAdapter {
    private final FragmentActivity mContext;
    private final List<AdressMangerBean.UalBean> data;
    private OnDeleteAdressListener onDeleteAdressListener;
    private OnEditAdressListener onEditAdressListener;
    private OnCheckedListener onCheckedListener;
    private OnItemClickListeners onItemClickListeners;

    private int lastPosition = -1;//定义一个标记为最后选择的位置

    public AdressMangerAdapter(FragmentActivity activity, List<AdressMangerBean.UalBean> ual) {
        this.mContext = activity;
        this.data = ual;
    }


    /***
     * 设置点击事件
     * @param onItemClickListeners
     */
    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    public interface OnItemClickListeners {

        void onItemClickListeners(View view, int position);
    }


    /**
     * 设为默认地址
     *
     * @param onCheckedListener
     */
    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }

    public interface OnCheckedListener {

        void onCheckedListener(View view, int position);
    }

    /**
     * 设置编辑的点击事件
     *
     * @param onEditAdressListener
     */
    public void setOnEditAdressListener(OnEditAdressListener onEditAdressListener) {
        this.onEditAdressListener = onEditAdressListener;
    }

    public interface OnEditAdressListener {

        void onEditAdressListener(View view, int position);
    }

    public interface OnDeleteAdressListener {

        void onDeleteAdressListener(View view, int position);
    }

    /**
     * 设置删除的点击事件
     *
     * @param onDeleteAdressListener
     */
    public void setOnDeleteAdressListener(OnDeleteAdressListener onDeleteAdressListener) {
        this.onDeleteAdressListener = onDeleteAdressListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_adress_manger, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        viewHolder.tvName.setText(data.get(position).getName());
        viewHolder.tvDetailPlace.setText(data.get(position).getLocation() + data.get(position).getAddress());
        viewHolder.tvPhone.setText(data.get(position).getTelephone());
        String is_default = data.get(position).getIs_default();

        //0:否，1：是
        if (is_default.equals("0")) {

            viewHolder.cbDefault.setChecked(false);

        } else {

            viewHolder.cbDefault.setChecked(true);
        }


        //设置删除的点击事件
        viewHolder.tvDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onDeleteAdressListener != null) {

                    onDeleteAdressListener.onDeleteAdressListener(v, position);
                }
            }
        });

        //单选设置位默认地址
        viewHolder.cbDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    if (onCheckedListener != null) {

                        onCheckedListener.onCheckedListener(buttonView, position);
                    }
                }
            }
        });


        //设置编辑的点击事件
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onEditAdressListener != null) {

                    onEditAdressListener.onEditAdressListener(v, position);
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_detail_place)
        TextView tvDetailPlace;
        @BindView(R.id.cb_default)
        CheckBox cbDefault;
        @BindView(R.id.tv_default)
        TextView tvDefault;
        @BindView(R.id.tv_edit)
        TextView tvEdit;
        @BindView(R.id.tv_delet)
        TextView tvDelet;
        @BindView(R.id.ll_adress)
        LinearLayout llAdress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
