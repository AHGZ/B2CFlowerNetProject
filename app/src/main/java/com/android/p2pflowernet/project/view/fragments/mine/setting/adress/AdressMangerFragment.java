package com.android.p2pflowernet.project.view.fragments.mine.setting.adress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AdressMangerAdapter;
import com.android.p2pflowernet.project.entity.AdressMangerBean;
import com.android.p2pflowernet.project.event.AdressMangerEvent;
import com.android.p2pflowernet.project.event.RefreshEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.AppPartnerAlertDialog;
import com.android.p2pflowernet.project.view.fragments.affirm.address.AddAdressActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/23.
 * by--地址管理
 */

public class AdressMangerFragment extends KFragment<IAdressmangerView, IAdressMangerPrenter>
        implements NormalTopBar.normalTopClickListener, IAdressmangerView {
    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.add_btn)
    Button addBtn;
    private ShapeLoadingDialog shapeLoadingDialog;
    private AdressMangerAdapter mAdapter;
    private String adressId = "";
    private List<AdressMangerBean.UalBean> ual = new ArrayList<>();//获取到的地址列表数据
    private String name = "", address = "", city_id = "", location = "", province_id = "", telephone = "", district_id = "";
    private String state = "0";//1订单选择地址，0设置中打开地址管理

    @Override
    public IAdressMangerPrenter createPresenter() {

        return new IAdressMangerPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_adress_manger;
    }

    @Override
    public void initData() {

        mPresenter.getUserAddressList();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        state = arguments.getString("state");
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        Utils.setStatusBar(getActivity(), 0, false);
        normalTop.setTitleText("地址管理");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);
        normalTop.setTopClickListener(this);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //初始化数据
        initData();
    }

    public static KFragment newIntence(String state) {

        AdressMangerFragment adressMangerFragment = new AdressMangerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("state", state);
        adressMangerFragment.setArguments(bundle);
        return adressMangerFragment;
    }

    @OnClick(R.id.add_btn)
    public void onClick() {//添加收货地址

        Intent intent = new Intent(getActivity(), AddAdressActivity.class);
        intent.putExtra("isUpdate", "1");
        intent.putExtra("id", "");
        intent.putExtra("name", "");
        intent.putExtra("telephone", "");
        intent.putExtra("province_id", "");
        intent.putExtra("city_id", "");
        intent.putExtra("district_id", "");
        intent.putExtra("location", "");
        intent.putExtra("address", "");
        intent.putExtra("is_default", "");
        startActivity(intent);
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }

    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {

        if (shapeLoadingDialog != null) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshEvent event) {

        //刷新页面
        mPresenter.getUserAddressList();
    }

    @Override
    public void setSuccessAdressList(final AdressMangerBean data) {

        if (data != null) {

            ual = data.getUal();
            //设置适配器
            mAdapter = new AdressMangerAdapter(getActivity(), ual);
            listview.setAdapter(mAdapter);

            //设置删除，编辑的点击事件
            if (mAdapter != null) {

                //设置删除的点击事件
                mAdapter.setOnDeleteAdressListener(new AdressMangerAdapter.OnDeleteAdressListener() {
                    @Override
                    public void onDeleteAdressListener(View view, int position) {

                        adressId = ual.get(position).getId();
                        showAlertMsgDialog("是否删除当前地址？", "温馨提示", "确认", "取消");
                    }
                });

                //设置编辑的点击事件
                mAdapter.setOnEditAdressListener(new AdressMangerAdapter.OnEditAdressListener() {
                    @Override
                    public void onEditAdressListener(View view, int position) {

                        Intent intent = new Intent(getActivity(), AddAdressActivity.class);
                        intent.putExtra("isUpdate", "1");
                        intent.putExtra("id", ual.get(position).getId());
                        intent.putExtra("name", ual.get(position).getName());
                        intent.putExtra("telephone", ual.get(position).getTelephone());
                        intent.putExtra("province_id", ual.get(position).getProvince_id());
                        intent.putExtra("city_id", ual.get(position).getCity_id());
                        intent.putExtra("district_id", ual.get(position).getDistrict_id());
                        intent.putExtra("location", ual.get(position).getLocation());
                        intent.putExtra("address", ual.get(position).getAddress());
                        intent.putExtra("is_default", ual.get(position).getIs_default());
                        startActivity(intent);
                    }
                });


                mAdapter.setOnCheckedListener(new AdressMangerAdapter.OnCheckedListener() {

                    @Override
                    public void onCheckedListener(View view, int position) {
                        name = ual.get(position).getName();
                        address = ual.get(position).getAddress();
                        city_id = ual.get(position).getCity_id();
                        adressId = ual.get(position).getId();
                        location = ual.get(position).getLocation();
                        province_id = ual.get(position).getProvince_id();
                        telephone = ual.get(position).getTelephone();
                        district_id = ual.get(position).getDistrict_id();
                        mPresenter.isDefult();
                        ual.get(position).setIs_default("1");
                    }
                });

                //设置点击事件
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (state != null) {
                            if (state.equals("1")) {
                                removeFragment();
                                EventBus.getDefault().post(new AdressMangerEvent(data == null ? null : data, position));
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public String getAdressId() {

        return adressId;
    }

    @Override
    public void onSuccess(String message) {

        mPresenter.getUserAddressList();
        showShortToast(message);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String gettelephone() {
        return telephone;
    }

    @Override
    public String getprovinceId() {
        return province_id;
    }

    @Override
    public String getcityId() {
        return city_id;
    }

    @Override
    public String getdistrictId() {
        return district_id;
    }

    @Override
    public String getaddress() {
        return address;
    }

    @Override
    public String getisDefault() {
        return null;
    }

    @Override
    public String getLocation() {

        return location;
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
