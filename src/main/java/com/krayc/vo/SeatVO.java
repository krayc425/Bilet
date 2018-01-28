package com.krayc.vo;

import com.krayc.model.SeatEntity;

public class SeatVO {
    private int sid;
    private String name;
    private int number;

    public SeatVO(SeatEntity seatEntity) {
        this.sid = seatEntity.getSid();
        this.name = seatEntity.getName();
        this.number = seatEntity.getNumber();
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
