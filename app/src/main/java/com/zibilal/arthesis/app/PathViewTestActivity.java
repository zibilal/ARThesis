package com.zibilal.arthesis.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zibilal.arthesis.app.sensor.OrientationAdapterService;
import com.zibilal.arthesis.app.views.PathView;


public class PathViewTestActivity extends Activity {

    private PathView mPathView;

    private OrientationAdapterService mOrientationService;
    private OrientationAdapterService.UpdateUI mUpdater = new OrientationAdapterService.UpdateUI() {
        @Override
        public void update(float[] orientation) {
            mPathView.rotateView(orientation);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_view_test);

        mPathView = (PathView) findViewById(R.id.path_view);
        mOrientationService = new OrientationAdapterService(this);
        mOrientationService.setListener(mUpdater);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mOrientationService.start();
    }

    @Override
    protected void onPause() {
        mOrientationService.stop();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.path_view_test, menu);
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
