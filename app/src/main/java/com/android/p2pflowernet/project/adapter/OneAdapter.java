package com.android.p2pflowernet.project.adapter;

import com.android.p2pflowernet.project.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * Created by geyifeng on 2017/6/3.
 * 测试适配器
 */

public class OneAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public OneAdapter() {
        super(R.layout.item_one);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text, item);
    }
}
