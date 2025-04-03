package com.example.speedtracker.Classes;

public class Surpasses {

    private Float longtitude;
    private Float latitude;
    private Float speed;
    private String datetime;

    public Surpasses(Float longtitude, Float latitude, Float speed, String datetime) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.speed = speed;
        this.datetime = datetime;
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Float getSpeed() {
        return speed;
    }

    public String getDatetime() {
        return datetime;
    }


}
