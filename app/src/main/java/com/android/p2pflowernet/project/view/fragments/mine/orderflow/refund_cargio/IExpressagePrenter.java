package com.android.p2pflowernet.project.view.fragments.mine.orderflow.refund_cargio;

import com.android.p2pflowernet.project.entity.ExpresListBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 获取快递公司列表逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/22 下午4:29
 * @修改人：zhangpeisen
 * @修改时间：2017/12/22 下午4:29
 * @修改备注：
 * @throws
 */
public class IExpressagePrenter extends IPresenter<IExpressageView> {

    private ExpressageModel expressageModel;


    public IExpressagePrenter() {

        expressageModel = new ExpressageModel();
    }

    /**
     * @throws
     * @描述:
     * @方法名: 获取快递公司列表
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/22 下午4:29
     * @修改人 zhangpeisen
     * @修改时间 2017/12/22 下午4:29
     * @修改备注
     */
    public void expressList() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        // 加载进度条
        getView().showDialog();
        expressageModel.expressList(new IModelImpl<ApiResponse<ExpresListBean>, ExpresListBean>() {
            @Override
            protected void onSuccess(ExpresListBean data, String message) {
                getView().hideDialog();
                getView().successExpress(data);

            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ExpresListBean>> data, String message) {
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



    @Override
    protected void cancel() {

        expressageModel.cancel();
    }

}
