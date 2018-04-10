package com.android.p2pflowernet.project.view.fragments.mine.wallet.bankcard;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.BankInfoBean;
import com.android.p2pflowernet.project.entity.CheckPwdBean;
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
 * author: zhangpeisen
 * created on: 2017/11/30 下午4:12
 * description: 有关银行卡逻辑处理
 */
public class BankcardModel implements IModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    public BankcardModel() {
        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    /**
     * @throws
     * @描述:添加银行
     * @方法名: bancardadd
     * @返回类型void
     * @创建人 zhangpeisen
     * @创建时间 2017/11/30 下午4:20
     * @修改人 zhangpeisen
     * @修改时间 2017/11/30 下午4:20
     * @修改备注
     */
    public void bancardadd(String card_num, String cardholder_name, String id_number, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("card_num", card_num);
        map.put("cardholder_name", cardholder_name);
        map.put("id_number", id_number);


        HashMap<String, String> param = new HashMap<>();
        param.put("card_num", card_num);
        param.put("cardholder_name", cardholder_name);
        param.put("id_number", id_number);

        String sign = SignUtil.getInstance().getSign(map);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.bankcardadd(sign, Constants.TOKEN, param)
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
     * @描述:银行卡列表
     * @方法名:bancardlist
     * @返回类型 void
     * @创建人 zhangpeisen
     * @创建时间 2017/11/30 下午4:21
     * @修改人 zhangpeisen
     * @修改时间 2017/11/30 下午4:21
     * @修改备注
     */
    public void bancardlist(final IModelImpl<ApiResponse<BankInfoBean>, BankInfoBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.bankcardlist(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<BankInfoBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<BankInfoBean> checkMobileBeanApiResponse) throws Exception {
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

    //解绑银行卡
    public void delBank(String bankId, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("id", bankId);

        HashMap<String, String> parma = new HashMap<>();
        parma.put("id", bankId);

        String sign = SignUtil.getInstance().getSign(parma);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.delbank(sign, Constants.TOKEN, parma)
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

    //检测用户是否设置支付密码
    public void checkPwd(final IModelImpl<ApiResponse<CheckPwdBean>, CheckPwdBean> listener) {

        HashMap<String, String> parma = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(parma);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.checkPwd(sign, Constants.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<CheckPwdBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<CheckPwdBean> checkMobileBeanApiResponse) throws Exception {
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


    //支付宝提现
    public void withdraw(String money, String alipayAccount, final IModelImpl<ApiResponse<String>, String> listener) {

        HashMap<String, String> parma = new HashMap<>();
        parma.put("money", money);
        parma.put("alipayAccount", alipayAccount);

        HashMap<String, String> map = new HashMap<>();
        map.put("money", money);
        map.put("alipayAccount", alipayAccount);

        String sign = SignUtil.getInstance().getSign(parma);
        BankcardService bankcardService = retrofit.create(BankcardService.class);
        Disposable subscribe = bankcardService.withdraw(sign, Constants.TOKEN, parma)
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
