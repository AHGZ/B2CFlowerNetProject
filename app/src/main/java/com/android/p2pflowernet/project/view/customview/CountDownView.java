package com.android.p2pflowernet.project.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.utils.TimeTools;
import com.android.p2pflowernet.project.utils.UIUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;




/**
 * 有天倒计时的View
 */
public class CountDownView extends LinearLayout {

    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_minute)
    TextView tvMinute;
    @BindView(R.id.tv_second)
    TextView tvSecond;

    @BindView(R.id.day)
    TextView day;
    @BindView(R.id.hour)
    TextView hour;
    @BindView(R.id.minute)
    TextView minute;

    private Context context;

    private int viewBg;//倒计时的背景
    private int cellBg;//每个倒计时的背景
    private int cellTextColor;//文字颜色
    private int textColor;//外部：等颜色
    private int textSize = 14;//外部文字大小
    private int cellTextSize = 12;//cell文字大小

    private TimerTask timerTask = null;
    private Timer timer = new Timer();
    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }
    };

    public CountDownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public CountDownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(attrs, defStyleAttr);
        initView(context);
    }

    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownView, defStyle, 0);
        viewBg = typedArray.getColor(R.styleable.CountDownView_viewBg, Color.parseColor("#FFFFFF"));
        cellBg = typedArray.getColor(R.styleable.CountDownView_cellBg, Color.parseColor("#F4F4F4"));
        cellTextColor = typedArray.getColor(R.styleable.CountDownView_cellTextColor, Color.parseColor("#646464"));
        textColor = typedArray.getColor(R.styleable.CountDownView_TextColor, Color.parseColor("#B3B3B3"));
        textSize = (int) typedArray.getDimension(R.styleable.CountDownView_TextSize, UIUtils.dip2Px(getContext(), 14));
        cellTextSize = (int) typedArray.getDimension(R.styleable.CountDownView_cellTextSize, UIUtils.dip2Px(getContext(), 12));
        typedArray.recycle();

    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_countdown_layout, this);
        ButterKnife.bind(view);

        initProperty();
    }

    private void initProperty() {
        tvDay.setBackgroundColor(cellBg);
        tvHour.setBackgroundColor(cellBg);
        tvMinute.setBackgroundColor(cellBg);
        tvSecond.setBackgroundColor(cellBg);

        tvDay.setTextColor(cellTextColor);
        tvHour.setTextColor(cellTextColor);
        tvMinute.setTextColor(cellTextColor);
        tvSecond.setTextColor(cellTextColor);

        day.setTextColor(textColor);
        hour.setTextColor(textColor);
        minute.setTextColor(textColor);
    }


    public void setLeftTime(long leftTime) {
        if (leftTime <= 0) return;
        long time = leftTime / 1000;
        long day = time / (3600 * 24);
        long hours = (time - day * 3600 * 24) / 3600;
        long minutes = (time - day * 3600 * 24 - hours * 3600) / 60;
        long seconds = time - day * 3600 * 24 - hours * 3600 - minutes * 60;

        setTextTime(time);
    }


    public void start() {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }

            };
            timer.schedule(timerTask, 1000, 1000);
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    handler.sendEmptyMessage(0);
//                }
//            }, 0, 1000);
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    //保证天，时，分，秒都两位显示，不足的补0
    private void setTextTime(long time) {
        String[] s = TimeTools.formatTimer(time);
        tvDay.setText(s[0]);
        tvHour.setText(s[1]);
        tvMinute.setText(s[2]);
        tvSecond.setText(s[3]);
    }

    private void countDown() {
        if (isCarry4Unit(tvSecond)) {
            if (isCarry4Unit(tvMinute)) {
                if (isCarry4Unit(tvHour)) {
                    if (isCarry4Unit(tvDay)) {
                        stop();
                    }
                }
            }
        }
    }

    private boolean isCarry4Unit(TextView tv) {
        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 59;
            tv.setText(time + "");
            return true;
        } else if (time < 10) {
            tv.setText("0" + time);
            return false;
        } else {
            tv.setText(time + "");
            return false;
        }
    }
}
