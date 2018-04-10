package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.map.MapLocationFragment;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * @描述: 地图定位主页面
 * @创建人：zhangpeisen
 * @创建时间：2017/11/11 上午10:21
 * @修改人：zhangpeisen
 * @修改时间：2017/11/11 上午10:21
 * @修改备注：
 * @throws
 */
public class MapLocationActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        return MapLocationFragment.newInstance();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
