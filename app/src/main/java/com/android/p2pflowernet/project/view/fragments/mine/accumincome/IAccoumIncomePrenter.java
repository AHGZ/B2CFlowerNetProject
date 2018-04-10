package com.android.p2pflowernet.project.view.fragments.mine.accumincome;

import com.android.p2pflowernet.project.entity.AcmIncomBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;

/**
 * @描述:累计收益逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:13
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:13
 * @修改备注：
 * @throws
 */
public class IAccoumIncomePrenter extends IPresenter<IAccoumIncomeView> {
    private PersonalModel personalModel;

    public IAccoumIncomePrenter() {
        personalModel = new PersonalModel();
    }

    /**
     * @throws
     * @描述:累计收益
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/25 上午11:42
     * @修改人 zhangpeisen
     * @修改时间 2017/12/25 上午11:42
     * @修改备注
     */
    public void userincome() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        //加载进度条
        getView().showDialog();
        personalModel.userincome(new IModelImpl<ApiResponse<AcmIncomBean>, AcmIncomBean>() {
            @Override
            protected void onSuccess(AcmIncomBean data, String message) {
                getView().hideDialog();
                getView().onSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AcmIncomBean>> data, String message) {
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
        personalModel.cancel();
    }
}
