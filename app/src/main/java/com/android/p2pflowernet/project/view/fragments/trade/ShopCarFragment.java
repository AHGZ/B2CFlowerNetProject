package com.android.p2pflowernet.project.view.fragments.trade;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GoodsInventoryCount;
import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.entity.ShopCarBean;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.event.ShopCarRefreshEvent;
import com.android.p2pflowernet.project.event.UserInfoEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.activity.GoodsDetailActivity;
import com.android.p2pflowernet.project.view.activity.LoginActivity;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.AffirmIndentActivity;
import com.android.p2pflowernet.project.view.fragments.mine.message.MessageActivity;
import com.jwenfeng.library.pulltorefresh.BaseRefreshListener;
import com.jwenfeng.library.pulltorefresh.PullToRefreshLayout;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/10/30.
 * by--重构购物车模块
 */

public class ShopCarFragment extends KFragment<ITradeView, ITradePresenter> implements
        ShopcatAdapter.CheckInterface, ShopcatAdapter.ModifyCountInterface, ShopcatAdapter.GroupEditorListener
        , ITradeView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.checkbox_all)
    CheckBox checkboxAll;//结算的全选
    @BindView(R.id.tv_shopcart_total)
    TextView tvShopcartTotal;
    @BindView(R.id.tv_shopcart_rabate)
    TextView tvShopcartRabate;
    @BindView(R.id.btn_check_out)
    Button btnCheckOut;
    @BindView(R.id.ll_check_all)
    LinearLayout llCheckAll;
    @BindView(R.id.cb_all)
    CheckBox cbAll;//编辑的全选
    @BindView(R.id.btn_collection)
    Button btnCollection;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.ll_delete)
    LinearLayout llDelete;
    @BindView(R.id.ex_listView)
    ExpandableListView listView;
    @BindView(R.id.ll_car)
    LinearLayout llCart;
    @BindView(R.id.layout_empty_shopcart)
    LinearLayout empty_shopcart;
    @BindView(R.id.layout_error_shopcart)
    LinearLayout error_shopcart;
    @BindView(R.id.pull_refresh)
    PullToRefreshLayout pullRefresh;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.layout_login)
    LinearLayout ll_login;
    private ShopcatAdapter adapter;

    /**
     * 编辑状态
     */
    private static final int ACTION_EDIT = 0;
    /**
     * 完成状态
     */
    private static final int ACTION_COMPLETE = 1;

    private ShapeLoadingDialog shapeLoadingDialog;
    private List<ShopCarBean.ListBean> groups;
    private String spec_id = "";
    private int count;
    private String cart_goods_id = "";
    private String goods_id = "";
    private List<ShopCarBean.ListBean.ShopBean> shop;
    private ShopCarBean.ListBean.ShopBean shopBean;
    private String select = "";
    private boolean choosed;
    private ShopCarBean.ListBean listBean;
    private boolean isRefresh = false;

    @Override
    public ITradePresenter createPresenter() {

        return new ITradePresenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_shopcar;
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.colorstart));

        //变成编辑状态
        hideDelete();
//        tvUpdate.setTag(ACTION_EDIT);
//        llDelete.setVisibility(View.GONE);

        //获取是否登录
        String token = SPUtils.get(getActivity(), "token", "").toString();

        if (token.equals("") || token == null) {

            tvUpdate.setVisibility(View.GONE);
            ll_login.setVisibility(View.VISIBLE);
            empty_shopcart.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            llDelete.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.GONE);

        } else {

            tvUpdate.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            llCheckAll.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);

            initData();
        }

        pullRefresh.setRefreshListener(new BaseRefreshListener() {
            @Override
            public void refresh() {

                mPresenter.getShopCars();
                // 结束刷新
                pullRefresh.finishRefresh();
            }

            @Override
            public void loadMore() {

                // 结束加载更多
                pullRefresh.finishLoadMore();
            }
        });
    }

    //编辑全选的按钮
    private void updataDoCheckall() {

        cart_goods_id = "";
        select = "";

        if (groups == null) {
            return;
        }

        boolean isChecked = cbAll.isChecked();
        if (isChecked) {
            select = "1";
        } else {
            select = "0";
        }

        for (int i = 0; i < groups.size(); i++) {

            ShopCarBean.ListBean listBean = groups.get(i);
            listBean.setChoosed(cbAll.isChecked());
            listBean.setSelect(select);
            List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();

            for (int j = 0; j < child.size(); j++) {

                child.get(j).setChoosed(cbAll.isChecked());//这里出现过错误
                child.get(j).setSelect(select);
                String cart_goods_ids = child.get(j).getCart_goods_id();
                cart_goods_id += cart_goods_ids + ",";
            }
        }

        cart_goods_id = cart_goods_id.toString().substring(0, cart_goods_id.toString().lastIndexOf(","));

        //设置购物车选中状态
        mPresenter.setCarGoods();

        adapter.notifyDataSetChanged();
        adapter.showTotalPrice();
        showGoodNum();
    }

    //初始化数据
    @Override
    public void initData() {
        //获取是否登录
        String token = SPUtils.get(getActivity(), "token", "").toString();
        if (token.equals("") || token == null) {
            return;
        }

        mPresenter.getShopCars();
        showGoodNum();
    }

    public static KFragment newInstance() {

        Bundle bundle = new Bundle();
        ShopCarFragment shopCarFragment = new ShopCarFragment();
        shopCarFragment.setArguments(bundle);
        return shopCarFragment;

    }

    @OnClick({R.id.tv_update, R.id.iv_message, R.id.btn_check_out, R.id.btn_collection,
            R.id.btn_delete, R.id.checkbox_all, R.id.btn_pay, R.id.tv_login, R.id.cb_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_update://编辑

                //设置编辑的点击事件
                int tag = (int) tvUpdate.getTag();
                if (tag == ACTION_EDIT) {

                    //变成完成状态
                    showDelete();

                } else {

                    //变成编辑状态
                    hideDelete();
                }

                break;
            case R.id.iv_message://消息

                //获取是否登录
                String token = SPUtils.get(getActivity(), "token", "").toString();

                if (token.equals("") || token == null) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                }

                break;
            case R.id.btn_check_out://结算

                checkChecked();
                //友盟统计
                MobclickAgent.onEvent(getActivity(), "check_out");

                break;
            case R.id.btn_collection://收藏


                break;
            case R.id.btn_delete://全选中的删除

                showAlertMsgDialog("确认要删除该商品吗?", "温馨提示", "确定", "取消");

                break;
            case R.id.checkbox_all://结算全选

                doCheckAll();

                break;
            case R.id.btn_pay://去购买

                //发送消息跳转至首页
                EventBus.getDefault().post(new MainEvent(0));

                break;
            case R.id.tv_login://去登陆

                startActivity(new Intent(getActivity(), LoginActivity.class));

                break;
            case R.id.cb_all://编辑全选

                updataDoCheckall();

                break;
        }
    }

    //检验选中的购物车id
    private void checkChecked() {

        cart_goods_id = "";
        spec_id = "";
        boolean isShelf = true;

        if (groups != null && groups.size() > 0) {

            for (int i = 0; i < groups.size(); i++) {

                List<ShopCarBean.ListBean.ShopBean> shop = groups.get(i).getShop();

                for (int i1 = 0; i1 < shop.size(); i1++) {

                    ShopCarBean.ListBean.ShopBean shopBean = shop.get(i1);
                    boolean choosed = shopBean.isChoosed();
                    String str = shopBean.getIs_sale();
                    if (choosed) {

                        String spec_ids = shopBean.getSpec_id();
                        spec_id += spec_ids + ",";
                    }
                    if ("0".equals(str)) {
                        isShelf = false;
                    }
                }
            }

            if (spec_id.equals("")) {

                showShortToast("没有选中任何商品哦!");

            } else {
                if (isShelf) {
                    spec_id = spec_id.toString().substring(0, spec_id.toString().lastIndexOf(","));
                    mPresenter.orderSel();
                }else{
                    showShortToast("有已下架商品");
                }
            }
        }
    }

    //隐藏删除
    private void hideDelete() {

        tvUpdate.setText("编辑");
        tvUpdate.setTag(ACTION_EDIT);
        checkboxAll.setChecked(true);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);
    }

    //显示完成按钮
    private void showDelete() {

        tvUpdate.setText("完成");
        tvUpdate.setTag(ACTION_COMPLETE);
        cbAll.setChecked(false);
        checkboxAll.setChecked(false);
        llDelete.setVisibility(View.VISIBLE);
        llCheckAll.setVisibility(View.GONE);
    }


    /**
     * @Title: showAlertMsgDialog @Description: TODO 弹出消息框 @param @param
     * msg @param @param title @param @param positive @param @param
     * negative @return_ticket void 返回类型 @throws
     */
    public void showAlertMsgDialog(String msg, String title, String positive, String negative) {
        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder().setTitle(title).setMsg(msg)
                .setPositiveButton(positive, new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        //全选的删除
                        doDelete();
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }


    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {

        cart_goods_id = "";
        select = "";

        if (groups == null) {
            return;
        }

        boolean checked = checkboxAll.isChecked();
        if (checked) {
            select = "1";
        } else {
            select = "0";
        }

        if (groups != null && groups.size() > 0) {

            for (int i = 0; i < groups.size(); i++) {

                ShopCarBean.ListBean listBean = groups.get(i);
                listBean.setChoosed(checkboxAll.isChecked());//这里出现过错误
                listBean.setSelect(select);
                listBean.setChoosed(checkboxAll.isChecked());
                List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();

                for (int j = 0; j < child.size(); j++) {

                    child.get(j).setChoosed(checkboxAll.isChecked());//这里出现过错误
                    child.get(j).setSelect(select);
                    String cart_goods_ids = child.get(j).getCart_goods_id();
                    cart_goods_id += cart_goods_ids + ",";
                }
            }
        }


        if (!TextUtils.isEmpty(cart_goods_id)) {

            cart_goods_id = cart_goods_id.toString().substring(0, cart_goods_id.toString().lastIndexOf(","));
            //设置购物车选中状态
            mPresenter.setCarGoods();
            adapter.notifyDataSetChanged();
            adapter.showTotalPrice();
            showGoodNum();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UserInfoEvent userInfoEvent) {

        if (userInfoEvent.getUserInfo() == null) {//未登录

            ll_login.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
            listView.setVisibility(View.GONE);
            empty_shopcart.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.GONE);

        } else {//已登录

            llDelete.setVisibility(View.GONE);
            llCheckAll.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            ll_login.setVisibility(View.GONE);

            initData();
        }
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {

        for (ShopCarBean.ListBean group : groups) {

            if (!group.isChoosed()) {

                return false;
            }
        }
        return true;
    }


    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {

        cart_goods_id = "";
        select = "";

        if (groups == null) {
            return;
        }

        List<ShopCarBean.ListBean> toBeDeleteGroups = new ArrayList<ShopCarBean.ListBean>(); //待删除的组元素

        for (int i = 0; i < groups.size(); i++) {

            ShopCarBean.ListBean listBean = groups.get(i);

            if (listBean.isChoosed()) {

                toBeDeleteGroups.add(listBean);
            }

            List<ShopCarBean.ListBean.ShopBean> toBeDeleteChilds = new ArrayList<>();//待删除的子元素
            List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();
            for (int j = 0; j < child.size(); j++) {

                if (child.get(j).isChoosed()) {

                    if (!TextUtils.isEmpty(child.get(j).getCart_goods_id())) {

                        ShopCarBean.ListBean.ShopBean shopBean = child.get(j);
                        String cart_goods_ids = shopBean.getCart_goods_id();
                        cart_goods_id += cart_goods_ids + ",";
                        toBeDeleteChilds.add(child.get(j));
                    }
                }
            }
        }

        if (cart_goods_id.equals(",") || cart_goods_id.equals("")) {

            showShortToast("没有选中商品哦~");

        } else {

            //删除购物车数据的接口
            cart_goods_id = cart_goods_id.toString().substring(0, cart_goods_id.toString().lastIndexOf(","));
            mPresenter.delGoods();
        }
    }


    /**
     * 重新计算是否清空了购物车
     */
    private void setCartNum() {

        if (groups == null) {
            return;
        }

        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            ShopCarBean.ListBean listBean = groups.get(i);
            listBean.setChoosed(checkboxAll.isChecked());

            List<ShopCarBean.ListBean.ShopBean> shop = listBean.getShop();

            for (ShopCarBean.ListBean.ShopBean shops : shop) {

                count++;
            }
        }

        //购物车已经清空
        if (count == 0) {

            clearCart();
        }
    }

    private void clearCart() {

        tvUpdate.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
        empty_shopcart.setVisibility(View.VISIBLE);//这里发生过错误

    }


    /**
     * 商铺的选择回调
     *
     * @param groupPosition 组元素的位置
     * @param isChecked     组元素的选中与否
     */
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {

        if (groups == null) {
            return;
        }

        cart_goods_id = "";
        select = "";
        if (isChecked) {
            select = "1";
        } else {
            select = "0";
        }

        ShopCarBean.ListBean listBean = groups.get(groupPosition);
        listBean.setChoosed(isChecked);
        listBean.setSelect(select);
        List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();

        for (int i = 0; i < child.size(); i++) {

            child.get(i).setChoosed(isChecked);
            child.get(i).setSelect(select);
            cart_goods_id += child.get(i).getCart_goods_id() + ",";
        }

        cart_goods_id = cart_goods_id.toString().substring(0, cart_goods_id.toString().lastIndexOf(","));
        //设置购物车选中状态
        mPresenter.setCarGoods();

        if (isCheckAll()) {

            checkboxAll.setChecked(true);//全选
            cbAll.setChecked(true);

        } else {

            checkboxAll.setChecked(false);//反选
            cbAll.setChecked(false);
        }

        adapter.notifyDataSetChanged();
        adapter.showTotalPrice();
        showGoodNum();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ShopCarRefreshEvent userInfoEvent) {

        if (groups != null && groups.size() > 0) {
            groups.clear();
        }

        mPresenter.getShopCars();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 每一个商品的选择回调
     *
     * @param groupPosition 组元素的位置
     * @param childPosition 子元素的位置
     * @param isChecked     子元素的选中与否
     */
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {

        if (groups == null) {
            return;
        }

        cart_goods_id = "";
        select = "";

        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        ShopCarBean.ListBean group = groups.get(groupPosition);
        List<ShopCarBean.ListBean.ShopBean> child = group.getShop();

        if (isChecked) {
            select = "1";
            child.get(childPosition).setChoosed(true);
        } else {
            select = "0";
            child.get(childPosition).setChoosed(false);
        }
        child.get(childPosition).setSelect(select);

        for (int i = 0; i < child.size(); i++) {

            //没有全部选中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        ShopCarBean.ListBean.ShopBean shopBean = child.get(childPosition);
        cart_goods_id = shopBean.getCart_goods_id();

        if (allChildSameState) {

            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
            group.setSelect(select);

        } else {

            group.setSelect("0");
            group.setChoosed(false);//否则一律视为未选中
        }

        if (isCheckAll()) {

            checkboxAll.setChecked(true);//全选
            cbAll.setChecked(true);

        } else {

            checkboxAll.setChecked(false);//反选
            cbAll.setChecked(false);
        }

        adapter.notifyDataSetChanged();
        //计算商品总价格
        adapter.showTotalPrice();
        showGoodNum();

        //设置购物车选中状态
        mPresenter.setCarGoods();
    }


    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ShopCarBean.ListBean.ShopBean good = (ShopCarBean.ListBean.ShopBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        count++;
        good.setCount(count);
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        adapter.showTotalPrice();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ShopCarBean.ListBean.ShopBean good = (ShopCarBean.ListBean.ShopBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        if (count == 1) {
            return;
        }
        count--;
        good.setCount(count);
        ((TextView) showCountView).setText("" + count);
        adapter.notifyDataSetChanged();
        adapter.showTotalPrice();
        showGoodNum();
    }

    /**
     * 修改单个商品的参数
     *
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isChecked
     */
    @Override
    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {

        ShopCarBean.ListBean.ShopBean good = (ShopCarBean.ListBean.ShopBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getCount();
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        adapter.showTotalPrice();
        showGoodNum();
    }

    /**
     * 删除单个商品的回调
     *
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void childDelete(final int groupPosition, final int childPosition) {

        AppPartnerAlertDialog alertDialog = new AppPartnerAlertDialog(getActivity()).builder()
                .setTitle("温馨提示").setMsg("确认要删除该商品吗?")
                .setPositiveButton("确定", new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        ShopCarBean.ListBean listBean = groups.get(groupPosition);
                        List<ShopCarBean.ListBean.ShopBean> child = listBean.getShop();
                        cart_goods_id = child.get(childPosition).getCart_goods_id();
                        mPresenter.delGoods();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }

    @Override
    public void groupEditor(int groupPosition) {

    }


    @Override
    public void onError(String s) {

        showShortToast(s);
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void SuccessShopCar(ShopCarBean data) {

        if (data != null) {

            groups = data.getList();

            if (groups != null && groups.size() > 0) {
                listView.setVisibility(View.VISIBLE);
                empty_shopcart.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.VISIBLE);
                if (tvUpdate.getTag().equals(ACTION_EDIT)) {
                    hideDelete();
                } else {
                    showDelete();
                }

                //设置适配器
                adapter = new ShopcatAdapter(groups, getActivity(), tvShopcartTotal, tvShopcartRabate, checkboxAll, cbAll);
                adapter.setCheckInterface(this);//关键步骤1：设置复选框的接口
                adapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
                adapter.setGroupEditorListener(this);//关键步骤3:监听组列表的编辑状态
                listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
                listView.setAdapter(adapter);

                showGoodNum();

                for (int i = 0; i < adapter.getGroupCount(); i++) {

                    listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
                }

//                //设置点击事件
                adapter.setOnItemViewListener(new ShopcatAdapter.OnItemClickViewListener() {
                    @Override
                    public void onItemClick(View view, int position, int groupPosition) {
                        Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                        List<ShopCarBean.ListBean.ShopBean> shop = groups.get(groupPosition).getShop();
                        intent.putExtra("shopBean", shop.get(position));
                        intent.putExtra("goodsId", shop.get(position).getGoods_id());
                        startActivity(intent);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


                adapter.setOnNumberChangeListener(new ShopcatAdapter.OnNumberChangeListener() {

                    @Override
                    public void addNumberClick(View view, int value, int position, int chilPosition) {

                        cart_goods_id = "";
                        select = "";
                        shop = groups.get(position).getShop();
                        shopBean = shop.get(chilPosition);
                        spec_id = shopBean.getSpec_id();
                        cart_goods_id = shopBean.getCart_goods_id();
                        goods_id = shopBean.getGoods_id();
                        shopBean.setChoosed(true);
                        shopBean.setSelect("1");
                        select = "1";
                        shopcheckAll(position);
                        String is_sale = shopBean.getIs_sale();
                        //商品是否下架
                        if (is_sale.equals("0")) {//下架
                            showShortToast("该商品已下架~");
                        } else {//有货
                            count = shopBean.getCount() + 1;
                            mPresenter.editCartCount();
                        }
                    }

                    @Override
                    public void subNumnerClick(View view, int value, int position, int chilPosition) {

                        cart_goods_id = "";
                        select = "";
                        shop = groups.get(position).getShop();
                        shopBean = shop.get(chilPosition);
                        spec_id = shopBean.getSpec_id();
                        cart_goods_id = shopBean.getCart_goods_id();
                        goods_id = shopBean.getGoods_id();
                        shopBean.setChoosed(true);
                        shopBean.setSelect("1");
                        select = "1";
                        shopcheckAll(position);
                        String is_sale = shopBean.getIs_sale();
                        //商品是否下架
                        if (is_sale.equals("0")) {//下架
                            showShortToast("该商品已下架~");
                        } else {//有货
                            count = shopBean.getCount() - 1;
                            mPresenter.editCartCount();
                        }
                    }
                });

            } else {
                listView.setVisibility(View.GONE);
                tvUpdate.setVisibility(View.GONE);
                llCheckAll.setVisibility(View.GONE);

                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                //购物车数据为空的页面
                empty_shopcart.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public String getCartId() {

        return cart_goods_id;
    }

    @Override
    public String getGoodId() {

        return goods_id;
    }

    @Override
    public String getSpecId() {

        return spec_id;
    }

    @Override
    public int getNum() {

        return count;
    }

    //商铺是否全选
    private void shopcheckAll(int groupPosition) {

        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        ShopCarBean.ListBean group = groups.get(groupPosition);
        List<ShopCarBean.ListBean.ShopBean> child = group.getShop();

        for (int i = 0; i < child.size(); i++) {

            //不选全中
            if (child.get(i).isChoosed() != true) {
                allChildSameState = false;
                break;
            }
            choosed = child.get(i).isChoosed();
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

        if (isCheckAll()) {

            checkboxAll.setChecked(true);//全选
            cbAll.setChecked(true);

        } else {

            checkboxAll.setChecked(false);//反选
            cbAll.setChecked(false);
        }

        adapter.notifyDataSetChanged();
        //计算商品总价格
        adapter.showTotalPrice();

        showGoodNum();
    }

    /**
     * 更改购物车数量成功的回调
     *
     * @param data
     */
    @Override
    public void SuccessEditInventory(GoodsInventoryCount data) {

        if (data != null) {

            int num = data.getNum();

            if (num < count) {

                showShortToast("该商品库存为" + num);

            } else {

                shopBean.setCount(num);
            }
            shopBean.setChoosed(true);
            shopBean.setSelect("1");
        }

        if (adapter != null) {

            adapter.notifyDataSetChanged();
            adapter.showTotalPrice();
            showGoodNum();
        }
    }

    //选中商品的个数
    private int showGoodNum() {

        int goodCount = 0;

        if (groups != null && groups.size() > 0) {

            for (int i = 0; i < groups.size(); i++) {

                ShopCarBean.ListBean listBean = groups.get(i);
                List<ShopCarBean.ListBean.ShopBean> shop = listBean.getShop();
                for (int j = 0; j < shop.size(); j++) {
                    ShopCarBean.ListBean.ShopBean shopBean = shop.get(j);
                    String select = shopBean.getSelect();
                    if (select.equals("1")) {//选中的商品
                        goodCount += shopBean.getCount();
                    }
                }
            }
        }

        btnCheckOut.setText("结算（" + goodCount + "）");
        return goodCount;
    }

    @Override
    public String getIsChoose() {

        return select;
    }

    /**
     * 删除购物车成功的回调
     *
     * @param message
     */
    @Override
    public void onSuccess(String message) {

        showShortToast("删除成功");

        tvUpdate.setText("编辑");
        tvUpdate.setTag(ACTION_EDIT);
        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);

        //重新获取数据
        mPresenter.getShopCars();

        showGoodNum();

        //重新调整购物车页面
        setCartNum();
    }

    /**
     * 设置状态改变成功的回调
     *
     * @param message
     */
    @Override
    public void onSuccessSet(String message) {

//        mPresenter.getShopCars();
    }

    @Override
    public String getSource() {
        return "2";
    }

    @Override
    public void SuccessOrder(OrderDetailBean data) {

        if (data != null) {

            Intent intent = new Intent(getActivity(), AffirmIndentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    @Override
    public String getSelect() {
        String selelct = "0";
        if (checkboxAll.isChecked()) {
            selelct = "1";
        } else {
            selelct = "0";
        }
        return selelct;
    }

    /***
     * 友盟统计
     */
    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("ShopCartPage"); //统计页面，"MainScreen"为页面名称，可自定义
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("ShopCartPage");
    }
}
