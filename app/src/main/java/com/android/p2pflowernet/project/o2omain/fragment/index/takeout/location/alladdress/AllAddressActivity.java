package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.alladdress;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

/**
 * Created by zhangkun on 2018/2/2.
 * 全部地址
 */

public class AllAddressActivity extends KActivity{

    @Override
    protected KFragment getFirstFragment() {
        Bundle bundle = getIntent().getExtras();
        List<PoiInfo> poiInfos = (List<PoiInfo>) bundle.getSerializable("data");
        String city = bundle.getString("city");
        return AllAddressFragment.newIntence(poiInfos,city);
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
