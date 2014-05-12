package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zibilal.arthesis.app.sensor.Pointf;

/**
 * Created by bmuhamm on 5/11/14.
 */
public class CoordinateView extends View {

    private Paint mPaint;

    private Pointf origin;
    private Pointf rightBottom;

    private Pointf startX;
    private Pointf startY;

    private Path mAxisX;
    private Path mAxisY;

    private Matrix mMatrix;

    public CoordinateView(Context context) {
        super(context);
        init();
    }

    public CoordinateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.DKGRAY);
        mPaint.setStrokeWidth(5f);

        origin = new Pointf();
        rightBottom = new Pointf();
        startX = new Pointf();
        startY = new Pointf();
        mMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {

        rightBottom.x=width;
        rightBottom.y=height;

        origin.x=rightBottom.x * 0.5f;
        origin.y=rightBottom.y * 0.5f;

        if(width > height ) {
            mMatrix.postTranslate(-origin.y, origin.x);
        } else {
            mMatrix.postTranslate(-origin.x, origin.y);
        }

        startX.x = 0f;
        startX.y = origin.y;

        startY.x = origin.x;
        startY.y = 0f;

        mAxisX = new Path();
        mAxisX.moveTo(startX.x, startX.y);
        mAxisX.lineTo(rightBottom.x, startX.y);

        mAxisX.transform(mMatrix);

        mAxisY = new Path();
        mAxisY.moveTo(startY.x, startY.y);
        mAxisY.lineTo(startY.x, rightBottom.y);
        mAxisY.transform(mMatrix);

        Log.d("CoordinateView", "Test");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mAxisX, mPaint);
        canvas.drawPath(mAxisY, mPaint);
    }
}
