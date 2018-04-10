package com.android.p2pflowernet.project.o2omain.fragment.index.groupbuying.shopdetails.submitorder;

import android.content.Intent;
import android.view.KeyEvent;

import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.mvp.KActivity;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by heguozhong on 2018/1/5/005.
 * 提交订单主页面
 */

public class SubmitOrderActivity extends KActivity {
    @Override
    protected KFragment getFirstFragment() {
        Intent intent = getIntent();
        String tel = intent.getStringExtra("tel");//联系电话
        String huafan_price = intent.getStringExtra("huafan_price");//花返价格
        GroupHomeBean.ListBean listBean = (GroupHomeBean.ListBean) intent.getSerializableExtra("listBean");
        return SubmitOrderFragment.newInstance(tel,huafan_price,listBean);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            removeFragment();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /***
     * 友盟统计
     */
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
