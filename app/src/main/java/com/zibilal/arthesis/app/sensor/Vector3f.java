package com.zibilal.arthesis.app.sensor;

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

    private float radians(double degrees) {
        return (float) (degrees * Math.PI / 180);
    }
}
