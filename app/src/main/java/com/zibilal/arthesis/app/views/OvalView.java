package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bmuhamm on 5/4/14.
 */
public class OvalView extends View {

    private Paint mPaint;
    private float mPadding;
    private RectF rect;

    public OvalView(Context context) {
        super(context);
        init();
    }

    public OvalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        rect = new RectF();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        mPadding = Math.max(width, height) * 0.1f;

        rect.left = mPadding;
        rect.right = width - mPadding;
        rect.top = mPadding;
        rect.bottom = height - mPadding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(mPadding / 12);
        mPaint.setColor(Color.RED);
        canvas.drawOval(rect, mPaint);

        mPaint.setStrokeWidth(mPadding / 12);
        mPaint.setColor(0xff000077);
        canvas.drawRect(rect, mPaint);

    }
}
