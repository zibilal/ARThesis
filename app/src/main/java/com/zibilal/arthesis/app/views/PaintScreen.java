package com.zibilal.arthesis.app.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

/**
 * Created by bmuhamm on 5/2/14.
 */
public class PaintScreen {
    private Canvas mCanvas;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    public PaintScreen() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    public Canvas getCanvas(){
        return mCanvas;
    }

    public void setFill(boolean fill) {
        if(fill) {
            mPaint.setStyle(Paint.Style.FILL);
        } else {
            mPaint.setStyle(Paint.Style.STROKE);
        }
    }

    public void setShader(Shader shader) {
        mPaint.setShader(shader);
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getWidth(){
        return mWidth;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getHeight(){
        return mHeight;
    }

    public void setStrokeWidth(float w) {
        mPaint.setStrokeWidth(w);
    }

    public void paintCircle(float x, float y, float radius) {
        getCanvas().drawCircle(x, y, radius, mPaint);
    }
}
