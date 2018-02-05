package com.krayc.vo;

import com.krayc.model.VenueEntity;

public class VenueInfoVO {

    private String vid;
    private String address;
    private String isPassed;
    private String name;
    private int seatsNumber;
    private int eventsNumber;

    public VenueInfoVO(VenueEntity venueEntity) {
        this.vid = String.format("%07d", venueEntity.getVid());
        this.address = venueEntity.getAddress();
        Byte isPassedByte = venueEntity.getIsPassed();
        if (isPassedByte.equals(Byte.valueOf("1"))) {
            this.isPassed = "审核通过";
        } else if (isPassedByte.equals(Byte.valueOf("-1"))) {
            this.isPassed = "审核未通过";
        } else if (isPassedByte.equals(Byte.valueOf("0"))) {
            this.isPassed = "尚未审核";
        }
        this.name = venueEntity.getName();
        this.seatsNumber = venueEntity.getSeatsByVid().size();
        this.eventsNumber = venueEntity.getEventsByVid().size();
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(String isPassed) {
        this.isPassed = isPassed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(int seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public int getEventsNumber() {
        return eventsNumber;
    }

    public void setEventsNumber(int eventsNumber) {
        this.eventsNumber = eventsNumber;
    }
}
