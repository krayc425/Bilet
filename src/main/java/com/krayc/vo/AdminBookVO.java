package com.krayc.vo;

import com.krayc.model.AdminBookEntity;

public class AdminBookVO {

    private double amount;
    private String isConfirmed;
    private String venue;
    private String event;
    private int eid;

    public AdminBookVO(AdminBookEntity adminBookEntity) {
        this.amount = adminBookEntity.getAmount();
        this.venue = adminBookEntity.getVenue().getName();
        this.event = adminBookEntity.getEvent().getName();
        this.isConfirmed = adminBookEntity.getIsConfirmed() == Byte.valueOf("1") ? "已结算" : "未结算";
        this.eid = adminBookEntity.getEvent().getEid();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getIsConfirmed() {
        return isConfirmed;
    }

    public void setIsConfirmed(String isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }
}
