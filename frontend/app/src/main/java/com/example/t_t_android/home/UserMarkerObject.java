package com.example.t_t_android.home;

public class UserMarkerObject {
    private int head_cnt;
    private String arrival;

    private double starting_point_latitude;
    private double starting_point_longitude;

    private double destination_latitude;
    private double destination_longitude;

    public UserMarkerObject(String arrival, double starting_point_latitude, double starting_point_longitude,
                            double destination_latitude, double destination_longitude, int head_cnt) {
        this.arrival = arrival;
        this.starting_point_latitude = starting_point_latitude;
        this.starting_point_longitude = starting_point_longitude;
        this.destination_latitude = destination_latitude;
        this.destination_longitude = destination_longitude;
        this.head_cnt = head_cnt;
    }

    public void setHead_cnt(int head_cnt) {
        this.head_cnt = head_cnt;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public double getStarting_point_latitude() {
        return starting_point_latitude;
    }

    public double getStarting_point_longitude() {
        return starting_point_longitude;
    }

    public double getDestination_latitude() {
        return destination_latitude;
    }

    public double getDestination_longitude() {
        return destination_longitude;
    }

    public int getHead_cnt() {
        return head_cnt;
    }

    public String getArrival() {
        return arrival;
    }

    public void setStarting_point_latitude(double starting_point_latitude) {
        this.starting_point_latitude = starting_point_latitude;
    }

    public void setStarting_point_longitude(double starting_point_longitude) {
        this.starting_point_longitude = starting_point_longitude;
    }

    public void setDestination_latitude(double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public void setDestination_longitude(double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public String userInfo() {
        return "인원 " + getHead_cnt() + "/4";
    }

}
