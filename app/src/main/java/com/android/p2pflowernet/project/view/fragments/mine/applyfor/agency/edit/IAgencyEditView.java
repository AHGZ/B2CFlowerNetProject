package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.edit;

import com.android.p2pflowernet.project.entity.AgentInfoBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;

import java.io.File;
import java.util.List;

/**
 * @描述: 填写代理人资质视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:12
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:12
 * @修改备注：
 * @throws
 */
public interface IAgencyEditView {
    // 真实姓名
    String getRealname();

    // 身份证号码
    String getIdnumber();

    // 身份证正面路径
    String getCfipath();

    // 身份证反面路径
    String getCbipath();

    // 工作场地照片路径
    String getSpipath();

    // 团队图片路径
    String getTeamImgPath();

    // 签名图片路径
    String getSignatureImgPath();

    String getApplyDate();


    String getUserId();

    String getState();

    String getOrderNum();


    List<File> getfileList();

    void onError(String message);

    void setUploadImgSuccess(ImgDataBean data);

    void showDialog();

    void hideDialog();

    void setAgencySuccess(String message);

    String getAgentId();

    void oSuccess(String message);

    void onGetAgent(AgentInfoBean data);

    String getType();
}
