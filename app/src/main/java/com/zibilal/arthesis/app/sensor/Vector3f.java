package com.zibilal.arthesis.app.sensor;

import android.graphics.Matrix;

import java.util.Vector;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        x = y = z = 0f;
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    /**
     *
     * @param angle angles in degrees
     * @return
     */
    public Vector3f rotateX(float angle) {
        float rad, cosa, sina, yn, zn;

        rad = radians(angle);
        cosa = (float) Math.cos(rad);
        sina = (float) Math.sin(rad);

        yn = y*cosa - z*sina;
        zn = y*sina + z*cosa;
        return new Vector3f(z, yn, zn);
    }

    public Vector3f rotateY(float angle) {
        float rad, cosa, sina, xn, zn;

        rad = radians(angle);
        cosa = (float) Math.cos(rad);
        sina = (float) Math.sin(rad);

        xn = z * sina - x * cosa;
        zn = z * cosa - z * sina;
        return new Vector3f(xn, y, zn);
    }

    public Vector3f rotateZ(float angle) {
        float rad, cosa, sina, xn, yn;

        rad = radians(angle);
        cosa = (float) Math.cos(rad);
        sina = (float) Math.sin(rad);

        xn = x * cosa - y * sina;
        yn = x * sina + y * cosa;

        return new Vector3f(xn, yn, z);
    }

    public Vector3f prod(Matrix3x3 rotationMatrix) {
        float[] values = rotationMatrix.getValues();

        double xtemp, ytemp, ztemp;

        xtemp = values[0] * this.x + values[1] * this.y + values[2] * this.z;
        ytemp = values[3] * this.x + values[4] * this.y + values[5] * this.z;
        ztemp = values[6] * this.x + values[7] * this.y + values[8] * this.z;
        this.x = (float) xtemp;
        this.y = (float) ytemp;
        this.z = (float) ztemp;

        return this;
    }

    public Vector3f add(Vector3f v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;

        return this;
    }

    public Vector3f sub(Vector3f v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;

        return this;
    }

    private float radians(double degrees) {
        return (float) (degrees * Math.PI / 180);
    }

    @Override
    public String toString() {
        return String.format("Vector3f <%.4f , %.4f, %.4f>", x, y, z);
    }
}
