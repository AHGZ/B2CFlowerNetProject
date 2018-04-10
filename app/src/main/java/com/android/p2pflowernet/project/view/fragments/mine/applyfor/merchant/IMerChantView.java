package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant;

import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;

import java.io.File;
import java.util.List;

/**
 * @描述: 申请商家视图层
 * @创建人：zhangpeisen
 * @创建时间：2017/11/22 下午5:12
 * @修改人：zhangpeisen
 * @修改时间：2017/11/22 下午5:12
 * @修改备注：
 * @throws
 */
public interface IMerChantView {
    // 真实姓名
    String getRealname();

    // 身份证号码
    String getIdnumber();

    // 是否商家法人  int 0-不是 1-是
    int getIslegal();

    boolean isCheck();

    // 店铺名字
    String getShopname();

    // 营业执照名称
    String getBusinessLicensename();


    // 统一社会信用代码
    String getUscc();

    // 负责人姓名
    String getShopOwnerName();

    // 负责人电话
    String getShopOwnerPhone();

    // 省份id
    int getShopProvinceid();

    // 市 id
    int getShopCityid();

    // 店铺所在区县
    int getShopDistrictid();

    // 店铺所在省市县
    String getAreaname();

    // 店铺详细地址
    String getShopDetailAddress();

    // 身份证正面路径
    String getCfipath();

    // 身份证反面路径
    String getCbipath();

    // 店铺牌匾图片路径
    String getSpipath();

    // 店铺内部图片路径列表 多个使用英文分号;  进行分割，例如1;2;3
    String getSiipaths();

    // 营业执照图片路径
    String getBlipath();

    // 餐饮服务图片路径
    String getCslipath();

    // 商标照片路径
    String getTipath();

    List<File> getUserImgList();

    String getType();

    void setUploadImgSuccess(ImgDataBean data);

    void onError(String errorMsg);

    void setVestAddressSuccess(AllPlaceDataBean data);

    void setMerchantSuccess(String message);

    void showDialog();

    void hideDialog();

    String getMerId();

    void onMerInfoSucc(MerInfoBean data);

    void onSuccess(String message);

    void setMerchTypeSuccess(MerchTypeBean data);

    String getfirst_cate_id();

    String getsecond_cate_id();

    String getthird_cate_id();
}
