package com.android.p2pflowernet.project.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.entity.BrandClassBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author: zhangpeisen
 * created on: 2017/12/5 下午2:44
 * description: 商品菜单列表
 */
public class GoodsSortLeftAdapter extends HFWBaseAdapter<BrandClassBean.FlBean> {

    OnLeftItemClickListener onLeftItemClickListener;


    @Override
    public BaseHolder<BrandClassBean.FlBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_recycler_left, parent, false);
        return new BrandSortHolder(view);
    }

    public void setOnLeftItemClickListener(OnLeftItemClickListener onLeftItemClickListener) {
        this.onLeftItemClickListener = onLeftItemClickListener;
    }

    @Override
    public void onViewHolderBind(BaseHolder<BrandClassBean.FlBean> holder, int position) {
        BrandClassBean.FlBean flBean = list.get(position);
        ((GoodsSortLeftAdapter.BrandSortHolder) holder).bindDateView(flBean, position);
    }

    public class BrandSortHolder extends BaseHolder<BrandClassBean.FlBean> {

        @BindView(R.id.tv_text)
        TextView textView;
        @BindView(R.id.view)
        View viewLine;

        public BrandSortHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(BrandClassBean.FlBean flBean, final int position) {
            if (flBean == null) {
                return;
            }

            textView.setText(TextUtils.isEmpty(flBean.getName()) ? "" : flBean.getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setChoose(false);
                    }
                    list.get(position).setChoose(true);
                    if (onLeftItemClickListener != null) {
                        onLeftItemClickListener.onLeftItemClick(position);
                    }
                    notifyDataSetChanged();
                }
            });

            if (flBean.isChoose() == true) {
                textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.Blue));
                viewLine.setVisibility(View.VISIBLE);
            } else {
                textView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                viewLine.setVisibility(View.GONE);
            }

            if (position == 0) {
                textView.setBackgroundColor(Color.parseColor("#e8e8e8"));
                textView.setTextColor(Color.parseColor("#006835"));
            } else {
                textView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.parseColor("#4b4b4b"));
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface OnLeftItemClickListener {
        void onLeftItemClick(int position);
    }

}
