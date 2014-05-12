package com.zibilal.arthesis.app;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import com.zibilal.arthesis.app.sensor.OrientationAdapterService;
import com.zibilal.arthesis.app.sensor.Vector3f;
import com.zibilal.arthesis.app.views.CubeView;

import java.io.IOException;
import java.util.List;


public class CameraPreviewActivity extends Activity {

    private static final String TAG = "CameraPreviewActivity";

    private SurfaceView mCameraPreview;
    private SurfaceHolder mPreviewHolder;
    private Camera mCamera;
    private boolean mInPreview;

    private CubeView mCubeView;

    private OrientationAdapterService mOrientationService;

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                Log.e(TAG, "Exception in setPreviewDisplay --> " + e.getMessage());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
            Camera.Parameters parameters = mCamera.getParameters();
            Camera.Size size = getBestPreviewSize(width, height, parameters);

            if(size != null) {
                parameters.setPreviewSize(size.width, size.height);
                List<String> focusModes = parameters.getSupportedFocusModes();
                for(String str: focusModes) {
                    if(str.equals(Camera.Parameters.FOCUS_MODE_AUTO)) {
                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                    }
                }
                mCamera.setParameters(parameters);
                mCamera.startPreview();
                mInPreview=true;
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    };

    private OrientationAdapterService.UpdateUI mUpdater = new OrientationAdapterService.UpdateUI() {
        @Override
        public void update(float[] orientation) {
            mCubeView.updateOrientation(orientation);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        mInPreview = false;

        mCameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        mPreviewHolder = mCameraPreview.getHolder();
        mPreviewHolder.addCallback(callback);

        //addContentView(new ARView(this), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mCubeView = new CubeView(this);
        addContentView(mCubeView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mOrientationService = new OrientationAdapterService(this);
        mOrientationService.setListener(mUpdater);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCamera = Camera.open();
        mOrientationService.start();
    }

    @Override
    protected void onPause() {

        if(mInPreview) {
            mCamera.stopPreview();
        }

        mCamera.release();
        mCamera=null;
        mInPreview=false;

        mOrientationService.stop();

        super.onPause();
    }

    private Camera.Size getBestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size bestSize = null;
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        for(Camera.Size sz : sizeList) {
            if (sz.width <= width && sz.height <= height) {
                if(bestSize==null) {
                    bestSize=sz;
                } else {
                    int resultArea=bestSize.width * bestSize.height;
                    int newArea=sz.width * sz.height;
                    if(newArea>resultArea){
                        bestSize=sz;
                    }
                }
            }
        }

        return bestSize;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.camera_preview, menu);
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
