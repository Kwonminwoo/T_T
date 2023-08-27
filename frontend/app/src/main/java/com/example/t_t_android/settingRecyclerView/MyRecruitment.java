package com.example.t_t_android.settingRecyclerView;

public class MyRecruitment {

    private String start;
    private String destination;
   private  String crewNumber;

    public MyRecruitment(String start, String destination, String crewNumber){
        this.start=start;
        this.destination=destination;
        this.crewNumber=crewNumber;
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
