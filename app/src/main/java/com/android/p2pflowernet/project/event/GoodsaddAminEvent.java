package com.android.p2pflowernet.project.event;

import android.widget.ImageView;

public class GoodsaddAminEvent {
    ImageView buyImageView;
    int[] startLocation;

    public GoodsaddAminEvent(ImageView buyImageView, int[] startLocation) {
        this.buyImageView = buyImageView;
        this.startLocation = startLocation;
    }

    public ImageView getBuyImageView() {
        return buyImageView;
    }

    public int[] getStartLocation() {
        return startLocation;
    }
}
