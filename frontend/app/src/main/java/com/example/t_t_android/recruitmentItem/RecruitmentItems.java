package com.example.t_t_android.recruitmentItem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.t_t_android.R;

public class RecruitmentItems extends Fragment {

    private String title;
    private String date;
    private int hour, min;
    private String start;
    private String destination;
    private  String crewNumber;

    public RecruitmentItems (String title, String date, int hour, int min, String start, String destination, String crewNumber){
        this.title = title;
        this.date = date;
        this.hour = hour;
        this.min = min;
        this.start=start;
        this.destination=destination;
        this.crewNumber=crewNumber;
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
    public String getStart(){
        return start;
    }

    public String getDestination(){
        return destination;
    }

    public String getCrewNumber(){
        return crewNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStart(String start){
        this.start=start;
    }

    public void setDestination(String destination){
        this.destination=destination;
    }

    public void setCrewNumber(String crewNumber){
        this.crewNumber=crewNumber;
    }
}