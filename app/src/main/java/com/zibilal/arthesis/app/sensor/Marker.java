package com.zibilal.arthesis.app.sensor;

import android.graphics.Matrix;
import android.location.Location;

import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.views.PaintScreen;

/**
 * Created by bmuhamm on 5/13/14.
 */
public class Marker {

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

    public boolean isVisible(){
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible=visible;
    }

    public void calculatePaint() {
        Vector3f tmp = new Vector3f(0f, 0f, 0f);
        tmp.add(locationVector);

        Matrix m = cameraTransformation.getRotationMatrix();
        tmp.prod(m);
        locationVector = cameraTransformation.projectPoint(tmp);
    }

    public Vector3f convertToVector(Location currentLocation) {

        float[] z = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                latitude, currentLocation.getLongitude(), z );
        float[] x = {0};
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                currentLocation.getLatitude(), longitude, x);

        float y = (float) ( altitude - currentLocation.getAltitude() );
        if(currentLocation.getLatitude() < latitude) {
            z[0] *= -1;
        }
        if(currentLocation.getLongitude() > longitude) {
            x[0] *= -1;
        }

        Vector3f v = new Vector3f(x[0], y, z[0]);
        return v;
    }

    public void draw(PaintScreen ps) {
        if(isVisible()) {
            calculatePaint();
            float radius = Math.min(ps.getWidth(), ps.getHeight()) / 50.0f;
            ps.paintCircle(locationVector.x, locationVector.y, radius);
        }
    }
}
