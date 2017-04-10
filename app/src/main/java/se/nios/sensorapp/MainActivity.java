package se.nios.sensorapp;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import se.nios.sensorapp.dbhelper.SensorDataDBHelper;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, NewSensorDialog.newSensorEditTextListener{
    private static final String TAG = "MainActivity";

    private SensorDataDBHelper sensorDataDBHelper;
    private SQLiteDatabase db;
    private ListView sensorListView;
    private SensorsCursorAdapter sensorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorDataDBHelper = new SensorDataDBHelper(this);
        db  = sensorDataDBHelper.getWritableDatabase();
        sensorListView = (ListView) findViewById(R.id.listView);
        final Cursor sensorCursor = db.rawQuery("SELECT * FROM sensors",null);
        sensorAdapter = new SensorsCursorAdapter(this,sensorCursor);
        sensorListView.setAdapter(sensorAdapter);
        sensorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent sensorViewIntent = new Intent(getApplicationContext(),SensorActivity.class);
                sensorViewIntent.putExtra("position", position);
                sensorViewIntent.putExtra("sensor",sensorCursor.getString(sensorCursor.getColumnIndexOrThrow("url")));
                startActivity(sensorViewIntent);
            }
        });

        FloatingActionButton fabNewSensor = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fabNewSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new NewSensorDialog();
                newFragment.show(getFragmentManager(), "newsensordialog");
            }
        });




        Intent startServiceIntent = new Intent(this,SensorService.class);
        startService(startServiceIntent);


    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onNewSensorDialogFinish(String url, String name, String group) {
        try {
            sensorDataDBHelper.createSensor(url, name, group);
            Log.d(TAG,"Rows in table sensors: " +sensorDataDBHelper.numberOfRowsSensors());
            Log.d(TAG,"Values:" + url + " : " +name +" : "+group);
            //update sensorlist
            Cursor sensorCursor = db.rawQuery("SELECT * FROM sensors",null);
            sensorAdapter.changeCursor(sensorCursor);
        }catch (SQLException e){
            Log.d(TAG,"SQL exception = " + e.getMessage());
        }
    }
}
