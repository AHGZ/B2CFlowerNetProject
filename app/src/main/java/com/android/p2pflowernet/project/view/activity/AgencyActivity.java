package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.IdEntityBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.applyfor.agency.edit.AgencyEditFragment;

/**
 * @描述: 代理人主页
 * @创建人：zhangpeisen
 * @创建时间：2017/11/23 下午6:05
 * @修改人：zhangpeisen
 * @修改时间：2017/11/23 下午6:05
 * @修改备注：
 * @throws
 */

public class AgencyActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        if (getIntent().getExtras() == null) {
            return null;
        }
        IdEntityBean bean = (IdEntityBean) getIntent().getSerializableExtra("idEntityBean");
        String state = getIntent().getStringExtra("state");
        String id = getIntent().getStringExtra("id");
        return AgencyEditFragment.newIntence(state,id,bean);
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
