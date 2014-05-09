package com.zibilal.arthesis.app;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;


public class CameraSensorDataViewActivity extends Activity implements SensorEventListener {

    private static final String TAG="CameraSensorDataViewActivity";

    private SurfaceView mCameraView;
    private DataView mDataView;
    private Camera mCamera;
    private CameraSurfaceHolderCallback mCallback;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mMagneticField;

    private boolean mInPreview;
    private final float mAlpha=0.8f;
    private float[] mGravity={0, 0, 0};
    private float[] mMagnetic={0, 0, 0};

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = lowPass(sensorEvent.values.clone(), mGravity);
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagnetic = lowPass(sensorEvent.values.clone(), mMagnetic);
        }

        if(mGravity != null && mMagnetic != null ) {
            float[] R=new float[9];
            float[] I=new float[9];
            if(SensorManager.getRotationMatrix(R, I, mGravity, mMagnetic)) {
                SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_MINUS_X, SensorManager.AXIS_MINUS_Z, R);
                float[] orientation = new float[3];
                SensorManager.getOrientation(R, orientation);
                orientation[0]=(float) Math.toDegrees(orientation[0]);
                orientation[1]=(float) Math.toDegrees(orientation[1]);
                orientation[2]=(float) Math.toDegrees(orientation[2]);
                mDataView.setData(orientation);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    private float[] lowPass(float[] input, float[] output) {
        if(output == null) return input;

        for(int i=0; i<input.length; i++) {
            output[i] = output[i] + 0.25f * (input[i] - output[i]);
        }

        return output;
    }

    public class CameraSurfaceHolderCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                Log.e(TAG, "Failed set preview display -->> " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height, parameters);
            if(size!=null) {
                parameters.setPreviewSize(size.width, size.height);
                mCamera.setParameters(parameters);
                mCamera.startPreview();

                mInPreview=true;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size bestSize = null;

        for(Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if(size.width <= width && size.height <= height) {
                if(bestSize == null) {
                    bestSize = size;
                } else {
                    int bestSizeArea = bestSize.width * bestSize.height;
                    int sizeArea = size.width * size.height;

                    if(sizeArea > bestSizeArea) {
                        bestSize=size;
                    }
                }
            }
        }

        return bestSize;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_sensor_data_view);

        mCameraView = (SurfaceView) findViewById(R.id.camera_preview);
        SurfaceHolder surfaceHolder = mCameraView.getHolder();
        mCallback = new CameraSurfaceHolderCallback();
        surfaceHolder.addCallback(mCallback);

        mDataView = (DataView) findViewById(R.id.data_view);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(mInPreview) {
            mCamera.stopPreview();
        }

        mCamera.release();
        mCamera=null;
        mInPreview=false;

        mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_sensor_data_view, menu);
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
