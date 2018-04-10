package com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.MerInfoBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.MerchantService;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * @描述: 商家信息逻辑层处理
 * @创建人：zhangpeisen
 * @创建时间：2017/11/27 上午10:10
 * @修改人：zhangpeisen
 * @修改时间：2017/11/27 上午10:10
 * @修改备注：
 * @throws
 */
public class IMerchantModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;


    public IMerchantModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @param id_number           身份证号码
     * @param is_legal            是否商家法人 int  0:不是  1:是
     * @param shop_name           店铺名称
     * @param license_name        营业执照名称
     * @param uscc                统一社会信用代码
     * @param shopownerphone      //客服电话
     * @param shop_province_id    店铺所在省
     * @param shop_city_id        店铺所在市
     * @param shop_district_id    店铺所在区县
     * @param area_name           店铺所在省市县
     * @param shop_detail_address 店铺详细地址
     * @param cfi_path            身份证正面路径
     * @param cbi_path            身份证反面路径
     * @param spi_path            店铺牌匾图片路径
     * @param sii_paths           店铺内部图片路径列表 多个使用英文分号;  进行分割，例如1;2;3
     * @param bli_path            营业执照图片路径
     * @param csli_path           餐饮服务图片路径
     * @param ti_path             商标照片路径
     * @throws
     * @描述:
     * @方法名:添加商家申请信息 realname 真实姓名
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:20
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:20
     * @修改备注
     */
    public void merchantaddinfo(String realname, String id_number, int is_legal, String shop_name, String
            license_name, String uscc, String shopownername, String shopownerphone, int shop_province_id, int shop_city_id, int shop_district_id,
                                String area_name, String shop_detail_address, String cfi_path, String cbi_path, String spi_path,
                                String sii_paths, String bli_path, String csli_path, String ti_path, final String first_cate_id, String second_cate_id, String third_cate_id,
                                final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("realname", realname);
        map.put("id_number", id_number);
        map.put("is_legal", String.valueOf(is_legal));
        map.put("shop_name", shop_name);
        map.put("license_name", license_name);
        map.put("uscc", uscc);
        map.put("manager_name", shopownername);
        map.put("manager_tel", shopownerphone);
        map.put("shop_province_id", String.valueOf(shop_province_id));
        map.put("shop_city_id", String.valueOf(shop_city_id));
        map.put("shop_district_id", String.valueOf(shop_district_id));
        map.put("area_name", area_name);
        map.put("shop_detail_address", shop_detail_address);
        map.put("cfi_path", cfi_path);
        map.put("cbi_path", cbi_path);
        map.put("spi_path", spi_path);
        map.put("sii_paths", sii_paths);
        map.put("bli_path", bli_path);
        map.put("csli_path", csli_path);
        map.put("ti_path", ti_path);
        map.put("first_cate_id", first_cate_id);
        map.put("second_cate_id", second_cate_id);
        map.put("third_cate_id", third_cate_id);

        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("realname", realname);
        param.put("id_number", id_number);
        param.put("is_legal", String.valueOf(is_legal));
        param.put("shop_name", shop_name);
        param.put("license_name", license_name);
        param.put("uscc", uscc);
        param.put("manager_name", shopownername);
        param.put("manager_tel", shopownerphone);
        param.put("shop_province_id", String.valueOf(shop_province_id));
        param.put("shop_city_id", String.valueOf(shop_city_id));
        param.put("shop_district_id", String.valueOf(shop_district_id));
        param.put("area_name", area_name);
        param.put("shop_detail_address", shop_detail_address);
        param.put("cfi_path", cfi_path);
        param.put("cbi_path", cbi_path);
        param.put("spi_path", spi_path);
        param.put("sii_paths", sii_paths);
        param.put("bli_path", bli_path);
        param.put("csli_path", csli_path);
        param.put("ti_path", ti_path);
        param.put("first_cate_id", first_cate_id);
        param.put("second_cate_id", second_cate_id);
        param.put("third_cate_id", third_cate_id);

        String sign = SignUtil.getInstance().getSign(map);
        MerchantService service = retrofit.create(MerchantService.class);
        Disposable subscribe = service.addmerchantapply(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    /**
     * @param type int 1-加入 2-退出，填空取全部
     * @throws
     * @描述:
     * @方法名: 获取商家申请信息
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/27 上午10:39
     * @修改人 zhangpeisen
     * @修改时间 2017/11/27 上午10:39
     * @修改备注
     */
    public void merchantapplyaplist(int type, IModelImpl<ApiResponse<String>, String> listener) {
//        HashMap<String, String> activity_course_order_layout = new HashMap<>();
//        // 生成签名
//        activity_course_order_layout.put("type", String.valueOf(type));
//        HashMap<String, String> param = new HashMap<>();
//        // 传参
//        param.put("type", String.valueOf(type));
//        String sign = SignUtil.getInstance().getSign(activity_course_order_layout);
//        MerchantService service = retrofit.create(MerchantService.class);
//        Disposable subscribe = service.merchantapplyaplist(sign, param)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ApiResponse<String>>() {
//                    @Override
//                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
//                        listener.onComplete(checkMobileBeanApiResponse);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        listener.onError("-1", throwable.getMessage());
//                    }
//                });
//        compositeDisposable.add(subscribe);
    }

    @Override
    public void cancel() {

        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    //获取商家信息
    public void getMerInfo(String merId, final IModelImpl<ApiResponse<MerInfoBean>, MerInfoBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("id", merId);
        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("id", merId);
        String sign = SignUtil.getInstance().getSign(map);

        MerchantService service = retrofit.create(MerchantService.class);
        Disposable subscribe = service.merchantapplyaplist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MerInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MerInfoBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //修改商家信息
    public void merchantUpdataInfo(String merId, String realname, String idnumber,
                                   int islegal, String shopname, String license_name,
                                   String uscc, String shopownername, String shopownerphone, int shopProvinceid,
                                   int shopCityid, int shopDistrictid, String areaname,
                                   String shopDetailAddress, String cfipath, String cbipath,
                                   String spipath, String siipaths, String blipath, String cslipath,
                                   String tipath, String first_cate_id, String second_cate_id, String third_cate_id, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        // 生成签名
        map.put("realname", realname);
        map.put("id", merId);
        map.put("id_number", idnumber);
        map.put("is_legal", String.valueOf(islegal));
        map.put("shop_name", shopname);
        map.put("license_name", license_name);
        map.put("uscc", uscc);
        map.put("manager_name", shopownername);
        map.put("manager_tel", shopownerphone);
        map.put("shop_province_id", String.valueOf(shopProvinceid));
        map.put("shop_city_id", String.valueOf(shopCityid));
        map.put("shop_district_id", String.valueOf(shopDistrictid));
        map.put("area_name", areaname);
        map.put("shop_detail_address", shopDetailAddress);
        map.put("cfi_path", cfipath);
        map.put("cbi_path", cbipath);
        map.put("spi_path", spipath);
        map.put("sii_paths", siipaths);
        map.put("bli_path", blipath);
        map.put("csli_path", cslipath);
        map.put("ti_path", tipath);
        map.put("first_cate_id", first_cate_id);
        map.put("second_cate_id", second_cate_id);
        map.put("third_cate_id", third_cate_id);

        HashMap<String, String> param = new HashMap<>();
        // 传参
        param.put("realname", realname);
        param.put("id", merId);
        param.put("id_number", idnumber);
        param.put("is_legal", String.valueOf(islegal));
        param.put("shop_name", shopname);
        param.put("license_name", license_name);
        param.put("uscc", uscc);
        param.put("manager_name", shopownername);
        param.put("manager_tel", shopownerphone);
        param.put("shop_province_id", String.valueOf(shopProvinceid));
        param.put("shop_city_id", String.valueOf(shopCityid));
        param.put("shop_district_id", String.valueOf(shopDistrictid));
        param.put("area_name", areaname);
        param.put("shop_detail_address", shopDetailAddress);
        param.put("cfi_path", cfipath);
        param.put("cbi_path", cbipath);
        param.put("spi_path", spipath);
        param.put("sii_paths", siipaths);
        param.put("bli_path", blipath);
        param.put("csli_path", cslipath);
        param.put("ti_path", tipath);
        param.put("first_cate_id", first_cate_id);
        param.put("second_cate_id", second_cate_id);
        param.put("third_cate_id", third_cate_id);


        String sign = SignUtil.getInstance().getSign(map);
        MerchantService service = retrofit.create(MerchantService.class);
        Disposable subscribe = service.updateMerchantapply(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<String>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<String> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }
}
