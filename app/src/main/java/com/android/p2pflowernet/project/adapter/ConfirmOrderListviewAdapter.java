package com.android.p2pflowernet.project.adapter;

import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.caimuhao.rxpicker.utils.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2018/1/11/011.
 * 确认订单商品适配器
 */

public class ConfirmOrderListviewAdapter extends BaseAdapter {
    ArrayList<O2oIndexBean.ListsBean.GoodsListBean> goodsListBeen;
    private int itemCount = 2;

    public ConfirmOrderListviewAdapter(ArrayList<O2oIndexBean.ListsBean.GoodsListBean> goodslistbeen) {
        this.goodsListBeen = goodslistbeen;
    }

    @Override
    public int getCount() {
        if (goodsListBeen.size() > 2) {
            return itemCount;
        } else {
            return goodsListBeen.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.confirm_order_item_listview, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.confirmOrderListviewOutprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        O2oIndexBean.ListsBean.GoodsListBean goodsListBean = goodsListBeen.get(position);
        if (goodsListBean == null) {
            return convertView;
        }
        // 商品图片
        String logo_url = ApiUrlConstant.API_IMG_URL + goodsListBean.getGoods_img();
        new RxImageLoader().display(viewHolder.confirmOrderListviewImg, logo_url,
                DensityUtil.getDeviceWidth(viewHolder.confirmOrderListviewImg.getContext()) / 3,
                DensityUtil.getDeviceWidth(viewHolder.confirmOrderListviewImg.getContext()) / 3);
        // 商品名称
        viewHolder.confirmOrderListviewName.setText(TextUtils.isEmpty(goodsListBean.getName()) ? "" : goodsListBean.getName());
        // 商品价格
        viewHolder.confirmOrderListviewHfPrice.setText(TextUtils.isEmpty(goodsListBean.getPrice()) ? "" : "¥" + goodsListBean.getPrice());
        // 商品过去价格
        viewHolder.confirmOrderListviewOutprice.setText(TextUtils.isEmpty(goodsListBean.getPrice()) ? "" : "¥" + goodsListBean.getPrice());
        // 商品过去价格
        viewHolder.confirmOrderListviewOutprice.setText(TextUtils.isEmpty(goodsListBean.getPrice()) ? "" : "¥" + goodsListBean.getPrice());
        // 商品过去价格
        viewHolder.confirmOrderListviewHfPrice.setText(TextUtils.isEmpty(goodsListBean.getHuafan()) ? "" : "¥" + goodsListBean.getHuafan());
        // 商品数量
        viewHolder.confirmOrderListviewSum.setText("X" + goodsListBeen.size());

        return convertView;
    }

    public void addItemNum(int number) {
        itemCount = number;
    }

    static class ViewHolder {
        //商品图片
        @BindView(R.id.confirm_order_listview_img)
        ImageView confirmOrderListviewImg;
        //商品名称
        @BindView(R.id.confirm_order_listview_name)
        TextView confirmOrderListviewName;
        //商品价格
        @BindView(R.id.confirm_order_listview_price)
        TextView confirmOrderListviewPrice;
        //商品过去价格
        @BindView(R.id.confirm_order_listview_outprice)
        TextView confirmOrderListviewOutprice;
        //商品数量
        @BindView(R.id.confirm_order_listview_sum)
        TextView confirmOrderListviewSum;
        //商品花返价格
        @BindView(R.id.confirm_order_listview_hf_price)
        TextView confirmOrderListviewHfPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
