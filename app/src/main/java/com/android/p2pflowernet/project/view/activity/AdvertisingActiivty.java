package com.android.p2pflowernet.project.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.MainActivity;
import com.android.p2pflowernet.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by caishen on 2018/2/1.
 * by--广告页面
 */

public class AdvertisingActiivty extends Activity {


    @BindView(R.id.welcome)
    ImageView mWelcome;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.lin_clock)
    LinearLayout lin_clock;
    // 是否显示广告页
    private String flag = "1";
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
                handler.sendEmptyMessageDelayed(0, 5000);
                animation.reset();
                textView.startAnimation(animation);
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_advertising);
        ButterKnife.bind(this);

        if ("1".equals(flag)) {

            if (!TextUtils.isEmpty(url)) {//广告图片点击之后的响应
                mWelcome.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AdvertisingActiivty.this, PublicWebActivity.class);
                        intent.putExtra("publicurl", url);
                        startActivity(intent);
                        handler.removeMessages(0);//关键-将定时器remove掉，不然跳转之后还在记时
                        finish();
                    }
                });
            }

        } else {

            //进入主页
            startActivity(new Intent(AdvertisingActiivty.this, MainActivity.class));
        }
    }

    private int getCount() {
        count--;
        if (count == 0) {
            //进入主页
            startActivity(new Intent(AdvertisingActiivty.this, MainActivity.class));
        }
        return count;
    }
}
