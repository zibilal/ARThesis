package com.zibilal.arthesis.app.location;

import android.graphics.Matrix;
import android.location.Location;
import android.util.Log;

import com.zibilal.arthesis.app.model.Geoname;
import com.zibilal.arthesis.app.sensor.Matrix3x3;
import com.zibilal.arthesis.app.sensor.Vector3f;

/**
 * Created by bmuhamm on 4/20/14.
 */
public class CameraTransformation {

    public static final int DEFAULT_VIEW_ANGLE_DEGREES=45;
    private static final String TAG="CameraTransformation";

    private double viewDistance;
    private int displayWidth;
    private int displayHeight;

    private Matrix3x3 rotationMatrix;

    public CameraTransformation(int width, int height){
        displayWidth=width;
        displayHeight=height;
        double rad = Math.toRadians(DEFAULT_VIEW_ANGLE_DEGREES /2 );
        float tan = (float) Math.tan(rad);
        viewDistance= (displayWidth/2) / tan;
        String str = String.format("Angle=%d ", 22);
        Log.d(TAG, str);
    }

    public Point convertToPoint(Location currentLocation, Geoname poiLocation) {
        String str1 = String.format("Convert to Vector --> VD=%.2f , width=%d, height=%d", viewDistance, displayWidth, displayHeight);
        Log.d(TAG, str1);

        float[] z = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                poiLocation.getLat(), currentLocation.getLongitude(), z);

        float[] x={0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                currentLocation.getLatitude(), poiLocation.getLng(), x);

        double y = poiLocation.getElavation() - currentLocation.getAltitude();

        if (currentLocation.getLatitude() < poiLocation.getLat()) z[0] *= -1;
        if (currentLocation.getLongitude() > poiLocation.getLng()) x[0] *= -1;

        String str = String.format("Point [%s] x=%.2f,y=%.2f,z=%.2f", poiLocation.getTitle(), x[0], y, z[0]);

        Log.d(TAG, str);

        return new Point(x[0], y, z[0]);
    }

    public void updateRotation(Matrix3x3 matrix) {
        rotationMatrix = matrix;
    }

    public Matrix3x3 getRotationMatrix(){
        return rotationMatrix;
    }

    public Point projectPoint(Point originalPoint) {
        double x, y, z;
        x = viewDistance * originalPoint.getX() / originalPoint.getZ();
        y = viewDistance * originalPoint.getY() / originalPoint.getZ();
        z = originalPoint.getZ();

        String str = String.format("Projected point x=%.2f,y=%.2f,z=%.2f", x, y, z);
        Log.d(TAG, str);

        Point point = new Point(x, y, z);
        return point;
    }

    public Vector3f projectPoint(Vector3f original) {
        float x, y, z;
        x = (float) viewDistance * original.x / -original.z;
        y = (float) viewDistance * original.y / -original.z;
        z = original.z;

        x = x + displayWidth/2;
        y = -y + displayHeight/2;

        Log.d("CameraTransformation", String.format("Display width = %d , height = %d", displayWidth, displayHeight) );
        Log.d("CameraTransformation", String.format("View distance %.4f", viewDistance));

        Vector3f v = new Vector3f(x, y, z);
        return v;
    }

    public Vector3f convertToVector(Location currentLocation, Geoname poiLocation) {
        String str1 = String.format("Convert to Vector --> VD=%.2f , width=%d, height=%d", viewDistance, displayWidth, displayHeight);
        Log.d(TAG, str1);

        float[] z = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                poiLocation.getLat(), currentLocation.getLongitude(), z);

        float[] x={0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                currentLocation.getLatitude(), poiLocation.getLng(), x);

        float y = (float) (poiLocation.getElavation() - currentLocation.getAltitude());

        if (currentLocation.getLatitude() < poiLocation.getLat()) z[0] *= -1;
        if (currentLocation.getLongitude() > poiLocation.getLng()) x[0] *= -1;

        String str = String.format("Point [%s] x=%.2f,y=%.2f,z=%.2f", poiLocation.getTitle(), x[0], y, z[0]);

        Log.d(TAG, str);

        return new Vector3f(x[0], y, z[0]);
    }

}
