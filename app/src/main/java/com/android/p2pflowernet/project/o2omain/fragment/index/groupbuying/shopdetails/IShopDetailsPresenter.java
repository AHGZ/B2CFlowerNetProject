package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GroupBuyingBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by heguozhong on 2018/1/4/004.
 * 团购商品详情逻辑层
 */

public class IShopDetailsPresenter extends IPresenter<IShopDetailsView> {
    private final IShopDetailsModel iShopDetailsModel;

    public IShopDetailsPresenter() {
        iShopDetailsModel = new IShopDetailsModel();
    }

    //查看团购商品详情方法
    public void getGroupBuyingDetail(int merch_id,int group_id) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        // 收货地址的纬度
        String latitude = getView().latitude();
        // 收货地址的经度
        String longitude = getView().longitude();
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            return;
        }
        getView().showDialog();
        iShopDetailsModel.getGroupBuyingDetail(merch_id,group_id,latitude,longitude,new IModelImpl<ApiResponse<GroupBuyingBean>, GroupBuyingBean>() {
            @Override
            protected void onSuccess(GroupBuyingBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GroupBuyingBean>> data, String message) {
                getView().hideDialog();
                getView().onSuccess(message);
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }

    @Override
    protected void cancel() {
        iShopDetailsModel.cancel();
    }
}