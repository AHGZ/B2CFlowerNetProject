package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.LocationListAdapter;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.event.O2oAddressEvent;
import com.android.p2pflowernet.project.event.O2oHomeAddressEvent;
import com.android.p2pflowernet.project.event.O2oTakeOutAddressEvent;
import com.android.p2pflowernet.project.event.UpdateAddressEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.confirmaddress.ConfirmAddressActivity;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2018/1/3.
 * by--选择收货地址
 */

public class LocationO2oFragment extends KFragment<ILocationO2oView, ILocationO2oPrenter>
        implements NormalTopBar.normalTopClickListener, ILocationO2oView {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.ll_location_name)
    LinearLayout llLocationName;
    @BindView(R.id.tosearch_tv)
    EditText tosearchTv;
    @BindView(R.id.tv_accon_name)
    TextView tvAcconName;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.o2o_location_linearLayout)
    LinearLayout mLinearLayout;
    @BindView(R.id.o2o_location_tv)
    TextView mTextView;

    // 加载view
    private ShapeLoadingDialog shapeLoadingDialog;

    private List<O2oAddressBean.ListsBean> bean;
    private List<O2oAddressBean.ListsBean> dataBean;
    //地址listView Adapter
    private LocationListAdapter mAdapter;
    private String adressId = "";//收货地址id
    private String flag;//标识 从哪个页面跳转过来 1-o2o首页 2-o2o外卖 3-确认订单主页面

    @Override
    public ILocationO2oPrenter createPresenter() {
        return new ILocationO2oPrenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = getArguments().getString("flag");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_location_o2o;
    }

    @Override
    public void initData() {

        mPresenter.getAddress();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Eyes.setStatusBarColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.coloro2o));
        normalTop.setTitleText("选择收货地址");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setRightText("添加地址");
        normalTop.setRightTextColor(Color.WHITE);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        initData();
    }

    public static LocationO2oFragment newIntence(String flag) {

        LocationO2oFragment locationO2oFragment = new LocationO2oFragment();
        Bundle bundle = new Bundle();
        bundle.putString("flag",flag);
        locationO2oFragment.setArguments(bundle);
        return locationO2oFragment;
    }


    @OnClick({R.id.o2o_location_linearLayout,R.id.ll_location})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.o2o_location_linearLayout://展开收起按钮监听
                if (mAdapter.getCount() == 3) {
                    dataBean.clear();
                    dataBean.addAll(bean);
                    mAdapter.notifyDataSetChanged();
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mTextView.setText("收起");
                }else{
                    dataBean.clear();
                    dataBean.add(bean.get(0));
                    dataBean.add(bean.get(1));
                    dataBean.add(bean.get(2));
                    mAdapter.notifyDataSetChanged();
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mTextView.setText("展开");
                }
                break;
            case R.id.ll_location://跳转确认收货地址页面
                Intent intent = new Intent(getActivity(), ConfirmAddressActivity.class);
                intent.putExtra("flag","2");
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

        Intent intent = new Intent(getActivity(), AddLocationActivity.class);
        intent.putExtra("tag", "0");//0-添加收货地址 1-编辑收货地址
        startActivity(intent);

    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public String IsOrder() {
        return "1";
    }

    @Override
    public String longitude() {
        return String.valueOf(SPUtils.get(getActivity(), "longitude", ""));
    }

    @Override
    public String latitude() {
        return String.valueOf(SPUtils.get(getActivity(), "latitude", ""));
    }

    @Override
    public void onError(String message) {
        showShortToast(message);
    }

    @Override
    public void onSuccess(O2oAddressBean data) {
        if (null != data) {
            bean = data.getLists();
            if (null != bean && bean.size() > 0) {
                //设置适配器
                if (bean.size() >= 3) {

                    dataBean = new ArrayList<O2oAddressBean.ListsBean>();
                    dataBean.add(bean.get(0));
                    dataBean.add(bean.get(1));
                    dataBean.add(bean.get(2));
                    mLinearLayout.setVisibility(View.VISIBLE);
                    mTextView.setText("展开全部");

                } else {

                    dataBean = bean;
                    mLinearLayout.setVisibility(View.GONE);
                }

                mAdapter = new LocationListAdapter(getActivity(), dataBean);
                listview.setAdapter(mAdapter);

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        O2oAddressBean.ListsBean listsBean = dataBean.get(position);
                        if (listsBean == null) {
                            return;
                        }

                        //发送消息
                        if ("1".equals(flag)) {
                            //o2o主页面
                            EventBus.getDefault().post(new O2oHomeAddressEvent(listsBean));
                        }else if("2".equals(flag)){
                            //o2o外卖页面
                            EventBus.getDefault().post(new O2oTakeOutAddressEvent(listsBean));
                        }else if("3".equals(flag)){
                            //确认订单页面
                            EventBus.getDefault().post(new UpdateAddressEvent(listsBean));
                        }
                        removeFragment();
                    }
                });


                //设置删除的点击事件
                mAdapter.setOnDeleteAdressListener(new LocationListAdapter.OnDeleteAdressListener() {
                    @Override
                    public void onDeleteAdressListener(View view, int position) {

                        adressId = bean.get(position).getId();
                        showAlertMsgDialog("是否删除当前地址？", "温馨提示", "确认", "取消");
                    }
                });

                //设置编辑的点击事件
                mAdapter.setOnEditAdressListener(new LocationListAdapter.OnEditAdressListener() {
                    @Override
                    public void onEditAdressListener(View view, int position) {

                        Intent intent = new Intent(getActivity(), AddLocationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", bean.get(position));
                        intent.putExtra("tag", "1");//0-添加收货地址 1-编辑收货地址
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public void showDialog() {
        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public String getAdressId() {
        return adressId;
    }

    @Override
    public void onSuccessMessage(String message) {

        showShortToast("删除成功");
        mPresenter.getAddress();

    }

    //地址添加成功后更新
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(O2oAddressEvent event) {
        mPresenter.getAddress();
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

                        mPresenter.deleteAdress();
                    }
                })
                .setNegativeButton(negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        alertDialog.show();
    }
}
