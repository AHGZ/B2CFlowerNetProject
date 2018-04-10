package com.android.p2pflowernet.project.view.fragments.register.setpwd;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.DataBean;
import com.android.p2pflowernet.project.entity.UserInfo;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.MD5Utils;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.rxy.netlib.http.ApiResponse;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @描述: 设置密码的控制层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/4 上午9:36
 * @修改人：zhangpeisen
 * @修改时间：2017/11/4 上午9:36
 * @修改备注：
 * @throws
 */
public class SetPwdPrenter extends IPresenter<SetPwdView> {

    CityDataModel cityDataModel;
    RegisterModel registerModel;

    public SetPwdPrenter() {
        cityDataModel = new CityDataModel();
        registerModel = new RegisterModel();
    }

    public void register() {
        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }
        // 手机号
        String phone = getView().getPhone();
        // 密码
        String pwd = getView().getPwd();
        // 邀请码
        String invidcode = getView().getInvidcode();
        // 城市id或区id
        int region = getView().region();
        if (region == 0) {
            return;
        }

        if (pwd != null) {

            //必须包含小写字母，数字，可以是字母数字下划线组成并且长度是6到20
//            String IDCardRegex = "(\"^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$\")";
            Pattern PwdCardRegex = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9])[a-zA-Z0-9_]{6,20}$");
            boolean matches = PwdCardRegex.matcher(pwd).matches();
            if (matches == false) {

                getView().onError("输入6-20位包含字母和数字组合");
                return;
            }
        }

        if (!check(phone, pwd))
            return;
        getView().showDialog();
        registerModel.register(phone, MD5Utils.MD5To32(pwd), invidcode, region, new IModelImpl<ApiResponse<UserInfo>, UserInfo>() {
            @Override
            protected void onSuccess(UserInfo userinfo, String message) {
                getView().hideDialog();
                getView().setPwdSuccess(userinfo);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<UserInfo>> data, String message) {

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

    // 归属地（省-市）(ty)
    public void getcitydata() {

        getView().showDialog();
        cityDataModel.getCityDatas(new IModelImpl<ApiResponse<DataBean>, DataBean>() {
            @Override
            protected void onSuccess(DataBean data, String message) {
                getView().hideDialog();
                getView().setVestAddressSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<DataBean>> data, String message) {

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

    private boolean check(String phone, String Pwd) {
        if (TextUtils.isEmpty(Pwd)) {
            getView().onError("请设置密码");
            return false;
        }
        if (TextUtils.isEmpty(phone)) {
            getView().onError("请填写手机号");
            return false;
        }
        return true;
    }

    @Override
    protected void cancel() {
        cityDataModel.cancel();
        registerModel.cancel();
    }
}
