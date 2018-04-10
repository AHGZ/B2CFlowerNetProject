package com.android.p2pflowernet.project.view.fragments.register.setpwd;


import com.android.p2pflowernet.project.entity.DataBean;
import com.android.p2pflowernet.project.entity.UserInfo;

/**
 * @描述:设置密码的视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/4 上午9:36
 * @修改人：zhangpeisen
 * @修改时间：2017/11/4 上午9:36
 * @修改备注：
 * @throws
 */
public interface SetPwdView {


    //获取手机号
    String getPhone();

    //获取密码
    String getPwd();

    //获取邀请码
    String getInvidcode();


    // 获取归属地
    int region();

    void setPwdSuccess(UserInfo userinfo);

    // 查询 归属地成功接口
    void setVestAddressSuccess(DataBean dataBean);

    void showDialog();

    void hideDialog();

    void onError(String errorMsg);

}
