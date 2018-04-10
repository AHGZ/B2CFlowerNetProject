package com.android.p2pflowernet.project.adapter;

import android.content.Context;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.MeiTuanBean;
import com.android.p2pflowernet.project.utils.ViewHolder;

import java.util.List;


/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class MeituanAdapter extends CommonAdapter<MeiTuanBean> {
    public MeituanAdapter(Context context, int layoutId, List<MeiTuanBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final MeiTuanBean cityBean) {
        holder.setText(R.id.tvCity, cityBean.getCity());
    }
}