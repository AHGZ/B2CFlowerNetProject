package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.callback.AllSortDatasLinstener;
import com.android.p2pflowernet.project.callback.CbSelectLinstener;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.AutoMeasureHeightGridView;
import com.android.p2pflowernet.project.view.customview.OnClickListenerWrapper;

import java.util.List;

/**
 */

public class RightSideslipLayAdapter extends SimpleBaseAdapter<BrandSortBean.ListsBeanX> {

    RightSideslipLayChildAdapter mChildAdapter;
    // 点击查看更多
    AllSortDatasLinstener allSortDatasLinstener;
    // 选中事件监听
    CbSelectLinstener cbSelectLinstener;

    public static TextView mSelectvalues;

    public RightSideslipLayAdapter(Context context, List<BrandSortBean.ListsBeanX> brandSortBeanLists) {
        super(context, brandSortBeanLists);
    }


    public void setCbSelectLinstener(CbSelectLinstener cbSelectLinstener) {
        this.cbSelectLinstener = cbSelectLinstener;
    }

    public void setAllSortDatasLinstener(AllSortDatasLinstener allSortDatasLinstener) {
        this.allSortDatasLinstener = allSortDatasLinstener;
    }

    @Override
    public int getItemResource() {

        return R.layout.item_right_sideslip_lay;
    }


    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        TextView itemFrameTitleTv = holder.getView(R.id.item_frameTv);
        TextView itemFrameSelectTv = holder.getView(R.id.item_selectTv);
        TextView clickbrand_tv = holder.getView(R.id.clickbrand_tv);
        LinearLayout layoutItem = holder.getView(R.id.item_select_lay);
        AutoMeasureHeightGridView itemFrameGv = holder.getView(R.id.item_selectGv);
        itemFrameGv.setVisibility(View.VISIBLE);
        BrandSortBean.ListsBeanX listsBeanX = getData().get(position);
        itemFrameTitleTv.setText(listsBeanX.getName());//名称
//       itemFrameSelectTv.setText(listsBeanX.getShowStr());//显示的数据
        setSelectText(itemFrameSelectTv);

        //显示的数据
        if (listsBeanX != null) {

            convertView.setVisibility(View.VISIBLE);

            if (listsBeanX.isoPen()) {

                itemFrameSelectTv.setTag(itemFrameGv);
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_up_prodcatelist, 0);
                fillLv2CateViews(listsBeanX.getLists(), itemFrameGv);
                layoutItem.setTag(itemFrameGv);
                clickbrand_tv.setVisibility(View.VISIBLE);

            } else {

                fillLv2CateViews(listsBeanX.getLists().subList(0, 0), itemFrameGv);
                itemFrameSelectTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down_prodcatelist, 0);
                layoutItem.setTag(itemFrameGv);
                clickbrand_tv.setVisibility(View.GONE);
                itemFrameSelectTv.setVisibility(View.VISIBLE);
            }

            layoutItem.setOnClickListener(onClickListener);

        } else {

            convertView.setVisibility(View.GONE);
        }

        itemFrameGv.setTag(position);

        // 查看全部
        UIUtils.setTouchDelegate(clickbrand_tv, 50);
        clickbrand_tv.setOnClickListener(new OnClickListenerWrapper() {
            @Override
            protected void onSingleClick(View v) {

                if (allSortDatasLinstener != null) {
                    allSortDatasLinstener.clickallsortdatas();
                }
            }
        });

        return convertView;
    }


    //二级列表的显示
    private void fillLv2CateViews(List<BrandSortBean.ListsBeanX.ListsBean> list, AutoMeasureHeightGridView childLvGV) {
        if (childLvGV.getAdapter() == null) {
            //设置二级列表的展开
            mChildAdapter = new RightSideslipLayChildAdapter(context, list);
            childLvGV.setAdapter(mChildAdapter);
        } else {
            mChildAdapter = (RightSideslipLayChildAdapter) childLvGV.getAdapter();
            //设置选中的数据
            mChildAdapter.replaceAll(list);
        }
        if (cbSelectLinstener != null) {

            mChildAdapter.setCbSelectLinstener(cbSelectLinstener);
        }
    }

    OnClickListenerWrapper onClickListener = new OnClickListenerWrapper() {
        @Override
        protected void onSingleClick(View v) {

            int id = v.getId();

            if (id == R.id.item_select_lay) {
                AutoMeasureHeightGridView childLv3GV = (AutoMeasureHeightGridView) v.getTag();
                int pos = (int) childLv3GV.getTag();
                BrandSortBean.ListsBeanX listsBean = data.get(pos);
                boolean isSelect = !listsBean.isoPen();

                // 再将当前选择CB的实际状态
                listsBean.setIsoPen(isSelect);
                notifyDataSetChanged();
            }
        }
    };

    public void setSelectText(TextView selectvalues) {
        mSelectvalues = selectvalues;
    }

    public TextView getSelectText() {
        return mSelectvalues;
    }

}
