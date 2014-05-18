package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bmuhamm on 5/15/14.
 */
public class VectorValueView extends View{

    private int width;
    private int height;
    private Paint paint;

    private static final int TEXT_SIZE=70;

    public VectorValueView(Context context) {
        super(context);
    }

    public VectorValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.SANS_SERIF);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width=w;
        height=h;
    }

    private void center(Canvas canvas, String str, int y) {
        int x = width / 2;

        Rect bounds = new Rect();
        //paint.getTextBounds();
    }
}
