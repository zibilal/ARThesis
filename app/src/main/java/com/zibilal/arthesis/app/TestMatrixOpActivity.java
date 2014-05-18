package com.zibilal.arthesis.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zibilal.arthesis.app.sensor.Matrix3x3;


public class TestMatrixOpActivity extends Activity {

    private TextView mFirstData;
    private TextView mResultOp;

    private float[] values1 = {1f, 3f, 5f, 7f, 8f , 9f, 1f ,2f , 3f};
    private float[] values2 = {5f, 6f, 7f, 7f, 9f, 0f, 1f, 2f, 4f};

    private Matrix3x3 m1;
    private Matrix3x3 m2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_matrix_op);

        mFirstData = (TextView) findViewById(R.id.first_data);
        mResultOp = (TextView) findViewById(R.id.result_op);

        m1 = new Matrix3x3();
        m2 = new Matrix3x3();
        m1.setValues(values1);
        m2.setValues(values2);

        mFirstData.setText(String.format("%s \n %s", m1, m2));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_matrix_op, menu);
        return true;
    }

    public void onOp(View view) {
        Matrix3x3 result = m1.sub(m2);

        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("Added result = %s", result));

        Matrix3x3 result2 = m1.add(m2);
        buffer.append(String.format("\nAdded result = %s", result2));

        float f = (1f/2f);
        Matrix3x3 result3 = m1.mult(f);
        buffer.append(String.format("\nMuliplied by 1/2 = %s", result3));

        Matrix3x3 result4 = m1.prod(m2);
        buffer.append(String.format("\nProduct two matrix = %s", result4));

        mResultOp.setText(buffer.toString());
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
