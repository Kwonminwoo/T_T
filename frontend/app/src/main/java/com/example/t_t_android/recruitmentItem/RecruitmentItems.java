package com.example.t_t_android.recruitmentItem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t_t_android.R;

import java.io.Serializable;

public class RecruitmentItems extends Fragment implements Serializable {

    private String title, date, crewNumber, contents;
    private int hour, min, crewNum;
    private double startLat, startLon, endLat, endLon;

    public RecruitmentItems (String title, String date, int hour, int min, double startLat, double startLon, double endLat, double endLon, int crewNum){
        this.title = title;
        this.date = date;
        this.hour = hour;
        this.min = min;
        this.startLat = startLat;
        this.startLon = startLon;
        this.endLat = endLat;
        this.endLon = endLon;
        this.crewNum=crewNum;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        String time = (String.format("%02d : %02d", hour, min));
        return time;
    }
    public double getStartLat(){
        return startLat;
    }
    public double getStartLon(){
        return startLon;
    }
    public double getEndLat(){
        return endLat;
    }
    public double getEndLon(){
        return endLon;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }
    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }
    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }
    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public int getCrewNum(){
        return crewNum;
    }
    public String getContents() {
        return contents;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCrewNum(String crewNum){
        this.crewNumber=crewNum;
    }
}