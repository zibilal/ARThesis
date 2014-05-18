package com.zibilal.arthesis.app.sensor;

import android.util.Log;

/**
 * Created by bmuhamm on 5/15/14.
 */
public class MovingAverageRotationMatrix {

    private static final String TAG="MovingAverageRotationMatrix";

    private Matrix3x3[] circularBuffer;
    private Matrix3x3 avg;
    private int circularIndex;
    private int count;
    private int k;

    public MovingAverageRotationMatrix(int k) {
        circularBuffer = new Matrix3x3[k];
        count = 0;
        circularIndex = 0;
        avg = new Matrix3x3();
        this.k = k;
    }

    public int getLength(){
        return k;
    }

    public Matrix3x3 getRotationMatrix() {
        return avg;
    }

    public void pushMatrix(Matrix3x3 m) {
        if(count++ == 0) {
            primeBuffer(m);
        }

        Matrix3x3 lastMatrix = circularBuffer[circularIndex];

        Log.d(TAG, String.format("---> Matrix m %s", m));
        Matrix3x3 mSub = m.sub(lastMatrix);
        Log.d(TAG, String.format("---> Matrix mSub %s ", mSub));
        Log.d(TAG, String.format("---> CircularBuffer length %.4f", (1/circularBuffer.length) ));
        Matrix3x3 mSubMult = mSub.mult(1/circularBuffer.length);
        Log.d(TAG, String.format("---> Matrix mSubMult %s", mSubMult));
        Matrix3x3 mAvg = avg.add(mSubMult);
        Log.d(TAG, String.format("---> Matrix mAvg %s", mAvg));

        avg = mAvg; // avg.add(m.sub(lastMatrix).mult(1/circularBuffer.length));
        circularBuffer[circularIndex]  = m;
        count=(circularIndex+1) >= circularBuffer.length ? 0:count;
        circularIndex=(circularIndex+1 >= circularBuffer.length)?0:circularIndex+1;
    }

    public int getCount(){
        return count;
    }

    private void primeBuffer(Matrix3x3 m) {
        for(int i=0; i < circularBuffer.length; ++i) {
            circularBuffer[i] = m;
        }

        avg = m;
    }
}
