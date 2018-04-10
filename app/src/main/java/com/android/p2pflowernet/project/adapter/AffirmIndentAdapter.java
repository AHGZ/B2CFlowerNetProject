package com.android.p2pflowernet.project.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2017/11/1.
 * by--确认订单页面的适配器
 */

public class AffirmIndentAdapter extends BaseExpandableListAdapter {

    private final FragmentActivity mContext;
    private final OrderDetailBean data;
    private InvoiceOnClickLintener invoiceOnClickLintener;


    public AffirmIndentAdapter(FragmentActivity activity, OrderDetailBean list) {

        this.mContext = activity;
        this.data = list;
    }

    /***
     * 点击发票的事件处理
     */
    public interface InvoiceOnClickLintener {

        void onItemClick(View view, TextView invoice);//发票的点击事件
    }


    public void setInvoiceOnClickLintener(InvoiceOnClickLintener mOnRecyclerViewListener) {

        this.invoiceOnClickLintener = mOnRecyclerViewListener;
    }


    @Override
    public int getGroupCount() {

        return data.getList().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        OrderDetailBean.ListBean listBean = data.getList().get(groupPosition);
        List<OrderDetailBean.ListBean.GoodsBean> goods = listBean.getGoods();
        return goods.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return data.getList().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        OrderDetailBean.ListBean.GoodsBean goodsBean = data.getList().get(groupPosition).getGoods().get(childPosition);
        return goodsBean;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        GroupViewHolder groupViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_affirm_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        OrderDetailBean.ListBean group = (OrderDetailBean.ListBean) getGroup(groupPosition);
        //设置店铺的数据
        groupViewHolder.tvBrand.setText(group.getMfrname());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_affirm_child, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置下属子类的数据
        OrderDetailBean.ListBean.GoodsBean child = (OrderDetailBean.ListBean.GoodsBean)
                getChild(groupPosition, childPosition);

        childViewHolder.tvTitle.setText(child.getGoods_name());
        childViewHolder.tvPrice.setText("￥" + child.getSale_price() + "");
        childViewHolder.tvNumber.setText("x" + child.getNum() + "");
        childViewHolder.tvPriceRabate.setText("¥" + child.getHuafan());
        childViewHolder.tvDescProperty.setText(child.getGoods_spec());
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        String file_path = ApiUrlConstant.API_IMG_URL + child.getFile_path();
        glideImageLoader.displayImage(mContext, file_path, childViewHolder.ivGov);

        int childrenCount = getChildrenCount(groupPosition);

        //判断显示每一个订单只需要显示一个订单详情
        int groupCount = getGroupCount();
        if (groupPosition == groupCount - 1 && childPosition == childrenCount - 1) {

            childViewHolder.llDetail.setVisibility(View.VISIBLE);
            childViewHolder.allNumber.setText("共" + data.getNum() + "件商品");//多少件商品
            childViewHolder.allInvoce.setText("¥" + data.getInvoice_money());//发票费用
            childViewHolder.tvFreight.setText("¥" + data.getExpress_money());//运费
            childViewHolder.allMoney.setText("¥" + data.getSale_price());//商品金额

        } else {

            childViewHolder.llDetail.setVisibility(View.GONE);
        }

        //判断是子类的最后一个就显示所有详细信息
        if (childPosition == childrenCount - 1) {

            childViewHolder.llRabate.setVisibility(View.VISIBLE);
            childViewHolder.allRabate.setText("花返¥" + data.getHuafan());//花返值

        } else {

            childViewHolder.llRabate.setVisibility(View.GONE);
        }

        //设置发票的点击事件
        childViewHolder.llInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (invoiceOnClickLintener != null) {
                    //回调接口
                    invoiceOnClickLintener.onItemClick(v, childViewHolder.tv_invoice_detail);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public class GroupViewHolder {
        @BindView(R.id.view_gray)
        View viewGray;
        @BindView(R.id.iv_brand)
        ImageView ivBrand;
        @BindView(R.id.tv_brand)
        TextView tvBrand;
        @BindView(R.id.ll_head)
        LinearLayout llHead;

        GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public class ChildViewHolder {

        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.iv_stockout)
        ImageView ivStockout;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_color)
        TextView tvColor;
        @BindView(R.id.tv_desc_property)
        TextView tvDescProperty;
        @BindView(R.id.tv_return)
        TextView tvReturn;
        @BindView(R.id.tv_return1)
        TextView tvReturn1;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv01)
        TextView tv01;
        @BindView(R.id.tv_price_rabate)
        TextView tvPriceRabate;
        @BindView(R.id.tv_number)
        TextView tvNumber;
        @BindView(R.id.all_rabate)
        TextView allRabate;
        @BindView(R.id.ll_rabate)
        LinearLayout llRabate;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.tv_invoice)
        TextView tvInvoice;
        @BindView(R.id.ll_invoice)
        RelativeLayout llInvoice;
        @BindView(R.id.all_number)
        TextView allNumber;
        @BindView(R.id.tv_freight)
        TextView tvFreight;
        @BindView(R.id.all_invoce)
        TextView allInvoce;
        @BindView(R.id.all_money)
        TextView allMoney;
        @BindView(R.id.ll_detail)
        LinearLayout llDetail;
        @BindView(R.id.tv_invoice_detail)
        TextView tv_invoice_detail;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
