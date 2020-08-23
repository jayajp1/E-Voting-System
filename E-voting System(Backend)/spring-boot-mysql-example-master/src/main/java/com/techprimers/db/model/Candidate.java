package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Candidate
{

    public Candidate() {
    }

    @Id
    @Column(name ="adharCard")
    private int adhar;

    @Column(name = "fullName")
    private String fullName;

    @Column(name="candidatephotopath")
    private  String candidatePhotopath;

    @Column(name="partyphotpath")
    private  String partyphotopath;

    @Column(name="partyname")
    private String partyname;

    @Column(name="city")
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getAdhar() {
        return adhar;
    }

    public void setAdhar(int adhar) {
        this.adhar = adhar;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getCandidatePhotopath() {
        return candidatePhotopath;
    }

    public void setCandidatePhotopath(String candidatePhotopath) {
        this.candidatePhotopath = candidatePhotopath;
    }

    public String getPartyphotopath() {
        return partyphotopath;
    }

    public void setPartyphotopath(String partyphotopath) {
        this.partyphotopath = partyphotopath;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }
}
