package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Voter
{

    @Id
    @Column(name = "adharcard")
    private int adharcard;

    @Column(name = "electioncard")
    private String electionCard;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "dob")
    private String dob;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name="vdone")
    private String vdone;

    public Voter() {
    }


    public int getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(int adharcard) {
        this.adharcard = adharcard;
    }

    public String getElectionCard() {
        return electionCard;
    }

    public void setElectionCard(String electionCard) {
        this.electionCard = electionCard;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getVdone() {
        return vdone;
    }

    public void setVdone(String vdone) {
        this.vdone = vdone;
    }
}
