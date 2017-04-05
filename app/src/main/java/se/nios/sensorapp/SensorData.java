package se.nios.sensorapp;

import java.util.Date;

/**
 * Created by Nicklas on 2017-04-04.
 */

class SensorData {
    private String ID;
    private Date timestamp;
    private int passCounter;

    SensorData(){

    }

    SensorData(String ID, Date timestamp, int passAmount){
        this.ID = ID;
        this.timestamp = timestamp;
        this.passCounter = passAmount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPassCounter() {
        return passCounter;
    }

    public void setPassCounter(int passCounter) {
        this.passCounter = passCounter;
    }
}
