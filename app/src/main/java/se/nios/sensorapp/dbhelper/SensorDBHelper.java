package se.nios.sensorapp.dbhelper;

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

public class SensorDBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "SensorProject.db";
    public static final String SENSORS_TABLE_NAME ="sensors";
    public static final String SENSOR_COLUMN_ID = "id";
    public static final String SENSOR_COLUMN_NAME ="name";
    public static final String SENSOR_COLUMN_GROUP ="group";
    public static final String SENSOR_COLUMN_LOCATION="location";



    public SensorDBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table sensors " +
                        "(id text primary key,name text ,group text,location text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS sensors");
        onCreate(db);
    }
    public boolean insertSensorData(String name,String group,String location){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("group", group);
        contentValues.put("location", location);
        db.insert("sensors", null, contentValues);
        return true;
    }
    public Cursor getData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from sensors where id="+id+"", null );
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, SENSORS_TABLE_NAME);
        return numRows;
    }

    public boolean updateSensorValue (String id,String name,String group, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("group", group);
        contentValues.put("location", location);
        db.update("sensors", contentValues, "id = ? ", new String[] { id } );
        return true;
    }

    public Integer deleteSensorValue (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("sensors",
                "id = ? ",
                new String[] { id });
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
