package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bmuhamm on 5/10/14.
 */
public class TriangleViewMatrix extends View {

    private Matrix mMatrix;
    private Path mPath;
    private Path mPath2;
    private Path mPath3;
    private Paint mPaint;
    private Paint mPaint2;
    private Paint mPaint3;

    public TriangleViewMatrix(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setColor(Color.LTGRAY);
        mPaint2.setStrokeWidth(25);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.GREEN);
        mPaint3.setStrokeWidth(25);

        mMatrix = new Matrix();
    }

    public TriangleViewMatrix(Context context, AttributeSet attrs) {
        super(context, attrs);

        setBackgroundColor(Color.WHITE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setColor(Color.LTGRAY);
        mPaint2.setStrokeWidth(25);

        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3.setStyle(Paint.Style.STROKE);
        mPaint3.setColor(Color.GREEN);
        mPaint3.setStrokeWidth(25);

        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        mMatrix.reset();

        int dwidth=0;
        int dheight=0;

        if(width > height) {
            mMatrix.postScale(height, -height);
            mMatrix.postTranslate(0, height);

            dwidth = width;
            dheight = height;
        } else {
            mMatrix.postScale(width, -width);
            mMatrix.postTranslate(0, height);

            dwidth = height;
            dheight = width;
        }

        // Create path
        mPath = new Path();
        mPath.moveTo(0.2f, 0.2f);
        mPath.lineTo(0.8f, 0.8f);
        mPath.lineTo(0.8f, 0.2f);
        mPath.close();

        // Convert coordinates
        mPath.transform(mMatrix);

        mPath2 = new Path();
        mPath2.moveTo(0.2f, 0.2f);
        mPath2.lineTo(0.8f, 0.8f);
        mPath2.lineTo(0.8f, 0.2f);
        mPath2.close();

        float px = dwidth * 0.5f;
        float py = dheight * 0.5f;

        mMatrix.postRotate(10, px, py );

        mPath2.transform(mMatrix);


        mPath3 = new Path();
        mPath3.moveTo(0.2f, 0.2f);
        mPath3.lineTo(0.8f, 0.8f);
        mPath3.lineTo(0.8f, 0.2f);
        mPath3.close();

        mMatrix.postRotate(-30, px, py);
        mPath3.transform(mMatrix);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mPath2, mPaint2);
        canvas.drawPath(mPath3, mPaint3);
    }
}
