package com.krayc.vo;

import com.krayc.model.EventEntity;

public class EventVO {

    private int eid;
    private String name;
    private String description;
    private String time;
    private String eventType;

    public EventVO(EventEntity eventEntity) {
        this.eid = eventEntity.getEid();
        this.name = eventEntity.getName();
        this.description = eventEntity.getDescription();
        this.eventType = eventEntity.getEventTypeEntity().getName();
        this.time = eventEntity.getTime().toString();
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
}
