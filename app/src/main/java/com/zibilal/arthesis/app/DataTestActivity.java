package com.zibilal.arthesis.app;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.location.LocationService;
import com.zibilal.arthesis.app.location.Point;
import com.zibilal.arthesis.app.model.Geoname;


public class DataTestActivity extends Activity {

    private static final String TAG="";

    private TextView resultCalculation;
    private TextView resultProjection;
    private EditText latitudeInput;
    private EditText longitudeInput;
    private EditText originalMatrixInput;

    private TextView textCurrentLocation;

    private LocationService locationService;
    private Location currentLocation;

    private CameraTransformation cameraTransformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_test);

        resultCalculation = (TextView) findViewById(R.id.result_location);
        resultProjection = (TextView) findViewById(R.id.result_projection);
        latitudeInput = (EditText) findViewById(R.id.lat_number);
        longitudeInput = (EditText) findViewById(R.id.lon_number);
        originalMatrixInput = (EditText) findViewById(R.id.original_matrix_number);
        textCurrentLocation = (TextView) findViewById(R.id.textCurrentLocation);

        locationService = new LocationService(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width=0;
        int height=0;
        if(displayMetrics.heightPixels > displayMetrics.widthPixels) {
            width=displayMetrics.heightPixels;
            height=displayMetrics.widthPixels;
        } else {
            width=displayMetrics.widthPixels;
            height=displayMetrics.heightPixels;
        }

        cameraTransformation = new CameraTransformation(width, height);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService.start();
    }

    @Override
    protected void onStop() {
        locationService.stop();
        super.onStop();
    }

    public void onCalculateLocation(View view) {
        String strlat = latitudeInput.getText().toString();
        String strlon = longitudeInput.getText().toString();
        calculateLocation(Double.parseDouble(strlat), Double.parseDouble(strlon));

        /*
        float[] val = new float[3];

        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                Double.parseDouble(strlat), Double.parseDouble(strlon), val);
        String pointStr = String.format("Calculated vector: val[0]=%.4f val[1]=%.4f val[2]=%.4f", val[0], val[1], val[2]);
        resultCalculation.setText(pointStr);
        */

    }

    public void onCalculateProjection(View view) {
        String matrixInput = originalMatrixInput.toString();

        String[] number = matrixInput.split(",");

        double x = Double.parseDouble(number[0]);
        double y = Double.parseDouble(number[1]);
        double z = Double.parseDouble(number[2]);

        calculateProjection(x, y, z);
    }

    public void onCurrentLocation(View view) {
        currentLocation = locationService.getCurrentLocation();

        String str="-";

        if(currentLocation != null) {
            str = String.format("Lat=%.4f , Lon=%.4f", currentLocation.getLatitude(), currentLocation.getLongitude());
        }
        textCurrentLocation.setText(str);
    }

    private void calculateLocation(double lat, double lon) {
        Geoname geoname = new Geoname();
        geoname.setLat(lat);
        geoname.setLng(lon);
        /*

        Point point = cameraTransformation.convertToVector(currentLocation, geoname);

        String pointStr = String.format("Calculated vector: x=%.4f y=%.4f z=%.4f", point.getX(), point.getY(), point.getZ());
        resultCalculation.setText(pointStr); */
    }

    private void calculateProjection(double x, double y, double z){
        Point point = new Point(x, y, z);
        Point rPoint = cameraTransformation.projectPoint(point);

        String pointStr = String.format("Calculated vector: x=%.4f y=%.4f z=%.4f", rPoint.getX(), rPoint.getY(), rPoint.getZ());
        resultProjection.setText(pointStr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.data_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
