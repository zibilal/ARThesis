package com.zibilal.arthesis.app;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.sensor.Marker;
import com.zibilal.arthesis.app.sensor.Matrix3x3;
import com.zibilal.arthesis.app.sensor.Vector3f;


public class TestNumberActivity extends Activity {

    private static final String TAG="TestNumberActivity";

    private float[] rotationMatrix = {0.60889655f,0.03857504f,-0.79168236f,-0.06930594f,0.99685f,-0.0054203114f,0.78930926f,0.057861723f,0.610161f};
    private float[] originalLocation = { -6.2657f, 106.7843f };
    private float[] viewerLocation = {-6.2897f , 106.8125f};

    private Matrix3x3 mMatrix;
    private Location mOriginalLocation;
    private Location mViewerLocation;

    private TextView originalLocationText;
    private TextView matrixLocationText;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_number);

        initNumbers();
        originalLocationText = (TextView) findViewById(R.id.ori_location);
        originalLocationText.setText(mOriginalLocation.toString());
        matrixLocationText = (TextView) findViewById(R.id.rot_matrix);
        matrixLocationText.setText(mMatrix.toString());
        resultText = (TextView) findViewById(R.id.result_calculation);
    }

    public void initNumbers() {
        mMatrix = new Matrix3x3();
        mMatrix.setValues(rotationMatrix);

        mOriginalLocation = new Location("gps");
        mOriginalLocation.setLatitude(originalLocation[0]);
        mOriginalLocation.setLongitude(originalLocation[1]);

        mViewerLocation = new Location("gps");
        mViewerLocation.setLatitude(viewerLocation[0]);
        mViewerLocation.setLongitude(viewerLocation[1]);
    }

    public void onCount(View view) {
        CameraTransformation cameraTransformation = new CameraTransformation(1280, 670);
        Marker marker = new Marker(cameraTransformation);
        marker.setLatitude((float) mOriginalLocation.getLatitude());
        marker.setLongitude((float) mOriginalLocation.getLongitude());
        Vector3f result1 = marker.updateTest(mViewerLocation);
        Log.d(TAG, "Location vector = " + result1);
        Vector3f result2 = marker.calculatePaintTest(mMatrix);
        resultText.setText(result2.toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_number, menu);
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
