package com.android.p2pflowernet.project.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.ReSelectAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.callback.ISelect;
import com.android.p2pflowernet.project.entity.O2oIndexBean;

/**
 * author: zhangpeisen
 * created on: 2017/10/26 下午6:34
 * description: 选择规格列表
 */
public class SelectSpecAdapter extends ReSelectAdapter<O2oIndexBean.ListsBean.GoodsListBean.SpecListBean> {

    public SelectSpecAdapter() {
        super(ISelect.SINGLE_MODE, false);
    }

    public SelectSpecAdapter(int currentMode, boolean longTouchEnable) {
        super(currentMode, longTouchEnable);
    }


    @Override
    public BaseHolder<O2oIndexBean.ListsBean.GoodsListBean.SpecListBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.o2ogsspec_listitem_layout, parent, false);
        return new MyViewHolder<>(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<O2oIndexBean.ListsBean.GoodsListBean.SpecListBean> holder, final int position) {
        final O2oIndexBean.ListsBean.GoodsListBean.SpecListBean testBean = list.get(position);

        ((MyViewHolder) holder).bindDateView(testBean);

    }

    @Override
    public int getItemViewTypes(int position) {
        return super.getItemViewTypes(position);
    }

    private static class MyViewHolder<T> extends BaseHolder<T> {
        private final CheckedTextView mTv;

        MyViewHolder(View itemView) {
            super(itemView);
            mTv = (CheckedTextView) itemView.findViewById(R.id.selecto2ospec_tv);
        }

        void bindDateView(O2oIndexBean.ListsBean.GoodsListBean.SpecListBean s) {
            mTv.setText(s.getGoods_spec());
            mTv.setChecked(s.isSelected());
        }
    }

    //you should Override this method to control whether the viewHolder can move and swipe or not! by default was impossible!!
    @NonNull
    @Override
    public int[] getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder.getAdapterPosition() % 2 == 0) {
            return new int[]{ItemTouchHelper.UP | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.ACTION_STATE_IDLE};
        }
        return super.getMovementFlags(recyclerView, viewHolder);

    }


}
