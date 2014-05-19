package com.zibilal.arthesis.app.sensor;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class SensorUtility {

    private static float A=0.1f;

    public static float[] lowPass(float[] input, float[] output) {
        /*
        for(int i=0; i < input.length; i++) {
            output[i] = output[i] + 0.25f * (input[i] - output[i]);
        }*/

        //float[] result = new float[input.length];

        for(int i=0; i < input.length; i++) {
            output[i] = output[i] * (1.0f - A) + input[i] * A;
        }

        return output;
    }
}
