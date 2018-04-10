package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail;

import com.android.p2pflowernet.project.entity.CouponCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

public class ITakeOutGroupDetailPrenter extends IPresenter<ITakeOutGroupDetailView> {

    private TakeOutGroupModel takeOutGroupModel;

    public ITakeOutGroupDetailPrenter() {
        this.takeOutGroupModel = new TakeOutGroupModel();
    }

    @Override
    protected void cancel() {
        takeOutGroupModel.cancel();
    }

    //查看用户团购订单
    public void selCode(){
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        String order_num = getView().getOrderNumber();

        getView().showDialog();
        takeOutGroupModel.selCode(order_num, new IModelImpl<ApiResponse<CouponCodeBean>, CouponCodeBean>() {
            @Override
            protected void onSuccess(CouponCodeBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<CouponCodeBean>> data, String message) {
                getView().hideDialog();
            }

            @Override
            protected void onFailure(String code, String message) {
                getView().hideDialog();
                getView().onError(code);
            }

            @Override
            protected void onSuccess() {

            }
        });
    }
}
