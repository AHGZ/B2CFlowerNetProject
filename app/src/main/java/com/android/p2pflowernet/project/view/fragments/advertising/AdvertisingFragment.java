package com.android.p2pflowernet.project.view.fragments.advertising;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.activity.PublicWebActivity;

import butterknife.BindView;

/**
 * Created by caishen on 2018/2/1.
 * by--广告页面
 */

public class AdvertisingFragment extends KFragment<IAdvertisingView, IAdvertisingPrenter> {

    @BindView(R.id.welcome)
    ImageView mWelcome;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.lin_clock)
    LinearLayout lin_clock;
    // 是否显示广告页
    private String flag;
    // 跳转的url
    private String url;
    // 广告秒数
    private int count;
    // 广告页本地地址
    private String filePath;
    private Animation animation;

    /**
     * 做定时器的handler
     */
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                textView.setText("(" + getCount() + "" + ")");
                handler.sendEmptyMessageDelayed(0, 1000);
                animation.reset();
                textView.startAnimation(animation);
            }
        }
    };

    /**
     * 声明处理动画的handler
     */
    private Handler handler_animation = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    lin_clock.setVisibility(View.VISIBLE);
                    mWelcome.setImageResource(R.mipmap.app_startpage);
                    animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_show);
                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;
                case 2:
                    break;
            }
        }
    };

    public static KFragment newIntence() {

        AdvertisingFragment advertisingFragment = new AdvertisingFragment();
        Bundle bundle = new Bundle();
        advertisingFragment.setArguments(bundle);
        return advertisingFragment;
    }


    @Override
    public IAdvertisingPrenter createPresenter() {
        return new IAdvertisingPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_advertising;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        //为了让app启动页正常显示，加了延时-该延时正常是下载图片的时间，这里将图片下载到了本地，每次启动时显示的都是上次加载的
        new Handler().postDelayed(new Runnable() {

            public void run() {

                if ("1".equals(flag)) {

                    handler_animation.sendEmptyMessage(1);
                    if (!TextUtils.isEmpty(url)) {//广告图片点击之后的响应
                        mWelcome.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getActivity(), PublicWebActivity.class);
                                intent.putExtra("publicurl", url);
                                startActivity(intent);
                                handler.removeMessages(0);//关键-将定时器remove掉，不然跳转之后还在记时
                                removeFragment();
                            }
                        });
                    }

                } else {

                    //进入主页
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
            }
        }, 1000);
    }
    private int getCount() {
        count--;
        if (count == 0) {
            //进入主页
            startActivity(new Intent(getActivity(), MainActivity.class));
        }
        return count;
    }


    /**
     * 广告页状态栏设置为透明(在setContentView()方法之前使用)
     */
    private void setTranslucentStatus() {
        // 4.4以上支持
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getActivity().getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            // getWindow().addFlags(
            // WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            // SystemStatusManager tintManager = new SystemStatusManager(this);
            // tintManager.setStatusBarTintEnabled(true);
            // // 设置状态栏的颜色
            // tintManager.setStatusBarTintResource(R.color.trans);
            // getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
