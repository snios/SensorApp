package se.nios.sensorapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
                        "(id integer primary key, seqno text,payload text,temperature text, humidity text,light text, motionCounter text, battery text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sensors");
        onCreate(db);
    }
    public boolean insertSensorData(String seqno,String payload,String temperature, String humidity, String light, String motionCounter,String battery){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seqno", seqno);
        contentValues.put("payload", payload);
        contentValues.put("temperature", temperature);
        contentValues.put("humidity", humidity);
        contentValues.put("light", light);
        contentValues.put("motionCounter",motionCounter);
        contentValues.put("battery",battery);
        db.insert("sensors", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sensors where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SENSORS_TABLE_NAME);
        return numRows;
    }

    public boolean updateSensorValue (Integer id,String seqno,String payload,String temperature, String humidity, String light, String motionCounter,String battery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seqno", seqno);
        contentValues.put("payload", payload);
        contentValues.put("temperature", temperature);
        contentValues.put("humidity", humidity);
        contentValues.put("light", light);
        contentValues.put("motionCounter",motionCounter);
        contentValues.put("battery",battery);
        db.update("sensors", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteSensorValue (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("sensors",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllSensors() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sensors", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(SENSORS_TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
