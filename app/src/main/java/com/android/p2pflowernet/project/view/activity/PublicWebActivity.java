package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.htmlweb.PubilcWebFragment;

/**
 * @描述: 公共html5页面
 * @创建人：zhangpeisen
 * @创建时间：2017/12/26 下午4:58
 * @修改人：zhangpeisen
 * @修改时间：2017/12/26 下午4:58
 * @修改备注：
 * @throws
 */
public class PublicWebActivity extends KActivity {
    private FragmentBackListener backListener;
    private boolean isInterception = false;

    @Override
    protected KFragment getFirstFragment() {
        if (getIntent().getExtras() == null) {
            return null;
        }
        String publicurl = getIntent().getStringExtra("publicurl");
        String tag = getIntent().getStringExtra("tag");
        return PubilcWebFragment.newInstance(publicurl,tag);
    }

    public void setBackListener(FragmentBackListener backListener) {
        this.backListener = backListener;
    }

    //是否拦截
    public boolean isInterception() {
        return isInterception;
    }

    public void setInterception(boolean isInterception) {
        this.isInterception = isInterception;
    }


    //返回键监听实现
    public interface FragmentBackListener {
        void onBackForward();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isInterception()) {
                if (backListener != null) {
                    backListener.onBackForward();
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
