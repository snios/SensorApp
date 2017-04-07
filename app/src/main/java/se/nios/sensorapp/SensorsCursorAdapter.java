package se.nios.sensorapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Nicklas on 2017-04-07.
 */

public class SensorsCursorAdapter extends CursorAdapter {
    public SensorsCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview_sensors,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //Fields
        TextView tvSensor = (TextView) view.findViewById(R.id.tvBody);
        TextView tvExtras = (TextView) view.findViewById(R.id.tvPriority);
        String sensor = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String extras = cursor.getString(cursor.getColumnIndexOrThrow("url"));

        tvSensor.setText(sensor);
        tvExtras.setText(extras);
    }
}
