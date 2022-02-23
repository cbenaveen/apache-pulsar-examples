package org.apachepulsar.example.schemas.complextypes.model;

public class SensorData {
    private String type;
    private String name;
    private String data;

    public SensorData() {
    }

    public SensorData(String type, String name, String data) {
        this.type = type;
        this.name = name;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
