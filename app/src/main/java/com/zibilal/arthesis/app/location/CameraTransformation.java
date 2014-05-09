package com.zibilal.arthesis.app.location;

import android.location.Location;
import android.util.Log;

import com.zibilal.arthesis.app.model.Geoname;

/**
 * Created by bmuhamm on 4/20/14.
 */
public class CameraTransformation {

    public static final int DEFAULT_VIEW_ANGLE_DEGREES=30;
    private static final String TAG="CameraTransformation";

    private double viewDistance;
    private int displayWidth;
    private int displayHeight;

    public CameraTransformation(int width, int height){
        displayWidth=width;
        displayHeight=height;



        double rad = Math.toRadians(DEFAULT_VIEW_ANGLE_DEGREES/2);
        viewDistance= (displayWidth/2) / Math.tan(rad);
        String str = String.format("Angle=%d , Tan(Angle)=%.2f", DEFAULT_VIEW_ANGLE_DEGREES/2, Math.tan(rad));
        Log.d(TAG, str);
    }

    public Point convertToVector(Location currentLocation, Geoname poiLocation) {
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

}
