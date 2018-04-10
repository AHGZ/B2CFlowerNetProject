package com.android.p2pflowernet.project.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.BrandScendBean;
import com.android.p2pflowernet.project.view.customview.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 下午2:44
 * description: 商品分类列表
 */
public class GoodsSortAdapter extends HFWBaseAdapter<BrandScendBean.FlBean> {
    @Override
    public BaseHolder<BrandScendBean.FlBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_recycler_right, parent, false);
        return new BrandScendHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<BrandScendBean.FlBean> holder, int position) {
        BrandScendBean.FlBean flBean = list.get(position);
        ((GoodsSortAdapter.BrandScendHolder) holder).bindDateView(flBean, position);
    }

    public class BrandScendHolder extends BaseHolder<BrandScendBean.FlBean> {
        @BindView(R.id.sort_gridview)
        // 分类视图
                MyGridView sortgridview;

        @BindView(R.id.scendsort_title)
        // 二级分类标题
                TextView scendsortTitle;
        @BindView(R.id.banner_img)
        // 二级分类标题
                ImageView banner_img;


        public BrandScendHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(BrandScendBean.FlBean flBean, int position) {
            if (flBean == null) {
                return;
            }
            if (position == 0) {
                banner_img.setVisibility(View.VISIBLE);
            } else {
                banner_img.setVisibility(View.GONE);
            }
            scendsortTitle.setText(TextUtils.isEmpty(flBean.getName()) ? "" : flBean.getName());
            if (flBean.getThree().isEmpty() && flBean.getThree().size() == 0) {
                return;
            }
            //设置适配器
            SortDetailApapter mAdapter = new SortDetailApapter(getContext(), flBean.getThree());
            sortgridview.setAdapter(mAdapter);
            sortgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                    if (listener != null) {
                        listener.onRightItemClick(position,index);
                    }
                }
            });
        }
    }

    public void setOnRightItemClickListener(OnRightItemClickListener l) {
        listener = l;
    }

    private OnRightItemClickListener listener;

    public interface OnRightItemClickListener {
        void onRightItemClick(int scendIndex, int position);
    }
}
