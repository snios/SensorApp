package se.nios.sensorapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nicklas on 2017-04-05.
 */

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Sensor.db";
    public static final String SENSORS_TABLE_NAME ="sensors";
    public static final String SENSORS_COLUMN_ID = "id";
    public static final String SENSOR_COLUMN_SEQNO ="seqno";
    public static final String SENSOR_COLUMN_PAYLOAD ="payload";
    public static final String SENSOR_COLUMN_TEMPERATURE ="temperature";
    public static final String SENSOR_COLUMN_HUMIDITY ="humidity";
    public static final String SENSOR_COLUMN_LIGHT ="light";
    public static final String SENSOR_COLUMN_MOTION_COUNTER ="motionCounter";
    public static final String SENSOR_COLUMN_BATTERY ="battery";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table sensors " +
                        "(id integer primary key, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
