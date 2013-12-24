package com.tiemenschut.flingscrollerexample;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureListener extends SimpleOnGestureListener {
    private TrainView trainView;

    public GestureListener(TrainView trainView) {
        this.trainView = trainView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        trainView.scroll((int) distanceX);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        trainView.fling((int) velocityX);
        return true;
    }
}
