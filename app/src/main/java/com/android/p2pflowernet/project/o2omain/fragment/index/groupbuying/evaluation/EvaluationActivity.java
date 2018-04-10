package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.evaluation;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 评论详情首页页面
 */

public class EvaluationActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        return EvaluationFragment.newInstance();
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