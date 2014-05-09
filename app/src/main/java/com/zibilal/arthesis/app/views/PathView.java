package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by bmuhamm on 5/4/14.
 */
public class PathView extends View {

    private static final String TAG="PathView";

    private Paint mPaint;
    private Path mPath;

    private float mPaddingX;
    private float mPaddingY;

    private float mCenterX;
    private float mCenterY;

    private float mX1;
    private float mY1;
    private float mX2;
    private float mY2;
    private float mX3;
    private float mY3;
    private float mX4;
    private float mY4;

    class Point {
        float x;
        float y;


    }

    public PathView(Context context) {
        super(context);
        init();
    }

    public PathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPath = new Path();

        mPaddingX = 100;
        mPaddingY = 100;
    }

    public void updateOrientation(float[] orientation){
        double odegrees1 = Math.toDegrees(orientation[0]);
        double odegrees2 = Math.toDegrees(orientation[1]);
        double odegrees3 = Math.toDegrees(orientation[2]);

        Log.d(TAG, String.format("------>>Azimuth=%.4f , Pitch=%.4f, Roll=%.4f", odegrees1, odegrees2, odegrees3));

        double cosa, sina, xn, yn;
        cosa = Math.cos(orientation[0]);
        sina = Math.sin(orientation[0]);

        //postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Path p = new Path();
        p.moveTo(mX1, mY1);
        p.lineTo(mX2, mY2);
        p.lineTo(mX3, mY3);
        p.lineTo(mX4, mY4);
        p.close();
        canvas.drawPath(p, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2.0f;
        mCenterY = h / 2.0f;

        mX1 = mPaddingX;
        mY1 = mPaddingY;
        mX4 = 2 * (mCenterX - mPaddingX);
        mY4 = 2 * (mCenterY - mPaddingY);
        mX2 = mX4;
        mY2 = mY1;
        mX3 = mX1;
        mY3 = mY4;
    }
}
