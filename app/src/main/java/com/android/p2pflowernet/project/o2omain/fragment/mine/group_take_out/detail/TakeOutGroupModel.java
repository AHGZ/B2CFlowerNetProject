package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.detail;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.CouponCodeBean;
import com.android.p2pflowernet.project.entity.TakeOutGroupBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.O2oService;
import com.android.p2pflowernet.project.utils.QRCodeUtil;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by caishen on 2018/1/6.
 * by--
 */

public class TakeOutGroupModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public TakeOutGroupModel() {
        this.retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }


    public static List<TakeOutGroupBean> getList(FragmentActivity activity) {

        List<TakeOutGroupBean> data = new ArrayList<>();
        //设置数据
        for (int i = 0; i < 5; i++) {

            Bitmap bitmap = QRCodeUtil.creatBarcode(activity, String.valueOf(i + 123456789), 600, 240, false);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();

            TakeOutGroupBean takeOutGroupBean = new TakeOutGroupBean();
            takeOutGroupBean.setPrice(String.valueOf(i * 10));
            takeOutGroupBean.setNum(String.valueOf(i));
            takeOutGroupBean.setHuafan("20" + i);
            takeOutGroupBean.setBytes(bytes);
            takeOutGroupBean.setFilepath("http://p1.ifengimg.com/a/2017_49/9cb090873b8a2ec_size73_w429_h600.jpg");
            takeOutGroupBean.setDate("2018-01-06 16:38");
            takeOutGroupBean.setOrdernum(String.valueOf(i + 123456789));
            takeOutGroupBean.setTitle("红烧段钊斌" + i);
            takeOutGroupBean.setNumber("劵码" + i);
            takeOutGroupBean.setBitmap(bitmap);
            data.add(takeOutGroupBean);
        }
        return data;
    }

    //查看用户团购码
    public void selCode(String order_num, IModelImpl<ApiResponse<CouponCodeBean>,CouponCodeBean> listener){
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("order_num",order_num);

        HashMap<String,String> param = new HashMap<>();
        param.put("order_num",order_num);

        String sign = SignUtil.getInstance().getSign(hashMap);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable disposable = o2oService.selCode(order_num, Constants.TOKEN,param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CouponCodeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CouponCodeBean> checkMobileBeanApiResponse) throws Exception {
                        listener.onComplete(checkMobileBeanApiResponse);
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
