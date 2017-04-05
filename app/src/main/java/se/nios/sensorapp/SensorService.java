package se.nios.sensorapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
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


/**
 * Created by Nicklas on 2017-04-04.
 */

public class SensorService extends Service implements Runnable {
    private static final String TAG = "SensorService";
    private Thread serviceThread = null;
    private boolean runService = true;
    private long sleepInterval; //In milliseconds

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (serviceThread == null) {
            serviceThread = new Thread(this);
            serviceThread.start();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void run() {

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

    private class GetSensorUpdateTask extends AsyncTask<String, Integer, String> {
        private String urlResponse;
        private InputStream inputStream;
        private JSONObject jsonObject;
        private JSONObject userData;
        private JSONObject parsedEntry;
        private Date date;
        private String counter;


        @Override
        protected String doInBackground(String... params) {
            try {
                inputStream = downloadUrl(params[0]);
                while (inputStream == null) {
                }
            } catch (IOException e) {
                e.printStackTrace();
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
                JSONArray sensorDate = jsonObject.getJSONArray("gwrx");
                JSONObject tempObject;
                String id;
                for(int i  = 0; i < sensorDate.length();i++ ){
                    tempObject = sensorDate.getJSONObject(i);
                   String time = tempObject.getString("time");
                    if(time != null){
                        Log.d(TAG,time);
                        break;
                    }
                }
                userData = jsonObject.getJSONObject("userdata");
                parsedEntry = userData.getJSONObject("parsedEntry");
                counter = parsedEntry.getString("motionCounter");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return String.valueOf(counter);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, String.valueOf(counter));
            super.onPostExecute(s);
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


