package com.zibilal.arthesis.app.views;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class Vector3d {
    private double x;
    private double y;
    private double z;

    public Vector3d(){
        x = y = z = 0;
    }

    public Vector3d(double x, double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public Vector3d rotateX(double angle) {
        double rad, cosa, sina, yn, zn;

        rad = radians(angle);
        cosa = Math.cos(rad);
        sina = Math.sin(rad);
        yn = this.y * cosa - this.z * sina;
        zn = this.y * sina + this.z * cosa;

        return new Vector3d(x, yn, zn);
    }

    public Vector3d rotateY(double angle) {
        double rad, cosa, sina, xn, zn;

        rad = radians(angle);
        cosa = Math.cos(rad);
        sina = Math.sin(rad);

        zn = z * cosa - x * sina;
        xn = z * sina - x * cosa;

        return new Vector3d(xn, y, zn);
    }

    public Vector3d rotateZ(double angle) {
        double rad, cosa, sina, xn, yn;

        rad = radians(angle);
        cosa = Math.cos(rad);
        sina = Math.sin(rad);

        xn = x * cosa - y * sina;
        yn = x * sina + y * cosa;

        return new Vector3d(xn, yn, z);
    }

    public Vector3d project (int viewWidth, int viewHeight, float fov, float viewDistance) {
        double factor, xn, yn;

        factor = fov / (viewDistance+z);
        xn = x * factor + viewWidth / 2;
        yn = y * factor + viewHeight / 2;

        return new Vector3d(xn, yn, z);
    }

    private double radians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
