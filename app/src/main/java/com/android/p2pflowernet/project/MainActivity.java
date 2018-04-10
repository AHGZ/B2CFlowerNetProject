package com.android.p2pflowernet.project;

import android.app.Notification;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.main.MainFragment;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.umeng.analytics.MobclickAgent;

/**
 * @描述: 花返网基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午5:07
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午5:07
 * @修改备注：
 * @throws
 */
public class MainActivity extends KActivity {

    private long clickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化推送
        push();
    }

    @Override
    protected KFragment getFirstFragment() {
        return MainFragment.newInstance();
    }

    protected void setImmerseLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 退出主界面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if ((System.currentTimeMillis() - clickTime) > 2000) {
            showShortToast("再次点击退出");
            clickTime = System.currentTimeMillis();
        } else {
            this.finish();
            System.exit(0);
        }
    }


    public void push() {

        // 初始化push
        initWithApiKey();
        unBindForApp();
        initWithApiKey();
        Resources resource = this.getResources();
        String pkgName = this.getPackageName();
        CustomPushNotificationBuilder cBuilder = new CustomPushNotificationBuilder(resource.getIdentifier("notification_custom_builder", "layout", pkgName),
                resource.getIdentifier("notification_icon", "id", pkgName),
                resource.getIdentifier("notification_title", "id", pkgName),
                resource.getIdentifier("notification_text", "id", pkgName));

        cBuilder.setNotificationFlags(Notification.FLAG_AUTO_CANCEL);
        cBuilder.setNotificationDefaults(Notification.DEFAULT_VIBRATE);
        cBuilder.setStatusbarIcon(this.getApplicationInfo().icon);
        cBuilder.setLayoutDrawable(resource.getIdentifier("rty", "drawable", pkgName));
        cBuilder.setChannelId("testId");
        cBuilder.setChannelName("testName");
        cBuilder.setNotificationSound((Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.beep)).toString());
        // 推送高级设置，通知栏样式设置为下面的ID
        PushManager.setNotificationBuilder(this, 1, cBuilder);
    }


    // api_key 绑定
    private void initWithApiKey() {

        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY,
                "BnjFpyRCzYCAhAAhNlWftrg11FeLziPv");
//        showShortToast("绑定成功");

    }

    // 解绑
    private void unBindForApp() {
        // Push：解绑
        PushManager.stopWork(getApplicationContext());
//        showShortToast("解绑成功");
    }


    /**
     * 友盟统计
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
