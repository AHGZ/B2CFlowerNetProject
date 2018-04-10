package com.android.p2pflowernet.project.view.fragments.trade;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.entity.StoreInfo;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.view.customview.NumberAddSubView;
import com.android.p2pflowernet.project.view.customview.SwipeListLayout;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.android.p2pflowernet.project.R.id.cb_shop;

/**
 * Created by caishen on 2017/10/30.
 * by--购物车适配器
 */

public class ShopcatAdapter extends BaseExpandableListAdapter {

    private final TextView tvShopcartTotal;
    private final TextView tvShopcartRabate;
    private final CheckBox checkboxAll;
    private final List<ShopCarBean.ListBean> groups;
    private CheckBox cbAll;
    private Context mContext;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private GroupEditorListener groupEditorListener;
    private OnNumberChangeListener onNumberChangeListener;
    private int count = 0;
    private boolean flag = true; //组的编辑按钮是否可见，true可见，false不可
    private OnItemClickViewListener onItemClickViewListener;
    private List<ShopCarBean.ListBean.ShopBean> childrens;
    private boolean choosed;

    public ShopcatAdapter(List<ShopCarBean.ListBean> list, FragmentActivity activity, TextView tvShopcartTotal,
                          TextView tvShopcartRabate, CheckBox all, CheckBox cbAll) {
        this.mContext = activity;
        this.tvShopcartTotal = tvShopcartTotal;
        this.tvShopcartRabate = tvShopcartRabate;
        this.cbAll = cbAll;
        this.checkboxAll = all;
        this.groups = list;

        //首次加载数据
        showTotalPrice();

        //设置编辑状态下的全选按钮
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = getCb_all().isChecked();
                checkAll_none(checked);
                showTotalPrice();
            }
        });
    }

    //recyclerview的点击回调接口
    public interface OnItemClickViewListener {

        void onItemClick(View view, int position, int groupPosition);//单击

        void onItemLongClick(View view, int position);//长按

    }

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }

    //设置加减控件的点击事件
    public interface OnNumberChangeListener {

        void addNumberClick(View view, int value, int position, int chilPosition);//加

        void subNumnerClick(View view, int value, int position, int chilPosition);//减

    }

    public void setOnItemViewListener(OnItemClickViewListener mOnRecyclerViewListener) {

        this.onItemClickViewListener = mOnRecyclerViewListener;
    }

    public void checkAll_none(boolean checked) {

        if (childrens != null && childrens.size() > 0 && groups != null && groups.size() > 0) {

            //店铺
            for (int i = 0; i < groups.size(); i++) {

                ShopCarBean.ListBean listBean = groups.get(i);
                if (checked == true) {
                    listBean.setSelect("1");
                } else {
                    listBean.setSelect("0");
                }

                listBean.setChoosed(checked);
                List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();

                for (int j = 0; j < child.size(); j++) {

                    ShopCarBean.ListBean.ShopBean shopBean = child.get(j);
                    shopBean.setChoosed(checked);
                    if (checked == true) {
                        shopBean.setSelect("1");
                    } else {
                        shopBean.setSelect("0");
                    }
                }
            }

            checkboxAll.setChecked(checked);
            notifyDataSetChanged();

        } else {

            checkboxAll.setChecked(false);
        }
    }

    @Override
    public int getGroupCount() {

        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        childrens = groups.get(groupPosition).getShop();
        return childrens.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        ShopCarBean.ListBean.ShopBean shopBean = groups.get(groupPosition).getShop().get(childPosition);
        return shopBean;

    }

    /**
     * 返回返润的回调
     *
     * @return
     */
    private double getRabateTotal() {

        double mtotalPrice = 0.00;

        for (int i = 0; i < groups.size(); i++) {

            ShopCarBean.ListBean listBean = groups.get(i);

            List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();

            for (int j = 0; j < child.size(); j++) {

                ShopCarBean.ListBean.ShopBean shopBean = child.get(j);

                String select = shopBean.getSelect();
                if (select.equals("0")) {
                    shopBean.setChoosed(false);
                } else {
                    shopBean.setChoosed(true);
                }

                if (shopBean.isChoosed()) {
                    String huafan = shopBean.getHuafan();
                    double parseDouble = Double.parseDouble(huafan.replace(",", ""));

                    mtotalPrice += parseDouble * shopBean.getCount();
                    BigDecimal bg = new BigDecimal(mtotalPrice);
                    mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
        }
        return mtotalPrice;
    }

    /***
     * 返回总价的回调
     * @return
     */
    private double getTotalPrice() {

        double mtotalPrice = 0.00;

        for (int i = 0; i < groups.size(); i++) {

            ShopCarBean.ListBean listBean = groups.get(i);

            List<ShopCarBean.ListBean.ShopBean> shop = listBean.getShop();

            for (int j = 0; j < shop.size(); j++) {

                ShopCarBean.ListBean.ShopBean shopBean = shop.get(j);
                String select = shopBean.getSelect();
                if (select.equals("0")) {
                } else {
                    String is_sale = shopBean.getSale_price();
                    double replace = Double.parseDouble(is_sale.replace(",", ""));

                    mtotalPrice += replace * shopBean.getCount();
                    BigDecimal bg = new BigDecimal(mtotalPrice);
                    mtotalPrice = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
            }
        }

        return mtotalPrice;
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    public void showTotalPrice() {

        //显示总价
        tvShopcartTotal.setText(getTotalPrice() + "");

        //显示返润的价格
        tvShopcartRabate.setText(getRabateTotal() + "");
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

    public CheckBox getCb_all() {

        return cbAll;
    }

    public void setCb_all(CheckBox cb_all) {

        this.cbAll = cb_all;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder groupViewHolder;

        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_shopcat_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);

        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

//        shopcheckAll(groupPosition);

        final ShopCarBean.ListBean group = (ShopCarBean.ListBean) getGroup(groupPosition);
        groupViewHolder.storeName.setText(group.getManufac_name());
        String select = group.getSelect();

        if (select.equals("0")) {
            group.setChoosed(false);
            groupViewHolder.storeCheckBox.setChecked(false);
        } else {
            group.setChoosed(true);
            groupViewHolder.storeCheckBox.setChecked(true);
        }

        String file_path = ApiUrlConstant.API_IMG_URL + group.getFile_path();
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        glideImageLoader.displayImage(mContext, file_path, groupViewHolder.imgBrand);

        groupViewHolder.storeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                group.setChoosed(((CheckBox) v).isChecked());
                checkInterface.checkGroup(groupPosition, ((CheckBox) v).isChecked());
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        final ChildViewHolder childViewHolder;

        if (convertView == null) {

            convertView = View.inflate(mContext, R.layout.item_shopcat_product, null);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);

        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag();
        }

        //设置点击事件
        childViewHolder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onItemClickViewListener.onItemClick(childViewHolder.llItem, childPosition, groupPosition);
            }
        });

        //增加接触面积
        UIUtils.setTouchDelegate(childViewHolder.singleCheckBox, 55);
        UIUtils.setTouchDelegate(childViewHolder.numberAddSubView, 50);

        ShopCarBean.ListBean.ShopBean child = (ShopCarBean.ListBean.ShopBean) getChild(groupPosition, childPosition);

        if (child != null) {

            childViewHolder.goodsName.setText(child.getGoods_name());
            childViewHolder.goodsPrice.setText("￥" + child.getSale_price() + "");
            childViewHolder.numberAddSubView.setValue(child.getCount());
            childViewHolder.tvDescProperty.setText(child.getGoods_spec());
            childViewHolder.tvPriceRabate.setText(child.getHuafan() + "");
            String select = child.getSelect();

            if (select.equals("0")) {
                child.setChoosed(false);
                childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            } else {
                child.setChoosed(true);
                childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            }

            String file_path = ApiUrlConstant.API_IMG_URL + child.getFile_path();
            GlideImageLoader glideImageLoader = new GlideImageLoader();
            glideImageLoader.displayImage(mContext, file_path, childViewHolder.ivGov);
            childViewHolder.numberAddSubView.setMaxValue(child.getStock());

            //是否设置商铺全选
//            shopcheckAll(groupPosition);

            //是否设置全选
            checkAll();

            //库存是否充足
            if (child.getStock() < 0) {
                childViewHolder.ivStockout.setVisibility(View.VISIBLE);
                childViewHolder.ivStockout.setImageResource(R.mipmap.stockout);
            } else {
                childViewHolder.ivStockout.setVisibility(View.GONE);
            }

            //商品是否下架
            if (child.getIs_sale().equals("0")) {//下架
                childViewHolder.ivStockout.setVisibility(View.VISIBLE);
                childViewHolder.ivStockout.setImageResource(R.drawable.sold_out);
            } else {
                childViewHolder.ivStockout.setVisibility(View.GONE);
            }

            //设置打折前的原价
//            SpannableString spannableString = new SpannableString("￥" + child.getPrime_price() + "");
//            StrikethroughSpan span = new StrikethroughSpan();
//            spannableString.setSpan(span, 0, spannableString.length() - 1 + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

            //删除的点击事件
            childViewHolder.tvDelete.setTag(childPosition);
            childViewHolder.llDelete.setTag(childPosition);
            childViewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (Integer) childViewHolder.llDelete.getTag();
                    modifyCountInterface.childDelete(groupPosition, pos);
                    childViewHolder.sll.setStatus(SwipeListLayout.Status.Close, true);
                }
            });


            //商品单选的点击事件
            childViewHolder.singleCheckBox.setChecked(child.isChoosed());
            childViewHolder.singleCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    child.setChoosed(((CheckBox) v).isChecked());
                    childViewHolder.singleCheckBox.setChecked(((CheckBox) v).isChecked());
                    checkInterface.checkChild(groupPosition, childPosition, ((CheckBox) v).isChecked());
                }
            });

            //加减视图控制
            childViewHolder.numberAddSubView.setOnNumberChangeListener(new NumberAddSubView.OnNumberChangeListener() {
                @Override
                public void addNumber(View view, int value) {

                    onNumberChangeListener.addNumberClick(view, value, groupPosition, childPosition);
                }

                @Override
                public void subNumner(View view, int value) {

                    onNumberChangeListener.subNumnerClick(view, value, groupPosition, childPosition);
                }
            });
        }
        return convertView;
    }

    //商铺是否全选
    public void shopcheckAll(int groupPosition) {

        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        ShopCarBean.ListBean group = groups.get(groupPosition);
        List<ShopCarBean.ListBean.ShopBean> child = group.getShop();

        for (int i = 0; i < child.size(); i++) {

//            String select = child.get(i).getSelect();
//            if (select.equals("0")) {
//                child.get(i).setChoosed(false);
//            } else {
//                child.get(i).setChoosed(true);
//            }

            choosed = child.get(i).isChoosed();

            //不选全中
            if (child.get(i).isChoosed() != choosed) {

                allChildSameState = false;

                break;
            }
        }

        if (allChildSameState) {

            //如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
            group.setChoosed(choosed);
            if (choosed) {
                group.setSelect("1");
            } else {
                group.setSelect("0");
            }

        } else {

            group.setChoosed(false);//否则一律视为未选中
            group.setSelect("0");
        }
    }

    //设置是否全选
    public void checkAll() {

        if (groups != null && groups.size() > 0) {

            for (int i = 0; i < groups.size(); i++) {
                ShopCarBean.ListBean listBean = groups.get(i);
                List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();
                for (int i1 = 0; i1 < child.size(); i1++) {

                    ShopCarBean.ListBean.ShopBean shopBean = child.get(i1);

                    if (!shopBean.isChoosed()) {

                        cbAll.setChecked(false);
                        checkboxAll.setChecked(false);
                        return;

                    } else {

                        checkboxAll.setChecked(true);
                        cbAll.setChecked(true);
                    }
                }
            }
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }


    public GroupEditorListener getGroupEditorListener() {
        return groupEditorListener;
    }

    public void setGroupEditorListener(GroupEditorListener groupEditorListener) {
        this.groupEditorListener = groupEditorListener;
    }

    public CheckInterface getCheckInterface() {

        return checkInterface;
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public ModifyCountInterface getModifyCountInterface() {
        return modifyCountInterface;
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }


    static class GroupViewHolder {

        @BindView(cb_shop)
        CheckBox storeCheckBox;
        @BindView(R.id.tv_brand)
        TextView storeName;
        @BindView(R.id.view_gray)
        View ll_head;
        @BindView(R.id.iv_brand)
        ImageView imgBrand;

        public GroupViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * 店铺的复选框
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param isChecked     组元素的选中与否
         */
        void checkGroup(int groupPosition, boolean isChecked);

        /**
         * 子选框状态改变触发的事件
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param isChecked     子元素的选中与否
         */
        void checkChild(int groupPosition, int childPosition, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param groupPosition 组元素的位置
         * @param childPosition 子元素的位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked);

        /**
         * 删除子Item
         *
         * @param groupPosition
         * @param childPosition
         */
        void childDelete(int groupPosition, int childPosition);
    }

    /**
     * 监听编辑状态
     */
    public interface GroupEditorListener {
        void groupEditor(int groupPosition);
    }

    /**
     * 使某个小组处于编辑状态
     */
    private class GroupViewClick implements View.OnClickListener {
        private StoreInfo group;
        private int groupPosition;
        private TextView editor;

        public GroupViewClick(StoreInfo group, int groupPosition, TextView editor) {
            this.group = group;
            this.groupPosition = groupPosition;
            this.editor = editor;
        }

        @Override
        public void onClick(View v) {
            if (editor.getId() == v.getId()) {
                groupEditorListener.groupEditor(groupPosition);
                if (group.isEditor()) {
                    group.setEditor(false);
                } else {
                    group.setEditor(true);
                }
                notifyDataSetChanged();
            }
        }
    }

    static class ChildViewHolder {
        @BindView(R.id.cb_gov)
        CheckBox singleCheckBox;
        @BindView(R.id.iv_gov)
        ImageView ivGov;
        @BindView(R.id.iv_stockout)
        ImageView ivStockout;
        @BindView(R.id.tv_desc_gov)
        TextView goodsName;
        @BindView(R.id.tv_desc_property)
        TextView tvDescProperty;
        @BindView(R.id.tv_price_gov)
        TextView goodsPrice;
        @BindView(R.id.tv_price_rabate)
        TextView tvPriceRabate;
        @BindView(R.id.numberAddSubView)
        NumberAddSubView numberAddSubView;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.ll_delete)
        LinearLayout llDelete;
        @BindView(R.id.swp_layout)
        SwipeListLayout sll;
        @BindView(R.id.tv_delete)
        TextView tvDelete;

        ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}