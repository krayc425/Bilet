package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "EventType", schema = "Bilet")
public class EventTypeEntity {
    private int etid;
    private String name;
//    private Collection<EventEntity> eventsByType;


    public EventTypeEntity() {
    }

    @Id
    @Column(name = "etid")
    public int getEtid() {
        return etid;
    }

    public void setEtid(int etid) {
        this.etid = etid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventTypeEntity that = (EventTypeEntity) o;

        if (etid != that.etid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = etid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "type", fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SUBSELECT)
//    public Collection<EventEntity> getEventsByType() {
//        return eventsByType;
//    }
//
//    public void setEventsByType(Collection<EventEntity> eventsByType) {
//        this.eventsByType = eventsByType;
//    }
}
