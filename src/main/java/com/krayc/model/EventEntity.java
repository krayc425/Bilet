package com.krayc.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Event", schema = "Bilet")
public class EventEntity {

    private int eid;
    private String name;
    private String description;
    private Timestamp time;
    private int type;
    private VenueEntity venueId;

    public EventEntity() {
    }

    @Id
    @Column(name = "eid")
    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTime(String timeString) {
        System.out.println("Set time through String");
        this.time = Timestamp.valueOf(timeString);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventEntity that = (EventEntity) o;

        if (eid != that.eid) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "vid", referencedColumnName = "vid", nullable = false)
    public VenueEntity getVenueId() {
        return venueId;
    }

    public void setVenueId(VenueEntity venueId) {
        this.venueId = venueId;
    }
}
