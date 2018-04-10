package com.android.p2pflowernet.project.o2omain.fragment;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.GoPayBean;
import com.android.p2pflowernet.project.entity.MerchantBean;
import com.android.p2pflowernet.project.entity.O2oAddressBean;
import com.android.p2pflowernet.project.entity.O2oHomeBean;
import com.android.p2pflowernet.project.entity.O2oIndexBean;
import com.android.p2pflowernet.project.entity.O2oorderCommitBean;
import com.android.p2pflowernet.project.entity.OrderDetailsBean;
import com.android.p2pflowernet.project.entity.ShopEvaluationBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.O2oService;
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
 * author: zhangpeisen
 * created on: 2018/1/10 上午9:26
 * description:O2o外卖数据访问层
 */
public class O2oModel implements IModel {
    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public O2oModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述:店铺商品列表
     * @方法名:o2ogoodslist
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午9:31
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午9:31
     * @修改备注
     */
    public void o2ogoodslist(String merch_id, String longitude, String latitude,
                             final IModelImpl<ApiResponse<O2oIndexBean>, O2oIndexBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);
        param.put("longitude", longitude);
        param.put("latitude", latitude);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2ogoodslist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oIndexBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oIndexBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述: 去结算
     * @方法名:o2ogopay
     * @返回类型 void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/17 下午3:27
     * @修改人 zhangpeisen
     * @修改时间 2018/1/17 下午3:27
     * @修改备注
     */
    public void o2ogopay(String merch_id, String longitude, String latitude,
                         final IModelImpl<ApiResponse<GoPayBean>, GoPayBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        HashMap<String, String> param = new HashMap<>();

        param.put("merch_id", merch_id);
        param.put("longitude", longitude);
        param.put("latitude", latitude);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2ogopay(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<GoPayBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<GoPayBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述:获取商品规格
     * @方法名:o2ogoodsspec
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午9:54
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午9:54
     * @修改备注
     */
    public void o2ogoodsspec(String merch_id, String goods_id,
                             final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("goods_id", goods_id);
        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);
        param.put("goods_id", goods_id);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2ogoodsspec(sign, Constants.TOKEN, param)
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
     * @throws
     * @描述:获取商家店铺评价
     * @方法名:o2oevellist
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午9:56
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午9:56
     * @修改备注
     */
    public void o2oevellist(String merch_id, int page,
                            final IModelImpl<ApiResponse<ShopEvaluationBean>, ShopEvaluationBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id + "");
        map.put("page", page + "");

        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id + "");
        param.put("page", page + "");
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2oevellist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ShopEvaluationBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ShopEvaluationBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述: 获取商家信息
     * @方法名:o2omerchinfo
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午9:57
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午9:57
     * @修改备注
     */
    public void o2omerchinfo(String merch_id, final IModelImpl<ApiResponse<MerchantBean>, MerchantBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id + "");
        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id + "");
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2omerchinfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MerchantBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MerchantBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述: 商品明细及商品评价
     * @方法名:o2omerchgoodslist
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午9:58
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午9:58
     * @修改备注
     */
    public void o2omerchgoodslist(String merch_id, String goods_id, String page, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("goods_id", goods_id);
        map.put("page", page);
        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);
        param.put("goods_id", goods_id);
        param.put("page", page);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2omerchgoodslist(sign, Constants.TOKEN, param)
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
     * @throws
     * @描述: 确认订单
     * @方法名:o2ocreorder
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:00
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:00
     * @修改备注
     */
    public void o2ocreorder(String merch_id, String goods_list, String address_id,
                            String dinner_set_count, String reach_time, String latitude, String longitude, String is_self, String phone,String note
            , final IModelImpl<ApiResponse<O2oorderCommitBean>, O2oorderCommitBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("merch_id", merch_id);
        map.put("goods_list", goods_list);
        map.put("address_id", address_id);
        map.put("dinner_set_count", dinner_set_count);
        map.put("reach_time", reach_time);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("is_self", is_self);
        map.put("phone", phone);
        map.put("phone", phone);
        map.put("note", note);


        HashMap<String, String> param = new HashMap<>();
        param.put("merch_id", merch_id);
        param.put("goods_list", goods_list);
        param.put("address_id", address_id);
        param.put("dinner_set_count", dinner_set_count);
        param.put("reach_time", reach_time);
        param.put("latitude", latitude);
        param.put("longitude", longitude);
        param.put("is_self", is_self);
        param.put("phone", phone);
        param.put("note", note);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2ocreorder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oorderCommitBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oorderCommitBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述:订单详情
     * @方法名:o2oorderdetail
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:06
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:06
     * @修改备注
     */
    public void o2oorderdetail(String order_num, final IModelImpl<ApiResponse<OrderDetailsBean>, OrderDetailsBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //订单编号
        map.put("order_num", order_num);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", order_num);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2oorderdetail(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<OrderDetailsBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<OrderDetailsBean> orderDetailsBeanApiResponse) throws Exception {
                        listener.onComplete(orderDetailsBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }

    //退款订单详情
    public void getRefundOrder(String order_num,String refund_id, final IModelImpl<ApiResponse<OrderDetailsBean>, OrderDetailsBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        //订单编号
        map.put("order_num", order_num);
        map.put("refund_id", refund_id);

        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", order_num);
        param.put("refund_id", refund_id);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getrefundorder(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<OrderDetailsBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<OrderDetailsBean> orderDetailsBeanApiResponse) throws Exception {
                        listener.onComplete(orderDetailsBeanApiResponse);
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
     * @throws
     * @描述:添加收货地址
     * @方法名:adduseraddre
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:08
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:08
     * @修改备注
     */
    public void adduseraddre(String name, String sex, String telephone, String location, String site_name, String address,
                             String latitude, String longitude
            , final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("sex", sex);
        map.put("telephone", telephone);
        map.put("location", location);
        map.put("site_name", site_name);
        map.put("address", address);
        map.put("latitude", latitude);
        map.put("longitude", longitude);


        HashMap<String, String> param = new HashMap<>();
        param.put("name", name);
        param.put("sex", sex);
        param.put("telephone", telephone);
        param.put("location", location);
        param.put("site_name", site_name);
        param.put("address", address);
        param.put("latitude", latitude);
        param.put("longitude", longitude);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.adduseraddre(sign, Constants.TOKEN, param)
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
     * @throws
     * @描述: 收货地址列表
     * @方法名:getaddlist
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:14
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:14
     * @修改备注
     */
    public void getaddlist(String is_order, String longitude, String latitude,
                           final IModelImpl<ApiResponse<O2oAddressBean>, O2oAddressBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("is_order", is_order);
        map.put("longitude", longitude);
        map.put("latitude", latitude);
        HashMap<String, String> param = new HashMap<>();
        param.put("is_order", is_order);
        param.put("longitude", longitude);
        param.put("latitude", latitude);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getaddlist(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oAddressBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oAddressBean> checkMobileBeanApiResponse) throws Exception {
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
     * @throws
     * @描述:编辑收货地址
     * @方法名:upuseradd
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/10 上午10:16
     * @修改人 zhangpeisen
     * @修改时间 2018/1/10 上午10:16
     * @修改备注
     */
    public void upuseradd(String add_id, String name, String sex, String telephone, String location, String address,
                          String latitude, String longitude
            , final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("add_id", add_id);
        map.put("name", name);
        map.put("sex", sex);
        map.put("telephone", telephone);
        map.put("location", location);
        map.put("address", address);
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        HashMap<String, String> param = new HashMap<>();
        param.put("add_id", add_id);
        param.put("name", name);
        param.put("sex", sex);
        param.put("telephone", telephone);
        param.put("location", location);
        param.put("address", address);
        param.put("latitude", latitude);
        param.put("longitude", longitude);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.upuseradd(sign, Constants.TOKEN, param)
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
     * @throws
     * @描述:删除收货地址
     * @方法名:deluseradd
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/15 下午4:17
     * @修改人 zhangpeisen
     * @修改时间 2018/1/15 下午4:17
     * @修改备注
     */
    public void deluseradd(String add_id
            , final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("add_id", add_id);
        HashMap<String, String> param = new HashMap<>();
        param.put("add_id", add_id);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.deluseradd(sign, Constants.TOKEN, param)
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
     * @param state 1-全部 2-待付款 3-待使用 4-待评价5退款
     * @param page  分页页数
     * @throws
     * @描述:
     * @方法名:o2oselorder
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/13 下午2:19
     * @修改人 zhangpeisen
     * @修改时间 2018/1/13 下午2:19
     * @修改备注
     */
    public void o2oselorder(String state, String page, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("page", page);
        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("page", page);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2oselorder(sign, Constants.TOKEN, param)
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
     * @throws
     * @描述:取消接单
     * @方法名:o2ocancelorder
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2018/1/13 下午2:45
     * @修改人 zhangpeisen
     * @修改时间 2018/1/13 下午2:45
     * @修改备注
     */
    public void o2ocancelorder(String merch_id, final IModelImpl<ApiResponse<String>, String> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("order_num", merch_id);
        HashMap<String, String> param = new HashMap<>();
        param.put("order_num", merch_id);
        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.o2ocancelorder(sign, Constants.TOKEN, param)
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


    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }


    //获取首页数据
    public void getO2oHome(String state, int pages, String sreen, String latitude, String longitude,
                           final IModelImpl<ApiResponse<O2oHomeBean>, O2oHomeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("pages", String.valueOf(pages));
        map.put("sreen", sreen);
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("pages", String.valueOf(pages));
        param.put("sreen", sreen);
        param.put("latitude", latitude);
        param.put("longitude", longitude);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getO2oHome(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oHomeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oHomeBean> checkMobileBeanApiResponse) throws Exception {
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

    //删除收货地址
    public void deleteAdress(String adressId, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("add_id", adressId);

        HashMap<String, String> param = new HashMap<>();
        param.put("add_id", adressId);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.deleteAdress(sign, Constants.TOKEN, param)
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

    //搜索o2o首页数据
    public void getSearchO2oHome(String searchKey, String state, int pages, String sreen,
                                 String latitude, String longitude, final IModelImpl<ApiResponse<O2oHomeBean>,
            O2oHomeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("pages", String.valueOf(pages));
        map.put("sreen", sreen);
        map.put("name", searchKey);
        map.put("latitude", latitude);
        map.put("longitude", longitude);

        HashMap<String, String> param = new HashMap<>();
        param.put("state", state);
        param.put("pages", String.valueOf(pages));
        param.put("name", searchKey);
        param.put("sreen", sreen);
        param.put("latitude", latitude);
        param.put("longitude", longitude);

        String sign = SignUtil.getInstance().getSign(map);
        O2oService o2oService = retrofit.create(O2oService.class);
        Disposable subscribe = o2oService.getSearchO2oHome(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<O2oHomeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<O2oHomeBean> checkMobileBeanApiResponse) throws Exception {
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
