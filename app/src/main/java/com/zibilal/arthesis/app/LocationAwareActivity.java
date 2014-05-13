package com.zibilal.arthesis.app;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zibilal.arthesis.app.list.adapter.GeonamesListAdapter;
import com.zibilal.arthesis.app.location.CameraTransformation;
import com.zibilal.arthesis.app.location.LocationService;
import com.zibilal.arthesis.app.location.Point;
import com.zibilal.arthesis.app.model.Geoname;
import com.zibilal.arthesis.app.model.Response;
import com.zibilal.arthesis.app.model.WSGeonamesNearbyResponse;
import com.zibilal.arthesis.app.network.HttpAsyncTask;

import java.util.List;


public class LocationAwareActivity extends Activity{

    private static final String TAG="LocationAwareActivity";
    private LocationService mLocationService;
    private ListView mListView;
    private GeonamesListAdapter mAdapter;
    private TextView mTextView;

    private CameraTransformation mCameraTransformation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_aware);
        mLocationService = new LocationService(this);
        mTextView = (TextView) findViewById(R.id.text1);
        mListView = (ListView) findViewById(R.id.list_view);
        mAdapter = new GeonamesListAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Geoname g = (Geoname) mListView.getItemAtPosition(i);
                Point p = g.getProjection();
                Toast.makeText(LocationAwareActivity.this, String.format("x=%.2f, y=%.2f, z=%.2f", p.getX(), p.getY(), p.getZ()), Toast.LENGTH_SHORT)
                .show();
            }
        });
        mLocationService.setCallback(new LocationService.Callback() {
            @Override
            public void onCurrentLocation(Location location) {
                if(location != null) {
                    String str = String.format("Latitude : %s , Longitude: %s", location.getLatitude(), location.getLongitude());
                    mTextView.setText(str);

                    final Location cloc = location;

                    HttpAsyncTask task = new HttpAsyncTask(WSGeonamesNearbyResponse.class, new HttpAsyncTask.Callback() {
                        @Override
                        public void onFinished(Response response) {
                            WSGeonamesNearbyResponse wsResponse = (WSGeonamesNearbyResponse) response;

                            List<Geoname> geonames = wsResponse.getGeonames();

                            for(Geoname g : geonames) {
                                Point p = null; // mCameraTransformation.convertToVector(cloc, g);
                                p = mCameraTransformation.projectPoint(p);
                                g.setProjection(p);
                            }

                            Log.d(TAG, "Response = " + wsResponse );
                            mAdapter.clear();
                            mAdapter.add(geonames);
                        }
                    });
                    String sUrl = String.format("http://ws.geonames.org/findNearbyWikipediaJSON?lat=%s&lng=%s&radius=20.0&maxRows=5&lang=en&username=mixare", location.getLatitude(), location.getLongitude());
                    task.execute(sUrl);
                }
            }
        });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width=0;
        int height=0;
        if(displayMetrics.heightPixels > displayMetrics.widthPixels) {
            width=displayMetrics.heightPixels;
            height=displayMetrics.widthPixels;
        } else {
            width=displayMetrics.widthPixels;
            height=displayMetrics.heightPixels;
        }

        mCameraTransformation = new CameraTransformation(width, height);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationService.start();
    }

    @Override
    protected void onStop() {
        mLocationService.stop();
        super.onStop();
    }

    public void onLastLocation(View view) {
        Location location = mLocationService.getCurrentLocation();
        String str = String.format("Latitude : %s , Longitude: %s", location.getLatitude(), location.getLongitude());
        mTextView.setText(str);

        HttpAsyncTask task = new HttpAsyncTask(WSGeonamesNearbyResponse.class, new HttpAsyncTask.Callback() {
            @Override
            public void onFinished(Response response) {
                WSGeonamesNearbyResponse wsResponse = (WSGeonamesNearbyResponse) response;
                Log.d(TAG, "Response = " + wsResponse );
            }
        });
        String sUrl = String.format("http://ws.geonames.org/findNearbyWikipediaJSON?lat=%s&lng=%s&radius=20.0&maxRows=50&lang=en&username=mixare", location.getLatitude(), location.getLongitude());
        task.execute(sUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location_aware, menu);
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
