package com.krayc.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Venue", schema = "Bilet")
public class VenueEntity {
    private int vid;
    private String address;
    private byte isPassed;
    private String name;
    private String password;
    private Collection<SeatEntity> seatsByVid;
    private Collection<EventEntity> eventsByVid;

    @Id
    @Column(name = "vid")
    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "isPassed")
    public byte getIsPassed() {
        return isPassed;
    }

    public void setIsPassed(byte isPassed) {
        this.isPassed = isPassed;
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
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VenueEntity that = (VenueEntity) o;

        if (vid != that.vid) return false;
        if (isPassed != that.isPassed) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vid;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (int) isPassed;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "venueId", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<SeatEntity> getSeatsByVid() {
        return seatsByVid;
    }

    public void setSeatsByVid(Collection<SeatEntity> seatsByVid) {
        this.seatsByVid = seatsByVid;
    }

    @OneToMany(mappedBy = "venueId", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    public Collection<EventEntity> getEventsByVid() {
        return eventsByVid;
    }

    public void setEventsByVid(Collection<EventEntity> eventsByVid) {
        this.eventsByVid = eventsByVid;
    }
}
