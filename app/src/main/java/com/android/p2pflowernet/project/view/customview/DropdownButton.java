package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.O2oHomeBean;

import java.util.List;

/**
 * Created by cuiMarker on 2016/12/13.
 */
public class DropdownButton extends RelativeLayout
        implements Checkable, View.OnClickListener, PopWinDownUtil.OnDismissLisener, AdapterView.OnItemClickListener {
    /**
     * 菜单按钮文字内容
     */
    private TextView text;
    /**
     * 菜单按钮底部的提示条
     */
    private boolean isCheced;
    private PopWinDownUtil popWinDownUtil;
    private Context mContext;
    private DropDownAdapter adapter;
    /**
     * 传入的数据
     */
    private List<O2oHomeBean.ZongheBean> drops;
    /**
     * 当前被选择的item位置
     */
    private int selectPosition;

    public DropdownButton(Context context) {
        this(context, null);
    }

    public DropdownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropdownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        //菜单按钮的布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dropdown_tab_button, this, true);
        text = (TextView) view.findViewById(R.id.textView);
        //点击事件，点击外部区域隐藏popupWindow
        setOnClickListener(this);
    }

    /**
     * 添加数据，默认选择第一项
     *
     * @param dropBeans
     */
    public void setData(List<O2oHomeBean.ZongheBean> dropBeans) {
        if (dropBeans == null || dropBeans.isEmpty()) {
            return;
        }
        drops = dropBeans;
        drops.get(0).setChoiced(true);
        text.setText(drops.get(0).getName());
        selectPosition = 0;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dropdown_content, null);
        view.findViewById(R.id.content).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popWinDownUtil.hide();
            }
        });
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);

        adapter = new DropDownAdapter(drops, mContext);
        listView.setAdapter(adapter);

        popWinDownUtil = new PopWinDownUtil(mContext, view, this);
        popWinDownUtil.setOnDismissListener(this);
    }

    public void setText(CharSequence content) {
        text.setText(content);
    }

    /**
     * 根据传过来的参数改变状态
     *
     * @param checked
     */
    @Override
    public void setChecked(boolean checked) {
        isCheced = checked;
        Drawable icon;
        if (checked) {
            icon = getResources().getDrawable(R.drawable.icon_xia);
            text.setTextColor(getResources().getColor(R.color.o2o_red));
            if (popWinDownUtil != null) {
                popWinDownUtil.show();
            }

        } else {
            icon = getResources().getDrawable(R.drawable.icon_shang);
            text.setTextColor(getResources().getColor(R.color.o2o_gray));
            if (popWinDownUtil != null) {
                popWinDownUtil.hide();
            }
        }
        //把箭头画到textView右边
        text.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    @Override
    public boolean isChecked() {
        return isCheced;
    }

    @Override
    public void toggle() {
        setChecked(!isCheced);
    }

    @Override
    public void onClick(View v) {
        setChecked(!isCheced);
    }

    @Override
    public void onDismiss() {
        setChecked(false);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (selectPosition == position) {
            return;
        }
        drops.get(selectPosition).setChoiced(false);
        drops.get(position).setChoiced(true);
        text.setText(drops.get(position).getName());
        adapter.notifyDataSetChanged();
        selectPosition = position;
        popWinDownUtil.hide();
        if (onDropItemSelectListener != null) {
            onDropItemSelectListener.onDropItemSelect(position);
        }
    }

    private OnDropItemSelectListener onDropItemSelectListener;

    public void setOnDropItemSelectListener(OnDropItemSelectListener onDropItemSelectListener) {
        this.onDropItemSelectListener = onDropItemSelectListener;
    }

    public interface OnDropItemSelectListener {
        void onDropItemSelect(int Postion);
    }

    class DropDownAdapter extends BaseAdapter {
        private List<O2oHomeBean.ZongheBean> drops;
        private Context context;

        public DropDownAdapter(List<O2oHomeBean.ZongheBean> drops, Context context) {
            this.drops = drops;
            this.context = context;
        }

        @Override
        public int getCount() {
            return drops.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.dropdown_item, parent, false);
                holder.tv = (TextView) convertView.findViewById(R.id.name);
                holder.tig = (ImageView) convertView.findViewById(R.id.check);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(drops.get(position).getName());
            if (drops.get(position).isChoiced()) {
                holder.tig.setVisibility(VISIBLE);
            } else {
                holder.tig.setVisibility(GONE);
            }
            return convertView;
        }

        private class ViewHolder {
            TextView tv;
            ImageView tig;
        }
    }
}
