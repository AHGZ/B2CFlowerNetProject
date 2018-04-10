package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.o2omain.view.shopcarview.StickyHeadersAdapter;

import java.util.List;

/**
 * @描述:滑动的头部信息
 * @创建人：zhangpeisen
 * @创建时间：2018/1/3 下午6:29
 * @修改人：zhangpeisen
 * @修改时间：2018/1/3 下午6:29
 * @修改备注：
 * @throws
 */
public class BigramHeaderAdapter implements StickyHeadersAdapter<BigramHeaderAdapter.ViewHolder> {

    private Context mContext;
    private List<O2oIndexBean.ListsBean.GoodsListBean> dataList;
    private List<O2oIndexBean.ListsBean> goodscatrgoryEntities;

    public BigramHeaderAdapter(Context context, List<O2oIndexBean.ListsBean.GoodsListBean> items
            , List<O2oIndexBean.ListsBean> goodscatrgoryEntities) {
        this.mContext = context;
        this.dataList = items;
        this.goodscatrgoryEntities = goodscatrgoryEntities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_goods_list, parent, false);
        return new BigramHeaderAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BigramHeaderAdapter.ViewHolder headerViewHolder, int position) {
        headerViewHolder.tvGoodsItemTitle.setText(goodscatrgoryEntities.get(
                dataList.get(position).getTag()).getName());
    }

    @Override
    public long getHeaderId(int position) {
        return dataList.get(position).getTag();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvGoodsItemTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvGoodsItemTitle = (TextView) itemView.findViewById(R.id.tvGoodsItemTitle);
        }
    }
}
