package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.base.HFWBaseAdapter;
import com.android.p2pflowernet.project.callback.BaseHolder;
import com.android.p2pflowernet.project.callback.OnSelectedListener;
import com.android.p2pflowernet.project.entity.GoodsAttrBean;
import com.android.p2pflowernet.project.entity.ParameterEntity;
import com.android.p2pflowernet.project.view.customview.SelectGsAttrFlowLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.R.id.type_txt;


/**
 * @描述: 商品属性适配器
 * @创建人：zhangpeisen
 * @创建时间：2017/10/17 下午4:30
 * @修改人：zhangpeisen
 * @修改时间：2017/10/17 下午4:30
 * @修改备注：
 * @throws
 */
public class GsAttrAdapter extends HFWBaseAdapter<GoodsAttrBean.ListsBean> {
    // 库存件数
    private int mStockNum;


    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }

    private OnSelectedListener onSelectedListener;


    public void setStockNum(int stocknum) {
        this.mStockNum = stocknum;
    }


    @Override
    public BaseHolder<GoodsAttrBean.ListsBean> onViewHolderCreate(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.emp_dialog_item, parent, false);
        return new GsAttrHolder(view);
    }


    @Override
    public void onViewHolderBind(BaseHolder<GoodsAttrBean.ListsBean> holder, int position) {
        GoodsAttrBean.ListsBean listsBean = list == null ? null : list.get(position);
        ((GsAttrHolder) holder).bindDateView(listsBean, position);
    }

    class GsAttrHolder extends BaseHolder<GoodsAttrBean.ListsBean> {
        @BindView(type_txt)
        // 商品属性名称
                TextView typeTxt;
        @BindView(R.id.news_top_title_base_flowlayout)
        // 商品属性组合
                SelectGsAttrFlowLayout newsTopTitleBaseFlowlayout;

        public GsAttrHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDateView(GoodsAttrBean.ListsBean listsBean, int pos) {
            if (listsBean == null) {
                return;
            }
            // 商品属性名称
            typeTxt.setText(TextUtils.isEmpty(listsBean.getAttr_name()) ? "" : listsBean.getAttr_name());
            // 获取选中属性集合
            ArrayList<ParameterEntity> list = new ArrayList<>();
            for (int i = 0; i < listsBean.getAttr_value().size(); i++) {
                ParameterEntity entity = new ParameterEntity(listsBean.getAttr_value().get(i));
                entity.attrValueBean.setIs_default(listsBean.getAttr_value().get(i).getIs_default());
                entity.selected = true;
                list.add(entity);
            }
            // 设置商品属性tag
            AttrAdapter attrAdapter = new AttrAdapter(getContext(), pos, list, newsTopTitleBaseFlowlayout);
            newsTopTitleBaseFlowlayout.setAdapter(attrAdapter);
        }
    }


    class AttrAdapter extends BaseAdapter {
        private ArrayList<ParameterEntity> paramTerEntitylist;
        Context mContext;
        int outPos;
        private int listAttrsSize;
        SelectGsAttrFlowLayout newsTopTitleBaseFlowlayout;

        public AttrAdapter(Context context, int pos, ArrayList<ParameterEntity> paramterentitylist, SelectGsAttrFlowLayout newsTopTitleBaseFlowlayout) {
            this.mContext = context;
            this.paramTerEntitylist = paramterentitylist;
            this.outPos = pos;
            this.newsTopTitleBaseFlowlayout = newsTopTitleBaseFlowlayout;
        }

        @Override
        public int getCount() {

            listAttrsSize = paramTerEntitylist.size();
            return paramTerEntitylist.size();
        }

        @Override
        public Object getItem(int position) {

            return paramTerEntitylist.isEmpty() ? null : paramTerEntitylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.news_top_title_search_, null);
                holder = new Holder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            final ParameterEntity param = paramTerEntitylist.get(position);
            holder.selectattr_tv.setText(param.attrValueBean.getOption_name());
            holder.selectattr_tv.setChecked(param.selected);
            holder.selectattr_tv.setTag("rbAttribute" + position);
            if (param.attrValueBean.getIs_default() == 1) {
                holder.selectattr_tv.setChecked(true);
                if (onSelectedListener != null) {
//                    onSelectedListener.onConfirm(list.get(outPos).getAttr_name() == null ? "" : list.get(outPos).getAttr_name(),
//                            holder.selectattr_tv.getText().toString().trim(), param.attrValueBean.getId());
                    if(!TextUtils.isEmpty(list.get(outPos).getAttr_name())) {
                        onSelectedListener.onConfirm(list.get(outPos).getAttr_name(), param.attrValueBean);
                    }
                }
            } else {
                holder.selectattr_tv.setChecked(false);
            }
            holder.selectattr_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckedTextView checkedTextView =
                            (CheckedTextView) newsTopTitleBaseFlowlayout.findViewWithTag("rbAttribute" + position);
                    for (int i = 0; i < listAttrsSize; i++) {
                        CheckedTextView allcheckedTextView =
                                (CheckedTextView) newsTopTitleBaseFlowlayout.findViewWithTag("rbAttribute" + i);
                        allcheckedTextView.setChecked(false);
                    }
                    checkedTextView.setChecked(true);
                    paramTerEntitylist.get(0).attrValueBean.setIs_default(0);
                    String trim = checkedTextView.getText().toString().trim();
                    if (onSelectedListener != null) {
//                        onSelectedListener.onConfirm(list.get(outPos).getAttr_name() == null ? "" : list.get(outPos).getAttr_name(), trim, param.attrValueBean.getId());
                        if(!TextUtils.isEmpty(list.get(outPos).getAttr_name())) {
                            onSelectedListener.onConfirm(list.get(outPos).getAttr_name(), param.attrValueBean);
                        }
                    }
                }
            });

            return convertView;
        }
    }

    class Holder {
        CheckedTextView selectattr_tv;

        public Holder(View view) {
            selectattr_tv = (CheckedTextView) view.findViewById(R.id.selectattr_tv);
        }
    }
}
