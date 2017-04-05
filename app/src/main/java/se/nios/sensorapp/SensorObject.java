package se.nios.sensorapp;

import java.util.List;

/**
 * Created by Nicklas on 2017-04-04.
 */

public class SensorObject {
    private String ID;
    private String name;
    private String group;
    private String location;
    private List<SensorData> sensorList;

    public SensorObject() {
    }

    public SensorObject(String ID, String name, String group, String location) {
        this.ID = ID;
        this.name = name;
        this.group = group;
        this.location = location;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SensorData> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<SensorData> sensorList) {
        this.sensorList = sensorList;
    }
}






