package com.techprimers.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {

    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "adharcard")
    private String adharcard;
    @Column(name = "name")
    private String name;

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(String adharcard) {
        this.adharcard = adharcard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
