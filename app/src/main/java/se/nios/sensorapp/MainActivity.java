package se.nios.sensorapp;

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, NewSensorDialog.newSensorEditTextListener{
    //Views


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }
}
