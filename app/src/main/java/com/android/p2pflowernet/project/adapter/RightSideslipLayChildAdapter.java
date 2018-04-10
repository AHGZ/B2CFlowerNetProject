package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.CbSelectLinstener;
import com.android.p2pflowernet.project.entity.BrandSortBean;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 */

/**
 * @描述:
 * @创建人：zhangpeisen
 * @创建时间：2017/12/14 上午11:57
 * @修改人：zhangpeisen
 * @修改时间：2017/12/14 上午11:57
 * @修改备注：
 * @throws
 */
public class RightSideslipLayChildAdapter extends SimpleBaseAdapter<BrandSortBean.ListsBeanX.ListsBean> {


    CbSelectLinstener cbSelectLinstener;

    public RightSideslipLayChildAdapter(Context context, List<BrandSortBean.ListsBeanX.ListsBean> list) {
        super(context, list);
    }

    public void setCbSelectLinstener(CbSelectLinstener cbSelectLinstener) {
        this.cbSelectLinstener = cbSelectLinstener;
    }

    @Override
    public int getItemResource() {

        return R.layout.gv_right_sideslip_child_layout;
    }


    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        final CheckBox itemFrameRb = holder.getView(R.id.item_frameRb);
        BrandSortBean.ListsBeanX.ListsBean listsBean = getData().get(position);
        itemFrameRb.setText(listsBean.getName());
        itemFrameRb.setTag(position);
        itemFrameRb.setId(Integer.parseInt(listsBean.getCate_id()));
        itemFrameRb.setChecked(listsBean.isChick());
        LinkedHashMap<String, Integer> selectDataMaps = new LinkedHashMap<>();

        itemFrameRb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    listsBean.setChick(true);
                    selectDataMaps.put(buttonView.getText().toString().trim(), buttonView.getId());
                } else {
                    listsBean.setChick(false);
                    selectDataMaps.remove(buttonView.getText().toString().trim());
                }

                if (cbSelectLinstener != null) {
                    cbSelectLinstener.selectbox(selectDataMaps);
                }

                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}