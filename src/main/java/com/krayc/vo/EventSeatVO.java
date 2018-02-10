package com.krayc.vo;

import com.krayc.model.EventSeatEntity;

public class EventSeatVO {

    private int number;
    private int price;
    private int esid;
    private String seatName;

    public EventSeatVO(EventSeatEntity eventSeatEntity) {
        this.number = eventSeatEntity.getNumber();
        this.price = eventSeatEntity.getPrice();
        this.seatName = eventSeatEntity.getSeat().getName();
        this.esid = eventSeatEntity.getEsid();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEsid() {
        return esid;
    }

    public void setEsid(int esid) {
        this.esid = esid;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
