package com.zibilal.arthesis.app.sensor;

/**
 * Created by bmuhamm on 5/15/14.
 */
public class Matrix3x3 {
    private float[] values;

    private static final int MAX=3;

    public Matrix3x3() {
        values = new float[9];
        for(int i=0; i < values.length;i++){
            values[i]=0.0f;
        }
    }

    public Matrix3x3 copy(Matrix3x3 matrix) {
        Matrix3x3 m = new Matrix3x3();
        m.setValues(matrix.getValues());
        return m;
    }

    public void setValues(float[] values) {
        for(int i=0; i < values.length;i++) {
            this.values[i]=values[i];
        }
    }

    public float[] getValues(){
        return values;
    }

    public Matrix3x3 add(Matrix3x3 matrix) {
        Matrix3x3 m = new Matrix3x3();
        m.setValues(getValues());
        float[] v = matrix.getValues();
        float[] dv = m.getValues();
        for(int i=0; i < dv.length;i++) {
            dv[i] += v[i];
        }
        m.setValues(dv);

        return m;
    }

    public Matrix3x3 sub(Matrix3x3 matrix) {
        Matrix3x3 m = new Matrix3x3();
        m.setValues(getValues());
        float[] v = matrix.getValues();
        float[] dv = m.getValues();
        for(int i=0; i < dv.length;i++) {
            dv[i] -= v[i];
        }
        m.setValues(dv);
        return m;
    }

    public Matrix3x3 mult(float v) {
        Matrix3x3 m = new Matrix3x3();
        m.setValues(getValues());
        float[] dv = m.getValues();

        for (int i=0; i < dv.length; i++) {
            dv[i] *= v;
        }

        m.setValues(dv);

        return m;

    }

    public Matrix3x3 prod(Matrix3x3 matrix) {
        Matrix3x3 m = new Matrix3x3();
        float[] v1 = this.getValues();
        float[] v2 = matrix.getValues();

        float[] v3 = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        int index=0;
        for(int k=0; k < MAX; k++) {
            for (int i = 0; i < MAX; i++) {
                for (int j = 0; j < MAX; j++) {
                    v3[index] += (v1[j+(MAX*k)] * v2[(MAX * j) + i]);
                }
                index++;
            }
        }

        m.setValues(v3);

        return m;
    }

    @Override
    public String toString() {
        return String.format("Matrix3x3 [ [ %.4f %.4f %.4f][%.4f %.4f %.4f][%.4f %.4f %.4f] ]", values[0],
                values[1], values[2], values[3], values[4], values[5],
                values[6], values[7], values[8]);
    }
}
