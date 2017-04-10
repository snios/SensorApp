package se.nios.sensorapp;

import android.database.sqlite.SQLiteDatabase;

import com.jjoe64.graphview.series.DataPoint;

import se.nios.sensorapp.dbhelper.SensorDataDBHelper;

/**
 * Created by Nicklas on 2017-04-10.
 * This is a helper class that will aid in getting data from DB to GUI
 */

public class SensorDataPuller {
    private SensorDataDBHelper sensorDataDBHelper;
    private SQLiteDatabase db;
    private SensorsCursorAdapter sensorAdapter;
    private DataPoint dataPoint;


    public SensorDataPuller(){


    }

}

