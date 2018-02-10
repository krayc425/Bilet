package com.krayc.vo;

import com.krayc.model.EventEntity;
import com.krayc.util.DateFormatter;

public class EventVO {

    private int eid;
    private String name;
    private String description;
    private String time;
    private String eventType;
    private Boolean canRandom;
    private Boolean hasPassed;
    private Boolean isConfirmed;

    public EventVO(EventEntity eventEntity) {
        this.eid = eventEntity.getEid();
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.eventType = eventEntity.getEventTypeEntity().getName();
        this.time = DateFormatter.getDateFormatter().stringFromDate(eventEntity.getTime());
        this.canRandom = (eventEntity.getTime().getTime() - System.currentTimeMillis()) > 14 * 24 * 60 * 60 * 1000;
        this.hasPassed = eventEntity.getTime().getTime() < System.currentTimeMillis();
        this.isConfirmed = eventEntity.getAdminBook().getIsConfirmed() == Byte.valueOf("1") && eventEntity.getVenueBook().getIsConfirmed() == Byte.valueOf("1");
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Boolean getCanRandom() {
        return canRandom;
    }

    public void setCanRandom(Boolean canRandom) {
        this.canRandom = canRandom;
    }

    public Boolean getHasPassed() {
        return hasPassed;
    }

    public void setHasPassed(Boolean hasPassed) {
        this.hasPassed = hasPassed;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }
}
