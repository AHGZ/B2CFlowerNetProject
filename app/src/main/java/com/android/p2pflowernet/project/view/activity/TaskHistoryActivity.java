package com.android.p2pflowernet.project.view.activity;

import android.view.KeyEvent;

import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.view.fragments.mine.myteam.agence_office.taskhistory.TaskHistoryFragment;

/**
 * author: zhangpeisen
 * created on: 2017/10/11 上午9:26
 * description: 任务历史
 */
public class TaskHistoryActivity extends KActivity {

    @Override
    protected KFragment getFirstFragment() {
        int tag = getIntent().getExtras().getInt("tag");
        return TaskHistoryFragment.newInstance(tag);
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
