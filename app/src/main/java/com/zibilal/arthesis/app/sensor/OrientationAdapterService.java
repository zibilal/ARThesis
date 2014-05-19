package com.zibilal.arthesis.app.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class OrientationAdapterService implements SensorEventListener {

    private SensorManager mManager;
    private Sensor mGravitySensor;
    private Sensor mMagneticSensor;
    private Context mContext;

    private float[] mGravity = {0, 0, 0};
    private float[] mMagnetic = {0, 0, 0};
    private float[] mOrientation = {0, 0, 0};

    private OnRotationChanged mListener;
    private OnSensorChanged mInvalidate;

    private int mK;

    public OrientationAdapterService(Context context){
        mContext = context;

        initService();
    }

    public void setListener(OnRotationChanged listener) {
        mListener=listener;
    }
    public void setListener2(OnSensorChanged listener2) { mInvalidate=listener2; }

    public void initService() {
        mManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mGravitySensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagneticSensor = mManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mK=100;
    }

    public void start(){
        mManager.registerListener(this, mGravitySensor, SensorManager.SENSOR_DELAY_UI);
        mManager.registerListener(this, mMagneticSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop(){
        mManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mGravity = SensorUtility.lowPass(sensorEvent.values, mGravity);
        } else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMagnetic = SensorUtility.lowPass(sensorEvent.values, mMagnetic);
        }

        float[] R = new float[9];
        float[] I = new float[9];

        if(SensorManager.getRotationMatrix(R, I, mGravity, mMagnetic)) {
            SensorManager.remapCoordinateSystem(R, SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z, R);
            Matrix3x3 matrix = new Matrix3x3();
            matrix.setValues(R);

            //SensorManager.getOrientation(R, mOrientation);
            Matrix3x3 avg = new Matrix3x3();
            for(int i=0; i <mK; i++) {
                avg = avg.add(matrix);
            }
            avg = avg.mult(1.0f/mK);

            mListener.update(avg);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public interface OnRotationChanged {
        public void update(Matrix3x3 matrix);
    }

    public interface OnSensorChanged {
        public void postInvalidate();
    }
}
