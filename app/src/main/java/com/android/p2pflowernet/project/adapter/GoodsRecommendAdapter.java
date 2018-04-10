package com.android.p2pflowernet.project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.BrandClassBean;
import com.android.p2pflowernet.project.view.customview.MyGridView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 下午2:44
 * description: 商品推荐分类列表
 */
public class GoodsRecommendAdapter extends HFWBaseAdapter<BrandClassBean.RecommendBean> {
    @Override
    public BaseHolder<BrandClassBean.RecommendBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_recycler_right, parent, false);
        return new BrandScendHolder(view);
    }

    @Override
    public void onViewHolderBind(BaseHolder<BrandClassBean.RecommendBean> holder, int position) {
        ((GoodsRecommendAdapter.BrandScendHolder) holder).bindDateView(list, position);
    }

    public class BrandScendHolder extends BaseHolder<BrandClassBean.RecommendBean> {
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

        void bindDateView(List<BrandClassBean.RecommendBean> data, int position) {
            if (data == null) {
                return;
            }
            if (position == 0) {
                banner_img.setVisibility(View.VISIBLE);
            } else {
                banner_img.setVisibility(View.GONE);
            }
            scendsortTitle.setText("热门分类");
            //设置适配器
            TypesApapter mAdapter = new TypesApapter(getContext(), data);
            sortgridview.setAdapter(mAdapter);
            sortgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listener != null) {
                        listener.onRightItemClick(position);
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public void setOnRightItemClickListener(OnRightItemClickListener l) {
        listener = l;
    }

    private OnRightItemClickListener listener;

    public interface OnRightItemClickListener {
        void onRightItemClick(int position);
    }
}
