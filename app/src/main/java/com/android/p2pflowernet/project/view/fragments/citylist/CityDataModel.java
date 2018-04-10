package com.android.p2pflowernet.project.view.fragments.citylist;

import com.android.p2pflowernet.project.mvp.IModel;

/**
 * author: zhangpeisen
 * created on: 2017/11/13 上午11:48
 * description:
 */
public class CityDataModel implements IModel {
//
//    private Retrofit mRetrofit;
//    private CompositeDisposable compositeDisposable;
//
//    public CityDataModel() {
//
//        mRetrofit = RetrofitUtils.getInstance();
//        compositeDisposable = new CompositeDisposable();
//    }
//
//    public void getCityDatas(final IModelImpl<ApiResponse<LocalCityBean>, LocalCityBean> listener) {
//
//        final HashMap<String, String> activity_course_order_layout = new HashMap<>();
//        activity_course_order_layout.put("rts", "0");
//        String sign = SignUtil.getInstance().getSign(activity_course_order_layout);
////        Constants.SIGN = sign;
//        CityDataService service = mRetrofit.create(CityDataService.class);
//        Disposable disposable = service.setCity(sign, activity_course_order_layout)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<ApiResponse<LocalCityBean>>() {
//                    @Override
//                    public void accept(@NonNull ApiResponse<LocalCityBean> formalUserInfoApiResponse) throws Exception {
//                        listener.onComplete(formalUserInfoApiResponse);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                        listener.onError("-1", throwable.getMessage());
//                    }
//                });
//
//        compositeDisposable.add(disposable);
//    }

    @Override
    public void cancel() {

//        if (compositeDisposable != null)
//            compositeDisposable.dispose();
    }
}
