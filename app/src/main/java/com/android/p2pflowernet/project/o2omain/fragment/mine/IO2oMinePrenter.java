package com.android.p2pflowernet.project.o2omain.fragment.mine;

import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * Created by caishen on 2018/1/2.
 * by--
 */

public class IO2oMinePrenter extends IPresenter<IO2oMineView> {
    private PersonalModel personalModel;

    public IO2oMinePrenter() {

        personalModel = new PersonalModel();
    }

    /**
     * @throws
     * @描述:我的
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/25 上午11:42
     * @修改人 zhangpeisen
     * @修改时间 2017/12/25 上午11:42
     * @修改备注
     */
    public void usermy() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        //加载进度条
        if (getView() !=null) {
            getView().showDialog();
        }
        personalModel.usermy(new IModelImpl<ApiResponse<MineMyBean>, MineMyBean>() {
            @Override
            protected void onSuccess(MineMyBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().setMineMyInfo(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MineMyBean>> data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
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

            }
        });
    }

    @Override
    protected void cancel() {
        personalModel.cancel();
    }

    //初始化分享详情
    public void getShareCode() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            if (getView() != null) {
                getView().onError("网络信号弱,请稍后重试");
            }
            return;
        }

        // 加载进度条
        if (getView() != null) {
            getView().showDialog();
        }
        personalModel.getShareCode(new IModelImpl<ApiResponse<ShareCodeBean>, ShareCodeBean>() {
            @Override
            protected void onSuccess(ShareCodeBean data, String message) {
                if (getView() != null) {
                    getView().hideDialog();
                    getView().onSuccessShare(data);
                }
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ShareCodeBean>> data, String message) {
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

            }
        });
    }
}
