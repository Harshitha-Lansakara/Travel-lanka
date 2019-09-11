package com.example.travellanka;

public class UserInformation2 {


    public String  type;
    public String name;
    public String tpnumber;
    public String depdate;
    public String email;



    public UserInformation2(){

    }

    public UserInformation2(String type, String depdate, String name, String tpnumber,String email) {
        this.type=type;
        this.depdate=depdate;
        this.name = name;
        this.tpnumber = tpnumber;
        this.email=email;
    }
}
