package com.android.p2pflowernet.project.adapter;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.entity.GroupFullDetailBean;
import com.android.p2pflowernet.project.utils.QRCodeUtil;
import com.android.p2pflowernet.project.view.customview.QcodeDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.umeng.socialize.utils.DeviceConfig.context;


/**
 * Created by heguozhong on 2018/1/15/015.
 */

public class GroupGullDetailsTGListviewAdapter extends BaseAdapter {
    private FragmentActivity activity;
    private List<GroupFullDetailBean.OrderBean.CodeBean> code;
    private final ArrayList<String> strings;


    public GroupGullDetailsTGListviewAdapter(FragmentActivity activity, List<GroupFullDetailBean.OrderBean.CodeBean> code) {
        this.activity = activity;
        this.code = code;
        strings = new ArrayList<>();
        for (int i=1;i<=code.size();i++){
                strings.add(i+"");

        }

    }

    @Override
    public int getCount() {
        return code.size();
    }

    @Override
    public Object getItem(int position) {
        return code.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.group_full_tgdhm_listview_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.groupFullDetailTgdhmTv.setText("券码"+strings.get(position));
        //赋值团购兑换码
        //0-未使用 1-已使用 2-已退款
        if (code.get(position).getState()==0){
            viewHolder.groupFullDetailTgdhm.setText(code.get(position).getGroup_code()+" (未使用)");
        }else if (code.get(position).getState()==1){
            viewHolder.groupFullDetailTgdhm.setText(code.get(position).getGroup_code()+" (已使用)");
        }else if (code.get(position).getState()==2){
            viewHolder.groupFullDetailTgdhm.setText(code.get(position).getGroup_code()+" (已退款)");
        }
        final Bitmap bitmap = QRCodeUtil.creatBarcode(context, code.get(position).getGroup_code(), 100, 25, false);
        viewHolder.groupFullDetailTgdhmImg.setImageBitmap(bitmap);
        viewHolder.groupFullDetailTgdhmImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDaYang(bitmap);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        //团购兑换码
        @BindView(R.id.group_full_detail_tgdhm)
        TextView groupFullDetailTgdhm;
        //团购兑换码图片
        @BindView(R.id.group_full_detail_tgdhm_img)
        ImageView groupFullDetailTgdhmImg;
        //团购兑换码文字说明
        @BindView(R.id.group_full_detail_tgdhm_tv)
        TextView groupFullDetailTgdhmTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    //展示二维码布局
    public void showDaYang(Bitmap bitmap){
        QcodeDialog dialog = new QcodeDialog(activity, R.style.Dialog);//设置dialog的样式
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.dayangstyle); // 添加动画
        dialog.show();
        WindowManager m = activity.getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        //这句就是设置dialog横向满屏了。
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.7);     //dialog屏幕占比
        window.setAttributes(lp);
        ImageView qrCodeImg = (ImageView) dialog.findViewById(R.id.qr_code_img);
        qrCodeImg.setImageBitmap(bitmap);
    }
}

