package com.android.p2pflowernet.project.view.fragments.mine.wallet;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.BillAllBean;
import com.android.p2pflowernet.project.entity.UserAcountBean;
import com.android.p2pflowernet.project.entity.WithDrawInfoBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.BankcardService;
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
 * @描述: 用户资产数据层
 * @创建人：zhangpeisen
 * @创建时间：2017/12/23 上午9:45
 * @修改人：zhangpeisen
 * @修改时间：2017/12/23 上午9:45
 * @修改备注：
 * @throws
 */
public class IWalletModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;


    public IWalletModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取我的钱包数据
    public void getUserAccount(final IModelImpl<ApiResponse<UserAcountBean>, UserAcountBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        BankcardService codeService = retrofit.create(BankcardService.class);
        Disposable subscribe = codeService.getUserAccount(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<UserAcountBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<UserAcountBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述: 账单
     * @创建人：zhangpeisen
     * @创建时间：2017/12/22 下午5:25
     * @修改人：zhangpeisen
     * @修改时间：2017/12/22 下午5:25
     * @修改备注：
     */
    public void walletbill(String page, String year, String month, final IModelImpl<ApiResponse<BillAllBean>, BillAllBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("y", year);
        map.put("m", month);
        HashMap<String, String> param = new HashMap<>();
        param.put("page", page);
        param.put("y", year);
        param.put("m", month);
        String sign = SignUtil.getInstance().getSign(map);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.walletbill(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BillAllBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BillAllBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述:查看提现
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/23 上午9:47
     * @修改人 zhangpeisen
     * @修改时间 2017/12/23 上午9:47
     * @修改备注
     */
    public void selwithdrawals(final IModelImpl<ApiResponse<WithDrawInfoBean>, WithDrawInfoBean> listener) {
        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        BankcardService b = retrofit.create(BankcardService.class);
        Disposable subscribe = b.selwithdrawals(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<WithDrawInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<WithDrawInfoBean> checkMobileBeanApiResponse) throws Exception {
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
     * @描述:提现
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/23 上午9:48
     * @修改人 zhangpeisen
     * @修改时间 2017/12/23 上午9:48
     * @修改备注
     */
    public void withdrawals(String balance, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("balance", balance);
        HashMap<String, String> param = new HashMap<>();
        param.put("balance", balance);
        String sign = SignUtil.getInstance().getSign(map);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.withdrawals(sign, Constants.TOKEN, param)
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
}
