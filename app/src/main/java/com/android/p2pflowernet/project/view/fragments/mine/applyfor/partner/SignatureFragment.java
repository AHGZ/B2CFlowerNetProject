package com.android.p2pflowernet.project.view.fragments.mine.applyfor.partner;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.SigntureEvent;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.UIUtils;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.LinePathView;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/11/20.
 * by--电子签名的页面
 */

public class SignatureFragment extends KFragment<ISignatureView, ISignaturePrenter>
        implements NormalTopBar.normalTopClickListener {

    @BindView(R.id.normal_top)
    NormalTopBar normalTop;
    @BindView(R.id.line_sign)
    LinePathView pathView;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ls.png";

    @Override
    public ISignaturePrenter createPresenter() {

        return new ISignaturePrenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_signature;
    }

    @Override
    public void initData() {

    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        normalTop.setTitleText("电子签名");
        normalTop.setTitleTextColor(Color.WHITE);
        normalTop.setLeftImageId(R.mipmap.icon_back_white);
        normalTop.setBackgroundResource(R.drawable.app_statusbar_bg);
        normalTop.setTopClickListener(this);
        Utils.setStatusBar(getActivity(), 0, false);
        UIUtils.setTouchDelegate(normalTop.getLeftImage(), 50);

    }

    public static KFragment newIntence() {

        SignatureFragment signatureFragment = new SignatureFragment();
        Bundle bundle = new Bundle();
        signatureFragment.setArguments(bundle);
        return signatureFragment;
    }

    @OnClick({R.id.btn_clear, R.id.btn_commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_clear://清除

                pathView.clear();

                break;
            case R.id.btn_commit://完成

                if (pathView.getTouched()) {

                    try {

                        pathView.save(path, false, 10);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //发送消息
                    EventBus.getDefault().post(new SigntureEvent(path));
                    removeFragment();

                } else {

                    showShortToast("您没有签名~");
                }
        }
    }

    @Override
    public void onLeftClick(View view) {

        removeFragment();
    }

    @Override
    public void onRightClick(View view) {

    }

    @Override
    public void onTitleClick(View view) {

    }
}
