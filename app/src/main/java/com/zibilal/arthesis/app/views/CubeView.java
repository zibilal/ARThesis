package com.zibilal.arthesis.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zibilal.arthesis.app.sensor.Vector3f;

/**
 * Created by bmuhamm on 5/3/14.
 */
public class CubeView extends View {

    // Vertices of the cube.
    private Vector3d vertices[];

    // Define the indices to the vertices of each face of the cube. */
    private int faces[][];

    // Colors of each face of the cube.
    private int colors[];

    // Orientation of the cube around X axis.
    private float ax;

    // Orientation of the cube around Y axis.
    private float ay;

    // Orientation of the cube around Z axis.
    private float az;

    private float lastTouchX;
    private float lastTouchY;

    Paint paint;

    public CubeView(Context context) {
        super(context);
        init();
    }

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void updateOrientation(float[] orientation) {
        ax = (float) Math.toDegrees(orientation[0]);
        ay = (float) Math.toDegrees(orientation[1]);
        az = (float) Math.toDegrees(orientation[2]);

        postInvalidate();
    }

    public void init() {
        vertices = new Vector3d[] {
                new Vector3d(-1, 1, -1),
                new Vector3d(1, 1, -1),
                new Vector3d(1, -1, -1),
                new Vector3d(-1, -1, -1),
                new Vector3d(-1, 1, 1),
                new Vector3d(1, 1, 1),
                new Vector3d(1, -1, 1),
                new Vector3d(-1, -1, 1),
        };

        faces = new int[][] {
                {0, 1, 2, 3},
                {1, 5, 6, 2},
                {5, 4, 7, 6},
                {4, 0, 3, 7},
                {0, 4, 5, 1},
                {3, 2, 6, 7}
        };

        colors = new int[] {
                Color.BLUE, Color.RED, Color.YELLOW, Color.LTGRAY, Color.CYAN, Color.MAGENTA
        };

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        init();

        // Allow the view to receive touch input
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            lastTouchX = event.getX();
            lastTouchY = event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = (event.getX() - lastTouchX) / 30.0f;
            float dy = (event.getY() - lastTouchY) / 30.0f;

            ax += dy;
            ay -= dx;
            postInvalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Vector3d t[] = new Vector3d[8];
        double avgZ[] = new double[6];
        int order[] = new int[6];

        for (int i=0; i < 8; i++) {
            t[i] = vertices[i].rotateX(ax).rotateY(ay).rotateZ(az);
            t[i] = t[i].project(getWidth(), getHeight(), 256, 4);
        }

        // Compute the average Z value of each face
        for(int i=0; i < 6; i++) {
            avgZ[i] = (t[faces[i][0]].getZ() + t[faces[i][1]].getZ() + t[faces[i][2]].getZ() + t[faces[i][3]].getZ() ) / 4;
            order[i] = i;
        }

        for (int i=0; i < 6; i++) {
            int iMax=i;
            for(int j=i + 1; j < 6; j++) {
                if(avgZ[iMax] < avgZ[j]) {
                    iMax=j;
                }
            }

            if(iMax != i) {
                double dTmp = avgZ[i];
                avgZ[i] = avgZ[iMax];
                avgZ[iMax] = dTmp;

                int iTmp = order[i];
                order[i] = order[iMax];
                order[iMax] = iTmp;
            }
        }

        for(int i=0; i < 6; i++) {
            int index = order[i];

            Path p = new Path();
            p.moveTo((float)t[faces[index][0]].getX(), (float)t[faces[index][0]].getY());
            p.lineTo((float)t[faces[index][1]].getX(), (float)t[faces[index][1]].getY());
            p.lineTo((float)t[faces[index][2]].getX(), (float)t[faces[index][2]].getY());
            p.lineTo((float)t[faces[index][3]].getX(), (float)t[faces[index][3]].getY());
            p.close();

            paint.setColor(colors[index]);
            canvas.drawPath(p, paint);
        }

    }

    public Vector3d[] getVertices() {
        return vertices;
    }

    public void setVertices(Vector3d[] vertices) {
        this.vertices = vertices;
    }

    public int[][] getFaces() {
        return faces;
    }

    public void setFaces(int[][] faces) {
        this.faces = faces;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public float getAx() {
        return ax;
    }

    public void setAx(float ax) {
        this.ax = ax;
    }

    public float getAy() {
        return ay;
    }

    public void setAy(float ay) {
        this.ay = ay;
    }

    public float getAz() {
        return az;
    }

    public void setAz(float az) {
        this.az = az;
    }

    public float getLastTouchX() {
        return lastTouchX;
    }

    public void setLastTouchX(float lastTouchX) {
        this.lastTouchX = lastTouchX;
    }

    public float getLastTouchY() {
        return lastTouchY;
    }

    public void setLastTouchY(float lastTouchY) {
        this.lastTouchY = lastTouchY;
    }
}
