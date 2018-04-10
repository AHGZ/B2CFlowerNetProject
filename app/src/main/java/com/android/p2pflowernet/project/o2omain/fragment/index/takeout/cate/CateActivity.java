package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by heguozhong on 2018/1/2/002.
 * 美食主页面
 */

public class CateActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String cate_id = intent.getStringExtra("cate_id");
        int zcate_id = intent.getIntExtra("zcate_id",0);
        String searchName = intent.getStringExtra("searchName");
        return CateFragment.newInstance(cate_id,zcate_id,searchName);
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