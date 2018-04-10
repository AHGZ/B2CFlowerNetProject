package com.android.p2pflowernet.project.o2omain;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.event.O2oBackB2cEvent;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.fragment.O2oMainFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * @描述: O2o花返网基类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/9 下午5:07
 * @修改人：zhangpeisen
 * @修改时间：2017/10/9 下午5:07
 * @修改备注：
 * @throws
 */
public class O2oMainActivity extends KActivity {

    private int index = 0;

    @Override
    protected KFragment getFirstFragment() {
        index = getIntent().getIntExtra("index", 0);
        String searchName = getIntent().getStringExtra("searchName");
        return O2oMainFragment.newInstance(searchName);
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

            EventBus.getDefault().post(new O2oBackB2cEvent(index));
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
