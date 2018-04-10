package com.android.p2pflowernet.project.view.fragments.advertising;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.SplashBean;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.DateUtils;
import com.android.p2pflowernet.project.utils.NetWorkUtils;
import com.android.p2pflowernet.project.utils.SPUtils;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;
import com.android.p2pflowernet.project.view.activity.WelcomeGuideActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.ParseException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by caishen on 2018/2/1.
 * by--欢迎页加广告页面
 */

public class SplashFragment extends KFragment<ISplashView, ISplashPrenter> implements ISplashView {


    @BindView(R.id.imageViewStart)
    ImageView mIVEntry;
    private static final int ANIM_TIME = 2000;
    private static final float SCALE_END = 1.15F;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.layout_skip)
    LinearLayout layoutSkip;
    boolean continueCount = true;
    private int timeCount = 0;
    private int initTimeCount = 2;
    private boolean flag = false;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            countNum();
            if (continueCount) {
                handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
            }
        }
    };
    private String currDate;//当前时间
    private String link_url = "";
    private SplashBean data1;
    private Boolean aBoolean;
    private boolean isFirstOpen;
    private Boolean isFirstEvery;

    @Override
    public ISplashPrenter createPresenter() {
        return new ISplashPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.start_activity;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        getActivity().getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currDate = DateUtils.getTimeString();
        String currDates = SPUtils.get(getActivity(), SPUtils.EVERYDAYOPEN, currDate).toString();

        //判断是当前天的第一次
        try {
            flag = !DateUtils.IsToday(currDates);

            SPUtils.put(getActivity(), SPUtils.EVERYDAYOPEN, currDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        initTimeCount = 2;
        // 判断是否是第一次开启应用
        isFirstOpen = SPUtils.getBoolean(getActivity(), SPUtils.FIRST_OPEN, true);
        isFirstEvery = SPUtils.getBoolean(getActivity(), SPUtils.ISFRISTEVERYDAY, true);

        // 如果是第一次启动，则先进入功能引导页
        if (isFirstOpen) {
            Intent intent = new Intent(getActivity(), WelcomeGuideActivity.class);
            startActivity(intent);
            removeFragment();
            return;
        }

        if (NetWorkUtils.isNetworkAvailable()) {

            //请求是否有播放广告权限
            mPresenter.getAdvertising();
        }

        //隐藏倒计时
        layoutSkip.setVisibility(View.INVISIBLE);
        handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
    }


    private int countNum() {//数秒

        timeCount++;
        if (timeCount == 2) {//数秒，超过2秒后如果没有网络，则进入下一个页面

            if (!NetWorkUtils.isNetworkAvailable()) {
                continueCount = false;
                toNextActivity();
                removeFragment();
            }
        }

        if (data1 != null) {

            String show_mode = data1.getShow_mode();//显示方式1-每日首次 2-每次
            initTimeCount = data1.getShow_length() + 2;//倒计时时间
            String file_path = ApiUrlConstant.API_IMG_URL + data1.getFile_path();
            String is_enable = data1.getIs_enable();//是否上线
            link_url = data1.getLink_url();

            if (is_enable.equals("1")) {//0-未上线 1-已上线

                if (show_mode.equals("1")) {//每日首次

                    //判断是否是同一天，如果是不存储
                    if (isFirstEvery && flag == false) {

                        if (file_path == null || TextUtils.isEmpty(file_path)) {

                            layoutSkip.setVisibility(View.VISIBLE);
                            glideImage(file_path);//加载广告
                            SPUtils.putBoolean(getActivity(), SPUtils.ISFRISTEVERYDAY, false);

                        } else {

                            continueCount = false;
                            toNextActivity();
                        }

                    } else if (flag == false) {

                        continueCount = false;
                        toNextActivity();

                    } else {

                        layoutSkip.setVisibility(View.VISIBLE);
                        glideImage(file_path);//加载广告
                        SPUtils.putBoolean(getActivity(), SPUtils.ISFRISTEVERYDAY, false);
                    }

                } else {//每次

                    if (file_path == null || TextUtils.isEmpty(file_path)) {

                        continueCount = false;
                        toNextActivity();

                    } else {

                        layoutSkip.setVisibility(View.VISIBLE);
                        glideImage(file_path);
                        SPUtils.putBoolean(getActivity(), SPUtils.ISFRISTEVERYDAY, false);
                    }
                }

            } else {

                continueCount = false;
                toNextActivity();
            }

        } else {

            continueCount = false;
            toNextActivity();
        }

        if (timeCount == initTimeCount) {//时间到
            continueCount = false;
            toNextActivity();
        }

        return timeCount;
    }

    //加载图片
    private void glideImage(String file_path) {

        Glide.with(getActivity())
                .load(file_path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.app_startpage)
                .error(R.mipmap.app_startpage)
                .into(mIVEntry);
    }

    private void toNextActivity() {

        startMainActivity();
    }

    public static KFragment newIntence() {

        SplashFragment splashFragment = new SplashFragment();
        Bundle bundle = new Bundle();
        splashFragment.setArguments(bundle);
        return splashFragment;
    }

    //进入首页
    private void startMainActivity() {

//        mIVEntry.setImageResource(R.mipmap.app_startpage);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        startAnim();
                    }
                });
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {

                startActivity(new Intent(getActivity(), MainActivity.class));
                removeFragment();
            }
        });
    }

    @Override
    public void onError(String s) {
        startMainActivity();
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void successData(SplashBean data) {

        if (data != null) {

            data1 = data;

        } else {

            continueCount = false;
            toNextActivity();
        }
    }

    @OnClick({R.id.imageViewStart, R.id.layout_skip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewStart://链接

                if (continueCount) {

                    continueCount = false;

                    if (!TextUtils.isEmpty(link_url)) {
                        Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                        intent.putExtra("publicurl", link_url);
                        intent.putExtra("tag", "0");//区别是否进入首页,0-由广告页进入首页 1-不进入首页
                        startActivity(intent);
                        removeFragment();
                    }
                }

                break;
            case R.id.layout_skip://跳过

                if (continueCount) {
                    continueCount = false;
                    toNextActivity();
                }

                break;
        }
    }
}
