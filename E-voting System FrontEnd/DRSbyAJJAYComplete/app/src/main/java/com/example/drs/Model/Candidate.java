package com.example.drs.Model;


public class Candidate
{


    private int adhar;
    private String fullName;

    private  String candidatePhotopath;
    private  String partyphotopath;
    private String partyname;
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
