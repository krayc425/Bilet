package com.krayc.model;

import javax.persistence.*;

@Entity
@Table(name = "Seat", schema = "Bilet", catalog = "")
public class SeatEntity {
    private int sid;
    private String name;
    private int number;
    private VenueEntity venueId;

    @Id
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
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
    @Column(name = "number")
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeatEntity that = (SeatEntity) o;

        if (sid != that.sid) return false;
        if (number != that.number) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sid;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + number;
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
