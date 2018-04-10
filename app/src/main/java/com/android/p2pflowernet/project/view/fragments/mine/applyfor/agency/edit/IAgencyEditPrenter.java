package com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.edit;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AgentInfoBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.IAgentModel;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述:填写代理人资质逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:13
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:13
 * @修改备注：
 * @throws
 */
public class IAgencyEditPrenter extends IPresenter<IAgencyEditView> {
    private PersonalModel personalModel;
    IAgentModel iAgentModel;

    public IAgencyEditPrenter() {
        personalModel = new PersonalModel();
        iAgentModel = new IAgentModel();
    }

    //上传图片
    public void uploadImg() {

        getView().showDialog();
        String userId = getView().getUserId();
        List<File> fileList = getView().getfileList();
        if (fileList.isEmpty()) {
            getView().onError("没有图片上传");
            return;
        }

        String type = getView().getType();

        personalModel.mofityPhoto(type, fileList, new IModelImpl<ApiResponse<ImgDataBean>, ImgDataBean>() {
            @Override
            protected void onSuccess(ImgDataBean data, String message) {
                getView().hideDialog();
                getView().setUploadImgSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<ImgDataBean>> data, String message) {
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

    /**
     * @throws
     * @描述:
     * @方法名: 添加代理人
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/29 上午10:17
     * @修改人 zhangpeisen
     * @修改时间 2017/11/29 上午10:17
     * @修改备注
     */
    public void Agencyadd() {

        String realname = getView().getRealname();
        String idnumber = getView().getIdnumber();
        // 身份证正面
        String cfipath = getView().getCfipath();
        // 身份证反面
        String cbipath = getView().getCbipath();
        // 工作场地照片路径
        String spipath = getView().getSpipath();
        // 代理人申请签名图片
        String signatureImgPath = getView().getSignatureImgPath();
        // 团队照片路径
        String teamImgPath = getView().getTeamImgPath();
        String applyDate = getView().getApplyDate();
        String orderNum = getView().getOrderNum();
        if (TextUtils.isEmpty(realname) && realname.equals("")) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idnumber) && idnumber.equals("")) {
            getView().onError("请填写身份证号");
            return;
        }
//      // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idnumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }
        if (TextUtils.isEmpty(cfipath) && cfipath.equals("")) {
            getView().onError("请上传身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(cbipath) && cbipath.equals("")) {
            getView().onError("请上传身份证反面照片");
            return;
        }
        if (TextUtils.isEmpty(spipath) && spipath.equals("")) {
            getView().onError("请上传工作场地照片");
            return;
        }
        if (TextUtils.isEmpty(signatureImgPath) && signatureImgPath.equals("")) {
            getView().onError("请上传代理人申请签名图片");
            return;
        }
        if (TextUtils.isEmpty(teamImgPath) && teamImgPath.equals("")) {
            getView().onError("请上传团队照片");
            return;
        }
        if (TextUtils.isEmpty(applyDate) && applyDate.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(orderNum) && orderNum.equals("")) {
            return;
        }

        getView().showDialog();

        iAgentModel.agentadd(realname, idnumber, cfipath, cbipath, spipath, teamImgPath,
                signatureImgPath, applyDate, orderNum, new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().setAgencySuccess(message);
                        getView().hideDialog();

                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                        getView().hideDialog();

                    }

                    @Override
                    protected void onFailure(String code, String message) {
                        getView().hideDialog();

                    }

                    @Override
                    protected void onSuccess() {
                        getView().hideDialog();

                    }
                });
    }


    //获取代理人信息
    public void findAgency() {

        String agentId = getView().getAgentId();
        if (agentId.equals("") && TextUtils.isEmpty(agentId)) {
            return;
        }

        getView().showDialog();
        iAgentModel.agentalist(agentId, new IModelImpl<ApiResponse<AgentInfoBean>, AgentInfoBean>() {
            @Override
            protected void onSuccess(AgentInfoBean data, String message) {
                getView().hideDialog();
                getView().onGetAgent(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AgentInfoBean>> data, String message) {

                getView().hideDialog();
                getView().oSuccess(message);
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

    //修改代理信息
    public void uptaAgent() {

        String realname = getView().getRealname();
        String idnumber = getView().getIdnumber();
        // 身份证正面
        String cfipath = getView().getCfipath();
        // 身份证反面
        String cbipath = getView().getCbipath();
        // 工作场地照片路径
        String spipath = getView().getSpipath();
        // 代理人申请签名图片
        String signatureImgPath = getView().getSignatureImgPath();
        // 团队照片路径
        String teamImgPath = getView().getTeamImgPath();
        String applyDate = getView().getApplyDate();
        String agentId = getView().getAgentId();
        String orderNum = getView().getOrderNum();
        if (TextUtils.isEmpty(agentId)) {
            return;
        }

        if (TextUtils.isEmpty(realname) && realname.equals("")) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idnumber) && idnumber.equals("")) {
            getView().onError("请填写身份证号");
            return;
        }

        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idnumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }

        if (TextUtils.isEmpty(cfipath) && cfipath.equals("")) {
            getView().onError("请上传身份证正面照片");
            return;
        }
        if (TextUtils.isEmpty(cbipath) && cbipath.equals("")) {
            getView().onError("请上传身份证反面照片");
            return;
        }
        if (TextUtils.isEmpty(spipath) && spipath.equals("")) {
            getView().onError("请上传工作场地照片");
            return;
        }
        if (TextUtils.isEmpty(signatureImgPath) && signatureImgPath.equals("")) {
            getView().onError("请上传代理人申请签名图片");
            return;
        }
        if (TextUtils.isEmpty(teamImgPath) && teamImgPath.equals("")) {
            getView().onError("请上传团队照片");
            return;
        }
        if (TextUtils.isEmpty(applyDate) && applyDate.equals("")) {
            return;
        }
        if (TextUtils.isEmpty(orderNum) && orderNum.equals("")) {
            return;
        }
        getView().showDialog();

        iAgentModel.uptaAgent(agentId, realname, idnumber, cfipath, cbipath,
                signatureImgPath, teamImgPath, signatureImgPath, applyDate, orderNum, new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {

                        getView().hideDialog();
                        getView().setAgencySuccess(message);

                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {

                        getView().hideDialog();
                        getView().setAgencySuccess(message);
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
        personalModel.cancel();
        iAgentModel.cancel();
    }
}
