package com.example.drs;

import android.app.Application;

import com.example.drs.Model.Voter;

public class
GlobalUser extends Application
{

    private String username;
    private String City;
    private int Adharcard;
    private  String firstname;
    private String VoteDone;
    private String MobileNo;
    private String Choice;

    public String getChoice() {
        return Choice;
    }

    public void setChoice(String choice) {
        Choice = choice;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getAdharcard() {
        return Adharcard;
    }

    public void setAdharcard(int adharcard) {
        Adharcard = adharcard;
    }

    public String getCity() {
        return City;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getVoteDone() {
        return VoteDone;
    }

    public void setVoteDone(String voteDone) {
        VoteDone = voteDone;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

