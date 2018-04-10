package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.uploadmerchant;

import android.text.TextUtils;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.mvp.IPresenter;
import com.android.p2pflowernet.project.utils.IDCardValidate;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.IMerChantView;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.IMerchantModel;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner.ImproveGuaranteeModel;
import com.android.p2pflowernet.project.view.fragments.mine.setting.personal.PersonalModel;
import com.rxy.netlib.http.ApiResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述:上传商家资质逻辑层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:13
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:13
 * @修改备注：
 * @throws
 */
public class IUploadMerChantPrenter extends IPresenter<IMerChantView> {
    IMerchantModel iMerchantModel;
    ImproveGuaranteeModel improveGuaranteeModel;
    PersonalModel personalModel;

    public IUploadMerChantPrenter() {
        iMerchantModel = new IMerchantModel();
        improveGuaranteeModel = new ImproveGuaranteeModel();
        personalModel = new PersonalModel();
    }

    /**
     * @throws
     * @描述:
     * @方法名: 添加商家申请信息
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 下午4:56
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 下午4:56
     * @修改备注
     */
    public void merchantUploadInfo() {

        // 姓名
        String realname = getView().getRealname();
        // 身份证
        String idnumber = getView().getIdnumber();
        // 是否商家法人  int 0-不是 1-是
        int islegal = getView().getIslegal();
        // 店铺名字
        String shopname = getView().getShopname();
        // 店铺牌匾图片路径
        String spipath = getView().getSpipath();
        // 店铺内部图片路径列表 多个使用英文分号;  进行分割，例如1;2;3
        String siipaths = getView().getSiipaths();
        // 身份证正面
        String cfipath = getView().getCfipath();
        // 身份证反面
        String cbipath = getView().getCbipath();
        // 营业执照图片路径
        String blipath = getView().getBlipath();
        // 餐饮服务图片路径
        String cslipath = getView().getCslipath();
        // 营业执照名称
        String license_name = getView().getBusinessLicensename();
        // 统一社会信用代码
        String uscc = getView().getUscc();
        // 负责人姓名
        String shopownernameStr = getView().getShopOwnerName();
        // 负责人电话
        String shopownerphoneStr = getView().getShopOwnerPhone();
        int shopProvinceid = getView().getShopProvinceid();
        int shopCityid = getView().getShopCityid();
        int shopDistrictid = getView().getShopDistrictid();
        String areaname = getView().getAreaname();
        String shopDetailAddress = getView().getShopDetailAddress();
        // 商标照片路径
        String tipath = getView().getTipath();
        if (TextUtils.isEmpty(realname)) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idnumber)) {
            getView().onError("请填写身份证号");
            return;
        }
        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idnumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }
        if (islegal == -1) {
            getView().onError("请选择是否是商家法人");
            return;
        }
        if (TextUtils.isEmpty(shopname)) {
            getView().onError("请填写店铺名称");
            return;
        }
        if (TextUtils.isEmpty(license_name)) {
            getView().onError("请填写营业执照名称");
            return;
        }
        if (TextUtils.isEmpty(uscc)) {
            getView().onError("请填写统一社会信用代码");
            return;
        }
        if (TextUtils.isEmpty(shopownernameStr)) {
            getView().onError("请填写负责人姓名");
            return;
        }
        if (TextUtils.isEmpty(shopownerphoneStr)) {
            getView().onError("请填写负责人电话");
            return;
        }
        if (shopProvinceid == -1) {
            return;
        }
        if (shopCityid == -1) {
            return;
        }
        if (shopDistrictid == -1) {
            return;
        }
        if (TextUtils.isEmpty(areaname)) {
            getView().onError("请选择区域地址");
            return;
        }
        if (TextUtils.isEmpty(shopDetailAddress)) {
            getView().onError("请填写详情地址");
            return;
        }
        if (TextUtils.isEmpty(spipath) && spipath.equals("")) {
            getView().onError("请上传店铺牌匾图片");
            return;
        }
        if (TextUtils.isEmpty(siipaths) && siipaths.equals("")) {
            getView().onError("请上传店铺内部图片");
            return;
        }
        if (TextUtils.isEmpty(cfipath) && cfipath.equals("")) {
            getView().onError("请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(cbipath) && cbipath.equals("")) {
            getView().onError("请上传身份证反面");
            return;
        }
        if (TextUtils.isEmpty(blipath) && blipath.equals("")) {
            getView().onError("请上传营业执照图片");
            return;
        }
        if (TextUtils.isEmpty(cslipath) && cslipath.equals("")) {
            getView().onError("请上传餐饮服务图片");
            return;
        }
        if (TextUtils.isEmpty(tipath) && tipath.equals("")) {
            getView().onError("请上传商标照片");
            return;
        }

        String first_cate_id = getView().getfirst_cate_id();
        if (TextUtils.isEmpty(first_cate_id) && first_cate_id.equals("")) {
            return;
        }


        String second_cate_id = getView().getsecond_cate_id();
        if (TextUtils.isEmpty(second_cate_id) && second_cate_id.equals("")) {
            return;
        }

        String third_cate_id = getView().getthird_cate_id();
        if (TextUtils.isEmpty(third_cate_id) && third_cate_id.equals("")) {
            return;
        }

        getView().showDialog();
        iMerchantModel.merchantaddinfo(realname, idnumber, islegal, shopname,
                license_name, uscc, shopownernameStr, shopownerphoneStr, shopProvinceid, shopCityid,
                shopDistrictid, areaname, shopDetailAddress, cfipath, cbipath, spipath, siipaths, blipath, cslipath,
                tipath, first_cate_id, second_cate_id, third_cate_id, new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().hideDialog();
                        getView().setMerchantSuccess(message);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {

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

    //获取三级区域列表
    public void getcitydata() {

        getView().showDialog();
        improveGuaranteeModel.getCityDatas(new IModelImpl<ApiResponse<AllPlaceDataBean>, AllPlaceDataBean>() {
            @Override
            protected void onSuccess(AllPlaceDataBean data, String message) {
                getView().hideDialog();
                getView().setVestAddressSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<AllPlaceDataBean>> data, String message) {

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


    //获取商家信息
    public void getMerInfo() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        String merId = getView().getMerId();
        iMerchantModel.getMerInfo(merId, new IModelImpl<ApiResponse<MerInfoBean>, MerInfoBean>() {
            @Override
            protected void onSuccess(MerInfoBean data, String message) {
                getView().hideDialog();
                getView().onMerInfoSucc(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MerInfoBean>> data, String message) {
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

    //修改商家数据
    public void merchantUpdataInfo() {

        // 姓名
        String realname = getView().getRealname();
        // 身份证
        String idnumber = getView().getIdnumber();
        // 是否商家法人  int 0-不是 1-是
        int islegal = getView().getIslegal();
        // 店铺名字
        String shopname = getView().getShopname();
        // 店铺牌匾图片路径
        String spipath = getView().getSpipath();
        // 店铺内部图片路径列表 多个使用英文分号;  进行分割，例如1;2;3
        String siipaths = getView().getSiipaths();
        // 身份证正面
        String cfipath = getView().getCfipath();
        // 身份证反面
        String cbipath = getView().getCbipath();
        // 营业执照图片路径
        String blipath = getView().getBlipath();
        // 餐饮服务图片路径
        String cslipath = getView().getCslipath();
        // 营业执照名称
        String license_name = getView().getBusinessLicensename();
        // 统一社会信用代码
        String uscc = getView().getUscc();
        // 负责人姓名
        String shopownernameStr = getView().getShopOwnerName();
        // 负责人电话
        String shopownerphoneStr = getView().getShopOwnerPhone();
        int shopProvinceid = getView().getShopProvinceid();
        int shopCityid = getView().getShopCityid();
        int shopDistrictid = getView().getShopDistrictid();
        String areaname = getView().getAreaname();
        String shopDetailAddress = getView().getShopDetailAddress();
        // 商标照片路径
        String tipath = getView().getTipath();
        if (TextUtils.isEmpty(realname)) {
            getView().onError("请填写姓名");
            return;
        }
        if (TextUtils.isEmpty(idnumber)) {
            getView().onError("请填写身份证号");
            return;
        }

        // 判断是否符合身份证号码的规范
        boolean checkIDCard = IDCardValidate.checkIDCard(idnumber);
        if (!checkIDCard) {//有效返回""，无效返回错误信息
            getView().onError("请填写正确的身份证号");
            return;
        }

        if (islegal == -1) {
            getView().onError("请选择是否是商家法人");
            return;
        }
        if (TextUtils.isEmpty(shopname)) {
            getView().onError("请填写店铺名称");
            return;
        }
        if (TextUtils.isEmpty(license_name)) {
            getView().onError("请填写营业执照名称");
            return;
        }
        if (TextUtils.isEmpty(uscc)) {
            getView().onError("请填写统一社会信用代码");
            return;
        }
        if (TextUtils.isEmpty(shopownernameStr)) {
            getView().onError("请填写负责人姓名");
            return;
        }
        if (TextUtils.isEmpty(shopownerphoneStr)) {
            getView().onError("请填写负责人电话");
            return;
        }
        if (shopProvinceid == -1) {
            return;
        }
        if (shopCityid == -1) {
            return;
        }
        if (shopDistrictid == -1) {
            return;
        }
        if (TextUtils.isEmpty(areaname)) {
            getView().onError("请选择区域地址");
            return;
        }
        if (TextUtils.isEmpty(shopDetailAddress)) {
            getView().onError("请填写详情地址");
            return;
        }
        if (TextUtils.isEmpty(spipath) && spipath.equals("")) {
            getView().onError("请上传店铺牌匾图片");
            return;
        }
        if (TextUtils.isEmpty(siipaths) && siipaths.equals("")) {
            getView().onError("请上传店铺内部图片");
            return;
        }
        if (TextUtils.isEmpty(cfipath) && cfipath.equals("")) {
            getView().onError("请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(cbipath) && cbipath.equals("")) {
            getView().onError("请上传身份证反面");
            return;
        }
        if (TextUtils.isEmpty(blipath) && blipath.equals("")) {
            getView().onError("请上传营业执照图片");
            return;
        }
        if (TextUtils.isEmpty(cslipath) && cslipath.equals("")) {
            getView().onError("请上传餐饮服务图片");
            return;
        }
        if (TextUtils.isEmpty(tipath) && tipath.equals("")) {
            getView().onError("请上传商标照片");
            return;
        }

        String merId = getView().getMerId();
        if (TextUtils.isEmpty(merId) && merId.equals("")) {
            return;
        }

        String first_cate_id = getView().getfirst_cate_id();
        if (TextUtils.isEmpty(first_cate_id) && first_cate_id.equals("")) {
            return;
        }


        String second_cate_id = getView().getsecond_cate_id();
        if (TextUtils.isEmpty(second_cate_id) && second_cate_id.equals("")) {
            return;
        }

        String third_cate_id = getView().getthird_cate_id();
        if (TextUtils.isEmpty(third_cate_id) && third_cate_id.equals("")) {
            return;
        }

        getView().showDialog();
        iMerchantModel.merchantUpdataInfo(merId, realname, idnumber, islegal, shopname,
                license_name, uscc, shopownernameStr, shopownerphoneStr, shopProvinceid, shopCityid,
                shopDistrictid, areaname, shopDetailAddress, cfipath, cbipath, spipath, siipaths,
                blipath, cslipath, tipath,first_cate_id, second_cate_id, third_cate_id, new IModelImpl<ApiResponse<String>, String>() {
                    @Override
                    protected void onSuccess(String data, String message) {
                        getView().hideDialog();
                        getView().setMerchantSuccess(message);
                    }

                    @Override
                    protected void onSuccess(ArrayList<ApiResponse<String>> data, String message) {
                        getView().hideDialog();
                        getView().onSuccess(message);
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

    //上传图片
    public void uploadImg() {

        getView().showDialog();
        List<File> fileList = getView().getUserImgList();
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

    @Override
    protected void cancel() {
        iMerchantModel.cancel();
        improveGuaranteeModel.cancel();
        personalModel.cancel();
    }

    //店铺类别
    public void getShopType() {

        if (!NetWorkUtils.isNetworkAvailable()) {
            getView().onError("网络信号弱,请稍后重试");
            return;
        }

        getView().showDialog();
        improveGuaranteeModel.getShopType(new IModelImpl<ApiResponse<MerchTypeBean>, MerchTypeBean>() {
            @Override
            protected void onSuccess(MerchTypeBean data, String message) {
                getView().hideDialog();
                getView().setMerchTypeSuccess(data);
            }

            @Override
            protected void onSuccess(ArrayList<ApiResponse<MerchTypeBean>> data, String message) {
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
}
