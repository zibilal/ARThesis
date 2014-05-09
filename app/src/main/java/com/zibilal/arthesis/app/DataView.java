package com.zibilal.arthesis.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bmuhamm on 4/19/14.
 */
public class DataView extends View {

    private int mWidth;
    private int mHeight;

    private Paint mPaint;

    private float[] mLinearAcceleration;

    public DataView(Context context) {
        super(context);

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mLinearAcceleration = new float[3];
    }

    public DataView(Context context, AttributeSet attrset) {
        super(context, attrset);

        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mLinearAcceleration = new float[3];
    }

    public void setData(float[] linearAcceleration) {
        mLinearAcceleration=linearAcceleration;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth=w;
        mHeight=h;
    }

    @Override
    public void draw(Canvas canvas) {
        float textSize = Math.min(mWidth, mHeight) * 0.08f;
        mPaint.setTextSize(textSize);

        float x = mWidth * 0.5f;
        float y = mHeight * 0.5f;

        String str = String.format("Azimuth=%.2f, Pitch=%.2f, Roll=%.2f", mLinearAcceleration[0], mLinearAcceleration[1], mLinearAcceleration[2]);
        canvas.drawText(str, x, y, mPaint);
    }
}
