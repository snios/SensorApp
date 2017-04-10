package se.nios.sensorapp;

import android.app.Service;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import se.nios.sensorapp.dbhelper.SensorDBHelper;
import se.nios.sensorapp.dbhelper.SensorDataDBHelper;


/**
 * Created by Nicklas on 2017-04-04.
 */

public class SensorService extends Service implements Runnable {
    private static final String TAG = "SensorService";
    private static final String URL = "http://lorawan.testbed.se/json/lora/";
    private Thread serviceThread = null;
    private boolean runService = true;
    private long sleepInterval; //In milliseconds
    private SensorDBHelper sensorDbHelper;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (serviceThread == null) {
            sensorDbHelper = new SensorDBHelper(this);
            serviceThread = new Thread(this);
            serviceThread.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        sensorDbHelper.close();
        super.onDestroy();
    }


    @Override
    public void run() {
        Looper.prepare();


        while (runService) {

            GetSensorUpdateTask getServiceUpdateTask = new GetSensorUpdateTask();
            getServiceUpdateTask.execute("http://lorawan.testbed.se/json/lora/018d.json");


            //Sleep thread..
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(30));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class GetSensorUpdateTask extends AsyncTask<String, Integer, SensorData> {

        private SensorDBHelper sensorDbHelper;
        private String urlResponse;
        private InputStream inputStream;
        private JSONObject jsonObject;
        private JSONObject userData;
        private JSONObject parsedEntry;

        private SensorData sensorData;
        private SensorDataDBHelper sensorDataDBHelper = new SensorDataDBHelper(getApplicationContext());

        private String moteeui;
        private String temperature;
        private String humidity;
        private String light;
        private String motionCounter;
        private String battery;
        private String seqno;
        private String payload;
        private Date timeDate;
        private String timeString;
        private String counter;




        @Override
        protected SensorData doInBackground(String... params) {
            try {
                inputStream = downloadUrl(params[0]);
            } catch (IOException e) {
               Log.d(TAG,"Connection failed = " +e.getMessage());
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");

                }

                urlResponse = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonObject = new JSONObject(urlResponse);
                //Receiving stations are in an array.. need to get date from atleast one of them
                JSONArray sensorDate = jsonObject.getJSONArray("gwrx");
                JSONObject tempObject;
                for(int i  = 0; i < sensorDate.length();i++ ){
                    tempObject = sensorDate.getJSONObject(i);
                   timeString = tempObject.getString("time");
                    if(timeString != null){
                        Log.d(TAG,timeString);
                        break;
                    }
                }
                /*
                        private String moteeui;
        private String temperature;
        private String humidity;
        private String light;
        private String motionCounter;
        private String battery;
        private Date timeDate;
        private String timeString;
        private String counter;
                 */
                //Go into object to get object..
                userData = jsonObject.getJSONObject("userdata");
                parsedEntry = userData.getJSONObject("parsedEntry");
                //Sensor data
                payload=userData.getString("payload");
                moteeui = jsonObject.getString("moteeui");
                seqno = jsonObject.getString("seqno");
                temperature = parsedEntry.getString("temperature");
                humidity = parsedEntry.getString("humidity");
                light = parsedEntry.getString("light");
                motionCounter = parsedEntry.getString("motionCounter");
                battery = parsedEntry.getString("battery");
                sensorData = new SensorData(timeString+"+"+moteeui,payload,seqno,temperature,humidity,light,motionCounter,battery,timeString);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return sensorData;
        }

        @Override
        protected void onPostExecute(SensorData sensorData) {;
            Log.d(TAG,sensorData.toString());
            try {
                sensorDataDBHelper.insertSensorData(moteeui, seqno, timeString, payload, temperature, humidity, light, motionCounter, battery);
            }catch (SQLException e){
                Log.d(TAG,"SQL exception = " + e.getMessage());
            }
            Log.d(TAG,"Rows in table sensordata: "+String.valueOf(sensorDataDBHelper.numberOfRowsSensorData()));



            super.onPostExecute(sensorData);
        }
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //timeout in milliseconds
        connection.setReadTimeout(2000);
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        // Starts the query
        connection.connect();
        InputStream stream = connection.getInputStream();
        return stream;
    }
}


