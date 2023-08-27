package com.example.t_t_android.home;

public class UserMarkerObject {
    private int peopleCnt;
    private String arrival;

    private double startLat;
    private double startLon;

    private double endLat;
    private double endLon;
    private String content;

    public UserMarkerObject() {
        this.peopleCnt = 1;
    }

    public UserMarkerObject(String arrival, double startLat, double startLon,
                            double endLat, double endLon, int peopleCnt) {
        this.arrival = arrival;
        this.startLat = startLat;
        this.startLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;
        this.peopleCnt = peopleCnt;
    }

    public void setHead_cnt(int head_cnt) {
        this.peopleCnt = head_cnt;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public double getStartLat() {
        return startLat;
    }

    public double getStartLon() {
        return startLon;
    }

    public double getEndLat() {
        return endLat;
    }

    public double getEndLon() {
        return endLon;
    }

    public int getPeopleCnt() {
        return peopleCnt;
    }

    public String getArrival() {
        return arrival;
    }

    public String getContent() { return content; }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }
    public void setContent(String content) { this.content = content; }

    public String userInfo() {
        return "인원 " + getPeopleCnt() + "/4";
    }

}
