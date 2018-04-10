package com.android.p2pflowernet.project.o2omain.fragment.index.takeout.location.alladdress;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.AllAddressAdapter;
import com.android.p2pflowernet.project.mvp.KFragment;
import com.android.p2pflowernet.project.utils.appstatus.Eyes;
import com.android.p2pflowernet.project.view.customview.NormalTopBar;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangkun on 2018/2/2.
 */

public class AllAddressFragment extends KFragment<IAllAddressView,IAllAddressPresenter> implements NormalTopBar.normalTopClickListener{

    @BindView(R.id.normal_top)
    NormalTopBar topBar;
    @BindView(R.id.fragment_allAddress_listView)
    ListView mListView;
    @BindView(R.id.editText_tosearch)
    EditText mEditText;

    private List<PoiInfo> poiInfos = new ArrayList<>();
    private AllAddressAdapter adapter;
    private String city;

    @Override
    public IAllAddressPresenter createPresenter() {
        return new IAllAddressPresenter();
    }

    public static KFragment newIntence(List<PoiInfo> poiInfos,String city){
        AllAddressFragment fragment = new AllAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", (Serializable) poiInfos);
        bundle.putString("city",city);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poiInfos.addAll((List<PoiInfo>) getArguments().getSerializable("data"));
        city = getArguments().getString("city");
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_alladdress;
    }

    @Override
    public void initData() {
        adapter = new AllAddressAdapter(poiInfos,getActivity());
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable("PoiInfo", (Parcelable) poiInfos.get(position));
                intent.putExtras(bundle);
                getActivity().setResult(2,intent);
                removeFragment();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || "".equals(s.toString())) {
                    mListView.setVisibility(View.GONE);
                } else {
                    //创建 PoiSearch 实例
                    PoiSearch poiSearch = PoiSearch.newInstance();
//                    //城市内检索
//                    PoiCitySearchOption poiCitySearchOption = new PoiCitySearchOption();
//                    //关键字
//                    poiCitySearchOption.keyword(s.toString());
//                    //城市
//                    poiCitySearchOption.city(city);
//                    //设置每页容量，默认为每页 10 条
//                    poiCitySearchOption.pageCapacity(10);
//                    //分页编号
//                    poiCitySearchOption.pageNum(1);

                    //设置 poi 检索监听者
                    poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                        //poi 查询结果回调
                        @Override
                        public void onGetPoiResult(PoiResult poiResult) {
                            List<PoiInfo> data = poiResult.getAllPoi();
                            if (null != data && data.size() > 0) {
                                poiInfos.clear();
                                poiInfos.addAll(data);
                                mListView.setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        //poi 详情查询结果回调
                        @Override
                        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
                        }

                        @Override
                        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

                        }
                    });

                    if (TextUtils.isEmpty(city)) {
                        return;
                    }
                    poiSearch.searchInCity((new PoiCitySearchOption())
                            .city(city)
                            .keyword(s.toString().trim())
                            .pageCapacity(100));
//                    poiSearch.searchInCity(poiCitySearchOption);
                }
            }
        });
    }

    @Override
    protected void init(@Nullable View view, @Nullable Bundle savedInstanceState) {
        Eyes.setStatusBarColor(getActivity(), getResources().getColor(R.color.red));
        topBar.setTitleText("全部");
        topBar.setTitleTextColor(getResources().getColor(R.color.white));
        topBar.setLeftImageId(R.mipmap.icon_back_white);
        topBar.setBackgroundColor(getResources().getColor(R.color.red));
        topBar.setTopClickListener(this);

        initData();
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
