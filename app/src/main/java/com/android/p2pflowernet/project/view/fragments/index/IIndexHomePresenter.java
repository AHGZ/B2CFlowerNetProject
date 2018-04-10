package com.android.p2pflowernet.project.view.fragments.index;

import com.android.p2pflowernet.project.entity.IndexHomeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述: 首页逻辑处理类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 上午10:18
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 上午10:18
 * @修改备注：
 * @throws
 */
public class IIndexHomePresenter extends IPresenter<IIndexHomeView> {

    private final IndexHomeModel indexHomeModel;

    @Override
    protected void cancel() {

    }

    public IIndexHomePresenter() {

        indexHomeModel = new IndexHomeModel();
    }

    //获取首页数据
    public void getHomeData() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        if (getView() != null) {
            getView().showDialog();
        }

        indexHomeModel.getHomeData(new IModelImpl<ApiResponse<IndexHomeBean>, IndexHomeBean>() {
            @Override
            protected void onSuccess(IndexHomeBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccessIndex(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<IndexHomeBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccess(message);
                }
            }

            @Override
            protected void onFailure(String code, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onError(message);
                }
            }

            @Override
            protected void onSuccess() {
                if (getView() != null) {
                    getView().hideDialog();
                }
            }
        });
    }
}
