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

    private Point p1 = new Point();
    private Point p2 = new Point();
    private Point p3 = new Point();
    private Point p4 = new Point();

    class Point {
        float x;
        float y;

        Point rotateZ(double angle) {
            double rad = Math.toRadians(angle);
            double cosa = Math.cos(rad);
            double sina = Math.sin(rad);
            float x = (float) (this.x * cosa - this.y * sina);
            float y = (float) (this.x * sina + this.y * cosa);
            Point p = new Point();
            p.x = x;
            p.y = y;
            return p;
        }
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
        // Rotate Z only
        p1 = p1.rotateZ(Math.toDegrees(orientation[0]));
        p2 = p2.rotateZ(Math.toDegrees(orientation[0]));
        p3 = p3.rotateZ(Math.toDegrees(orientation[0]));
        p4 = p4.rotateZ(Math.toDegrees(orientation[0]));

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        p1.x = mPaddingX;
        p1.y = mPaddingY;
        p3.x = 2 * (mCenterX - mPaddingX);
        p3.y = 2 * (mCenterY - mPaddingY);
        p2.x = p3.x;
        p2.y = p1.y;
        p4.x = p1.x;
        p4.y = p3.y;

        Path p = new Path();
        p.moveTo(p1.x, p1.y);
        p.lineTo(p2.x, p2.y);
        p.lineTo(p3.x, p3.y);
        p.lineTo(p4.x, p4.y);
        p.close();
        canvas.drawPath(p, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCenterX = w / 2.0f;
        mCenterY = h / 2.0f;

        p1.x = mPaddingX;
        p1.y = mPaddingY;
        p3.x = 2 * (mCenterX - mPaddingX);
        p3.y = 2 * (mCenterY - mPaddingY);
        p2.x = p3.x;
        p2.y = p1.y;
        p4.x = p1.x;
        p4.y = p3.y;
    }
}
