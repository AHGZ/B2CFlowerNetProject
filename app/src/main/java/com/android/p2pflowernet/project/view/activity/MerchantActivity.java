package com.android.p2pflowernet.project.view.activity;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.merchant.MerChantFragment;

/**
 * @描述: 商家主页
 * @创建人：zhangpeisen
 * @创建时间：2017/11/23 下午6:05
 * @修改人：zhangpeisen
 * @修改时间：2017/11/23 下午6:05
 * @修改备注：
 * @throws
 */

public class MerchantActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {

        Intent intent = getIntent();
        IdEntityBean idEntityBean = (IdEntityBean) intent.getExtras().getSerializable("idEntityBean");
        String state = intent.getStringExtra("state");
        String id = intent.getStringExtra("id");

        return MerChantFragment.newIntence(state,id,idEntityBean);
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
