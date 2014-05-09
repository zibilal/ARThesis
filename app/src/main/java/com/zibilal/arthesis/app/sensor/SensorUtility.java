package com.zibilal.arthesis.app.sensor;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class SensorUtility {

    public static float[] lowPass(float[] input, float[] output) {

        for(int i=0; i < input.length; i++) {
            output[i] = output[i] + 0.25f * (input[i] - output[i]);
        }

        return output;
    }
}
