package com.android.p2pflowernet.project.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.adapter.RightSideslipLayAdapter;
import com.android.p2pflowernet.project.adapter.ScreeningListAdapter;
import com.android.p2pflowernet.project.callback.CbSelectLinstener;
import com.android.p2pflowernet.project.entity.AllSortBean;
import com.android.p2pflowernet.project.entity.BrandSortBean;
import com.android.p2pflowernet.project.event.RefreshAllBrandSort;
import com.android.p2pflowernet.project.view.customview.OnClickListenerWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:筛选的侧滑页面
 * @创建人：zhangpeisen
 * @创建时间：2017/12/14 上午11:41
 * @修改人：zhangpeisen
 * @修改时间：2017/12/14 上午11:41
 * @修改备注：
 * @throws
 */
public class RightSideslipChildLay extends FrameLayout {

    private final List<BrandSortBean.ListsBeanX> data;
    private ListView mBrandList;
    private ScreeningListAdapter mAdapter;
    private Context mCtx;
    private ImageView meunBackIm;
    private TextView brand_tv;
    private TextView meunOkTv;
    private View inflates;

    BackDataLinstener backDataLinstener;

    ConfirmDataLinstener confirmDataLinstener;


    public RightSideslipChildLay(Context context, List<AllSortBean.ListsBean> Fristlist, List<BrandSortBean.ListsBeanX> lists) {

        super(context);
        mCtx = context;
        this.data = lists;
        initView(Fristlist);
    }

    public void setBackDataLinstener(BackDataLinstener backDataLinstener) {
        this.backDataLinstener = backDataLinstener;
    }


    public void setConfirmDataLinstener(ConfirmDataLinstener confirmDataLinstener) {
        this.confirmDataLinstener = confirmDataLinstener;
    }


    //初始化view
    private void initView(List<AllSortBean.ListsBean> Fristlist) {
        inflates = View.inflate(getContext(), R.layout.include_right_sideslip_child_layout, this);
        mBrandList = (ListView) findViewById(R.id.select_brand_list);
        meunBackIm = (ImageView) findViewById(R.id.select_brand_back_im);
        brand_tv = (TextView) findViewById(R.id.brand_tv);
        meunOkTv = (TextView) findViewById(R.id.select_brand_ok_tv);
        meunBackIm.setOnClickListener(ClickListener);
        meunOkTv.setOnClickListener(ClickListener);
        //初始化数据
        setupList(Fristlist);
    }

    /**
     * 重置刷新数据
     * @param userInfoEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(RefreshAllBrandSort userInfoEvent) {

        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
            RightSideslipLayAdapter.mSelectvalues.setText("");
        }
    }

    //设置默认选中的CheckBox的状态
    private void setupList(List<AllSortBean.ListsBean> Fristlist) {
        mAdapter = new ScreeningListAdapter(mCtx, Fristlist, data);
        mBrandList.setAdapter(mAdapter);
    }

    private OnClickListenerWrapper ClickListener = new OnClickListenerWrapper() {

        private StringBuilder stringBuilder;

        @Override
        protected void onSingleClick(View v) {
            switch (v.getId()) {
                case R.id.select_brand_back_im://返回
                    mAdapter.setCbSelectLinstener(new CbSelectLinstener() {
                        @Override
                        public void selectbox(LinkedHashMap<String, Integer> selectboxMaps) {
                            if (selectboxMaps == null) {
                                return;
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            for (Map.Entry<String, Integer> entry : selectboxMaps.entrySet()) {
                                String selectv = entry.getKey();
                                stringBuilder.append(selectv).append(",");
                            }
                            RightSideslipLayAdapter.mSelectvalues.setText(stringBuilder.toString());
                        }
                    });

                    if (backDataLinstener != null) {
                        backDataLinstener.backdata();
                    }

                    break;
                case R.id.select_brand_ok_tv://确定

                    mAdapter.setCbSelectLinstener(new CbSelectLinstener() {
                        @Override
                        public void selectbox(LinkedHashMap<String, Integer> selectboxMaps) {
                            if (selectboxMaps == null) {
                                return;
                            }
                            stringBuilder = new StringBuilder();
                            for (Map.Entry<String, Integer> entry : selectboxMaps.entrySet()) {
                                String selectv = entry.getKey();
                                stringBuilder.append(selectv).append(",");
                            }
                            RightSideslipLayAdapter.mSelectvalues.setText(stringBuilder.toString());
                        }
                    });
                    if (confirmDataLinstener != null) {
                        confirmDataLinstener.confirmdata(stringBuilder, mAdapter.getData());
                    }
                    break;
            }
        }
    };

    public interface BackDataLinstener {
        void backdata();
    }

    public interface ConfirmDataLinstener {
        void confirmdata(StringBuilder stringBuilder, List<AllSortBean.ListsBean> data);
    }
}
