package se.nios.sensorapp;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import se.nios.sensorapp.dbhelper.SensorDBHelper;
import se.nios.sensorapp.dbhelper.SensorDataDBHelper;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, NewSensorDialog.newSensorEditTextListener{
    private static final String TAG = "MainActivity";
    //Views
    private SensorDataDBHelper sensorDataDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorDataDBHelper = new SensorDataDBHelper(this);
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
        }catch (SQLException e){
            Log.d(TAG,"SQL exception = " + e.getMessage());
        }
    }
}
