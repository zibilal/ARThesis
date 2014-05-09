package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by bmuhamm on 5/2/14.
 */
public class ARView extends View {

    private int color1 = 0xff00ff00;
    private int color2 = 0xff005500;

    private float centerX, centerY, radius;

    private RadialGradient gradient;
    private PaintScreen paintScreen;

    public ARView(Context context) {
        super(context);

        paintScreen = new PaintScreen();
        paintScreen.setFill(true);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        centerX = width / 2.0f;
        centerY = height / 2.0f;

        radius = Math.min(width, height) / 3.0f;

        gradient = new RadialGradient(centerX - radius * 0.3f,
                centerY - radius * 0.3f, radius*1.3f, color1, color2, Shader.TileMode.CLAMP);
        paintScreen.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintScreen.setCanvas(canvas);
        paintScreen.paintCircle(centerX, centerY, radius);
    }
}