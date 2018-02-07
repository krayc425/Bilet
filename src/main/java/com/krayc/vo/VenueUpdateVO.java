package com.krayc.vo;

import com.krayc.model.VenueEntity;

public class VenueUpdateVO {

    private int vid;
    private String address;
    private String name;
    private String password;

    public VenueUpdateVO(VenueEntity venueEntity) {
        this.vid = venueEntity.getVid();
        this.address = venueEntity.getAddress();
        this.name = venueEntity.getName();
        this.password = venueEntity.getPassword();
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
