package se.nios.sensorapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.Sensor;
import android.util.Log;

import java.util.ArrayList;

import se.nios.sensorapp.SensorReaderContract.SensorData;
import se.nios.sensorapp.SensorReaderContract.Sensors;
import se.nios.sensorapp.SensorReaderContract;

import static se.nios.sensorapp.SensorReaderContract.TABLE_SENSORS;
import static se.nios.sensorapp.SensorReaderContract.TABLE_SENSOR_DATA;

/**
 * Created by Nicklas on 2017-04-05.
 */

public class SensorDataDBHelper extends SQLiteOpenHelper{
    private static final String TAG = "SensorDataDBHelper";

    //Database version and name
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME ="SensorApp.db";



    private static final String SQL_CREATE_TABLE_SENSOR_DATA =
            "CREATE TABLE " + SensorData.TABLE_NAME + " (" +
                    SensorData._ID + " INTEGER PRIMARY KEY," +
                    SensorData.COLUMN_NAME_SENSOR_ID + " TEXT," +
                    SensorData.COLUMN_NAME_SEQNO + " TEXT," +
                    SensorData.COLUMN_NAME_BATTERY +" TEXT," +
                    SensorData.COLUMN_NAME_HUMIDITY + " TEXT," +
                    SensorData.COLUMN_NAME_LIGHT + " TEXT," +
                    SensorData.COLUMN_NAME_MOTION_COUNTER + " TEXT," +
                    SensorData.COLUMN_NAME_PAYLOAD + " TEXT," +
                    SensorData.COLUMN_NAME_TEMPERATURE + " TEXT," +
                    SensorData.COLUMN_NAME_TIMESTAMP + " TEXT NOT NULL UNIQUE)";

    private static final String SQL_CREATE_TABLE_SENSORS =
            "CREATE TABLE " + Sensors.TABLE_NAME + " (" +
                    Sensors._ID +" INTEGER PRIMARY KEY," +
                    Sensors.COLUMN_NAME_SENSOR_ID + " TEXT," +
                    Sensors.COLUMN_NAME_NAME + " TEXT," +
                    Sensors.COLUMN_NAME_GROUP + " TEXT," +
                    Sensors.COLUMN_NAME_URL + " TEXT NOT NULL UNIQUE)";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS ";




    public SensorDataDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_SENSOR_DATA);
        db.execSQL(SQL_CREATE_TABLE_SENSORS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE + TABLE_SENSOR_DATA);
        db.execSQL(SQL_DELETE_TABLE + TABLE_SENSORS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,oldVersion,newVersion);
    }

    public long createSensor(String url, String name, String group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Sensors.COLUMN_NAME_URL, url);
        contentValues.put(Sensors.COLUMN_NAME_NAME, name);
        contentValues.put(Sensors.COLUMN_NAME_GROUP,group);
        return db.insertOrThrow(Sensors.TABLE_NAME,null,contentValues);
    }


    public boolean insertSensorData(String sensorId, String seqno,String timestamp, String payload, String temperature, String humidity, String light, String motionCounter, String battery){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SensorData.COLUMN_NAME_SENSOR_ID, sensorId);
            contentValues.put(SensorData.COLUMN_NAME_SEQNO, seqno);
            contentValues.put(SensorData.COLUMN_NAME_PAYLOAD, payload);
            contentValues.put(SensorData.COLUMN_NAME_TEMPERATURE, temperature);
            contentValues.put(SensorData.COLUMN_NAME_HUMIDITY, humidity);
            contentValues.put(SensorData.COLUMN_NAME_LIGHT, light);
            contentValues.put(SensorData.COLUMN_NAME_MOTION_COUNTER, motionCounter);
            contentValues.put(SensorData.COLUMN_NAME_BATTERY, battery);
            contentValues.put(SensorData.COLUMN_NAME_TIMESTAMP, timestamp);
            db.insertOrThrow(SensorData.TABLE_NAME,null,contentValues);
            //db.insert("sensor_data", null, contentValues);
        return true;
    }
    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sensor_data where id="+id+"", null );
        return res;
    }
    public int numberOfRowsSensorData(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SensorData.TABLE_NAME);
        return numRows;
    }
    public int numberOfRowsSensors(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Sensors.TABLE_NAME);
        return numRows;
    }


    public boolean updateSensorValue (String id,String seqno,String payload,String temperature, String humidity, String light, String motionCounter,String battery) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("seqno", seqno);
        contentValues.put("payload", payload);
        contentValues.put("temperature", temperature);
        contentValues.put("humidity", humidity);
        contentValues.put("light", light);
        contentValues.put("motionCounter",motionCounter);
        contentValues.put("battery",battery);
        db.update("sensor_data", contentValues, "id = ? ", new String[] { id } );
        return true;
    }

    public boolean dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE sensor_data");
        return  true;
    }

    public Integer deleteSensorValue (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("sensor_data",
                "id = ? ",
                new String[] { id });
    }

    public ArrayList<String> getAllSensorData() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sensor_data", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(SensorData.TABLE_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
