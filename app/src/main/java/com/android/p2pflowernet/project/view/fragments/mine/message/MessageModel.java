package com.android.p2pflowernet.project.view.fragments.mine.message;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.MessaDetailBean;
import com.android.p2pflowernet.project.entity.MessaTypeBean;
import com.android.p2pflowernet.project.entity.MessagesBean;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.MessageService;
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
 * Created by caishen on 2017/12/28.
 * by--
 */

public class MessageModel implements IModel {

    private final Retrofit retrofit;
    private final CompositeDisposable compositeDisposable;

    @Override
    public void cancel() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    public MessageModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }

    //获取消息列表数据
    public void getMessages(IModelImpl<ApiResponse<MessagesBean>, MessagesBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        MessageService codeService = retrofit.create(MessageService.class);
        Disposable subscribe = codeService.getMessages(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MessagesBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MessagesBean> checkMobileBeanApiResponse) throws Exception {
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


    //获取指定消息列表数据
    public void getNoticeList(String type, int page, String receiver, IModelImpl<ApiResponse<MessaTypeBean>, MessaTypeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("receiver", receiver);
        map.put("page", String.valueOf(page));

        HashMap<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("receiver", receiver);
        param.put("page", String.valueOf(page));

        String sign = SignUtil.getInstance().getSign(map);
        MessageService codeService = retrofit.create(MessageService.class);
        Disposable subscribe = codeService.getNoticeList(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MessaTypeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MessaTypeBean> checkMobileBeanApiResponse) throws Exception {
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

    //获取消息详情
    public void getNoticeInfo(String type, String noticeId, String receiver, IModelImpl<ApiResponse<MessaDetailBean>, MessaDetailBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        map.put("type", type);
        map.put("receiver", receiver);
        map.put("notice_id", noticeId);

        HashMap<String, String> param = new HashMap<>();
        param.put("type", type);
        param.put("receiver", receiver);
        param.put("notice_id", noticeId);

        String sign = SignUtil.getInstance().getSign(map);
        MessageService codeService = retrofit.create(MessageService.class);
        Disposable subscribe = codeService.getNoticeInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MessaDetailBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MessaDetailBean> checkMobileBeanApiResponse) throws Exception {
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
