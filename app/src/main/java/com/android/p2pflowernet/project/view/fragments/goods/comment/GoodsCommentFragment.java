package com.android.p2pflowernet.project.view.fragments.goods.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.HomeFragmentAdapter;
import com.android.p2pflowernet.project.callback.AdapterLoader;
import com.android.p2pflowernet.project.entity.EveluateBean;
import com.android.p2pflowernet.project.entity.ListsBean;
import com.android.p2pflowernet.project.event.GoodEvaNumEvent;
import com.android.p2pflowernet.project.mvp.KFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @描述: 商品评价类
 * @创建人：zhangpeisen
 * @创建时间：2017/10/18 下午1:41
 * @修改人：zhangpeisen
 * @修改时间：2017/10/18 下午1:41
 * @修改备注：
 * @throws
 */
public class GoodsCommentFragment extends KFragment<IGoodsCommentlView, IGoodsCommentPresenter>
        implements AdapterLoader.OnItemClickListener<ListsBean>, IGoodsCommentlView {

    @BindView(R.id.tab_hometitle)
    TabLayout tabHometitle;
    @BindView(R.id.vp_homepager)
    ViewPager vpHomepager;
    private String goods_id = "";
    private ArrayList<Fragment> list_fragment;
    private ArrayList<String> list_title;
    private HomeFragmentAdapter fAdapter;

    public static GoodsCommentFragment newInstance(String goods_Id) {
        Bundle args = new Bundle();
        GoodsCommentFragment fragment = new GoodsCommentFragment();
        fragment.setArguments(args);
        args.putString("goods_Id", goods_Id);
        return fragment;
    }

    @Override
    public IGoodsCommentPresenter createPresenter() {
        return new IGoodsCommentPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        goods_id = arguments.getString("goods_Id");
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_goods_comment;
    }

    @Override
    public void initData() {

        //获取商品评价列表数据
        mPresenter.getEveluate();
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        //0全部，1好评，2中评，3差评，4有图
        list_fragment.add(GoodsEvaFragment.newIntence(0, goods_id));
        list_fragment.add(GoodsEvaFragment.newIntence(1, goods_id));
        list_fragment.add(GoodsEvaFragment.newIntence(2, goods_id));
        list_fragment.add(GoodsEvaFragment.newIntence(3, goods_id));
        list_fragment.add(GoodsEvaFragment.newIntence(4, goods_id));

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("好评");
        list_title.add("中评");
        list_title.add("差评");
        list_title.add("有图");

        //设置TabLayout的模式
//        tabHometitle.setTabMode(TabLayout.MODE_FIXED);

        //为TabLayout添加tab名称
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(0)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(1)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(2)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(3)));
        tabHometitle.addTab(tabHometitle.newTab().setText(list_title.get(4)));


        fAdapter = new HomeFragmentAdapter(getActivity().getSupportFragmentManager(),
                list_fragment, list_title);

        //viewpager加载adapter
        vpHomepager.setAdapter(fAdapter);

        tabHometitle.setupWithViewPager(vpHomepager);

        //默认选中第1个页面
        tabHometitle.getTabAt(0).select();
    }


    /***
     * 评论个数的回调
     * @param goodEvaNumEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GoodEvaNumEvent goodEvaNumEvent) {

        if (goodEvaNumEvent.getData() != null) {

            EveluateBean data = goodEvaNumEvent.getData();
            list_title.set(0, "全部" + "   (" + data.getAll_num() + ")");
            list_title.set(1, "好评" + "   (" + data.getGood_num() + ")");
            list_title.set(2, "中评" + "   (" + data.getGeneral_num() + ")");
            list_title.set(3, "差评" + "   (" + data.getNeg_num() + ")");
            list_title.set(4, "有图" + "   (" + data.getPic_eval_num() + ")");

        } else {

            list_title.set(0, "全部" + "   (" + 0 + ")");
            list_title.set(1, "好评" + "   (" + 0 + ")");
            list_title.set(2, "中评" + "   (" + 0 + ")");
            list_title.set(3, "差评" + "   (" + 0 + ")");
            list_title.set(4, "有图" + "   (" + 0 + ")");
        }

        if (fAdapter != null) {
            fAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public String goodsId() {

        return goods_id;
    }

    @Override
    public int getPage() {
        return 1;
    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void successEveluate(EveluateBean data) {

    }

    @Override
    public void onSuccess(String message) {

        showShortToast(message);
    }

    @Override
    public void onError(String message) {

        showShortToast(message);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void onItemClick(View itemView, int position, ListsBean item) {


    }
}
