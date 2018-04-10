package com.android.p2pflowernet.project.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.constant.ApiUrlConstant;
import com.android.p2pflowernet.project.entity.GroupHomeBean;
import com.android.p2pflowernet.project.utils.GlideImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heguozhong on 2017/12/28/028.
 * 团队优惠Gridview适配器
 */

public class GroupBuyingGridviewAdapter extends BaseAdapter {

//    private final ArrayList<String> name;
//    private final ArrayList<Integer> icon;
    private List<GroupHomeBean.CategoryBean> categoryBeans;
    private Context mContext;

    public GroupBuyingGridviewAdapter(Context context,List<GroupHomeBean.CategoryBean> data) {
//        name = new ArrayList<>();
//        icon = new ArrayList<>();
//        name.add("火锅");
//        name.add("甜品");
//        name.add("自助餐");
//        name.add("快餐");
//        name.add("西餐");
//        name.add("烤肉");
//        name.add("烤鱼");
//        name.add("海鲜");
//        icon.add(R.drawable.group_buying_icon_hg);
//        icon.add(R.drawable.group_buying_icon_tp);
//        icon.add(R.drawable.group_buying_icon_zzc);
//        icon.add(R.drawable.group_buying_icon_kc);
//        icon.add(R.drawable.group_buying_icon_xc);
//        icon.add(R.drawable.group_buying_icon_kr);
//        icon.add(R.drawable.group_buying_icon_ky);
//        icon.add(R.drawable.group_buying_icon_hx);
        this.mContext = context;
        this.categoryBeans = data;
    }

    @Override
    public int getCount() {
        return null != categoryBeans ? categoryBeans.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return categoryBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.item_menue, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GroupHomeBean.CategoryBean bean = categoryBeans.get(position);
        viewHolder.tvNick.setText(TextUtils.isEmpty(bean.getName()) ? "" : bean.getName());
        new GlideImageLoader().displayImage(mContext, ApiUrlConstant.API_IMG_URL + bean.getFile_path(),viewHolder.ivMenue);
        return convertView;
    }

    static class ViewHolder {
        //功能图标
        @BindView(R.id.iv_menue)
        ImageView ivMenue;
        //功能文字描述
        @BindView(R.id.tv_nick)
        TextView tvNick;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
