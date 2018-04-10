package com.android.p2pflowernet.project.o2omain.fragment.storedetail;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 店铺详情
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 下午8:00
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 下午8:00
 * @修改备注：
 * @throws
 */
public class StoreDetailPrenter extends IPresenter<StoreDetailView> {
    O2oModel o2oModel;

    public StoreDetailPrenter() {
        o2oModel = new O2oModel();
    }

    /**
     * @throws
     * @描述: 店铺商品列表
     * @方法名:o2ogoodslist
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:23
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:23
     * @修改备注
     */
    public void o2ogoodslist() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merchId = getView().merchId();
        String longitude = getView().longitude();
        String latitude = getView().latitude();
        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }

//        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
//            getView().onError("抱歉,未能找到结果");
//            return;
//        }
//
//        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
//            getView().onError("抱歉,未能找到结果");
//            return;
//        }

        getView().showDialog();
        o2oModel.o2ogoodslist(merchId, longitude, latitude, new IModelImpl<ApiResponse<O2oIndexBean>, O2oIndexBean>() {
            @Override
            protected void onSuccess(O2oIndexBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oIndexBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {
                getView().hideDialog();
            }
        });
    }


    //获取店铺商家信息
    public void getShopMerchantInfo(String merch_id) {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        getView().showDialog();
        o2oModel.o2omerchinfo(merch_id, new IModelImpl<ApiResponse<MerchantBean>, MerchantBean>() {
            @Override
            protected void onSuccess(MerchantBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MerchantBean>> data, String message) {
                getView().hideDialog();
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
        o2oModel.cancel();
    }

    //去结算
    public void goPay() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String merchId = getView().merchId();
        String longitude = getView().longitude();
        String latitude = getView().latitude();
        if (TextUtils.isEmpty(merchId) && merchId.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(longitude) && longitude.equals("")) {
            getView().onError("抱歉,未能找到结果");
            return;
        }
        if (TextUtils.isEmpty(latitude) && latitude.equals("")) {
            getView().onError("抱歉,未能找到结果");
            return;
        }
        getView().showDialog();
        o2oModel.o2ogopay(merchId, longitude, latitude, new IModelImpl<ApiResponse<GoPayBean>, GoPayBean>() {
            @Override
            protected void onSuccess(GoPayBean data, String message) {
                getView().hideDialog();
                getView().onSuccessGoPay(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<GoPayBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(message);
            }

            @Override
            protected void onSuccess() {

                getView().hideDialog();
            }
        });
    }
}
