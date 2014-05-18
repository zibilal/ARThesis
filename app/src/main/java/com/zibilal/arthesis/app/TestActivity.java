package com.zibilal.arthesis.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends Activity {

    private static final String TAG="TestActivity";

    private ListView listView;
    private List<ClassObj> lClass;


    private static class ClassObj {
        private Class<? extends Activity> dActivity;
        private String dName;

        public ClassObj(String name, Class<? extends Activity> clss) {
            dName = name;
            dActivity = clss;
        }

        public Class<? extends Activity> getdActivity() {
            return dActivity;
        }

        public void setdActivity(Class<? extends Activity> dActivity) {
            this.dActivity = dActivity;
        }

        public String getdName() {
            return dName;
        }

        public void setdName(String dName) {
            this.dName = dName;
        }

        @Override
        public String toString() {
            return getdName();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        lClass = new ArrayList<ClassObj>();
        initLClass();

        listView = (ListView) findViewById(R.id.list_view);
        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ClassObj dobj = (ClassObj) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(TestActivity.this, dobj.getdActivity());
                startActivity(intent);
            }
        });

        ArrayAdapter<ClassObj> adapter = new ArrayAdapter<ClassObj>(this, android.R.layout.simple_list_item_1, lClass);
        listView.setAdapter(adapter);

        SensorManager smanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> slist = smanager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor s : slist) {
            Log.d(TAG, "------->> " + s.getName());
        }
    }

    private void initLClass() {
        lClass.add(new ClassObj("TestMatrixOpActivity", TestMatrixOpActivity.class));
        lClass.add(new ClassObj("TestNumberActivity", TestNumberActivity.class));
        lClass.add(new ClassObj("Camera Preview", CameraPreviewActivity.class));
        lClass.add(new ClassObj("Compass Preview", CompassActivity.class));
        lClass.add(new ClassObj("Location Aware", LocationAwareActivity.class));
        lClass.add(new ClassObj("North Finder", NorthFinderActivity.class));
        lClass.add(new ClassObj("Camera Sensor Data View", CameraSensorDataViewActivity.class));
        lClass.add(new ClassObj("Data Text Activity", DataTestActivity.class));
        lClass.add(new ClassObj("CubeIn2dCanvas", CubeIn2DCanvasActivity.class));
        lClass.add(new ClassObj("OvalViewTestActivity", OvalViewTestActivity.class));
        lClass.add(new ClassObj("PathViewTestActivity", PathViewTestActivity.class));
        lClass.add(new ClassObj("TriangleViewActivity", TriangleViewActivity.class));
        lClass.add(new ClassObj("CoordinateViewActivity", CoordinateViewActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
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
