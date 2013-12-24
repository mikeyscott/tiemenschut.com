package com.tiemenschut.flingscrollerexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

public class TrainView extends View {
    private static final int TRAIN_CARS = 25;

    private Drawable         train;
    private Drawable         trainCars;

    private int              offset     = 0;
    private OverScroller     scroller;
    private GestureDetector  gestureDetector;

    public TrainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        train = getResources().getDrawable(R.drawable.train);
        trainCars = getResources().getDrawable(R.drawable.cars);

        scroller = new OverScroller(context);
        GestureListener gestureListener = new GestureListener(this);
        gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (scroller.computeScrollOffset()) {
            offset = scroller.getCurrX();
            postInvalidateDelayed(30);
        }

        train.setBounds(offset, 0, train.getIntrinsicWidth() + offset, train.getIntrinsicHeight());
        for (int i = 0; i < TRAIN_CARS; ++i) {
            int left = -(i + 1) * trainCars.getIntrinsicWidth() + offset;
            trainCars.setBounds(left, 0, left + trainCars.getIntrinsicWidth(), train.getIntrinsicHeight());
            trainCars.draw(canvas);
        }

        train.draw(canvas);
    }

    public void scroll(int distanceX) {
        scroller.forceFinished(true);
        offset -= distanceX;
        checkOffset();
        invalidate();
    }

    public void fling(int velocityX) {
        scroller.forceFinished(true);
        scroller.fling(offset, 0, velocityX, 0, 0, getMaxOffset(), 0, 0, 50, 0);
        invalidate();
    }

    private int getMaxOffset() {
        return TRAIN_CARS * trainCars.getIntrinsicWidth();
    }

    private void checkOffset() {
        if (offset < 0) {
            offset = 0;
        } else if (offset > getMaxOffset()) {
            offset = getMaxOffset();
        }
    }
}
