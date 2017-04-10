package se.nios.sensorapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import se.nios.sensorapp.dbhelper.SensorDataDBHelper;

public class SensorActivity extends AppCompatActivity {
    private static final String TAG = "SensorActivity";
    private SensorDataDBHelper sensorDataDBHelper;
    private SQLiteDatabase db;
    private SensorsCursorAdapter sensorAdapter;
    private DataPoint dataPoint[];
    private String sensorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        //Generate data for table...
        Intent getIntent = getIntent();
        Log.d(TAG,getIntent.getStringExtra("sensor").toString());
        sensorId = getIntent.getStringExtra("sensor").toString();
        sensorDataDBHelper = new SensorDataDBHelper(this);
        Cursor cursor = sensorDataDBHelper.getData(sensorId);

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            //Get values from db, they are strings..
            Log.d(TAG,"Date = " +cursor.getString(1));
            cursor.moveToNext();
        }



        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
    }
}
