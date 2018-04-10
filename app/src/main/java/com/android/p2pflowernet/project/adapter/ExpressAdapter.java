package com.android.p2pflowernet.project.adapter;

import android.content.Context;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.ExpresListBean;
import com.android.p2pflowernet.project.utils.ViewHolder;

import java.util.List;

/**
 * Created by caishen on 2017/11/18.
 * by--
 */

public class ExpressAdapter extends CommonAdapter<ExpresListBean.ListsBean> {

    public ExpressAdapter(Context context, int layoutId, List<ExpresListBean.ListsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, ExpresListBean.ListsBean expressBean) {
        holder.setText(R.id.tvCity, expressBean.getExpress_name());
    }

}
