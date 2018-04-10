package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AllPlaceDataBean;
import com.android.p2pflowernet.project.entity.MerchTypeBean;
import com.android.p2pflowernet.project.entity.ReplyBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.ApplyForPartnerService;
import com.android.p2pflowernet.project.service.CityDataService;
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
 * Created by caishen on 2017/11/20.
 * by--填写保单信息的数据层
 */

public class ImproveGuaranteeModel implements IModel {

    private final Retrofit mRetrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {

        if (compositeDisposable != null)
            compositeDisposable.dispose();
    }

    public ImproveGuaranteeModel() {
        mRetrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();

    }

    //获取城市列表数据
    public void getCityDatas(final IModelImpl<ApiResponse<AllPlaceDataBean>, AllPlaceDataBean> listener) {

        final HashMap<String, String> map = new HashMap<>();
        map.put("grts", Constants.RTS);
        String sign = SignUtil.getInstance().getSign(map);
        HashMap<String, String> param = new HashMap<>();
        param.put("grts", Constants.RTS);
        CityDataService service = mRetrofit.create(CityDataService.class);
        Disposable disposable = service.setAllCity(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AllPlaceDataBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AllPlaceDataBean> dataBean) throws Exception {
                        listener.onComplete(dataBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    //填写保单信息
    public void addInsuranceInfo(String realname, String address, String cardName, String idName,
                                 String getphone, String backPhoto, String frontPhoto, String maritalState, String cityId, String provinceIid, String countyId, String signUrl,
                                 String locationAddress, String userId, String recordId, final IModelImpl<ApiResponse<ReplyBean>, ReplyBean> listener) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("insurant_realname", realname);
        map.put("id_number", idName);
        map.put("phone", getphone);
        map.put("card_num", cardName);
        map.put("sign_url", signUrl);
        map.put("front_photo", frontPhoto);
        map.put("back_photo", backPhoto);
        map.put("marital_state", maritalState);
        map.put("domicile_address", address);
        map.put("record_id", recordId);
        map.put("location_address", provinceIid + cityId + countyId + locationAddress);

        final HashMap<String, String> param = new HashMap<>();
        param.put("insurant_realname", realname);
        param.put("id_number", idName);
        param.put("phone", getphone);
        param.put("card_num", cardName);
        param.put("sign_url", signUrl);
        param.put("front_photo", frontPhoto);
        param.put("back_photo", backPhoto);
        param.put("marital_state", maritalState);
        param.put("domicile_address", address);
        param.put("record_id", recordId);
        param.put("location_address", provinceIid + cityId + countyId + locationAddress);

        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService service = mRetrofit.create(ApplyForPartnerService.class);
        Disposable disposable = service.addInsurancesInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ReplyBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ReplyBean> dataBean) throws Exception {
                        listener.onComplete(dataBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    //获取店铺类别
    public void getShopType(final IModelImpl<ApiResponse<MerchTypeBean>, MerchTypeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("mts", Constants.RTS);
        HashMap<String, String> param = new HashMap<>();
        param.put("mts", Constants.RTS);
        String sign = SignUtil.getInstance().getSign(map);
        ApplyForPartnerService service = mRetrofit.create(ApplyForPartnerService.class);
        Disposable disposable = service.getShopType(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MerchTypeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MerchTypeBean> dataBean) throws Exception {
                        listener.onComplete(dataBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }
}
