package com.example.drs.Model;

public class Count {
    String partyname;
    String candidate;
    int count;
    String candidate_img;
    String party_img;

    public String getCandidate_img() {
        return candidate_img;
    }

    public void setCandidate_img(String candidate_img) {
        this.candidate_img = candidate_img;
    }

    public String getParty_img() {
        return party_img;
    }

    public void setParty_img(String party_img) {
        this.party_img = party_img;
    }

    public String getPartyname() {
        return partyname;
    }

    public void setPartyname(String partyname) {
        this.partyname = partyname;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
