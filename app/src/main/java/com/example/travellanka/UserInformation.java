package com.example.travellanka;

public class UserInformation {

    public String noof1stclasstickets;
    public String noof2ndclasstickets;
    public String name;
    public String tpnumber;
    public String date;
    public String month;
    public String time;
    public String train;
    public String total;
    public String email;



    public UserInformation(){

    }

    public UserInformation(String noof1stclasstickets, String noof2ndclasstickets, String name, String tpnumber, String date, String month
    , String time, String train, String total, String email) {
        this.noof1stclasstickets = noof1stclasstickets;
        this.noof2ndclasstickets = noof2ndclasstickets;
        this.name = name;
        this.tpnumber = tpnumber;
        this.date=date;
        this.month=month;
        this.time=time;
        this.train=train;
        this.total=total;
        this.email=email;
    }
}
