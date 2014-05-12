package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.zibilal.arthesis.app.sensor.Pointf;
import com.zibilal.arthesis.app.sensor.Transform;

/**
 * Created by bmuhamm on 5/10/14.
 */
public class TriangleView extends View {

    private Transform mTransform;
    private Path mPath1;
    private Path mPath2;
    private Path mPath3;
    private Paint mPaint;

    private Paint mTextPaint;

    private Paint mLinePaint;

    private Pointf A1;
    private Pointf B1;
    private Pointf C1;

    private Pointf A2;
    private Pointf B2;
    private Pointf C2;

    private Pointf A3;
    private Pointf B3;
    private Pointf C3;

    private Pointf X;
    private Pointf Y;
    private Pointf O;

    public TriangleView(Context context) {
        super(context);
        init();
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTransform = new Transform();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(25);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.BLUE);
        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(O.x, O.y, X.x, X.y, mLinePaint);
        canvas.drawLine(O.x, O.y, Y.x, Y.y, mLinePaint);

        canvas.drawPath(mPath1, mPaint);
        canvas.drawText("A", A1.x, A1.y, mTextPaint );
        canvas.drawText("B", B1.x, B1.y, mTextPaint);
        canvas.drawText("C", C1.x, C1.y, mTextPaint);

        canvas.drawPath(mPath2, mPaint);
        canvas.drawText("A", A2.x, A2.y, mTextPaint );
        canvas.drawText("B", B2.x, B2.y, mTextPaint);
        canvas.drawText("C", C2.x, C2.y, mTextPaint);

        mPaint.setColor(Color.DKGRAY);
        canvas.drawPath(mPath3, mPaint);
        canvas.drawText("A", A3.x, A3.y, mTextPaint );
        canvas.drawText("B", B3.x, B3.y, mTextPaint);
        canvas.drawText("C", C3.x, C3.y, mTextPaint);

        Path path = new Path();
        path.moveTo(A1.x, A1.y);
        path.lineTo(B1.x, B1.y);
        path.lineTo(C1.x, C1.y);
        path.close();

        Matrix matrix = new Matrix();
        matrix.postRotate(-20, C1.x, C1.y);
        path.transform(matrix);
        mPaint.setColor(Color.RED);
        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        if(width > height) {
            mTransform.setScale(height, -height);
            mTransform.setMove(0, height);
            mTextPaint.setTextSize(height * 0.1f);
            X = mTransform.transform(new Pointf(1.6f, 0.0f));
            Y = mTransform.transform(new Pointf(0.0f, 1.0f));
        } else  {
            mTransform.setScale(width, -width);
            mTransform.setMove(0, width);
            mTextPaint.setTextSize(width * 0.1f);
            X = mTransform.transform(new Pointf(1.0f, 0.0f));
            Y = mTransform.transform(new Pointf(0.0f, 1.6f));
        }

        O = mTransform.transform(new Pointf(0.0f, 0.0f));

        // Convert coordinates
        A1 = mTransform.transform(new Pointf(0.2f, 0.2f));
        B1 = mTransform.transform(new Pointf(0.5f, 0.5f));
        C1 = mTransform.transform(new Pointf(0.5f, 0.2f));

        A2 = mTransform.transform(new Pointf(0.2f, 0.2f));
        B2 = mTransform.transform(new Pointf(0.5f, 0.5f));
        C2 = mTransform.transform(new Pointf(0.8f, 0.2f));

        A3 = mTransform.transform(new Pointf(0.2f, 0.2f));
        B3 = mTransform.transform(new Pointf(0.5f, 0.5f));
        C3 = mTransform.transform(new Pointf(0.5f, 0.2f));

        double angle=-30;
        A2 = mTransform.rotate(A1, angle);
        B2 = mTransform.rotate(B1, angle);
        C2 = mTransform.rotate(C1, angle);

        angle=-90;
        A3 = mTransform.rotate(A1, angle);
        B3 = mTransform.rotate(B1, angle);
        C3 = mTransform.rotate(C1, angle);

        // Create path
        mPath1 = new Path();
        mPath1.moveTo(A1.x, A1.y);
        mPath1.lineTo(B1.x, B1.y);
        mPath1.lineTo(C1.x, C1.y);
        mPath1.close();

        mPath2 = new Path();
        mPath2.moveTo(A2.x, A2.y);
        mPath2.lineTo(B2.x, B2.y);
        mPath2.lineTo(C2.x, C2.y);
        mPath2.close();

        mPath3 = new Path();
        mPath3.moveTo(A3.x, A3.y);
        mPath3.lineTo(B3.x, B3.y);
        mPath3.lineTo(C3.x, C3.y);
        mPath3.close();


    }
}
