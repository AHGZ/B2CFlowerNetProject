package com.android.p2pflowernet.project.view.fragments.goods.detail;

import com.android.p2pflowernet.project.entity.OrderDetailBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 商品详情主页面逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class IGoodsDetailHomePresenter extends IPresenter<IGoodsDetailHomeView> {

    private final GoodsDetailModel goodsDetailModel;

    @Override
    protected void cancel() {

    }

    public IGoodsDetailHomePresenter() {

        goodsDetailModel = new GoodsDetailModel();
    }

    //确认订单
    public void orderSel() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        String specId = getView().getSpecId();
        String count = getView().getCount();
        String source = getView().getSource();

        goodsDetailModel.orderSel(specId, specId, count, source, new IModelImpl<ApiResponse<OrderDetailBean>, OrderDetailBean>() {
            @Override
            protected void onSuccess(OrderDetailBean data, String message) {
                getView().hideDialog();
                getView().SuccessOrder(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<OrderDetailBean>> data, String message) {
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
}
