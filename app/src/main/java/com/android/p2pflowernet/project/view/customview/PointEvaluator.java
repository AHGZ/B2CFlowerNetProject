package com.android.p2pflowernet.project.view.customview;

import android.animation.TypeEvaluator;

/**
 * Created by panyi on 16/4/5.
 *
 */
public class PointEvaluator implements TypeEvaluator<Points> {

    @Override
    public Points evaluate(float fraction, Points startValue, Points endValue) {
        float x = startValue.getX() + fraction * (endValue.getX() - startValue.getX());
        float y = startValue.getY() + fraction * (endValue.getY() - startValue.getY());
        Points point = new Points(x, y);
        return point;
    }
}//end class
