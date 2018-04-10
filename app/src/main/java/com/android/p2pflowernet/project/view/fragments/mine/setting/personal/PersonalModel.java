package com.android.p2pflowernet.project.view.fragments.mine.setting.personal;

import android.text.TextUtils;

import com.android.p2pflowernet.project.constant.Constants;
import com.android.p2pflowernet.project.entity.AcmIncomBean;
import com.android.p2pflowernet.project.entity.ImgDataBean;
import com.android.p2pflowernet.project.entity.MineMyBean;
import com.android.p2pflowernet.project.entity.PersonInfo;
import com.android.p2pflowernet.project.entity.ShareCodeBean;
import com.android.p2pflowernet.project.entity.ShowPersonInfo;
import com.android.p2pflowernet.project.mvp.IModel;
import com.android.p2pflowernet.project.mvp.IModelImpl;
import com.android.p2pflowernet.project.service.PersonalService;
import com.android.p2pflowernet.project.service.UpLoadImgService;
import com.android.p2pflowernet.project.utils.SignUtil;
import com.rxy.netlib.http.ApiResponse;
import com.rxy.netlib.http.RetrofitUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by caishen on 2017/11/15.
 * by--个人信息的
 */

public class PersonalModel implements IModel {

    private Retrofit retrofit;
    private CompositeDisposable compositeDisposable;

    private Disposable subscribe;

    public PersonalModel() {

        //初始化网络请求
        retrofit = RetrofitUtils.getInstance();
        compositeDisposable = new CompositeDisposable();
    }


    //修改个人信息
    public void updatePersonal(String userName, int userId, int sex, String brithDay, String filePath,
                               final IModelImpl<ApiResponse<PersonInfo>, PersonInfo> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        if (!TextUtils.isEmpty(userName) && !userName.equals("")) {
            param.put("nickname", userName);
            map.put("nickname", userName);
        }
        if (!TextUtils.isEmpty(brithDay) && !brithDay.equals("")) {
            param.put("birthday", brithDay);
            map.put("birthday", brithDay);
        }
        if (userId != -1) {
            param.put("user_id", String.valueOf(userId));
            map.put("user_id", String.valueOf(userId));
        }
        if (sex != -1) {
            param.put("sex", String.valueOf(sex));
            map.put("sex", String.valueOf(sex));
        }

        if (!TextUtils.isEmpty(filePath)) {
            param.put("file_path", filePath);
            map.put("file_path", filePath);
        }

        String sign = SignUtil.getInstance().getSign(map);

        PersonalService personal = retrofit.create(PersonalService.class);
        // 文本信息上传
        subscribe = personal.personinfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<PersonInfo>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<PersonInfo> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
     * @描述:修改用户头像
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/11/21 下午5:41
     * @修改人 zhangpeisen
     * @修改时间 2017/11/21 下午5:41
     * @修改备注
     */
    public void mofityPhoto(String type, List<File> userImglist, final IModelImpl<ApiResponse<ImgDataBean>, ImgDataBean> listener) {

        // 带有图片信息上传
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        // 传入参数
//        param.put("user_id", String.valueOf(userId));
        param.put("type", type);
        // 传入签名
//        activity_course_order_layout.put("user_id", String.valueOf(userId));
        map.put("type", type);
        String sign = SignUtil.getInstance().getSign(map);
        UpLoadImgService upLoadImgService = retrofit.create(UpLoadImgService.class);
        Map<String, RequestBody> requestbodymap = new HashMap<>();
        for (int i = 0; i < userImglist.size(); i++) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), userImglist.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), userImglist.get(i));
            requestbodymap.put("file" + i + "\";filename=\"" + userImglist.get(i).getName(), requestFile);
        }
        subscribe = upLoadImgService.upLoadImg(sign, Constants.TOKEN, type, requestbodymap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ImgDataBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ImgDataBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    public void uploadImg(int userId, File file, final IModelImpl<ApiResponse<ImgDataBean>, ImgDataBean> listener) {
        // 带有图片信息上传
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        // 传入参数
        param.put("user_id", String.valueOf(userId));
        param.put("type", "3");
        // 传入签名
        map.put("user_id", String.valueOf(userId));
        map.put("type", "3");
        String sign = SignUtil.getInstance().getSign(map);
        UpLoadImgService upLoadImgService = retrofit.create(UpLoadImgService.class);
        Map<String, RequestBody> requestbodymap = new HashMap<>();
        String descriptionString = "This is a description";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        subscribe = upLoadImgService.upLoadImgs(sign, Constants.TOKEN, param, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ImgDataBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ImgDataBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    //获取个人信息
    public void showPersonalInfo(final IModelImpl<ApiResponse<ShowPersonInfo>, ShowPersonInfo> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        PersonalService personal = retrofit.create(PersonalService.class);
        Disposable subscribe = personal.showPersonInfo(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ShowPersonInfo>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ShowPersonInfo> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
     * @描述: 我的
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/25 上午11:40
     * @修改人 zhangpeisen
     * @修改时间 2017/12/25 上午11:40
     * @修改备注
     */
    public void usermy(final IModelImpl<ApiResponse<MineMyBean>, MineMyBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        PersonalService personal = retrofit.create(PersonalService.class);
        Disposable subscribe = personal.usermy(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<MineMyBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<MineMyBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
     * @描述: 累计收益
     * @方法名:
     * @返回类型
     * @创建人 zhangpeisen
     * @创建时间 2017/12/25 上午11:40
     * @修改人 zhangpeisen
     * @修改时间 2017/12/25 上午11:40
     * @修改备注
     */
    public void userincome(final IModelImpl<ApiResponse<AcmIncomBean>, AcmIncomBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        PersonalService personal = retrofit.create(PersonalService.class);
        Disposable subscribe = personal.userincome(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<AcmIncomBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<AcmIncomBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        listener.onError("-1", throwable.getMessage());
                    }
                });
        compositeDisposable.add(subscribe);
    }


    //邀请好友分享
    public void getShareCode(final IModelImpl<ApiResponse<ShareCodeBean>, ShareCodeBean> listener) {

        HashMap<String, String> map = new HashMap<>();
        HashMap<String, String> param = new HashMap<>();
        String sign = SignUtil.getInstance().getSign(map);
        PersonalService personal = retrofit.create(PersonalService.class);
        Disposable subscribe = personal.getShareCode(sign, Constants.TOKEN, param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ApiResponse<ShareCodeBean>>() {
                    @Override
                    public void accept(@NonNull ApiResponse<ShareCodeBean> sendCodeBeanApiResponse) throws Exception {
                        listener.onComplete(sendCodeBeanApiResponse);
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
