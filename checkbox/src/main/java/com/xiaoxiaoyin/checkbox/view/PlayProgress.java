package com.xiaoxiaoyin.checkbox.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiaoxiaoyin on 16/1/27.
 */
public class PlayProgress extends View {
    public PlayProgress(Context context) {
        super(context);
    }

    public PlayProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PlayProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int mWith;
    private int mHeight;
    private Paint mPaint;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mHeight = h;
        this.mWith = w;
        init();
    }

    /**
     * 0:startX ;
     * 1:startY;
     * 2:stopX;
     * 3:stopY;
     */
    private int[] start = new int[4];
    private int[] start1 = new int[4];
    private int[] start2 = new int[4];
    private int[] start3 = new int[4];

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.parseColor("#6839c0"));
        mPaint.setStrokeWidth(10);
        start[0] = mWith / 5;
        start[2] = mWith / 5;
        start[1] = mHeight - 10;
        start[3] = 10;
        start1[0] = mWith / 5 * 2;
        start1[2] = mWith / 5 * 2;
        start1[1] = mHeight - 10;
        start1[3] = 30;
        start2[0] = mWith / 5 * 3;
        start2[2] = mWith / 5 * 3;
        start2[1] = mHeight - 10;
        start2[3] = 50;
        start3[0] = mWith / 5 * 4;
        start3[2] = mWith / 5 * 4;
        start3[1] = mHeight - 10;
        start3[3] = 70;
        animateY(300);
    }

    private boolean isDraw, isStop;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isStop) {
            if (isDraw) {

                canvas.drawLine(start[0], start[1], start[2], (start[1] - start[3]) * value, mPaint);
                canvas.drawLine(start1[0], start1[1], start1[2], (start1[1] - start1[3]) * ((1 - value) == 0 ?0.1f : (1 - value)), mPaint);
                canvas.drawLine(start2[0], start2[1], start2[2], (start2[1] - start2[3]) * value, mPaint);
                canvas.drawLine(start3[0], start3[1], start3[2], (start3[1] - start3[3]) * ((1 - value) == 0 ?0.1f : (1 - value)), mPaint);
                if (value == 1) {
                    isDraw = false;
                    animateY(300);
                }
            } else {
                canvas.drawLine(start[0], start[1], start[2], (start[1] - start[3]) * ((1 - value) == 0 ? 0.1f : (1 - value)), mPaint);
                canvas.drawLine(start1[0], start1[1], start1[2], (start1[1] - start1[3]) * value, mPaint);
                canvas.drawLine(start2[0], start2[1], start2[2], (start2[1] - start2[3]) * ((1 - value) == 0 ? 0.1f : (1 - value)), mPaint);
                canvas.drawLine(start3[0], start3[1], start3[2], (start3[1] - start3[3]) * value, mPaint);
                if (value == 1) {
                    isDraw = true;
                    animateY(300);
                }
            }
        } else {
            canvas.drawLine(start[0], start[1], start[2], isDraw ? (start[1] - start[3]) * stopValue : (1 - stopValue), mPaint);
            canvas.drawLine(start1[0], start1[1], start1[2], isDraw ? (start1[1] - start1[3]) * stopValue : (1 - stopValue), mPaint);
            canvas.drawLine(start2[0], start2[1], start2[2], isDraw ? (start2[1] - start2[3]) * stopValue : (1 - stopValue), mPaint);
            canvas.drawLine(start3[0], start3[1], start3[2], isDraw ? (start3[1] - start3[3]) * stopValue : (1 - stopValue), mPaint);
        }
    }

    public void stop() {
        if (isStop)
            return;
        isStop = true;
        stopValue = value;
        invalidate();
    }

    public void start() {
        if (!isStop)
            return;
        isStop = false;
        invalidate();
    }

    private float value, stopValue;
    private ObjectAnimator animatorY;

    public void animateY(int durationMillis) {

        if (android.os.Build.VERSION.SDK_INT < 11)
            return;

        animatorY = ObjectAnimator.ofFloat(this, "phaseY", 0f, 1f);
        animatorY.setDuration(durationMillis);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // ViewCompat.postInvalidateOnAnimation(Chart.this);
                value = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animatorY.start();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        }, 5);
    }
}
