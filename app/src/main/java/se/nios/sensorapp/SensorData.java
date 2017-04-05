package se.nios.sensorapp;

import java.util.Date;

/**
 * Created by Nicklas on 2017-04-04.
 */

class SensorData {
    private String moteeui;
    private String temperature;
    private String humidity;
    private String light;
    private String motionCounter;
    private String battery;
    private String timeString;

    public SensorData(String moteeui, String temperature, String humidity, String light, String motionCounter, String battery, String timeString) {
        this.moteeui = moteeui;
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.motionCounter = motionCounter;
        this.battery = battery;
        this.timeString = timeString;
    }

    public String getMoteeui() {
        return moteeui;
    }

    public void setMoteeui(String moteeui) {
        this.moteeui = moteeui;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getMotionCounter() {
        return motionCounter;
    }

    public void setMotionCounter(String motionCounter) {
        this.motionCounter = motionCounter;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public String toString() {
        return moteeui + " : "+ battery + " : " + humidity + " : " + motionCounter + " : " + light + " : " + timeString;
    }
}