package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.uploadmerchant;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.GlideImageLoader;
import com.android.p2pflowernet.project.utils.RxImageLoader;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.ShapeLoadingDialog;
import com.android.p2pflowernet.project.view.customview.actionsheet.ActionSheet;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.IMerChantView;
import com.caimuhao.rxpicker.RxPicker;
import com.caimuhao.rxpicker.bean.ImageItem;
import com.ghnor.flora.Flora;
import com.ghnor.flora.callback.Callback;
import com.ghnor.flora.spec.calculation.Calculation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;


/**
 * @描述:上传商家资质页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/15 下午4:03
 * @修改人：zhangpeisen
 * @修改时间：2017/11/15 下午4:03
 * @修改备注：
 * @throws
 */
public class UploadMerChantFragment extends KFragment<IMerChantView, IUploadMerChantPrenter>
        implements IMerChantView, ActionSheet.ActionSheetListener {

    @BindView(R.id.im_back)
    // 返回按钮
            ImageView imBack;

    @BindView(R.id.submit_btn)
    // 提交按钮
            Button submitBtn;
    @BindView(R.id.storeloadimg_im)
    //门店牌匾照片
            ImageView storeloadimgIm;
    private String storeloadimg_impath = "";
    @BindView(R.id.storeuploadimg_im)
    //店内环境照片
            ImageView storeuploadimgIm;
    private String storeuploadimg_impath = "";
    @BindView(R.id.frontimg_im)
    // 身份证正面
            ImageView frontimgIm;
    private String frontimg_impath = "";
    @BindView(R.id.backimg_im)
    // 身份证反面
            ImageView backimgIm;
    private String backimg_impath = "";
    @BindView(R.id.yyzzloadimg_im)
    // 营业执照
            ImageView yyzzloadimgIm;
    private String yyzzloadimg_impath = "";
    @BindView(R.id.cyxkzloadimg_im)
    // 餐饮服务许可证
            ImageView cyxkzloadimgIm;
    private String cyxkzloadimg_impath = "";
    @BindView(R.id.brandloadimg_im)
    // 商标图
            ImageView brandloadimgIm;
    private String brandloadimg_impath = "";
    // 加载进度条
    private ShapeLoadingDialog shapeLoadingDialog;
    // 姓名
    String realname;
    // 身份证号
    String IdcardNum;
    //
    int Islegal;
    // 店铺名字
    String Shopname;
    // 营业执照名称
    String Licensename;
    // 统一社会信用代码
    String uscc;
    // 店铺负责人姓名
    String shopownername;
    // 店铺负责人电话
    String shopownerphone;
    // 省份id
    int ShopProvinceid;
    // 市 id
    int ShopCityid;
    // 店铺所在区县
    int ShopDistrictid;
    // 店铺所在省市县
    String Areaname;
    // 店铺详细地址
    String ShopDetailAddress;
    private String state = "";
    private String id = "";
    // 图片集合
    private List<File> fileList = new ArrayList<>();

    private String type = "";
    // 上传图片id区分
    private int ViewID = -1;
    private String first_cate_id = "";//店铺类别
    private String second_cate_id = "";//店铺类别
    private String third_cate_id = "";//店铺类别

    public static UploadMerChantFragment newIntence(String realname, String idcardNum, int islegal, String shopname,
                                                    String licensename, String uscc, String shopownername, String shopownerphone, int shopProvinceid,
                                                    int shopCityid, int shopDistrictid, String areaname,
                                                    String shopDetailAddress, String state, String id, String first_cate_id, String second_cate_id, String third_cate_id) {

        Bundle bundle = new Bundle();
        UploadMerChantFragment uploadMerChantFragment = new UploadMerChantFragment();
        bundle.putString("realname", realname);
        bundle.putString("idcardnum", idcardNum);
        bundle.putInt("islegal", islegal);
        bundle.putString("shopname", shopname);
        bundle.putString("licensename", licensename);
        bundle.putString("uscc", uscc);
        bundle.putString("shopownername", shopownername);
        bundle.putString("shopownerphone", shopownerphone);
        bundle.putInt("shopProvinceid", shopProvinceid);
        bundle.putInt("shopCityid", shopCityid);
        bundle.putInt("shopDistrictid", shopDistrictid);
        bundle.putString("areaname", areaname);
        bundle.putString("shopDetailAddress", shopDetailAddress);
        bundle.putString("shopDetailAddress", shopDetailAddress);
        bundle.putString("shopDetailAddress", shopDetailAddress);
        bundle.putString("state", state);
        bundle.putString("id", id);
        bundle.putString("first_cate_id", first_cate_id);
        bundle.putString("second_cate_id", second_cate_id);
        bundle.putString("third_cate_id", third_cate_id);
        uploadMerChantFragment.setArguments(bundle);
        return uploadMerChantFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        realname = getArguments().getString("realname");
        IdcardNum = getArguments().getString("idcardnum");
        Islegal = getArguments().getInt("islegal");
        Shopname = getArguments().getString("shopname");
        Licensename = getArguments().getString("licensename");
        uscc = getArguments().getString("uscc");
        shopownername = getArguments().getString("shopownername");
        shopownerphone = getArguments().getString("shopownerphone");
        ShopProvinceid = getArguments().getInt("shopProvinceid");
        ShopCityid = getArguments().getInt("shopCityid");
        ShopDistrictid = getArguments().getInt("shopDistrictid");
        Areaname = getArguments().getString("areaname");
        ShopDetailAddress = getArguments().getString("shopDetailAddress");
        state = getArguments().getString("state");
        first_cate_id = getArguments().getString("first_cate_id");
        second_cate_id = getArguments().getString("second_cate_id");
        third_cate_id = getArguments().getString("third_cate_id");
        id = getArguments().getString("id");
    }

    @Override
    public IUploadMerChantPrenter createPresenter() {
        return new IUploadMerChantPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.mine_uploadmerchantinfo_fragment;
    }

    @Override
    public void initData() {

        mPresenter.getMerInfo();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 2, false);
        //增加控件点击区域
        UIUtils.setTouchDelegate(imBack, 50);

        shapeLoadingDialog = new ShapeLoadingDialog.Builder(getActivity())
                .loadText("加载中...")
                .delay(5000)
                .build();

        //修改信息
        if (state.equals("2")) {

            initData();
        }
    }

    @OnClick({R.id.im_back, R.id.storeloadimg_im, R.id.storeuploadimg_im, R.id.frontimg_im, R.id.backimg_im,
            R.id.yyzzloadimg_im, R.id.cyxkzloadimg_im, R.id.brandloadimg_im, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                // 返回按钮
                removeFragment();
                break;
            case R.id.storeloadimg_im:
                // 门店牌匾照片
                ViewID = R.id.storeloadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(storeloadimgIm.getId(), "请选择门店牌匾照片");
                break;
            case R.id.storeuploadimg_im:
                // 店内环境照片
                ViewID = R.id.storeuploadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(storeuploadimgIm.getId(), "请选择店内环境照片");
                break;
            case R.id.frontimg_im:
                // 身份证正面照片
                ViewID = R.id.frontimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(frontimgIm.getId(), "请选择身份证正面照片");
                break;
            case R.id.backimg_im:
                // 身份证反面照片
                ViewID = R.id.backimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(backimgIm.getId(), "请选择身份证反面照片");
                break;
            case R.id.yyzzloadimg_im:
                // 营业执照照片
                ViewID = R.id.yyzzloadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(yyzzloadimgIm.getId(), "请选择餐饮服务许可证照片");
                break;
            case R.id.cyxkzloadimg_im:
                // 餐饮服务许可证照片
                ViewID = R.id.cyxkzloadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(cyxkzloadimgIm.getId(), "请选择餐饮服务许可证照片");
                break;
            case R.id.brandloadimg_im:
                // 商标图照片
                ViewID = R.id.brandloadimg_im;
                getActivity().setTheme(R.style.ActionSheetStyleiOS7);
                showActionSheet(brandloadimgIm.getId(), "请选择商标图照片");
                break;
            case R.id.submit_btn:

                if (state.equals("2")) {//修改数据

                    mPresenter.merchantUpdataInfo();

                } else {

                    mPresenter.merchantUploadInfo();
                }

                break;
        }
    }

    /**
     * 显示更换头像
     *
     * @param viewid
     */
    public void showActionSheet(int viewid, String titles) {

        ActionSheet.createBuilder(getActivity(), getFragmentManager(), viewid)
                .setCancelButtonTitle("取消")
                .setOtherButtonTitles(titles)
                .setCancelableOnTouchOutside(true).setListener(this).show();
    }


    @Override
    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

    }

    @Override
    public void onOtherButtonClick(ActionSheet actionSheet, int index, int viewId) {
        switch (viewId) {
            case R.id.storeloadimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 门店牌匾照片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.storeuploadimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 店内环境照片
                RxPicker.of().single(false).limit(1, 2).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        String pathloadimg = "";
                        StringBuilder pathStringBuilder = new StringBuilder();
                        if (imageItems.size() > 1) {
                            for (ImageItem imageItem : imageItems) {
                                pathStringBuilder.append(imageItem.getPath()).append(";");
                            }
                            pathloadimg = pathStringBuilder.toString().substring(0, pathStringBuilder.toString().lastIndexOf(";"));
                        } else {
                            pathloadimg = imageItems.get(0).getPath();
                        }
                        type = "3";
                        compressByUs(pathloadimg);

                    }
                });
                break;
            case R.id.frontimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 身份证正面照片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "2";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.backimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 身份证反面照片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "2";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.yyzzloadimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 营业执照
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.cyxkzloadimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 餐饮服务许可证照片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());

                    }
                });
                break;
            case R.id.brandloadimg_im:
                if (!fileList.isEmpty() && fileList.size() > 0) {
                    fileList.clear();
                }
                // 商标图照片
                RxPicker.of().single(true).start(getActivity()).subscribe(new Consumer<List<ImageItem>>() {

                    @Override
                    public void accept(@NonNull List<ImageItem> imageItems) throws Exception {
                        type = "3";
                        compressByUs(imageItems.get(0).getPath());
                    }
                });
                break;
        }
    }

    /**
     * @throws
     * @描述: 压缩图片
     * @创建人：zhangpeisen
     * @创建时间：2017/12/29 下午3:14
     * @修改人：zhangpeisen
     * @修改时间：2017/12/29 下午3:14
     * @修改备注：
     */
    private void compressByUs(String path) {
        Flora.with(this).calculation(new Calculation() {
            @Override
            public int calculateInSampleSize(int srcWidth, int srcHeight) {
                return super.calculateInSampleSize(srcWidth, srcHeight);
            }

            @Override
            public int calculateQuality(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
                return super.calculateQuality(srcWidth, srcHeight, targetWidth, targetHeight);
            }
        }).load(path).compress(new Callback<String>() {
            @Override
            public void callback(String compressResults) {
                fileList.add(new File(compressResults));
                mPresenter.uploadImg();
            }
        });
    }

    @Override
    public void setUploadImgSuccess(ImgDataBean data) {
        switch (ViewID) {

            case R.id.storeloadimg_im:
                // 门店牌匾照片
                storeloadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(storeloadimgIm, ApiUrlConstant.API_IMG_URL + storeloadimg_impath, storeloadimgIm.getWidth()
                        , storeloadimgIm.getHeight());
                break;
            case R.id.storeuploadimg_im:
                // 店内环境照片
                storeuploadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(storeuploadimgIm, ApiUrlConstant.API_IMG_URL + storeuploadimg_impath, storeuploadimgIm.getWidth(),
                        storeuploadimgIm.getHeight());
                break;
            case R.id.frontimg_im:
                // 身份证正面照片
                frontimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(frontimgIm, ApiUrlConstant.API_IMG_URL + frontimg_impath, frontimgIm.getWidth(),
                        frontimgIm.getHeight());
                break;
            case R.id.backimg_im:
                // 身份证反面照片
                backimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(backimgIm, ApiUrlConstant.API_IMG_URL + backimg_impath, backimgIm.getWidth(),
                        backimgIm.getHeight());
                break;
            case R.id.yyzzloadimg_im:
                // 营业执照
                yyzzloadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(yyzzloadimgIm, ApiUrlConstant.API_IMG_URL + yyzzloadimg_impath, yyzzloadimgIm.getWidth(),
                        yyzzloadimgIm.getHeight());
                break;
            case R.id.cyxkzloadimg_im:
                // 餐饮服务许可证照片
                cyxkzloadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(cyxkzloadimgIm, ApiUrlConstant.API_IMG_URL + cyxkzloadimg_impath, cyxkzloadimgIm.getWidth(),
                        cyxkzloadimgIm.getHeight());
                break;
            case R.id.brandloadimg_im:
                // 商标图照片
                brandloadimg_impath = data.getFile_path().get(0);
                new RxImageLoader().display(brandloadimgIm, ApiUrlConstant.API_IMG_URL + brandloadimg_impath, brandloadimgIm.getWidth(),
                        brandloadimgIm.getHeight());
                break;
        }
    }


    @Override
    public String getRealname() {
        return realname;
    }

    @Override
    public String getIdnumber() {
        return IdcardNum;
    }

    @Override
    public int getIslegal() {
        return Islegal;
    }

    @Override
    public boolean isCheck() {
        return false;
    }

    @Override
    public String getShopname() {
        return Shopname;
    }

    @Override
    public String getBusinessLicensename() {
        return Licensename;
    }


    @Override
    public String getUscc() {
        return uscc;
    }

    @Override
    public String getShopOwnerName() {
        return shopownername;
    }

    @Override
    public String getShopOwnerPhone() {
        return shopownerphone;
    }


    @Override
    public int getShopProvinceid() {
        return ShopProvinceid;
    }

    @Override
    public int getShopCityid() {

        return ShopCityid;
    }

    @Override
    public int getShopDistrictid() {

        return ShopDistrictid;
    }

    @Override
    public String getAreaname() {

        return Areaname;
    }

    @Override
    public String getShopDetailAddress() {

        return ShopDetailAddress;
    }

    @Override
    public String getCfipath() {

        return frontimg_impath;
    }

    @Override
    public String getCbipath() {

        return backimg_impath;
    }

    @Override
    public String getSpipath() {

        return storeloadimg_impath;
    }

    @Override
    public String getSiipaths() {

        return storeuploadimg_impath;
    }

    @Override
    public String getBlipath() {
        // 营业执照
        return yyzzloadimg_impath;
    }

    @Override
    public String getCslipath() {
        // 餐服许可证
        return cyxkzloadimg_impath;
    }

    @Override
    public String getTipath() {
        // 商标图
        return brandloadimg_impath;
    }

    @Override
    public List<File> getUserImgList() {
        return fileList;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void onError(String errorMsg) {

        showShortToast(errorMsg);
    }

    @Override
    public void setVestAddressSuccess(AllPlaceDataBean data) {

    }

    @Override
    public void setMerchantSuccess(String message) {

        showShortToast(message);
        removeFragment();
        getActivity().finish();
    }


    @Override
    public void showDialog() {

        shapeLoadingDialog.show();
    }

    @Override
    public void hideDialog() {
        if (shapeLoadingDialog.isShowing()) {
            shapeLoadingDialog.dismiss();
        }
    }

    @Override
    public String getMerId() {

        return id;
    }

    @Override
    public void onMerInfoSucc(MerInfoBean data) {

        if (data != null) {

            List<MerInfoBean.AplistBean> aplist = data.getAplist();
            if (aplist != null && aplist.size() > 0) {

                MerInfoBean.AplistBean aplistBean = aplist.get(0);

                //设置展示图片
                GlideImageLoader glideImageLoader = new GlideImageLoader();
                List<MerInfoBean.AplistBean.ImgsBean> imgs = aplistBean.getImgs();
                for (int i = 0; i < imgs.size(); i++) {

                    //6法人手持身份证正 7法人手持身份证反 8店铺牌匾 9店铺内部环境 10营业执照 11餐饮服务许可证 12商标
                    MerInfoBean.AplistBean.ImgsBean imgsBean = imgs.get(i);
                    String imgPath = ApiUrlConstant.API_IMG_URL + imgsBean.getFile_path();
                    if (imgsBean.getImg_type().equals("6")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, frontimgIm);
                        frontimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("7")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, backimgIm);
                        backimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("8")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, storeloadimgIm);
                        storeloadimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("9")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, storeuploadimgIm);
                        storeuploadimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("10")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, yyzzloadimgIm);
                        yyzzloadimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("11")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, cyxkzloadimgIm);
                        cyxkzloadimg_impath = imgsBean.getFile_path();
                    } else if (imgsBean.getImg_type().equals("12")) {
                        glideImageLoader.displayImage(getActivity(), imgPath, brandloadimgIm);
                        brandloadimg_impath = imgsBean.getFile_path();
                    }
                }
            }
        }
    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    @Override
    public void setMerchTypeSuccess(MerchTypeBean data) {

    }

    @Override
    public String getfirst_cate_id() {
        return first_cate_id;
    }

    @Override
    public String getsecond_cate_id() {
        return second_cate_id;
    }

    @Override
    public String getthird_cate_id() {
        return third_cate_id;
    }
}
