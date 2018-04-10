package com.android.p2pflowernet.project.o2omain.view;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.p2pflowernet.project.R;
import com.android.p2pflowernet.project.o2omain.utils.ViewUtils;

import java.math.BigDecimal;

import static com.android.p2pflowernet.project.o2omain.fragment.storedetail.StoreDetailFragment.carAdapter;


/**
 * @描述: 已点列表
 * @创建人：zhangpeisen
 * @创建时间：2018/1/2 上午11:00
 * @修改人：zhangpeisen
 * @修改时间：2018/1/2 上午11:00
 * @修改备注：
 * @throws
 */
public class ShopCarView extends FrameLayout {
    public TextView car_limit, tv_amount;
    public ImageView iv_shop_car;
    public TextView car_badge;
    private TextView distrib_money;
    private BottomSheetBehavior behavior;
    public boolean sheetScrolling;
    public View shoprl;
    public int[] carLoc;


    public void setBehavior(final BottomSheetBehavior behavior) {
        this.behavior = behavior;
    }

    public void setBehavior(final BottomSheetBehavior behavior, final View blackView) {
        this.behavior = behavior;
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                sheetScrolling = false;
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    blackView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                sheetScrolling = true;
                blackView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(blackView, slideOffset);
            }
        });


        blackView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                return true;
            }
        });
    }

    public ShopCarView(@NonNull Context context) {
        super(context);
    }

    public ShopCarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (iv_shop_car == null) {
            iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
            car_badge = (TextView) findViewById(R.id.car_badge);
            car_limit = (TextView) findViewById(R.id.car_limit);
            tv_amount = (TextView) findViewById(R.id.tv_amount);
            distrib_money = (TextView) findViewById(R.id.distrib_money);
            shoprl = findViewById(R.id.car_rl);
            shoprl.setOnClickListener(new toggleCar());
            carLoc = new int[2];
            iv_shop_car.getLocationInWindow(carLoc);
            carLoc[0] = carLoc[0] + iv_shop_car.getWidth() / 2 - ViewUtils.dip2px(getContext(), 10);
        }
    }

    public void updateAmount(BigDecimal amount, String carlimitamount) {

        if (amount.compareTo(new BigDecimal(0.0)) == 0) {
            car_limit = (TextView) findViewById(R.id.car_limit);
            car_limit.setText(car_limit.getText().toString().trim());
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.VISIBLE);
            findViewById(R.id.amount_container).setVisibility(View.GONE);
            iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
            iv_shop_car.setImageResource(R.mipmap.shop_car_empty);

            if (behavior != null) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            distrib_money = (TextView) findViewById(R.id.distrib_money);
            distrib_money.setText(distrib_money.getText().toString().trim());

        } else if (amount.compareTo(new BigDecimal(carlimitamount)) < 0) {

            car_limit = (TextView) findViewById(R.id.car_limit);
            car_limit.setText("还差 ¥" + new BigDecimal(carlimitamount).setScale(2, BigDecimal.ROUND_HALF_DOWN).subtract(amount) + " 起送");
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
            iv_shop_car.setImageResource(R.mipmap.shop_car_have);
            distrib_money = (TextView) findViewById(R.id.distrib_money);
            distrib_money.setText(distrib_money.getText().toString().trim());

        } else {

            car_limit = (TextView) findViewById(R.id.car_limit);
            car_limit.setText("     去结算     ");
            car_limit.setTextColor(Color.WHITE);
            car_limit.setBackgroundColor(Color.parseColor("#FF9600"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
            iv_shop_car.setImageResource(R.mipmap.shop_car_have);
            distrib_money = (TextView) findViewById(R.id.distrib_money);
            distrib_money.setText(distrib_money.getText().toString().trim());
        }

        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_amount.setText("¥" + amount);
    }

    public void showBadge(int total) {
        if (total > 0) {
            car_badge.setVisibility(View.VISIBLE);
            car_badge.setText(total + "");
        } else {
            car_badge.setVisibility(View.INVISIBLE);
        }
    }

    private class toggleCar implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (sheetScrolling) {
                return;
            }
            if (carAdapter != null && carAdapter.getItemCount() == 0) {
                return;
            }
            if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            } else {
                if (behavior != null) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        }
    }
}
