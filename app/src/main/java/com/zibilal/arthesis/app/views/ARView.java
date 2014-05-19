package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.location.Location;
import android.view.View;

import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.sensor.Marker;
import com.zibilal.arthesis.app.sensor.Matrix3x3;
import com.zibilal.arthesis.app.sensor.MovingAverageRotationMatrix;

/**
 * Created by bmuhamm on 5/2/14.
 */
public class ARView extends View {

    private int color1 = 0xff00ff00;
    private int color2 = 0xff005500;

    private float centerX, centerY, radius;

    private RadialGradient gradient;
    private PaintScreen paintScreen;

    private Location currentLocation;
    private Marker pondokIndahMarker;
    private CameraTransformation cameraTransformation;
    private MovingAverageRotationMatrix maRotationMatrix;

    private int k;

    public ARView(Context context, CameraTransformation cameraTransformation) {
        super(context);

        paintScreen = new PaintScreen();
        paintScreen.setFill(true);
        this.cameraTransformation=cameraTransformation;
        pondokIndahMarker = new Marker(this.cameraTransformation);
        pondokIndahMarker.setVisible(true);

        k = 60;
        maRotationMatrix = new MovingAverageRotationMatrix(k);
    }

    public void setMovingAverageSize(int k) {
        this.k=k;
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        centerX = width / 2.0f;
        centerY = height / 2.0f;

        radius = Math.min(width, height) / 50.0f;

        gradient = new RadialGradient(centerX - radius * 0.3f,
                centerY - radius * 0.3f, radius*1.3f, color1, color2, Shader.TileMode.CLAMP);
        paintScreen.setWidth(width);
        paintScreen.setHeight(height);
        paintScreen.setShader(gradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintScreen.setCanvas(canvas);
        paintScreen.paintCircle(centerX, centerY, radius);

        pondokIndahMarker.draw(paintScreen);
    }

    public void setLocation(Location location){
        currentLocation = location;
        pondokIndahMarker.update(currentLocation);
    }

    public void updateLocation(Location location) {

        pondokIndahMarker.update(location);
    }

    public void reInvalidate() {
        postInvalidate();
    }

    public void updatePosition(Matrix3x3 matrix) {
        //smoothR2(matrix);

        cameraTransformation.updateRotation(matrix);
        postInvalidate();

    }

    private Matrix3x3 avg = new Matrix3x3();
    private void smoothR2(Matrix3x3 matrix) {
        for(int i=0; i < k; i++) {
            avg = avg.add(matrix);
        }
        avg = avg.mult(1.0f / k);
        cameraTransformation.updateRotation(avg);
        postInvalidate();
    }

    private void smoothR(Matrix3x3 matrix){
        maRotationMatrix.pushMatrix(matrix);
        //if(maRotationMatrix.getCount() == maRotationMatrix.getLength()-1 ) {
            cameraTransformation.updateRotation(maRotationMatrix.getRotationMatrix());
        //}
    }
}
