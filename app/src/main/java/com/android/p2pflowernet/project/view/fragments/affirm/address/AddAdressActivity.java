package com.android.p2pflowernet.project.view.fragments.affirm.address;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by caishen on 2017/11/7.
 * by--地址管理的页面
 */

public class AddAdressActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        String isUpdate = intent.getStringExtra("isUpdate");
        String id = intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        String telephone = intent.getStringExtra("telephone");
        String province_id = intent.getStringExtra("province_id");
        String city_id = intent.getStringExtra("city_id");
        String district_id = intent.getStringExtra("district_id");
        String address = intent.getStringExtra("address");
        String is_default = intent.getStringExtra("is_default");
        String location = intent.getStringExtra("location");
        return AdressFragment.newIntence(isUpdate, id, name, telephone, location, province_id,
                city_id, district_id, address, is_default);
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
