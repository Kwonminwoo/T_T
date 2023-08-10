package com.example.t_t_android.home;

public class UserMarkerObject {
    private int head_cnt;
    private String arrival;
    private double latitude;
    private double longitude;

    public UserMarkerObject(String arrival, double latitude, double longitude, int head_cnt) {
        this.arrival = arrival;
        this.latitude = latitude;
        this.longitude = longitude;
        this.head_cnt = head_cnt;
    }

    public void setHead_cnt(int head_cnt) {
        this.head_cnt = head_cnt;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getHead_cnt() {
        return head_cnt;
    }

    public String getArrival() {
        return arrival;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String userInfo() {
        return "인원 " + getHead_cnt() + "/4";
    }

}
