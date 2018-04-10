package com.android.p2pflowernet.project.view.fragments.search;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.event.MainEvent;
import com.android.p2pflowernet.project.helper.HistorySQLiteOpenHelper;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.o2omain.O2oMainActivity;
import com.android.p2pflowernet.project.o2omain.fragment.index.takeout.cate.CateActivity;
import com.android.p2pflowernet.project.utils.Utils;
import com.android.p2pflowernet.project.view.customview.CustomEditText;
import com.android.p2pflowernet.project.view.customview.MyListView;
import com.android.p2pflowernet.project.view.fragments.goods.goodslist.GoodsListActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by caishen on 2017/10/26.
 * by--搜索页面
 */

public class SearchFragment extends KFragment<ISearchView, ISearchPrenter> implements View.OnClickListener,
        CustomEditText.IMyRightDrawableClick, TextWatcher, View.OnKeyListener {


    @BindView(R.id.ic_back)
    ImageView icBack;
    @BindView(R.id.message_linear)
    LinearLayout messageLinear;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.edit_search)
    CustomEditText editSearch;
    @BindView(R.id.linear1)
    LinearLayout linear1;
    @BindView(R.id.hs_hot_search)
    HorizontalScrollView hsHotSearch;
    @BindView(R.id.ll_hot_search)
    LinearLayout llHotSearch;
    @BindView(R.id.listview)
    MyListView listView;
    @BindView(R.id.ll_clear)
    LinearLayout llClear;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.view)
    View view;
    private SQLiteDatabase db = null;
    private HistorySQLiteOpenHelper helper;
    private BaseAdapter adapter;
    private String tag = "0";//0-b2c 1-o2o
    private String cate_id;//0-b2c 1-o2o
    private int zcate_id;//0-b2c 1-o2o

    @Override
    public ISearchPrenter createPresenter() {

        return new ISearchPrenter();
    }

    @Override
    protected int getLayout() {

        return R.layout.fragment_search;
    }

    @Override
    public void initData() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getArguments().getString("tag");
        cate_id = String.valueOf(getArguments().getInt("cate_id"));
        zcate_id = getArguments().getInt("zcate_id");
    }

    @Override
    protected void init(@Nullable final View view, @Nullable Bundle savedInstanceState) {

        //初始化沉浸式
        Utils.setStatusBar(getActivity(), 0, false);
        helper = new HistorySQLiteOpenHelper(getActivity());

        //动态添加热搜的数据
        for (int i = 0; i < 12; i++) {

            View Hot_View = LayoutInflater.from(getActivity()).inflate(R.layout.hot_search_item, null);
            TextView tv_title = (TextView) Hot_View.findViewById(R.id.tv_title);
            tv_title.setText("热搜第：" + i);
            //设置点击事件
            tv_title.setTag("点击了" + i);
            tv_title.setOnClickListener(this);
            // 把item添加到父view中
            linear1.addView(Hot_View);
        }

        //设置搜索历史的适配器
        // 清空搜索历史
        llClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        //设置搜索框的删除按钮的点击事件
        editSearch.setRightDrawable(getResources().getDrawable(R.drawable.et_clear_search));
        editSearch.setDrawableClick(this);

        // 搜索框的键盘搜索键点击回调
        editSearch.setOnKeyListener(this);

        // 搜索框的文本变化实时监听
        editSearch.addTextChangedListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                editSearch.setText(name);

                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
                if (tag.equals("0") || "4".equals(tag)) {//0-b2c 1-o2o 2-o2o外卖

                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("id", "");
                    intent.putExtra("tag", "1");
                    intent.putExtra("searchName", editSearch.getText().toString().trim());
                    startActivity(intent);

                } else if (tag.equals("1")) {//1-o2o首页

                    Intent intent = new Intent(getActivity(), O2oMainActivity.class);
                    intent.putExtra("searchName", editSearch.getText().toString().trim());
                    startActivity(intent);
                }else if (tag.equals("2")) {//2-外卖

                    Intent intent = new Intent(getActivity(), CateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchName",editSearch.getText().toString().trim());
                    bundle.putString("cate_id",cate_id);
                    bundle.putInt("zcate_id",zcate_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                removeFragment();
            }
        });

//        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
//        Date date = new Date();
//        long time = date.getTime();
//        insertData("Leo" + time);

        // 第一次进入查询所有的历史记录
        queryData("");
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {

        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);

        //判断是否有历史记录
        if (cursor != null && cursor.moveToNext()) {

            llClear.setVisibility(View.VISIBLE);

        } else {

            llClear.setVisibility(View.GONE);
        }

        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {

        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});

        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {

        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    public static KFragment newInstance(String tag,int zcate_id,int cate_id) {

        Bundle bundle = new Bundle();
        SearchFragment searchFragment = new SearchFragment();
        bundle.putString("tag", tag);
        bundle.putInt("zcate_id", zcate_id);
        bundle.putInt("cate_id", cate_id);
        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @OnClick(R.id.ic_back)
    public void onClick() {

        removeFragment();

        if(tag.equals("0")) {//b2c首页
            //发送消息跳转到b2c首页
            EventBus.getDefault().post(new MainEvent(0));
        }else if(tag.equals("1")) {//o2o首页
            //发送消息跳转到o2o首页
            Intent intent = new Intent(getActivity(), O2oMainActivity.class);
            intent.putExtra("searchName", "").addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("index", 0);
            startActivity(intent);
        }else if (tag.equals("4")) {
            //发送消息跳转到分类页面
            EventBus.getDefault().post(new MainEvent(1));
        }
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {

        String tag = (String) v.getTag();
        Intent intent = new Intent(getActivity(), GoodsListActivity.class);
        intent.putExtra("id", "");
        intent.putExtra("tag", "1");
        intent.putExtra("searchName", editSearch.getText().toString().trim());
        startActivity(intent);
    }

    /**
     * 输入框右侧的点击回调
     *
     * @param view
     */
    @Override
    public void rightDrawableClick(View view) {

        switch (view.getId()) {
            case R.id.edit_search:

                //清除所有搜索的字符
                editSearch.setText("");

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String tempName = editSearch.getText().toString();
        // 根据tempName去模糊查询数据库中有没有数据
        queryData(tempName);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
//                    // 先隐藏键盘
//                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                            getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
            boolean hasData = hasData(editSearch.getText().toString().trim());

            if (!hasData) {

                //输入为空不能插入数据
                if (!editSearch.getText().toString().trim().equals("")) {

                    insertData(editSearch.getText().toString().trim());
                    queryData("");
                }
            }

            if (editSearch.getText().toString().trim().equals("")) {

                showShortToast("什么也没有输入哦！");

            } else {

                if (tag.equals("0")) {//b2c

                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                    intent.putExtra("id", "");
                    intent.putExtra("tag", "1");
                    intent.putExtra("searchName", editSearch.getText().toString().trim());
                    startActivity(intent);

                } else if (tag.equals("1")) {//1-o2o首页

                    Intent intent = new Intent(getActivity(), O2oMainActivity.class);
                    intent.putExtra("searchName", editSearch.getText().toString().trim());
                    startActivity(intent);

                }else if (tag.equals("2")) {//2-外卖

                    Intent intent = new Intent(getActivity(), CateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchName",editSearch.getText().toString().trim());
                    bundle.putString("cate_id",cate_id);
                    bundle.putInt("zcate_id",zcate_id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                removeFragment();
            }
        }
        return false;
    }
}
