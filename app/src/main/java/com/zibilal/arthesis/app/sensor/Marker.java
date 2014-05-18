package com.zibilal.arthesis.app.sensor;

import android.location.Location;
import android.util.Log;

import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.views.PaintScreen;

/**
 * Created by bmuhamm on 5/13/14.
 */
public class Marker {

    private static final String TAG="Marker";

    private float latitude;
    private float longitude;
    private float altitude;

    private Vector3f locationVector;
    private CameraTransformation cameraTransformation;
    private boolean visible;

    public Marker(CameraTransformation cameraTransformation) {
        latitude=-6.265708333333333f;
        longitude=106.78429722222222f;
        altitude=0f;
        this.cameraTransformation=cameraTransformation;
    }

    public void update(Location location) {
        if(altitude == 0) {
            altitude = (float) location.getAltitude();
        }
        locationVector = convertToVector(location);
    }

    public void setLatitude(float latitude){
        this.latitude=latitude;
    }

    public void setLongitude(float longitude){
        this.longitude=longitude;
    }

    // TODO test purpose. delete if finished

    public Vector3f updateTest(Location location) {
        if(altitude == 0) {
            altitude = (float) location.getAltitude();
        }
        locationVector = convertToVector(location);
        return locationVector;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible=visible;
    }

    public void calculatePaint() {
        Vector3f tmp = new Vector3f(0f, 0f, 0f);
        tmp.add(locationVector);
        Log.d("Marker", "Location vector = " + locationVector);
        Log.d("Marker", "Tmp1 = " + tmp);
        Matrix3x3 m = cameraTransformation.getRotationMatrix();
        Log.d("Marker", "Rotation matrix = " + m);
        tmp.prod(m);
        Log.d("Marker", "Tmp2 = " + tmp);
        locationVector = cameraTransformation.projectPoint(tmp);
        Log.d("Marker", "Location vector2 = " + locationVector);
    }

    // TODO test only, delete if finish
    public Vector3f calculatePaintTest(Matrix3x3 rotationMatrix) {
        Vector3f tmp = new Vector3f(0f, 0f, 0f);
        tmp.add(locationVector);
        Log.d("Marker", String.format(" Original location vector = %s ", tmp));
        tmp.prod(rotationMatrix);
        Log.d("Marker", String.format(" Multiplied by rotation vector = %s", tmp));

        locationVector = cameraTransformation.projectPoint(tmp);
        return locationVector;
    }

    public Vector3f convertToVector(Location currentLocation) {

        Log.d("Marker", String.format("Current Location = latitude = %.4f longitude = %.4f", currentLocation.getLatitude(), currentLocation.getLongitude()));
        Log.d("Marker", String.format("Pondok Indah Mall = latitude = %.4f longitude = %.4f", latitude, longitude));

        float[] z = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                latitude, currentLocation.getLongitude(), z );
        float[] x = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                currentLocation.getLatitude(), longitude, x);

        float y =  (float) ( altitude - currentLocation.getAltitude() );
        if(currentLocation.getLatitude() < latitude) {
            z[0] *= -1;
        }
        if(currentLocation.getLongitude() > longitude) {
            x[0] *= -1;
        }

        Log.d("Marker", String.format("Results = x = %.4f , y = %.4f , z = %.4f", x[0], y, z[0]));

        Vector3f v = new Vector3f(x[0], y, z[0]);
        return v;
    }

    public void draw(PaintScreen ps) {
        if(isVisible() && locationVector != null) {
            calculatePaint();
            float radius = Math.min(ps.getWidth(), ps.getHeight()) / 30.0f;
            ps.paintCircle(locationVector.x, locationVector.y, radius);
        }
    }
}
