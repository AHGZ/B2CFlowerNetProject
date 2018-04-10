package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.CbSelectLinstener;
import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.view.customview.OnClickListenerWrapper;

import java.util.LinkedHashMap;
import java.util.List;


public class ScreeningListAdapter extends SimpleBaseAdapter<AllSortBean.ListsBean> {
    private final List<BrandSortBean.ListsBeanX> sortData;
    CbSelectLinstener cbSelectLinstener;

    public ScreeningListAdapter(Context context, List<AllSortBean.ListsBean> data, List<BrandSortBean.ListsBeanX> listsBeanXes) {
        super(context, data);
        this.sortData = listsBeanXes;
    }


    public void setCbSelectLinstener(CbSelectLinstener cbSelectLinstener) {
        this.cbSelectLinstener = cbSelectLinstener;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_right_sideslip_child_layout;
    }

    @Override
    public View getItemView(final int position, View convertView, ViewHolder holder) {
        TextView mubTv = holder.getView(R.id.brand_list_Tv);
        LinearLayout select_brand_lay = holder.getView(R.id.select_brand_lay);
        CheckBox brandCb = holder.getView(R.id.select_brand_cb);
        AllSortBean.ListsBean listsBean = getData().get(position);
        mubTv.setText((listsBean.getName()));
        select_brand_lay.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {
                boolean chick = !listsBean.isChick();
                listsBean.setChick(chick);
                notifyDataSetChanged();
            }
        });

        //判断从二级列表进入的数据是否有相同并且选中的数据
        for (int i = 0; i < sortData.size(); i++) {
            List<BrandSortBean.ListsBeanX.ListsBean> lists = sortData.get(i).getLists();
            for (int i1 = 0; i1 < lists.size(); i1++) {
                if (lists.get(i1).getBrand_id().equals(listsBean.getBrand_id()) && lists.get(i1).isChick()) {
                    listsBean.setChick(lists.get(i1).isChick());
                }
            }
        }

        brandCb.setVisibility(listsBean.isChick() ? View.VISIBLE : View.GONE);
        brandCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LinkedHashMap<String, Integer> selectDataMaps = new LinkedHashMap<>();
                if (isChecked) {
                    selectDataMaps.put(buttonView.getText().toString().trim(), buttonView.getId());
                    if (cbSelectLinstener != null) {
                        cbSelectLinstener.selectbox(selectDataMaps);
                    }
                }
            }
        });
        return convertView;
    }
}
