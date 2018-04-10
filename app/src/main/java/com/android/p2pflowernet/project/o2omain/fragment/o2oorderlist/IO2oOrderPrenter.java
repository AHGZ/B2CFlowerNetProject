package com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.O2oGoodsInfoBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.o2omain.fragment.O2oModel;
import com.android.p2pflowernet.project.o2omain.fragment.o2oorderlist.o2oorderdetail.IO2oOrderDetaiModel;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: O2O点餐列表逻辑控制层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 下午8:00
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 下午8:00
 * @修改备注：
 * @throws
 */
public class IO2oOrderPrenter extends IPresenter<O2oOrderView> {


    private final IO2oOrderDetaiModel io2oOrderDetaiModel;
    private final O2oModel o2oModel;

    @Override
    protected void cancel() {

        io2oOrderDetaiModel.cancel();
        o2oModel.cancel();
    }

    public IO2oOrderPrenter() {

        io2oOrderDetaiModel = new IO2oOrderDetaiModel();
        o2oModel = new O2oModel();
    }

    //商品明细
    public void get_goods_info() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String merId = getView().getMerId();
        if (TextUtils.isEmpty(merId)) {
            return;
        }
        String goodId = getView().getGoodId();
        if (TextUtils.isEmpty(goodId)) {
            return;
        }

        int page = getView().getpage();

        //加载进度条
        getView().showDialog();
        io2oOrderDetaiModel.get_goods_info(merId, goodId, page, new IModelImpl<ApiResponse<O2oGoodsInfoBean>, O2oGoodsInfoBean>() {
            @Override
            protected void onSuccess(O2oGoodsInfoBean data, String message) {
                getView().hideDialog();
                getView().onSuccessData(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<O2oGoodsInfoBean>> data, String message) {
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
