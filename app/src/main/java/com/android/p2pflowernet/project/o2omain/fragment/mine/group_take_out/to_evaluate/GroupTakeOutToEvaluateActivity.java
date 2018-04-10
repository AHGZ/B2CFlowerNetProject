package com.android.p2pflowernet.project.o2omain.fragment.mine.group_take_out.to_evaluate;

import android.os.Bundle;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.TakeOutOrderGroupBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by zhangkun on 2018/1/22.
 */

public class GroupTakeOutToEvaluateActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        Bundle bundle = getIntent().getExtras();
        TakeOutOrderGroupBean.ListBean listBean = (TakeOutOrderGroupBean.ListBean) bundle.getSerializable("good");
        return GroupTakeOutToEvaluateFragment.newIntence(listBean);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return  true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
