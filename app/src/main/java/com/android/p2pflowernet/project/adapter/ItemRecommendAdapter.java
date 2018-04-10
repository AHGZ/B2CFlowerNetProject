package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.RecommendGoodsBean;
import com.bigkoo.convenientbanner.holder.Holder;

import java.util.List;

/**
 * item页底部的推荐商品适配器
 */
public class ItemRecommendAdapter implements Holder<List<RecommendGoodsBean>> {
    private View rootview;
    private GridView gv_recommend_goods;

    @Override
    public View createView(final Context context) {
        rootview = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_recommend, null);
        gv_recommend_goods = (GridView) rootview.findViewById(R.id.gv_recommend_goods);
        return rootview;
    }

    @Override
    public void UpdateUI(final Context context, int position, final List<RecommendGoodsBean> data) {
        gv_recommend_goods.setAdapter(new ItemRecommendGoodsAdapter(context, data));
    }
}
